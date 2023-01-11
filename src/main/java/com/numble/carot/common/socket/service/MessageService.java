package com.numble.carot.common.socket.service;

import com.numble.carot.common.socket.Room;
import com.numble.carot.common.socket.dto.MessageDTO;
import com.numble.carot.common.socket.repository.MessageRepository;
import com.numble.carot.common.socket.repository.RoomRepository;
import com.numble.carot.exception.CustomException;
import com.numble.carot.exception.ErrorCode;
import com.numble.carot.model.item.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;

//    public void sendMessage(MessageDTO data){
//        String roomId = data.getRoomId();
//        Room room = findOrCreateRoom(roomId);
//    }

//    private Room findOrCreateRoom(String roomId) {
//        long id = Long.parseLong(roomId);
//        Optional<Room> room = roomRepository.findById(id);
//        if(room.isPresent()){
//            return room.get();
//        }
//        return new Room(new Item)
//    }
}
