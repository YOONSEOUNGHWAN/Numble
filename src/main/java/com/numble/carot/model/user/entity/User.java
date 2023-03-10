package com.numble.carot.model.user.entity;

//import com.numble.carot.common.socket.Room;
import com.numble.carot.model.enums.Role;
import com.numble.carot.model.BaseEntity;
import com.numble.carot.model.item.entity.Item;
import com.numble.carot.model.like.Likes;
import com.numble.carot.model.user.entity.dto.request.SignUpRequestDTO;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
//import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CRUSER")
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자 생성
@AllArgsConstructor
@DynamicUpdate //column 많으므로..
@Getter
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

    @Enumerated(EnumType.STRING)
    private Role userRole; // AOP... -> Token(User_id 정보) 식별...
//    private String location;

    //영속성 전이를 사용(Persist & Delete) //자식을 지우는 경우 -> persist concept 고아객체 삭제요망.
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> itemList = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likeList = new ArrayList<>();

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Room> roomList = new ArrayList<>();

    public void updateNickName(String nickName){
        this.nickName = nickName;
    }

    public void updateThumbnail(String url){
        this.thumbnail = url;
    }

    public void deleteThumbnail(){
        this.thumbnail = null;
    }


    public User(SignUpRequestDTO request) {
        this.name = request.getName();
        this.nickName = request.getNickName();
        this.email = request.getEmail();
        this.password = request.getPw();
        this.phoneNumber = request.getPhone();
        this.userRole = Role.USER;
    }

}
