package com.numble.carot.model.like.controller;

import com.numble.carot.model.item.entity.dto.response.ItemListInfo;
import com.numble.carot.model.item.entity.dto.response.SliceResponseDTO;
import com.numble.carot.model.like.service.LikeService;
import com.numble.carot.model.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{itemId}")
    public ResponseEntity<Boolean> likeItem(Authentication authentication, @PathVariable("itemId")Long itemId){
        User user = (User) authentication.getPrincipal();
        Boolean isLike = likeService.touchLikeButton(user.getId(), itemId);
        return ResponseEntity.ok().body(isLike);
    }

    @GetMapping("/user")
    public SliceResponseDTO<ItemListInfo> likeList(Authentication authentication, Pageable pageable){
        Object principal = authentication.getPrincipal();
        SliceResponseDTO<ItemListInfo> list = likeService.likeList((User)principal, pageable);
        return list;
    }
}
