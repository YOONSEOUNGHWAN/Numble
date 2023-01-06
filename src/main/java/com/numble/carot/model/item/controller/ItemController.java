package com.numble.carot.model.item.controller;

import com.numble.carot.enums.Category;
import com.numble.carot.model.item.entity.dto.request.CreateItemReq;
import com.numble.carot.model.item.entity.dto.response.ItemInfo;
import com.numble.carot.model.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Long> create(HttpServletRequest request, @Valid @ModelAttribute CreateItemReq data) {
        Long itemId = itemService.create(request, data);
        return ResponseEntity.ok().body(itemId);
    }

    @GetMapping("/{id}")
    public ItemInfo findOne(HttpServletRequest request, @PathVariable("id")Long id){
        ItemInfo result = itemService.findOne(request, id);
        return result;
    }

    @GetMapping("/category")
    public List<String> getCategory(){
        List<Category> collect = Stream.of(Category.values()).collect(Collectors.toList());
        List<String> data = collect.stream().map(Category::getName).collect(Collectors.toList());
        return data;
    }

//    @PatchMapping("/{id}")
//    public ItemInfo




}
