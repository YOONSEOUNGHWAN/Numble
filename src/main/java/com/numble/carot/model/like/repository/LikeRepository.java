package com.numble.carot.model.like.repository;

import com.numble.carot.model.item.entity.Item;
import com.numble.carot.model.like.Likes;
import com.numble.carot.model.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByUserAndItem(User user, Item item);

    Page<Likes> findAllByUser(User user, Pageable pageable);
}
