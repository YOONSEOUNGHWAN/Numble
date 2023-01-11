package com.numble.carot.common.socket.repository;

import com.numble.carot.common.socket.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
