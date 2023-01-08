package com.numble.carot.model.item.entity;

import com.numble.carot.common.aws.entity.S3Object;
import com.numble.carot.enums.Category;
import com.numble.carot.enums.Status;
import com.numble.carot.model.BaseEntity;
import com.numble.carot.model.item.entity.dto.request.CreateItemRequestDTO;
import com.numble.carot.model.like.Likes;
import com.numble.carot.model.user.entity.User;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Getter
@AllArgsConstructor
@Builder
/**
 * UUID 를 따뤄
 */
public class Item extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likeList = new ArrayList<>();

    private String title;

    private Integer price;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<S3Object> photoUrls = new ArrayList<>();
    private String text;

    @Enumerated(EnumType.STRING)
    private Status status;


    public Item(User user, CreateItemRequestDTO data) {
        this.user = user;
        this.title = data.getTitle();
        this.price = data.getPrice();
        this.category = Category.valueOfName(data.getCategory());
        this.text = data.getText();
        this.status = Status.ING;
    }

    public void update(CreateItemRequestDTO data) {
        this.title = data.getTitle();
        this.price = data.getPrice();
        this.category = Category.valueOfName(data.getCategory());
        this.text = data.getText();
    }

    public void updateStatus(Status status){
        this.status = status;
    }

}
