package com.numble.carot.common.jwt;

import com.numble.carot.model.user.entity.User;
import com.numble.carot.model.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomJwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    //Error Handling 어쩌지..?
    @Override
    public UserDetails loadUserByUsername(String userId) {
        User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));
        return new CustomJwtUserDetails(user);
    }

    //모든 요청마다 -> DB를 한번 타.. user 확인하려고
    //mybatis -> cache memory
    //DB X -> mybatis -> return

    //Role 검색... -> Token...확인

}
