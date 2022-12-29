package com.numble.carot.model.user.entity;

import com.numble.carot.enums.Role;
import com.numble.carot.model.BaseEntity;
import com.numble.carot.model.item.entity.Item;
import com.numble.carot.model.like.Likes;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CRUSER")
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자 생성
@DynamicUpdate //column 많으므로..
public class User extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String password;

    private String name;

    private String nickName;

    private String phoneNumber;

    private String thumbnail;
    private Role userRole;

    //영속성 전이를 사용(Persist & Delete) //자식을 지우는 경우 -> persist concept 고아객체 삭제요망.
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> itemList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likeList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public Role getUserRole() {
        return userRole;
    }
}
