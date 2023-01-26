package com.numble.carot.common.aws.service;

import com.numble.carot.common.aws.entity.FileObject;
import com.numble.carot.model.item.entity.Item;
import com.numble.carot.model.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ObjectService {

    void uploadItemFiles(List<MultipartFile> files, Item item);
    void uploadUserProfile(MultipartFile file, User user);
}
