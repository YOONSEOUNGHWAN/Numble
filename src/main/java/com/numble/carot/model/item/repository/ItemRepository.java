package com.numble.carot.model.item.repository;

import com.numble.carot.model.item.entity.Item;
import com.numble.carot.model.item.entity.dto.response.ItemListInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom{
    Page<Item> findAllByUserId(Long userId, Pageable pageable);
    @Override
    Page<Item> findAll(Pageable pageable);




}
