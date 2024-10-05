package org.goorm.everytime.board.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goorm.everytime.board.domain.Image;
import org.goorm.everytime.board.domain.Post;
import org.goorm.everytime.board.domain.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ImageService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3Client amazonS3Client;
    private final ImageRepository imageRepository;
    private final String POST_IMAGE_PATH = "post/";

    public void uploadFile(MultipartFile file, Post post) {
        String fileName = POST_IMAGE_PATH + UUID.randomUUID() + "_" + file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        try {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            imageRepository.save(Image.builder()
                    .imageUrl(amazonS3Client.getUrl(bucket, fileName).toURI().toURL().toString())
                    .post(post)
                    .build());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }

    }
}