package com.numble.carot.model.item.service;

import com.numble.carot.common.aws.entity.S3Object;
import com.numble.carot.common.aws.service.S3Service;
import com.numble.carot.common.jwt.JwtProvider;
import com.numble.carot.exception.CustomException;
import com.numble.carot.exception.ErrorCode;
import com.numble.carot.model.item.entity.Item;
import com.numble.carot.model.item.entity.dto.request.CreateItemReq;
import com.numble.carot.model.item.entity.dto.response.ItemInfo;
import com.numble.carot.model.item.repository.ItemRepository;
import com.numble.carot.model.like.Likes;
import com.numble.carot.model.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final JwtProvider jwtProvider;
    private final S3Service s3Service;

    public Long create(HttpServletRequest request, CreateItemReq data){
        User user = jwtProvider.getUser(request);
        Item item = new Item(user, data);
        s3Service.uploadItemFiles(data.getFiles(), item);
        itemRepository.save(item);
        return item.getId();

    }

    public ItemInfo findOne(HttpServletRequest request, Long id) {
        User user = jwtProvider.getUser(request);
        Item item = itemRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ITEM));
        return ItemInfo.builder()
                .itemId(item.getId())
                .createAt(item.getCreateDate())
                .photoUrls(item.getPhotoUrls().stream().map(S3Object::getUrl).collect(Collectors.toList()))
                .price(item.getPrice())
                .text(item.getText())
                .title(item.getTitle())
                .nickName(item.getUser().getNickName())
                .isLike(isUserLikeItem(user, item))
                .likeCount(item.getLikeList().size())
                .status(item.getStatus().getName())
                .category(item.getCategory().getName())
                .itemList(item.getUser().getItemList().stream().map(ItemInfo.SubItemInfo::new).collect(Collectors.toList()))
                .isMine(Objects.equals(user.getId(), item.getUser().getId()))
                .build();

    }

    private boolean isUserLikeItem(User user, Item item) {
        List<Item> collect = user.getLikeList().stream().map(Likes::getItem).collect(Collectors.toList());
        return collect.contains(item);
    }
}
