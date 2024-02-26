package com.example.jediscache.repository;

import com.example.jediscache.model.RedisHashUser;
import org.springframework.data.repository.CrudRepository;

public interface RedisHashUserRepository extends CrudRepository<RedisHashUser, Long> {

}
