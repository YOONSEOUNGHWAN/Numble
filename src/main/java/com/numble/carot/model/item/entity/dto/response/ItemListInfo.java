package com.numble.carot.model.item.entity.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.numble.carot.model.item.entity.Item;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "아이템 정보 DTO")
@Data
public class ItemListInfo {
    @Schema(description = "아이디", example = "0")
    private Long itemId;

    @Schema(description = "제목", example = "내 디카 팔아요")
    private String title;

    @Schema(description = "URL", example = "https://1.2.3.4/abc")
    private String url;

    @Schema(description = "가격", example = "12000")
    private Integer price;

    @Schema(description = "좋아요 수", example = "15")
    private Integer likeCount;

    @Schema(description = "상태", example = "판매중", allowableValues = {"판매중", "거래완료", "예약중"})
    private String status;

    @Schema(description = "업데이트 날짜", example = "2022-11-33 15:33:41")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;

    public ItemListInfo(Item item) {
        this.itemId = item.getId();
        this.title = item.getTitle();
        if (item.getPhotoUrls().isEmpty()) {
            this.url = null;
        } else {
            this.url = item.getPhotoUrls().get(0).getUrl();
        }
        this.price = item.getPrice();
        this.likeCount = item.getLikeList().size();
        this.status = item.getStatus().getName();
        this.updateDate = item.getUpdateDate();
    }

}
