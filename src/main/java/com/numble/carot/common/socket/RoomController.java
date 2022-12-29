package com.numble.carot.common.socket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class RoomController {

    private final ChatRoomRepository chatRoomRepository;

    @GetMapping(value = "/rooms")
    public String rooms() {
        log.info("# All Chat Rooms");
        List<ChatRoomDTO> allRooms = chatRoomRepository.findAllRooms();
        System.out.println("allRooms.toString() = " + allRooms.toString());
        return "ok";
    }

    @PostMapping(value = "/room")
    public String create(@RequestParam String name){
        chatRoomRepository.createChatRoomDTO(name);
        log.info("# Create Room, name = {}", name);
        return "ok";
    }

    @GetMapping("/room")
    public void getRoom(String roomId){
        log.info("# get Chat Room, roomId = {}", roomId);
        ChatRoomDTO roomById = chatRoomRepository.findRoomById(roomId);
        log.info("room = {}", roomById);
    }


}
