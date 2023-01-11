//package com.numble.carot.common.socket.config;
//
//import com.numble.carot.common.jwt.JwtProvider;
//import com.numble.carot.exception.CustomException;
//import com.numble.carot.exception.ErrorCode;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.simp.stomp.StompCommand;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.messaging.support.ChannelInterceptor;
//import org.springframework.stereotype.Component;
//
//@RequiredArgsConstructor
//@Component
//public class StompHandler implements ChannelInterceptor {
//    private final JwtProvider jwtProvider;
//
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//        if(accessor.getCommand() == StompCommand.CONNECT){
//            String token = accessor.getFirstNativeHeader("Authorization");
//            if(!jwtProvider.validateToken(token)){
//                throw new CustomException(ErrorCode.INVALID_TOKEN);
//            }
//        }
//        return message;
//    }
//}
