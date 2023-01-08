package com.numble.carot.model.item.controller;

import com.numble.carot.enums.Category;
import com.numble.carot.enums.Status;
import com.numble.carot.model.item.entity.dto.request.CreateItemRequestDTO;
import com.numble.carot.model.item.entity.dto.response.ItemInfo;
import com.numble.carot.model.item.entity.dto.response.ItemListInfo;
import com.numble.carot.model.item.entity.dto.response.SliceResponseDTO;
import com.numble.carot.model.item.service.ItemService;
import com.numble.carot.model.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<Long> create(Authentication authentication, @Valid @ModelAttribute CreateItemRequestDTO data) {
        Object principal = authentication.getPrincipal();
        Long itemId = itemService.create((User) principal, data);
        return ResponseEntity.ok().body(itemId);
    }

    @GetMapping
    public SliceResponseDTO<ItemListInfo> list(HttpServletRequest request, Pageable pageable){
        /**
         * 유저의 위치정보에 따라 보여줘야하는 Item 상이
         * todo: 위치 정보
         */
        return itemService.findAllByPageable(pageable);
    }


    @GetMapping("/{id}")
    public ItemInfo findOne(Authentication authentication, @PathVariable("id")Long id){
        Object principal = authentication.getPrincipal();
        ItemInfo result = itemService.findOne((User) principal, id);
        return result;
    }

    @GetMapping("/{id}/list")
    public SliceResponseDTO<ItemListInfo> userLIst(HttpServletRequest request, @PathVariable("id")Long userId, Pageable pageable){
        return itemService.findAllByUserId(userId, pageable);
    }

    @GetMapping("/category")
    public List<String> getCategory(){
        List<Category> collect = Stream.of(Category.values()).collect(Collectors.toList());
        List<String> data = collect.stream().map(Category::getName).collect(Collectors.toList());
        return data;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Long> updateOne(Authentication authentication, @PathVariable("id")Long id, @Valid @ModelAttribute CreateItemRequestDTO data){
        Object principal = authentication.getPrincipal();
        Long itemId = itemService.updateOne((User) principal, id, data);
        return ResponseEntity.ok().body(itemId);
    }

    /**
     * 아직 미완..
     * @param authentication
     * @param id
     * @param status
     * @return
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Long> updateOneStatus(Authentication authentication, @PathVariable("id")Long id, @Valid Status status){
        Object principal = authentication.getPrincipal();
        Long itemId = itemService.updateOneStatus((User) principal, id, status);
        return ResponseEntity.ok().body(itemId);
    }

    //Interceptor 애는 JSON 형식이 적용이 안됨.
    //Object 와 문자열은 다른 개념인 것인가..? Long 은 Object 이고..?
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOne(Authentication authentication, @PathVariable("id")Long id){
        Object principal = authentication.getPrincipal();
        itemService.delete((User) principal, id);
        return ResponseEntity.ok().body("ok");
    }

}
