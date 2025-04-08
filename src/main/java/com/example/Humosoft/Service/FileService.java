package com.example.Humosoft.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class FileService {
		public static final String fileStoragePath = "D:\\STORAGE";
		public boolean saveFile(MultipartFile file) {
			try {
				var tagetFilePath = new File(fileStoragePath + File.separator + file.getOriginalFilename());
				if (!Objects.equals(tagetFilePath.getParent(), fileStoragePath)) {
					throw new SecurityException("Invalid file path");
				}
				Files.copy(file.getInputStream(), tagetFilePath.toPath(), StandardCopyOption.REPLACE_EXISTING);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		public File downloadFile(String fileName) {
			var file = new File(fileStoragePath + File.separator + fileName);
			if (!file.exists()) {
				throw new IllegalArgumentException("File not found");
			}
			if (!Objects.equals(file.getParent(), fileStoragePath)) {
				throw new SecurityException("Invalid file path");
			}
			return file;
		}
}
