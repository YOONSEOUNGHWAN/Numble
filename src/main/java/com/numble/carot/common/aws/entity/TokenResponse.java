package com.numble.carot.common.aws.entity;

import lombok.Data;

@Data
public class TokenResponse {
    private Access access = new Access();

    public String getTokenId() {
        return access.token.id;
    }

    @Data
    public static class Access {
        private Token token = new Token();
    }

    @Data
    public static class Token {
        private String id;
    }
}
