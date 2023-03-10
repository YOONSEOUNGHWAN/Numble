package com.numble.carot.model.item.repository;

import com.numble.carot.model.item.entity.dto.response.ItemListInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<ItemListInfo> findItemPage(Pageable pageable, String query, String category, String status);

}
