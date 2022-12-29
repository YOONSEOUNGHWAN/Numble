package com.numble.carot.common.socket;

import lombok.Data;

@Data
public class ChatMessageDTO {
    private String roomId;
    private String writer;
    private String message;
}
