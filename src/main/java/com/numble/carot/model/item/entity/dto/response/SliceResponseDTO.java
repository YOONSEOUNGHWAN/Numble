package com.numble.carot.model.item.entity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;

import java.io.Serializable;
import java.util.List;

@Schema(description = "페이징 응답 DTO")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SliceResponseDTO<T> implements Serializable {

    @Schema(description = "컨텐츠")
    private List<T> content;

    @Schema(description = "다음 페이지가 있는지?", example = "true")
    private boolean hasNext;

    @Schema(description = "첫 페이지인지?", example = "true")
    private boolean isFirst;

    @Schema(description = "마지막 페이지인지?", example = "false")
    private boolean isLast;

    public SliceResponseDTO(List<T> content, Pageable pageable, boolean hasNext){
        SliceImpl<T> slice = new SliceImpl<>(content, pageable, hasNext);
        this.content = slice.getContent();
        this.hasNext = slice.hasNext();
        this.isFirst = slice.isFirst();
        this.isLast = slice.isLast();
    }
}
