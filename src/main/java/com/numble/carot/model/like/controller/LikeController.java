package com.numble.carot.model.like.controller;

import com.numble.carot.common.swagger.SwaggerConfig;
import com.numble.carot.model.item.entity.dto.response.ItemListInfo;
import com.numble.carot.model.item.entity.dto.response.SliceResponseDTO;
import com.numble.carot.model.like.service.LikeService;
import com.numble.carot.model.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Like", description = "좋아요 API")
@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @Operation(summary = "좋아요", description = "좋아요 표시하는 API")
    @SecurityRequirement(name = SwaggerConfig.AUTHENTICATION)
    @PostMapping("/{itemId}")
    public ResponseEntity<Boolean> likeItem(Authentication authentication, @PathVariable("itemId") Long itemId){
        User user = (User) authentication.getPrincipal();
        Boolean isLike = likeService.touchLikeButton(user.getId(), itemId);
        return ResponseEntity.ok().body(isLike);
    }

    @Operation(summary = "좋아요 목록 가져오기", description = "'좋아요'를 준 상품 목록들을 가져오는 API")
    @SecurityRequirement(name = SwaggerConfig.AUTHENTICATION)
    @GetMapping("/user")
    public SliceResponseDTO<ItemListInfo> likeList(Authentication authentication, @ParameterObject Pageable pageable){
        Object principal = authentication.getPrincipal();
        SliceResponseDTO<ItemListInfo> list = likeService.likeList((User)principal, pageable);
        return list;
    }
}
