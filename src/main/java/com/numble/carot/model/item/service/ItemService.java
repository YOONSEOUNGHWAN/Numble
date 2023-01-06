package com.numble.carot.model.item.service;

import com.numble.carot.common.aws.entity.S3Object;
import com.numble.carot.common.aws.service.S3Service;
import com.numble.carot.common.jwt.JwtProvider;
import com.numble.carot.enums.Status;
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
        /**
         * Item 의 Id를 얻어오고 싶은데, 아직 DB에 적용 전이라 Id 값이 주어지지 않음.
         * 논리 상 현재 순서가 맞다고 생각하기에,, 해결 방법이 없을까 고민 중.
         */
        s3Service.uploadItemFiles(data.getFiles(), item);
        Item save = itemRepository.save(item);
        return save.getId();

    }

    public ItemInfo findOne(HttpServletRequest request, Long id) {
        User user = jwtProvider.getUser(request);
        Item item = itemRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ITEM));
        return getItemInfo(user, item);

    }

    public Long updateOne(HttpServletRequest request, Long id, CreateItemReq data) {
        User user = jwtProvider.getUser(request);
        Item item = itemRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ITEM));
        checkUserAndItem(user, item);
        /**
         * 질문하기 -> Builder 로 Setter 역할을 대신할 수 있나..?
         */
//        Item build = item.builder()
//                .text(data.getText()).build();
        //기존에 fileName을 들고 있음.. 들어오는 것과 중복 체크 하면 되려나..?
        //중복 체크 어려움 1.시간 2.UUID 둘 다 제거할 수 없음.
        //중복 체크 -> fileOriginName 으로..?
        s3Service.uploadItemFiles(data.getFiles(), item);
        item.update(data);
        //변경 감지.
        Item save = itemRepository.save(item);
        return save.getId();
    }

    public Long updateOneStatus(HttpServletRequest request, Long id, Status status) {
        User user = jwtProvider.getUser(request);
        Item item = itemRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ITEM));
        checkUserAndItem(user, item);
        item.updateStatus(status);
        //변경감지로 안 해도 되긴 함.
        Item save = itemRepository.save(item);
        return save.getId();
    }

    private boolean isUserLikeItem(User user, Item item) {
        List<Item> collect = user.getLikeList().stream().map(Likes::getItem).collect(Collectors.toList());
        return collect.contains(item);
    }

    private ItemInfo getItemInfo(User user, Item item) {
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
                .isMine(user.getId().equals(item.getUser().getId()))
                .build();
    }

    public void delete(HttpServletRequest request, Long id) {
        User user = jwtProvider.getUser(request);
        Item item = itemRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ITEM));
        checkUserAndItem(user, item);
        itemRepository.delete(item);
    }

    /**
     * front 에서도 검사 요망.
     * @param user
     * @param item
     */
    private void checkUserAndItem(User user, Item item){
        if(!user.getId().equals(item.getUser().getId())){
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }
}
