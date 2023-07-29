package com.example.apigatewayservice.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    public <T> Optional<T> getValues(String key, Class<T> cls) {
        String result = String.valueOf(redisTemplate.opsForValue().get(key));
        if (!StringUtils.hasText(result)) {
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(objectMapper.readValue(result, cls));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("redis Json 변환 실패");
        }
    }

    public void setValues(String key, Object value) throws JsonProcessingException {
        String valueStr = objectMapper.writeValueAsString(value);
        redisTemplate.opsForValue().set(key, valueStr);
    }

    public void setSets(String key, Object... values) throws JsonProcessingException {
        String valueStr = objectMapper.writeValueAsString(values);
        redisTemplate.opsForSet().add(key, valueStr);
    }

    public Set getSets(String key) {
        return redisTemplate.opsForSet().members(key);
    }

}
