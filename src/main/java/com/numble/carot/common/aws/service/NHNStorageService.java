package com.numble.carot.common.aws.service;

import com.numble.carot.common.aws.entity.FileObject;
import com.numble.carot.common.aws.service.nhn.NHNAuthService;
import com.numble.carot.common.aws.service.nhn.ObjectStorageService;
import com.numble.carot.model.item.entity.Item;
import com.numble.carot.model.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NHNStorageService implements ObjectService{
    private final NHNAuthService nhnAuthService;
    private final ObjectStorageService objectStorageService;
    @Override
    public void uploadItemFiles(List<MultipartFile> files, Item item) {
        final String prefix = "item-";

        String token = nhnAuthService.requestToken();
        log.info("NHN upload 시작");
        List<FileObject> photoUrls = item.getPhotoUrls();
        files.stream().filter(file -> !checkDuplicateFileName(photoUrls, file.getOriginalFilename()))
                .map(file -> uploadFile(token, file, prefix))
                .forEach(fileName -> {
                    FileObject fileObject = FileObject.builder()
                            .item(item)
                            .url(objectStorageService.getObjectURL(prefix + fileName))
                            .fileName(fileName)
                            .build();
                    photoUrls.add(fileObject);
                });
        //중복체크
        log.info("upload 끝");
    }


    @Override
    public void uploadUserProfile(MultipartFile file, User user) {
        final String prefix = "profile-";
        log.info("NHN upload 시작");
        String token = nhnAuthService.requestToken();
        String fileName = uploadFile(token, file, prefix);
        user.updateThumbnail(objectStorageService.getObjectURL(prefix + fileName));
        log.info("upload 끝");
    }

    private String uploadFile(String token, MultipartFile file, String prefix)  {
        try {
            objectStorageService.uploadObject(token, prefix + file.getOriginalFilename(), file.getInputStream());
            return file.getOriginalFilename();
        } catch (IOException e) {
            log.info("파일 저장 실패!");
            throw new RuntimeException(e);
        }
    }

    private boolean checkDuplicateFileName(List<FileObject> photoUrls, String originalFilename) {
        List<String> collect = photoUrls.stream().map(FileObject::getFileName).collect(Collectors.toList());
        return collect.contains(originalFilename);
    }


}
