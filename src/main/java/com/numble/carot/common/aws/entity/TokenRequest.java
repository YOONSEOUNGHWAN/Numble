package com.numble.carot.common.aws.entity;

import lombok.Data;

@Data
public
class TokenRequest {
    private Auth auth = new Auth();

    @Data
    public static class Auth {
        private String tenantId;
        private PasswordCredentials passwordCredentials = new PasswordCredentials();
    }

    @Data
    public static class PasswordCredentials {
        private String username;
        private String password;
    }
}
