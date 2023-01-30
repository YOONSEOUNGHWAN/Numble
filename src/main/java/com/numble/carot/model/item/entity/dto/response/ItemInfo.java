package com.numble.carot.model.item.entity.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.numble.carot.common.aws.entity.FileObject;
import com.numble.carot.model.item.entity.Item;
import com.numble.carot.model.like.Likes;
import com.numble.carot.model.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JPQL -> Entity 만들어서 넣기 : 한방쿼리~~ JOIN
 */
@Schema(description = "아이템 정보 DTO")
@Data
public class ItemInfo {
    @Schema(description = "생성한 사용자 정보")
    private ItemUserInfo createUser;

    @Schema(description = "상품 Id", example = "1")
    private Long itemId;

    @Schema(description = "상품 사진 URL 목록", example = "[\"https://1.2.3.4/1.png\", \"https://1.2.3.4/2.png\"]")
    private List<String> photoUrls;

    @Schema(description = "상품 사진 이름 목록", example = "[\"1.png\", \"2.png\"]")
    private List<String> photoNames;

    @Schema(description = "판매 상태", example = "판매중", allowableValues = {"판매중", "거래완료", "예약중"})
    private String status;

    @Schema(description = "제목", example = "당근 팔아요")
    private String title;

    @Schema(description = "본문", example = "오늘 하루만 팔게요")
    private String text;

    @Schema(description = "가격", example = "12500")
    private Integer price;

    @Schema(description = "좋아요 수", example = "21")
    private Integer likeCount;

    @Schema(description = "카테고리", example = "식품")
    private String category;

    @Schema(description = "좋아요를 눌렀는지?", example = "false")
    private boolean isLike;

    @Schema(description = "내가 올린건지?", example = "false")
    private boolean isMine;

    @Schema(description = "하위 상품 목록")
    private List<SubItemInfo> itemList;

    @Schema(description = "업데이트 날짜", example = "2022-01-02 15:11:12")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;


    @Schema(description = "상품 판매자 정보")
    @Data
    public static class ItemUserInfo{

        @Schema(description = "판매자 id", example = "1")
        private Long userId;

        @Schema(description = "닉네임", example = "seoung59")
        private String nickName;

        @Schema(description = "프로필 사진 URL", example = "https://1.2.3.4/profile.png")
        private String thumbnail;

        public ItemUserInfo(User user){
            this.userId = user.getId();
            this.nickName = user.getNickName();
            this.thumbnail = user.getThumbnail();
        }
    }

    @Schema(description = "하위 상품 정보")
    @Data
    public static class SubItemInfo{

        @Schema(description = "상품 id", example = "1")
        private Long itemId;

        @Schema(description = "URL", example = "https://1.2.3.4/item/2")
        private String url;

        @Schema(description = "제목", example = "하위 상품")
        private String title;

        @Schema(description = "가격", example = "12900")
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
        this.photoUrls = item.getPhotoUrls().stream().map(FileObject::getUrl).collect(Collectors.toList());
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
