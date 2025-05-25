package com.example.Booking_Resort.config;

import com.example.Booking_Resort.dto.response.AuthenticationRespone;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User
{
    OAuth2User _oAuth2User;
    AuthenticationRespone _authResponse;

    public CustomOAuth2User(OAuth2User oAuth2User, AuthenticationRespone authResponse) {
        _oAuth2User = oAuth2User;
        _authResponse = authResponse;
    }

    public AuthenticationRespone getAuthResponse() {
        return _authResponse;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return _oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return _oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return _oAuth2User.getName();
    }
}
