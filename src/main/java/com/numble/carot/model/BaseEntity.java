package com.numble.carot.model;

import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass //Entity로 사용 X -> 추상화
@EntityListeners(AuditingEntityListener.class)
@Getter
//@DynamicUpdate -> 데이터가 적음
public abstract class BaseEntity {
    @CreatedDate
    @Column(updatable = false, name = "create_at")
    private DateTime createDate;

    @LastModifiedDate
    @Column(name = "update_at")
    private DateTime updateDate;
}
