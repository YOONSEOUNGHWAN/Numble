package com.numble.carot.model.item.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "상품 정보 요청 DTO")
@Data
public class CreateItemRequestDTO {
    @Schema(description = "상품 사진들")
    private List<MultipartFile> files = new ArrayList<>();

    @Schema(description = "제목", example = "내 디카 팔아요")
    @NotBlank(message = "제목을 작성해주세요")
    private String title;

    @Schema(description = "카테고리", example = "전자기기")
    @NotBlank(message = "카테고리를 골라주세요")
    private String category;

    @Schema(description = "가격", example = "12000")
    @NotNull(message = "가격을 입력해주세요") //NotBlank, NotEmpty -> 형식 검사 함.
    private Integer price;

    @Schema(description = "본문", example = "살 분들만 연락바랍니다.")
    @NotBlank(message = "본문을 작성해주세요")
    private String text;
}
