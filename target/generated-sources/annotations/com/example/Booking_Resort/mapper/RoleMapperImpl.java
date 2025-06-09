package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.RoleRequest;
import com.example.Booking_Resort.dto.response.PermissionRespone;
import com.example.Booking_Resort.dto.response.RoleResponse;
import com.example.Booking_Resort.models.Permissions;
import com.example.Booking_Resort.models.Roles;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-10T00:41:06+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Oracle Corporation)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public Roles toRole(RoleRequest request) {
        if ( request == null ) {
            return null;
        }

        Roles.RolesBuilder roles = Roles.builder();

        roles.name( request.getName() );
        roles.description( request.getDescription() );

        return roles.build();
    }

    @Override
    public RoleResponse toRoleRespone(Roles role) {
        if ( role == null ) {
            return null;
        }

        RoleResponse.RoleResponseBuilder roleResponse = RoleResponse.builder();

        roleResponse.name( role.getName() );
        roleResponse.description( role.getDescription() );
        roleResponse.permissions( permissionsSetToPermissionResponeSet( role.getPermissions() ) );

        return roleResponse.build();
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
}
