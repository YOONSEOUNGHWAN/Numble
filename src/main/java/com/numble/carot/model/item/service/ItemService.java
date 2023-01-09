package com.numble.carot.model.item.service;

import com.numble.carot.common.aws.service.S3Service;
import com.numble.carot.model.enums.Status;
import com.numble.carot.exception.CustomException;
import com.numble.carot.exception.ErrorCode;
import com.numble.carot.model.item.entity.Item;
import com.numble.carot.model.item.entity.dto.request.CreateItemRequestDTO;
import com.numble.carot.model.item.entity.dto.response.ItemInfo;
import com.numble.carot.model.item.entity.dto.response.ItemListInfo;
import com.numble.carot.model.item.entity.dto.response.SliceResponseDTO;
import com.numble.carot.model.item.repository.ItemRepository;
import com.numble.carot.model.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final S3Service s3Service;

    public Long create(User user, CreateItemRequestDTO data){
        Item item = new Item(user, data);
        /**
         * todo: UUID
         * Item 의 Id를 얻어오고 싶은데, 아직 DB에 적용 전이라 Id 값이 주어지지 않음.
         * 논리 상 현재 순서가 맞다고 생각하기에,, 해결 방법이 없을까 고민 중.
         * UUID - 생성.
         */
        s3Service.uploadItemFiles(data.getFiles(), item);
        Item save = itemRepository.save(item);
        return save.getId();

    }

    public ItemInfo findOne(User user, Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ITEM));
        return new ItemInfo(user, item);
    }

    public Long updateOne(User user, Long id, CreateItemRequestDTO data) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ITEM));
        isCreate(user, item);
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

    public Long updateOneStatus(User user, Long id, String status) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ITEM));
        isCreate(user, item);
        item.updateStatus(Status.valueOfName(status));
        //변경감지로 안 해도 되긴 함.
        Item save = itemRepository.save(item);
        return save.getId();
    }

    public void delete(User user, Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ITEM));
        isCreate(user, item);
        itemRepository.delete(item);
    }
    private void isCreate(User user, Item item){
        if(!user.getId().equals(item.getUser().getId())){
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    /**
     * todo : Qeury DSL 적용하기.
     * @param pageable
     * @param query
     * @param category
     * @param status
     * @return
     */
    public SliceResponseDTO<ItemListInfo> findAllByPageable(Pageable pageable, String query, String category, String status) {
        Slice<Item> all = itemRepository.findAll(pageable);
        Slice<ItemListInfo> map = all.map(ItemListInfo::new);
        return new SliceResponseDTO<>(map.getContent(), map.getPageable(), map.hasNext());
    }

    public SliceResponseDTO<ItemListInfo> findAllByUserId(Long userId, Pageable pageable) {
        Slice<Item> all = itemRepository.findAllByUserId(userId, pageable);
        Slice<ItemListInfo> map = all.map(ItemListInfo::new);
        return new SliceResponseDTO<>(map.getContent(), map.getPageable(), map.hasNext());
    }
}
