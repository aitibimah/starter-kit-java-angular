package com.yourcompany.project.controller;

import com.yourcompany.project.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/files")
@Tag(name = "File", description = "File management APIs")
public class FileController {

    private final FileStorageService fileStorageService;

    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    @Operation(summary = "Upload a file", description = "Uploads a file and returns its filename")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String filename = fileStorageService.store(file);
            return ResponseEntity.ok(filename);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to upload file");
        }
    }

    @GetMapping("/files")
    @Operation(summary = "List all files", description = "Returns a list of all uploaded files")
    public ResponseEntity<List<String>> listFiles() {
        List<String> files = fileStorageService.loadAll()
                .map(path -> MvcUriComponentsBuilder
                        .fromMethodName(FileController.class, "getFile", path.getFileName().toString())
                        .build().toString())
                .collect(Collectors.toList());
        return ResponseEntity.ok(files);
    }

    @GetMapping("/files/{filename:.+}")
    @Operation(summary = "Download a file", description = "Downloads a file by its filename")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Resource file = new UrlResource(fileStorageService.load(filename).toUri());
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } catch (MalformedURLException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/files/{filename:.+}")
    @Operation(summary = "Delete a file", description = "Deletes a file by its filename")
    public ResponseEntity<Void> deleteFile(@PathVariable String filename) {
        fileStorageService.delete(filename);
        return ResponseEntity.noContent().build();
    }
}