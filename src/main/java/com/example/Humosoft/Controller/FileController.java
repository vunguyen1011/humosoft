package com.example.Humosoft.Controller;

import com.example.Humosoft.Service.FileService;

import lombok.RequiredArgsConstructor;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {
	private final  FileService fileService;
	@GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            // Gọi phương thức downloadFile từ FileService
            File file = fileService.downloadFile(fileName);

            // Tạo Resource từ file
            Resource resource = new FileSystemResource(file);

            // Thiết lập response headers
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"")
                    .body(resource);
        } catch (IllegalArgumentException | SecurityException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
