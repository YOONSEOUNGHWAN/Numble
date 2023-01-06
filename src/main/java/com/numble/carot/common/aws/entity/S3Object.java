package com.numble.carot.common.aws.entity;

import com.numble.carot.model.item.entity.Item;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class S3Object {
    @Id
    @GeneratedValue
    @Column(name = "objcet_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;

    //URL 길이가 256byte 넘어감..
    @Lob
    private String url;

    private String fileName;

}
