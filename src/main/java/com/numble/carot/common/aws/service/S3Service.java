package com.numble.carot.common.aws.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.numble.carot.common.aws.entity.S3Object;
import com.numble.carot.model.item.entity.Item;
import com.numble.carot.model.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class S3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public void uploadItemFiles(List<MultipartFile> files, Item item){
        log.info("S3 upload 시작");
        List<S3Object> photoUrls = item.getPhotoUrls();
        String prefix = DateTime.now() + "-" + item.getUser().getId() + "-" + item.getId() + "-";
        files.stream()
                .forEach(file ->{
                    String fileName = uploadFile(file, prefix, "item");
                    S3Object s3Object = S3Object.builder()
                            .url(amazonS3.getUrl(bucket, fileName).toString())
                            .fileName(fileName)
                            .build();
                    photoUrls.add(s3Object);
                });
        log.info("upload 끝");
    }

    public void uploadUserProfile(MultipartFile file, User user){
        log.info("S3 upload 시작");
        String prefix = DateTime.now() + "-" + user.getId() + "-";
        String fileName = uploadFile(file, prefix, "profile");
        user.updateThumbnail(getUrl(fileName));
        log.info("upload 끝");
    }


    private String uploadFile(MultipartFile file, String prefix, String dirName) {
        String fileName = dirName + "/" + prefix + file.getOriginalFilename() + "-" + UUID.randomUUID();
        try{
            InputStream is = file.getInputStream();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(is.available());
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, is, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
            return fileName;
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getUrl(String fileName){
        return amazonS3.getUrl(bucket, fileName).toString();
    }
}
