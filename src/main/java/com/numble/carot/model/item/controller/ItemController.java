package com.numble.carot.model.item.controller;

import com.numble.carot.model.enums.Category;
import com.numble.carot.model.enums.Status;
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
        User user = (User) authentication.getPrincipal();
        Long itemId = itemService.create(user, data);
        return ResponseEntity.ok().body(itemId);
    }

    @GetMapping
    public SliceResponseDTO<ItemListInfo> list(Pageable pageable,
                                               @RequestParam(value = "query", required = false)String query,
                                               @RequestParam(value = "category", required = false)String category,
                                               @RequestParam(value = "status", required = false)String status){
        return itemService.findAllByPageable(pageable, query, category, status);
    }


    @GetMapping("/{itemId}")
    public ItemInfo findOne(Authentication authentication, @PathVariable("itemId")Long itemId){
        User user = (User) authentication.getPrincipal();
        ItemInfo result = itemService.findOne(user, itemId);
        return result;
    }

    @GetMapping("/{userId}/list")
    public SliceResponseDTO<ItemListInfo> userLIst(@PathVariable("userId")Long userId, Pageable pageable){
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

    @PatchMapping("/{id}/status")
    public ResponseEntity<Long> updateOneStatus(Authentication authentication, @PathVariable("id")Long id, @RequestParam String status){
        Object principal = authentication.getPrincipal();
        Long itemId = itemService.updateOneStatus((User) principal, id, status);
        return ResponseEntity.ok().body(itemId);
    }

    //Interceptor ?????? JSON ????????? ????????? ??????.
    //Object ??? ???????????? ?????? ????????? ?????????..? Long ??? Object ??????..?
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOne(Authentication authentication, @PathVariable("id")Long id){
        Object principal = authentication.getPrincipal();
        itemService.delete((User) principal, id);
        return ResponseEntity.ok().body("ok");
    }

}
