package com.tenten.outsourcing.bucket.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class Bucket {

    private Long userId;

    private Long storeId;

    private Long menuId;

    private int count;

    /**
     * JsonString을 Bucket 목록으로 변환
     */
    public static List<Bucket> jsonStringToBuckets(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.registerModule(new JavaTimeModule()).readValue(jsonString, new TypeReference<List<Bucket>>() {
        });
    }

    /**
     * Bucket 목록을 JsonString으로 변환
     */
    public static String BucketToJsonString(List<Bucket> bucket) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.registerModule(new JavaTimeModule()).writeValueAsString(bucket);
    }

    public void updateCount(int change) {
        this.count += change;
    }
}
