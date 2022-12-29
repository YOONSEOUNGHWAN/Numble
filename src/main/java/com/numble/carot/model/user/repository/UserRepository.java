package com.numble.carot.model.user.repository;

import com.numble.carot.model.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
