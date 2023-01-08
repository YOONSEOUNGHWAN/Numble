package com.numble.carot.model.item.entity.dto.response;

import com.numble.carot.model.item.entity.Item;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * JPQL -> Entity 만들어서 넣기 : 한방쿼리~~ JOIN
 */
@Data
@Builder
public class ItemInfo {
    private Long itemId;
    private List<String> photoUrls;
    private String nickName;
    private String status;
    private String title;
    private String category;
    private LocalDateTime createAt;
    private String text;
    private Integer likeCount;
    private List<SubItemInfo> itemList;
    private boolean isLike;
    private Integer price;
    private boolean isMine;

    @Data
    public static class SubItemInfo{
        private Long itemId;
        private String url;
        private String title;
        private Integer price;

        public SubItemInfo(Item item){
            this.itemId = item.getId();
            this.url = item.getPhotoUrls().get(0).getUrl();
            this.title = item.getTitle();
            this.price = item.getPrice();
        }
    }
}
