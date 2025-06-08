package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.PermissionRequest;
import com.example.Booking_Resort.dto.response.PermissionRespone;
import com.example.Booking_Resort.models.Permissions;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-08T22:39:23+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Oracle Corporation)"
)
@Component
public class PermissionMapperImpl implements PermissionMapper {

    @Override
    public Permissions toPermissions(PermissionRequest request) {
        if ( request == null ) {
            return null;
        }

        Permissions.PermissionsBuilder permissions = Permissions.builder();

        permissions.name( request.getName() );
        permissions.description( request.getDescription() );

        return permissions.build();
    }

    @Override
    public PermissionRespone toPermissionRespone(Permissions permissions) {
        if ( permissions == null ) {
            return null;
        }

        PermissionRespone.PermissionResponeBuilder permissionRespone = PermissionRespone.builder();

        permissionRespone.name( permissions.getName() );
        permissionRespone.description( permissions.getDescription() );

        return permissionRespone.build();
    }
}
