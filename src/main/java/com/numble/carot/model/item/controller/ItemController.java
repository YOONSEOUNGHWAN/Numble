package com.numble.carot.model.item.controller;

import com.numble.carot.common.swagger.SwaggerConfig;
import com.numble.carot.model.enums.Category;
import com.numble.carot.model.item.entity.dto.request.CreateItemRequestDTO;
import com.numble.carot.model.item.entity.dto.response.ItemInfo;
import com.numble.carot.model.item.entity.dto.response.ItemListInfo;
import com.numble.carot.model.item.entity.dto.response.SliceResponseDTO;
import com.numble.carot.model.item.service.ItemService;
import com.numble.carot.model.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Tag(name = "Item", description = "상품 관련 API")
@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @Operation(summary = "상품 등록", description = "상품을 등록하는 API")
    @SecurityRequirement(name = SwaggerConfig.AUTHENTICATION)
    @PostMapping
    public ResponseEntity<Long> create(Authentication authentication, @Valid @ModelAttribute CreateItemRequestDTO data) {
        User user = (User) authentication.getPrincipal();
        Long itemId = itemService.create(user, data);
        return ResponseEntity.ok().body(itemId);
    }

    @Operation(summary = "상품 목록 조회", description = "상품 목록 조회 API. 샘플이므로 아직 검색 부분은 구현되어있지 않습니다.")
    @GetMapping
    public SliceResponseDTO<ItemListInfo> list(@ParameterObject Pageable pageable,
                                               @Parameter(description = "검색 키워드") @RequestParam(value = "query", required = false) String query,
                                               @Parameter(description = "검색 카테고리") @RequestParam(value = "category", required = false) String category,
                                               @Parameter(description = "상품 판매 상태 키워드") @RequestParam(value = "status", required = false) String status) {
        return itemService.findAllByPageable(pageable, query, category, status);
    }

    @Operation(summary = "특정 상품 검색", description = "itemId로 특정 상품을 검색하는 API")
    @SecurityRequirement(name = SwaggerConfig.AUTHENTICATION)
    @GetMapping("/{itemId}")
    public ItemInfo findOne(Authentication authentication, @PathVariable("itemId") Long itemId) {
        User user = (User) authentication.getPrincipal();
        return itemService.findOne(user, itemId);
    }

    @Operation(summary = "판매자의 판매 상품들 조회", description = "userId로 특정 판매자의 판매 상품 목록들을 조회하는 API")
    @GetMapping("/{userId}/list")
    public SliceResponseDTO<ItemListInfo> userLIst(@PathVariable("userId") Long userId, @ParameterObject Pageable pageable) {
        return itemService.findAllByUserId(userId, pageable);
    }

    @Operation(summary = "카테고리 조회", description = "사용가능한 상품 카테고리 목록 조회")
    @GetMapping("/category")
    public List<String> getCategory() {
        List<Category> collect = Stream.of(Category.values()).collect(Collectors.toList());
        List<String> data = collect.stream().map(Category::getName).collect(Collectors.toList());
        return data;
    }

    @Operation(summary = "상품 정보 수정", description = "상품 정보를 수정하는 API")
    @SecurityRequirement(name = SwaggerConfig.AUTHENTICATION)
    @PatchMapping("/{id}")
    public ResponseEntity<Long> updateOne(Authentication authentication,
                                          @Parameter(description = "상품 ID") @PathVariable("id") Long id,
                                          @Valid @ModelAttribute CreateItemRequestDTO data) {
        Object principal = authentication.getPrincipal();
        Long itemId = itemService.updateOne((User) principal, id, data);
        return ResponseEntity.ok().body(itemId);
    }

    @Operation(summary = "상품 판매 상태 수정", description = "상품의 판매 상태를 수정하는 API")
    @SecurityRequirement(name = SwaggerConfig.AUTHENTICATION)
    @PatchMapping("/{id}/status")
    public ResponseEntity<Long> updateOneStatus(Authentication authentication,
                                                @Parameter(description = "상품 ID") @PathVariable("id") Long id,
                                                @Parameter(description = "판매 상태") @RequestParam String status) {
        Object principal = authentication.getPrincipal();
        Long itemId = itemService.updateOneStatus((User) principal, id, status);
        return ResponseEntity.ok().body(itemId);
    }

    //Interceptor 애는 JSON 형식이 적용이 안됨.
    //Object 와 문자열은 다른 개념인 것인가..? Long 은 Object 이고..?
    @Operation(summary = "상품 삭제", description = "상품 id로 특정 상품을 삭제하는 API")
    @SecurityRequirement(name = SwaggerConfig.AUTHENTICATION)
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOne(Authentication authentication,
                                            @Parameter(description = "상품 ID") @PathVariable("id") Long id) {
        Object principal = authentication.getPrincipal();
        itemService.delete((User) principal, id);
        return ResponseEntity.ok().body("ok");
    }

}
