package com.numble.carot.model.item.entity.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreateItemRequestDTO {
    private List<MultipartFile> files = new ArrayList<>();
    @NotBlank(message = "제목을 작성해주세요")
    private String title;
    @NotBlank(message = "카테고리를 골라주세요")
    private String category;
    @NotNull(message = "가격을 입력해주세요") //NotBlank, NotEmpty -> 형식 검사 함.
    private Integer price;
    @NotBlank(message = "본문을 작성해주세요")
    private String text;
}
