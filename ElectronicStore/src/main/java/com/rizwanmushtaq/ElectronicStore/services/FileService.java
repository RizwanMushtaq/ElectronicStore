package com.rizwanmushtaq.ElectronicStore.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileService {
  String uploadImage(MultipartFile file, String path);

  InputStream getResource(String path, String fileName);
}
