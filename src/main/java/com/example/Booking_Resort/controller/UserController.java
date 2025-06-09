package com.example.Booking_Resort.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.example.Booking_Resort.config.CustomOAuth2User;
import com.example.Booking_Resort.dto.request.UserCreationRequest;
import com.example.Booking_Resort.dto.request.UserUpdateRequest;
import com.example.Booking_Resort.dto.response.ApiRespone;
import com.example.Booking_Resort.dto.response.AuthenticationRespone;
import com.example.Booking_Resort.dto.response.UserRespone;
import com.example.Booking_Resort.service.AuthenticationService;
import com.example.Booking_Resort.service.UserService;
import com.example.Booking_Resort.models.User;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/user")
public class UserController {

    UserService userService;
    AuthenticationService authenticationService;

    // Endpoint lấy danh sách người dùng
    @GetMapping("/list_user")
    public ApiRespone<List<UserRespone>> getAllUser()
    {
        return ApiRespone.<List<UserRespone>>builder()
                .data(userService.getAllUsers())
                .build();
    }

    // Endpoint lấy thông tin cá nhân
    @GetMapping("/inf")
    public ApiRespone<UserRespone> getMyInf()
    {
        return ApiRespone.<UserRespone>builder()
                .message("Success")
                .data(userService.getMyInf())
                .build();
    }

    // Endpoint tìm thông tin người dùng qua ID
    @GetMapping("/{userID}")
    public ApiRespone<UserRespone> getUserById(@PathVariable ("userID")String userID)
    {
        return ApiRespone.<UserRespone>builder()
                .message("User Search Successfully")
                .data(userService.getUserById(userID))
                .build();
    }

    // Endpoint tạo người dùng
    @PostMapping(value = "/create_user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiRespone<UserRespone> createUser( @RequestPart("request") UserCreationRequest request,
                                               @RequestPart(value = "file", required = false) MultipartFile file)
    {
        request.setAvatar(file);
        return ApiRespone.<UserRespone>builder()
                .message("successful creation")
                .data(userService.saveUser(request))
                .build();
    }

    // Endpoint thay đổi thông tin người dùng
    @PutMapping("/{userID}")
    public ApiRespone<UserRespone> updateUser(@PathVariable ("userID")String userID,
                                              @RequestPart("request") UserUpdateRequest request,
                                              @RequestPart(value = "file", required = false) MultipartFile file)
    {
        request.setAvatar(file);
        return ApiRespone.<UserRespone>builder()
                .message("User successfully updated")
                .data(userService.updateUser(userID, request))
                .build();
    }

    // Endpoint xóa người dùng qua ID
    @DeleteMapping("/delete/{userID}")
    public ApiRespone<String> deleteUser(@PathVariable ("userID")String userID)
    {
        userService.deleteUser(userID);
        return ApiRespone.<String>builder()
                .message("Successful deletion")
                .build();
    }

    @GetMapping("/infUserGoogle")
    public ResponseEntity<?> getUserInfo(Principal principal) {
        if (principal instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) principal;

            if (oauthToken.getPrincipal() instanceof CustomOAuth2User) {
                CustomOAuth2User customOAuth2User = (CustomOAuth2User) oauthToken.getPrincipal();
                AuthenticationRespone authResp = customOAuth2User.getAuthResponse();

                return ResponseEntity.ok(authResp);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap("error", "Not authenticated with OAuth2"));
    }

    @PostMapping("/google-signin")
    public ResponseEntity<AuthenticationRespone> googleSignIn(@RequestBody String idTokenString) {
        try {
            // 1. Verify the ID token with Google
            // You'll need to set up GoogleIdTokenVerifier with your client ID
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList("137525615923-pkjhv8r88q9givj66mcfpg605p3oolef.apps.googleusercontent.com")) // Replace with your actual Web Client ID
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                String email = payload.getEmail();
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");

                // 2. Process the user in your system (find or create)
                User user = userService.processOAuth2User(email, name, pictureUrl); // Re-use your existing logic

                // 3. Generate your own JWT and Refresh Token
                String appToken = authenticationService.generateToken(user);
                String appRefreshToken = authenticationService.generateRefreshToken(user);

                AuthenticationRespone authResponse = AuthenticationRespone.builder()
                        .idUser(user.getIdUser())
                        .token(appToken)
                        .refreshToken(appRefreshToken)
                        .authenticated(true)
                        .build();

                return ResponseEntity.ok(authResponse);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Invalid ID token
            }
        } catch (GeneralSecurityException | IOException e) {
            // Handle token verification exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
