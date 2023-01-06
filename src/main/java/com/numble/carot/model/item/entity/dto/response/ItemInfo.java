package com.numble.carot.model.item.entity.dto.response;

import com.numble.carot.common.aws.entity.S3Object;
import com.numble.carot.enums.Category;
import com.numble.carot.enums.Status;
import com.numble.carot.model.item.entity.Item;
import lombok.Builder;
import lombok.Data;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ItemInfo {
    private Long itemId;
    private List<S3Object> photoUrls;
    private String nickName;
    private Status status;
    private String title;
    private Category category;
    private DateTime createAt;
    private String text;
    private Integer likeCount;
    private List<SubItemInfo> itemList;
    private boolean isLike;
    private Integer price;

    public static class SubItemInfo{
        private Long itemId;
        private S3Object url;
        private String title;
        private Integer price;

        public SubItemInfo(Item item){
            this.itemId = item.getId();
            this.url = item.getPhotoUrls().get(0);
            this.title = item.getTitle();
            this.price = item.getPrice();
        }
    }
}
