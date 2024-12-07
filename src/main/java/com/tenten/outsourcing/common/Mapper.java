package com.tenten.outsourcing.common;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tenten.outsourcing.order.entity.Bucket;

public class Mapper {

    /**
     * JsonString을 Bucket으로 변환
     */
    public static Bucket jsonStringToBucket(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(jsonString, Bucket.class);
    }

    /**
     * Bucket을 JsonString으로 변환
     */
    public static String BucketToJsonString(Bucket bucket) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.registerModule(new JavaTimeModule()).writeValueAsString(bucket);
    }
}
