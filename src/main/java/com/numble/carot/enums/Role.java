package com.numble.carot.enums;

public enum Role {
    USER("USER"), GUEST("GUEST"), ADMIN("USER,ADMIN");

    private final String role;

    Role(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
