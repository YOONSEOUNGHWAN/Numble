package com.numble.carot.model.item.entity.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SliceRes<T> implements Serializable {
    private List<T> content;
    private boolean hasNext;
    private boolean isFirst;
    private boolean isLast;

    public SliceRes(List<T> content, Pageable pageable, boolean hasNext){
        SliceImpl<T> slice = new SliceImpl<>(content, pageable, hasNext);
        this.content = slice.getContent();
        this.hasNext = slice.hasNext();
        this.isFirst = slice.isFirst();
        this.isLast = slice.isLast();
    }
}
