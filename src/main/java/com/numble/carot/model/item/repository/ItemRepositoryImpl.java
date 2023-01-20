package com.numble.carot.model.item.repository;

import com.numble.carot.model.enums.Category;
import com.numble.carot.model.enums.Status;
import com.numble.carot.model.item.entity.dto.response.ItemListInfo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.numble.carot.model.item.entity.QItem.*;
import static com.numble.carot.model.like.QLikes.*;

public class ItemRepositoryImpl implements ItemRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ItemRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ItemListInfo> findItemPage(String query, String category, String status, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        if(query != null){
            builder.and(item.title.contains(query)).or(item.text.contains(query));
        }
        if(status != null){
            builder.and(item.status.eq(Status.valueOfName(status)));
        }
        if(category != null){
            builder.and(item.category.eq(Category.valueOfName(category)));
        }

        List<ItemListInfo> content = queryFactory
                .select(Projections.fields(ItemListInfo.class,
                        item.title,
                        item.photoUrls.as("url"),
                        item.price,
                        likes.count().as("likeCount"),
                        item.status,
                        item.updateDate))
                .from(item)
                .leftJoin(item.likeList, likes)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = content.size();

        return new PageImpl<>(content, pageable, total);
    }
}
