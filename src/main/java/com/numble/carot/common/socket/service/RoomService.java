package com.numble.carot.common.socket.service;

import com.numble.carot.common.socket.repository.RoomRepository;
import com.numble.carot.model.item.repository.ItemRepository;
import com.numble.carot.model.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {
    private final RoomRepository roomRepository;
    private final ItemRepository itemRepository;

//    public Long create(User user, Long itemId) {
//        //1 대 1 방 개설. -> Item 별로..
//
//    }
}
