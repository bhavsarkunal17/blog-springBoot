package com.project.blog.services.Impl;

import com.project.blog.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
            String fileName = file.getOriginalFilename();
            String uploadPath = path+ File.separator+fileName;
            File folderPath = new File(path);
            if(!folderPath.exists()){
                folderPath.mkdir();
            }
            Files.copy(file.getInputStream(), Path.of(uploadPath));
            return fileName;
    }

    @Override
    public InputStream getFile(String path, String fileName) throws FileNotFoundException {
        String filePath = path+File.separator+fileName;
        return new FileInputStream(filePath);
    }
}
