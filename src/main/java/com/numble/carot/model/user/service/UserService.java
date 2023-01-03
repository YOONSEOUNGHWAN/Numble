package com.numble.carot.model.user.service;

import com.numble.carot.model.user.entity.User;

public interface UserService {

    void signIn();

    User findByUserId();



}
