package com.numble.carot.model.item.controller;

import com.numble.carot.model.item.entity.dto.request.CreateItemReq;
import com.numble.carot.model.item.entity.dto.response.ItemInfo;
import com.numble.carot.model.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemInfo create(HttpServletRequest request, @Valid @RequestBody CreateItemReq data){
        return itemService.create(request, data);
    }

}
