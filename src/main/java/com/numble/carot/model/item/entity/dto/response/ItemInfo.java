package com.numble.carot.model.item.entity.dto.response;

import com.numble.carot.common.aws.entity.S3Object;
import com.numble.carot.model.item.entity.Item;
import com.numble.carot.model.like.Likes;
import com.numble.carot.model.user.entity.User;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JPQL -> Entity 만들어서 넣기 : 한방쿼리~~ JOIN
 */
@Data
public class ItemInfo {
    private ItemUserInfo createUser;
    private Long itemId;
    private List<String> photoUrls;
    private String status;
    private String title;
    private String text;
    private Integer price;
    private Integer likeCount;
    private String category;
    private boolean isLike;
    private boolean isMine;
    private List<SubItemInfo> itemList;
    private LocalDateTime updatedAt;

    @Data
    public static class ItemUserInfo{
        private Long userId;
        private String nickName;
        private String thumbnail;
        public ItemUserInfo(User user){
            this.userId = user.getId();
            this.nickName = user.getNickName();
            this.thumbnail = user.getThumbnail();
        }
    }

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
    public ItemInfo(User user, Item item){
        this.createUser = new ItemUserInfo(item.getUser());
        this.itemId = item.getId();
        this.photoUrls = item.getPhotoUrls().stream().map(S3Object::getUrl).collect(Collectors.toList());
        this.status = item.getStatus().getName();
        this.title = item.getTitle();
        this.text = item.getText();
        this.price = item.getPrice();
        this.likeCount = item.getLikeList().size();
        this.category = item.getCategory().getName();
        this.isLike = isUserLikeItem(user, item);
        this.isMine = item.getUser().getId().equals(user.getId());
        this.itemList =item.getUser().getItemList().stream().map(SubItemInfo::new).collect(Collectors.toList());
        this.updatedAt = item.getUpdateDate();
    }

    private boolean isUserLikeItem(User user, Item item) {
        List<Long> collect = item.getLikeList().stream().map(Likes::getUser).map(User::getId).collect(Collectors.toList());
        return collect.contains(user.getId());
    }
}
