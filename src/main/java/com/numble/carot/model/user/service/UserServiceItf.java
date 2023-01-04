package com.numble.carot.model.user.service;

import com.numble.carot.model.user.entity.User;

/**
 * Interface 를 구현하려 했지만.. @Bean 등록이 번거롭기에 @Service 를 사용하기 위해서 구현체를 직접 생성.
 */
public interface UserServiceItf {

    void signIn();

    User findByUserId();


}
