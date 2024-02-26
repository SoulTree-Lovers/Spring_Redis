package com.example.jediscache.service;

import com.example.jediscache.model.User;
import com.example.jediscache.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RedisTemplate<String, User> userRedisTemplate;
    private final RedisTemplate<String, Object> objectRedisTemplate;

    public User getUser(final Long id) {
        final String key = "users:%d".formatted(id); // key 설정

        // 1. 캐시에서 가져오기
        var cachedUser = objectRedisTemplate.opsForValue().get(key);
        if (cachedUser != null) {
            return (User)cachedUser;
        }

        // 2. 캐시에 없으면 DB에서 가져오고, 캐시에 저장하기
        User dbUser = userRepository.findById(id).orElseThrow();
        objectRedisTemplate.opsForValue().set(key, dbUser, Duration.ofSeconds(30));
        return dbUser;
    }
}
