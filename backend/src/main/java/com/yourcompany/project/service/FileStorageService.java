package com.yourcompany.project.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileStorageService {
    void init();

    String store(MultipartFile file) throws IOException;

    Stream<Path> loadAll();

    Path load(String filename);

    void delete(String filename);

    void deleteAll();
}