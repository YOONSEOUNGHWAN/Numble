package com.numble.carot.model.item.entity.dto.response;

import com.numble.carot.model.item.entity.Item;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemListInfo {
    private String title;
    private String url;
    private Integer price;
    private Integer likeCount;
    private String status;
    private LocalDateTime updateDate;

    public ItemListInfo(Item item){
        this.title = item.getTitle();
        this.url = item.getPhotoUrls().get(0).getUrl();
        this.price = item.getPrice();
        this.likeCount = item.getLikeList().size();
        this.status = item.getStatus().getName();
        this.updateDate = item.getUpdateDate();
    }

}
