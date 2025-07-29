package com.rizwanmushtaq.ElectronicStore.services.implementations;

import com.rizwanmushtaq.ElectronicStore.exceptions.BadApiRequest;
import com.rizwanmushtaq.ElectronicStore.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
  private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

  @Override
  public String uploadImage(MultipartFile file, String path) {
    String originalFilename = file.getOriginalFilename();
    logger.info("Uploading file: " + originalFilename);
    String fileName = UUID.randomUUID().toString();
    String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
    String fileNameWithExtension = fileName + fileExtension;
    String fullPathWithFileName = path + File.separator + fileNameWithExtension;
    if (fileExtension.equalsIgnoreCase(".png") || fileExtension.equalsIgnoreCase(".jpg") ||
        fileExtension.equalsIgnoreCase(".jpeg")) {
      // Save the file to the specified path
      try {
        File folder = new File(path);
        if (!folder.exists()) {
          folder.mkdirs();
        }
        Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
        return fileNameWithExtension;
      } catch (Exception e) {
        throw new BadApiRequest("Error while saving file: " + e.getMessage());
      }
    } else {
      throw new BadApiRequest("File with this extension is not allowed: " + fileExtension);
    }
  }

  @Override
  public InputStream getResource(String path, String fileName) {
    try {
      String fullPath = path + File.separator + fileName;
      InputStream inputStream = new FileInputStream(fullPath);
      return inputStream;
    } catch (Exception e) {
      throw new BadApiRequest("Error while accessing file: " + e.getMessage());
    }
  }
}
