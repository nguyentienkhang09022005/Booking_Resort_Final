package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.UserCreationRequest;
import com.example.Booking_Resort.dto.request.UserUpdateRequest;
import com.example.Booking_Resort.dto.response.PermissionRespone;
import com.example.Booking_Resort.dto.response.RoleResponse;
import com.example.Booking_Resort.dto.response.UserRespone;
import com.example.Booking_Resort.models.Permissions;
import com.example.Booking_Resort.models.Roles;
import com.example.Booking_Resort.models.User;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-15T11:06:53+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserRespone toUserRespone(User user) {
        if ( user == null ) {
            return null;
        }

        UserRespone.UserResponeBuilder userRespone = UserRespone.builder();

        userRespone.nameuser( user.getNameuser() );
        userRespone.sex( user.getSex() );
        userRespone.phone( user.getPhone() );
        userRespone.email( user.getEmail() );
        userRespone.identificationCard( user.getIdentificationCard() );
        userRespone.dob( user.getDob() );
        userRespone.passport( user.getPassport() );
        userRespone.account( user.getAccount() );
        userRespone.role_user( rolesSetToRoleResponseSet( user.getRole_user() ) );

        return userRespone.build();
    }

    @Override
    public User toUser(UserCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.nameuser( request.getNameuser() );
        user.sex( request.getSex() );
        user.phone( request.getPhone() );
        user.email( request.getEmail() );
        user.identificationCard( request.getIdentificationCard() );
        user.dob( request.getDob() );
        user.passport( request.getPassport() );
        user.account( request.getAccount() );
        user.passworduser( request.getPassworduser() );

        return user.build();
    }

    @Override
    public void updateUser(User user, UserUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        user.setNameuser( request.getNameuser() );
        user.setSex( request.getSex() );
        user.setPhone( request.getPhone() );
        user.setEmail( request.getEmail() );
        user.setIdentificationCard( request.getIdentificationCard() );
        user.setDob( request.getDob() );
        user.setPassport( request.getPassport() );
        user.setPassworduser( request.getPassworduser() );
    }

    protected PermissionRespone permissionsToPermissionRespone(Permissions permissions) {
        if ( permissions == null ) {
            return null;
        }

        PermissionRespone.PermissionResponeBuilder permissionRespone = PermissionRespone.builder();

        permissionRespone.name( permissions.getName() );
        permissionRespone.description( permissions.getDescription() );

        return permissionRespone.build();
    }

    protected Set<PermissionRespone> permissionsSetToPermissionResponeSet(Set<Permissions> set) {
        if ( set == null ) {
            return null;
        }

        Set<PermissionRespone> set1 = new LinkedHashSet<PermissionRespone>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Permissions permissions : set ) {
            set1.add( permissionsToPermissionRespone( permissions ) );
        }

        return set1;
    }

    protected RoleResponse rolesToRoleResponse(Roles roles) {
        if ( roles == null ) {
            return null;
        }

        RoleResponse.RoleResponseBuilder roleResponse = RoleResponse.builder();

        roleResponse.name( roles.getName() );
        roleResponse.description( roles.getDescription() );
        roleResponse.permissions( permissionsSetToPermissionResponeSet( roles.getPermissions() ) );

        return roleResponse.build();
    }

    protected Set<RoleResponse> rolesSetToRoleResponseSet(Set<Roles> set) {
        if ( set == null ) {
            return null;
        }

        Set<RoleResponse> set1 = new LinkedHashSet<RoleResponse>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Roles roles : set ) {
            set1.add( rolesToRoleResponse( roles ) );
        }

        return set1;
    }
}
