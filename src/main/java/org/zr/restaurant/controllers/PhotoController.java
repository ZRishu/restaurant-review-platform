package org.zr.restaurant.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zr.restaurant.domain.dtos.PhotoDto;
import org.zr.restaurant.domain.entities.Photo;
import org.zr.restaurant.mappers.PhotoMapper;
import org.zr.restaurant.services.PhotoService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/photos")
public class PhotoController {

    private final PhotoService photoService;
    private final PhotoMapper photoMapper;

    @PostMapping
    public PhotoDto uploadPhoto(@RequestParam("file")MultipartFile file) {
        Photo savedPhoto = photoService.uploadPhoto(file);
        return photoMapper.toDto(savedPhoto);
    }

    @GetMapping(path = "/{filename:.+}")
    public ResponseEntity<Resource> getPhoto(@PathVariable String filename) {
        return photoService.getPhotoAsResource(filename).map(photo ->
                ResponseEntity.ok()
                        .contentType(
                                MediaTypeFactory.getMediaType(photo)
                                        .orElse(MediaType.APPLICATION_OCTET_STREAM)
                        )
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                        .body(photo)
        ).orElse(ResponseEntity.notFound().build());
    }
}
