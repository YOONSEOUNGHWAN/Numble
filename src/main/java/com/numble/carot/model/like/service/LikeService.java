package com.numble.carot.model.like.service;

import com.numble.carot.model.item.entity.Item;
import com.numble.carot.model.item.entity.dto.response.ItemListInfo;
import com.numble.carot.model.item.entity.dto.response.SliceResponseDTO;
import com.numble.carot.model.item.repository.ItemRepository;
import com.numble.carot.model.like.Likes;
import com.numble.carot.model.like.repository.LikeRepository;
import com.numble.carot.model.user.entity.User;
import com.numble.carot.model.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public Boolean touchLikeButton(Long userId, Long itemId) {
        Item item = itemRepository.findById(itemId).get();
        User user = userRepository.findById(userId).get();
        return touchButton(user, item);
    }
    private boolean touchButton(User user, Item item){
        Optional<Likes> byUserAndItem = likeRepository.findByUserAndItem(user, item);
        //좋아요가 존재하는 경우.
        if(byUserAndItem.isPresent()){
            Likes likes = byUserAndItem.get();
            likeRepository.delete(likes);
            return false;
        }else{
            Likes likes = new Likes(user, item);
            likeRepository.save(likes);
            return true;
        }
    }

    public SliceResponseDTO<ItemListInfo> likeList(User user, Pageable pageable) {
        Slice<Likes> allByUser = likeRepository.findAllByUser(user, pageable);
        Slice<Item> itemSlice = allByUser.map(Likes::getItem);
        Slice<ItemListInfo> map = itemSlice.map(ItemListInfo::new);
        return new SliceResponseDTO<>(map.getContent(), map.getPageable(), map.hasNext());
    }
}
