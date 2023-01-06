package com.numble.carot.model.item.entity.dto.request;

import com.numble.carot.enums.Category;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateItemReq {
    private List<MultipartFile> files = new ArrayList<>();
    private String title;
    private String category;
    private Integer price;
    private String text;
}
