package com.orbitalchemistry.controller;

import com.orbitalchemistry.service.StorageService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/** Admin only: everything under /api/admin/** is protected in SecurityConfig. */
@RestController
public class UploadController {

    private final StorageService storage;
    public UploadController(StorageService storage) { this.storage = storage; }

    @PostMapping(value = "/api/admin/uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, String> upload(@RequestParam("file") MultipartFile file) {
        return Map.of("url", storage.saveImage(file));
    }
}
