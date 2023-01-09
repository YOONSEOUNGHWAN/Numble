package com.numble.carot.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    USER("USER"), GUEST("GUEST"), ADMIN("USER,ADMIN");

    private final String role;
}
