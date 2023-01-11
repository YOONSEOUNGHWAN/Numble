package com.numble.carot.common.socket.repository;

import com.numble.carot.common.socket.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
