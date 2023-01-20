package com.numble.carot.common.jwt;

import com.numble.carot.exception.CustomException;
import com.numble.carot.exception.ErrorCode;
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
    @Override
    public CustomJwtUserDetails loadUserByUsername(String userId) {
        User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return new CustomJwtUserDetails(user);
    }
}
