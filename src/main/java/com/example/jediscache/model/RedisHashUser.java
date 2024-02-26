package com.example.jediscache.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@RedisHash(value = "redishash-user", timeToLive = 30L)
public class RedisHashUser {

    @Id
    private Long id;

    private String name;

    @Indexed
    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
