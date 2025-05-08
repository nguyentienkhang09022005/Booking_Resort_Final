package com.example.Booking_Resort.service;

import com.example.Booking_Resort.dto.request.AuthenticationRequest;
import com.example.Booking_Resort.dto.request.IntrospectRequest;
import com.example.Booking_Resort.dto.request.LogoutRequest;
import com.example.Booking_Resort.dto.request.RefreshRequest;
import com.example.Booking_Resort.dto.response.AuthenticationRespone;
import com.example.Booking_Resort.dto.response.IntrospectResponse;
import com.example.Booking_Resort.exception.ErrorCode;
import com.example.Booking_Resort.exception.ApiException;
import com.example.Booking_Resort.models.InvalidatedToken;
import com.example.Booking_Resort.models.User;
import com.example.Booking_Resort.repository.InvalidatedTokenRepository;
import com.example.Booking_Resort.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService
{
    UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected Long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected Long REFRESHABLE_DURATION;

    // Hàm xác thực token
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();

        boolean isValid = true;
        try {
            verifyToken(token, false);
        }catch (ApiException e){
            isValid = false;
        }
        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    // Hàm xác thực đăng nhập
    public AuthenticationRespone authenticated(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        var user = userRepository
                .findByAccount(request.getUsername())
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassworduser());

        if (!authenticated) throw new ApiException(ErrorCode.UNAUTHENTICATED);

        var token = generateToken(user);
        var refreshToken = generateRefreshToken(user);

        return AuthenticationRespone.builder()
                .token(token)
                .refreshToken(refreshToken)
                .authenticated(true)
                .build();
    }


    // Hàm tạo ACCESS TOKEN
    private String generateToken(User user)
    {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder() // Nội dung của Payload
                .subject(user.getAccount())
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();


        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create TOKE", e);
            throw new RuntimeException(e);
        }
    }

    // Hàm tạo REFRESH TOKEN
    private String generateRefreshToken(User user)
    {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder() // Nội dung của Payload
                .subject(user.getAccount())
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();


        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create TOKE", e);
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user)
    {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (!CollectionUtils.isEmpty(user.getRole_user()))
            user.getRole_user().forEach(roles -> {
                stringJoiner.add("ROLE_" + roles.getName());
                if(!CollectionUtils.isEmpty(roles.getPermissions()))
                    roles.getPermissions()
                        .forEach(permissions -> stringJoiner.add(permissions.getName()));
            });
        return stringJoiner.toString();
    }

    // Hàm logout TOKEN
    public void Logout (LogoutRequest request) throws ParseException, JOSEException {
        try {
            var signToken = verifyToken(request.getToken(), true);
            String jit = signToken.getJWTClaimsSet().getJWTID();

            Date expirationTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jit)
                    .expiryTime(expirationTime)
                    .build();

            invalidatedTokenRepository.save(invalidatedToken);
        }catch (ApiException e){
            log.error("Cannot verify token", e);
        }
    }

    // Hàm Refresh TOKEN
    public AuthenticationRespone reFreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signToken = verifyToken(request.getToken(), true);

        var account = signToken.getJWTClaimsSet().getSubject();

        var user = userRepository.findByAccount(account).orElseThrow(
                () -> new ApiException(ErrorCode.USER_NOT_FOUND));

        var token = generateToken(user);

        return AuthenticationRespone.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token); // Chuyển chuỗi JWT thành đối tượng SignedJWT

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT
                .getJWTClaimsSet()
                .getIssueTime()
                .toInstant()
                .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime(); // Lấy thời gian hết hạn của TOKEN

        var verified = signedJWT.verify(verifier); // Kiểm tra chữ ký của TOKEN với SIGNER_KEY có giống nhau hay không
        if(!(verified && expiryTime.after(new Date())))
            throw new ApiException(ErrorCode.UNAUTHENTICATED);

        if(invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new ApiException(ErrorCode.UNAUTHENTICATED);

        return SignedJWT.parse(token);
    }
}
