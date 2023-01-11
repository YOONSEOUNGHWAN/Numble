//package com.numble.carot.common.socket.controller;
//
//import com.numble.carot.common.socket.dto.MessageDTO;
//import com.numble.carot.common.socket.service.RoomService;
//import com.numble.carot.model.user.entity.User;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//public class MessageController {
//    private final SimpMessagingTemplate messagingTemplate;
//    private final RoomService roomService;
//
////    @MessageMapping("/chat/message") // /pub/chat/message
////    public void chat(MessageDTO message){
////        messagingTemplate.convertAndSend("/sub/" + message.getRoomId() + message.getMessage());
////    }
//
////    @PostMapping("/chat/room/{itemId}") // 방 생성..
////    public ResponseEntity<Long> createRoom(Authentication authentication, @PathVariable("itemId")Long itemId){
////        Object principal = authentication.getPrincipal();
////        Long roomId = roomService.create((User)principal, itemId);
////        return ResponseEntity.ok().body(roomId);
////    }
//
//    @GetMapping("/chat/room")
//    public void getRoomList(){
//
//    }
//
//}
