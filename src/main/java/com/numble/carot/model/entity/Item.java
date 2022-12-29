package com.numble.carot.model.entity;

import com.numble.carot.model.enums.Category;
import com.numble.carot.model.enums.Status;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Item extends BaseEntity {
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

    private String photoUrl;

    private String location;

    private String text;

    @Enumerated(EnumType.STRING)
    private Status status;

}
