package com.example.Booking_Resort.impl;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
public interface UploadImageFile
{
    String uploadImage(MultipartFile file) throws IOException;
}
