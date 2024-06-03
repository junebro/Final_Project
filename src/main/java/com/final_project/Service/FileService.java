package com.final_project.Service;

import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service // 이미지에 대한 업로드(복사)/삭제 등을 위한 유틸리티 서비스 클래스
public class FileService {
    @Value("${image.upload.dir}") // application.properties에서 설정된 이미지 저장 경로
    private String imageUploadDir;
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    // 이미지 파일 업로드
    public String uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }

        // 파일 이름 생성 (현재 시간을 이용하여 충돌 방지)
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path destinationPath = Paths.get(imageUploadDir).resolve(filename).normalize().toAbsolutePath();

        // 디렉토리가 존재하지 않는 경우 생성
        if (!Files.exists(destinationPath.getParent())) {
            Files.createDirectories(destinationPath.getParent());
        }

        // 파일 저장
        file.transferTo(destinationPath);
        logger.info("File uploaded to: {}", destinationPath);

        return filename; // 저장된 파일의 이름을 반환
    }

    // 썸네일 파일 업로드
    public String uploadThumbnailFile(String filename) throws IOException {
        String thumbnailFilename = "thumb_" + filename;
        Path destinationPath = Paths.get(imageUploadDir).resolve(filename).normalize().toAbsolutePath();
        Path thumbnailPath = Paths.get(imageUploadDir).resolve(thumbnailFilename).normalize().toAbsolutePath();

        if (!Files.exists(destinationPath)) {
            throw new IOException("Could not find file: " + destinationPath);
        }

        Thumbnails.of(destinationPath.toFile())
                .size(150, 150)
                .toFile(thumbnailPath.toFile());

        logger.info("Thumbnail created at: {}", thumbnailPath);

        return thumbnailFilename;
    }



    // 이미지 파일 삭제
    public boolean deleteFile(String filename) {
        Path path = Paths.get(imageUploadDir).resolve(filename).normalize().toAbsolutePath();
        File file = path.toFile();
        if (file.exists()) {
            return file.delete(); // 파일 존재 시 삭제
        }
        return false; // 파일이 존재하지 않으면 false 반환
    }
}
