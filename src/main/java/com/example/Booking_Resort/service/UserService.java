package com.example.Booking_Resort.service;

import com.example.Booking_Resort.constant.PredefinedRole;
import com.example.Booking_Resort.dto.request.UserCreationRequest;
import com.example.Booking_Resort.dto.request.UserUpdateRequest;
import com.example.Booking_Resort.dto.response.UserRespone;
import com.example.Booking_Resort.exception.ErrorCode;
import com.example.Booking_Resort.exception.ApiException;
import com.example.Booking_Resort.impl.UploadImageFile;
import com.example.Booking_Resort.mapper.UserMapper;
import com.example.Booking_Resort.models.Image;
import com.example.Booking_Resort.models.Roles;
import com.example.Booking_Resort.models.User;
import com.example.Booking_Resort.repository.ImageRepository;
import com.example.Booking_Resort.repository.RoleRepository;
import com.example.Booking_Resort.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService
{
    UserRepository userRepository;
    UserMapper userMapper;
    ImageRepository imageRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    UploadImageFile uploadImageFile;


    // Hàm lấy danh sách người dùng
    public List<UserRespone> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> {
            Image image = imageRepository.findFirstByIdUser_IdUser(user.getIdUser());

            return UserRespone.builder()
                    .idUser(user.getIdUser())
                    .nameuser(user.getNameuser())
                    .sex(user.getSex())
                    .phone(user.getPhone())
                    .email(user.getEmail())
                    .identificationCard(user.getIdentificationCard())
                    .dob(user.getDob())
                    .passport(user.getPassport())
                    .account(user.getAccount())
                    .avatar(image != null ? image.getUrl() : null)
                    .build();
        }).collect(Collectors.toList());
    }

    // Hàm tìm user thông qua id
    public UserRespone getUserById(String id)
    {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserRespone(user);
    }

    // Hàm lấy thông tin cá nhân
    public UserRespone getMyInf()
    {
        var context = SecurityContextHolder.getContext();
        String account = context.getAuthentication().getName();
        User user = userRepository.findByAccount(account).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND)
        );
        return userMapper.toUserRespone(user);
    }

    // Hàm lưu người dùng xuống csdl
    public UserRespone saveUser(UserCreationRequest request)
    {
        if (userRepository.existsByEmail(request.getEmail()))
        {
            throw new ApiException(ErrorCode.USER_EXISTS);
        }

        if(userRepository.existsByAccount(request.getAccount()))
        {
            throw new ApiException(ErrorCode.USER_EXISTS);
        }

        User user = userMapper.toUser(request);
        String encodedPassword = passwordEncoder.encode(user.getPassworduser());
        user.setPassworduser(encodedPassword);

        HashSet<Roles> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
        user.setRole_user(roles);

        try {
            userRepository.save(user);

        }catch (DataIntegrityViolationException e)
        {
            throw new ApiException(ErrorCode.USER_EXISTS);
        }

        String avatarUrl = null;
        if (request.getAvatar() != null && !request.getAvatar().isEmpty()) {
            try {
                avatarUrl = uploadImageFile.uploadImage(request.getAvatar());

                Image image = new Image();
                image.setIdUser(user);
                image.setUrl(avatarUrl);

                imageRepository.save(image);
            } catch (IOException e) {
                log.error("Upload avatar failed", e);
                throw new ApiException(ErrorCode.UPLOAD_FAILED);
            }
        }

        UserRespone userRespone =  userMapper.toUserRespone(user);
        userRespone.setAvatar(avatarUrl);
        return userRespone;
    }

    // Hàm thay đổi thông tin người dùng
    @Transactional
    public UserRespone updateUser(String id, UserUpdateRequest request)
    {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorCode.USER_NOT_FOUND));

        userMapper.updateUser(user, request);

        user.setPassworduser(passwordEncoder.encode(request.getPassworduser()));

        var role = roleRepository.findAllById(request.getRole_user()); // Phải luôn có trong JSON vì là ignore

        user.setRole_user(new HashSet<>(role));


        String avatarUrl = null;
        if (request.getAvatar() != null && !request.getAvatar().isEmpty()) {
            try {
                imageRepository.deleteByIdUser(user);// Xóa hình ảnh cũ của user

                avatarUrl = uploadImageFile.uploadImage(request.getAvatar());

                Image image = new Image();
                image.setIdUser(user);
                image.setUrl(avatarUrl);

                imageRepository.save(image);
            } catch (IOException e) {
                log.error("Upload avatar failed", e);
                throw new ApiException(ErrorCode.UPLOAD_FAILED);
            }
        }

        UserRespone userRespone =  userMapper.toUserRespone(userRepository.save(user));
        userRespone.setAvatar(avatarUrl);
        return userRespone;
    }

    // Hàm xóa người dùng
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUser(String id)
    {
        userRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorCode.USER_NOT_FOUND));
        userRepository.deleteById(id);
    }

    // Hàm lưu người dùng đăng nhập bằng Google xuống csdl
    @Transactional
    public User processOAuth2User(String email, String name, String urlAvatar)
    {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent())
        {
            return user.get();
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setNameuser(name);

        HashSet<Roles> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
        newUser.setRole_user(roles);

        User savedUser = userRepository.save(newUser);

        // Lưu ảnh đại diện
        Image image = new Image();
        image.setIdUser(newUser);
        image.setUrl(urlAvatar);
        imageRepository.save(image);

        return savedUser;
    }
}
