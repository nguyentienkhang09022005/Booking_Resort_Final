package com.example.Booking_Resort.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import com.example.Booking_Resort.config.CustomOAuth2User;
import com.example.Booking_Resort.dto.request.UserCreationRequest;
import com.example.Booking_Resort.dto.request.UserUpdateRequest;
import com.example.Booking_Resort.dto.response.ApiRespone;
import com.example.Booking_Resort.dto.response.AuthenticationRespone;
import com.example.Booking_Resort.dto.response.UserRespone;
import com.example.Booking_Resort.service.AuthenticationService;
import com.example.Booking_Resort.service.UserService;
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
}
