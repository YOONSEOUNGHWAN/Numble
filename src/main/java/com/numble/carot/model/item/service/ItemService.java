package com.numble.carot.model.item.service;

import com.numble.carot.common.aws.service.S3Service;
import com.numble.carot.common.jwt.JwtProvider;
import com.numble.carot.model.item.entity.Item;
import com.numble.carot.model.item.entity.dto.request.CreateItemReq;
import com.numble.carot.model.item.entity.dto.response.ItemInfo;
import com.numble.carot.model.item.repository.ItemRepository;
import com.numble.carot.model.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.lang.module.FindException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final JwtProvider jwtProvider;
    private final S3Service s3Service;

    public ItemInfo create(HttpServletRequest request, CreateItemReq data) {
        User user = jwtProvider.getUser(request);
        //item 생성
        Item item = new Item(user, data);
        //fileUpload
        s3Service.uploadItemFiles(data.getFiles(), item);
        ItemInfo ret = ItemInfo.builder()
                .itemId(item.getId())
                .createAt(item.getCreateDate())
                .photoUrls(item.getPhotoUrls())
                .price(item.getPrice())
                .itemList(user.getItemList().stream().map(i -> new ItemInfo.SubItemInfo(item)).collect(Collectors.toList()))
                .text(item.getText())
                .title(item.getTitle())
                .isLike(false)
                .category(item.getCategory())
                .status(item.getStatus())
                .likeCount(0)
                .nickName(user.getNickName())
                .build();
        itemRepository.save(item);
        return ret;
    }
}
