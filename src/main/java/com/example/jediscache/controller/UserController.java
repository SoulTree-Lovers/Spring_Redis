package com.example.jediscache.controller;

import com.example.jediscache.model.RedisHashUser;
import com.example.jediscache.model.User;
import com.example.jediscache.repository.UserRepository;
import com.example.jediscache.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JedisPool jedisPool;

    @GetMapping("/")
    public Map<String, String> home(
        HttpSession session
    ) {
        Integer visitCount = (Integer) session.getAttribute("visits");
        if (visitCount == null) {
            visitCount = 0;
        }
        session.setAttribute("visits", ++visitCount);
        return Map.of("session id", session.getId(), "visits", visitCount.toString());
    }


    @GetMapping("/users/{id}")
    public User getUser(
        @PathVariable Long id
    ) {
        return userService.getUser(id);
    }

    @GetMapping("/redishash-users/{id}")
    public RedisHashUser getRedisHashUser(
        @PathVariable Long id
    ) {
        return userService.getRedisHashUser(id);
    }

    @GetMapping("/cache-users/{id}")
    public User getCacheUser(
        @PathVariable Long id
    ) {
        return userService.getCacheUser(id);
    }

    @GetMapping("/users/{id}/email")
    public String getUserEmail(
            @PathVariable Long id
    ) {
        try (Jedis jedis = jedisPool.getResource()) {
            final var userEmailRedisKey = "users:%d:email".formatted(id);

            // 1. 캐시에서 먼저 데이터를 찾는다.
            String userEmail = jedis.get(userEmailRedisKey);

            if (userEmail != null) {
                return userEmail;
            }

            // 2. 캐시에 없으면 DB에서 가져온다.
            userEmail = userRepository.findById(id).orElse(User.builder().build()).getEmail();

            // 3. 캐시에 저장하고 응답한다.
            jedis.set(userEmailRedisKey, userEmail);
            jedis.setex(userEmailRedisKey, 30, userEmail); // 30초 동안만 유지
            return userEmail;
        }
    }
}
