package com.htphy.wx.common.util.netutils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.MapType;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;

/**
 * jackson json工具类
 *
 * @author lw
 */
@Slf4j
public class JacksonUtils {

    public static Map<String, Object> linkedHashMapToObj(Map<String, Object> factObjects) {
        Map<String, Object> factObjectsResult = new IdentityHashMap<String, Object>();
        try {
            for (Map.Entry<String, Object> factObject : factObjects.entrySet()) {
                String factName = factObject.getKey();
                Class factObjectClass = Class.forName(factName);
                Object fact = factObject.getValue();

                ObjectMapper mapper = new ObjectMapper();
                Object object = mapper.convertValue(fact, factObjectClass);
                factObjectsResult.put(factName, object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return factObjectsResult;
    }

    static ObjectMapper mapper = new ObjectMapper();

    static {
        SimpleModule module = new SimpleModule();
        mapper.registerModule(module);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

    public static String toJsonString(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toJsonString(ObjectMapper customMapper, Object o) throws IOException {
        return customMapper.writeValueAsString(o);
    }

    public static JsonNode toJsonNode(Object object) {
        JsonNode rootNode = mapper.valueToTree(object);
        return rootNode;
    }

    public static JsonNode toJsonNode(ObjectMapper customMapper, Object object) {
        JsonNode rootNode = customMapper.valueToTree(object);
        return rootNode;
    }

    public static <T> T fromJsonString(String s, Class<T> clazz) throws IOException {
        return mapper.readValue(s, clazz);
    }

    public static Object fromJsonString(String s, TypeReference<?> typeReference)
        throws IOException {
        return mapper.readValue(s, typeReference);
    }

    public static JavaType constructCollectionType(Class<? extends Collection> collectionClass, Class<?> elementClasses) {
        return mapper.getTypeFactory().constructCollectionType(collectionClass, elementClasses);
    }

    public static JavaType constructParametricType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public static JavaType constructType(Class<?> elementClasses) {
        return mapper.getTypeFactory().constructType(elementClasses);
    }

    public static Object deserializeCollection(String s, JavaType type) throws IOException {
        return mapper.readValue(s, type);
    }

    public static <T> List<T> deserializeList(String s, Class<T> clazz) {
        JavaType javaType = constructCollectionType(List.class, clazz);
        try {
            return (List<T>) deserializeCollection(s, javaType);
        } catch (IOException e) {
            log.warn("Json反序列化失败");
            e.printStackTrace();
            return null;
        }
    }

    public static <K, V> Map<K, V> deserializeMap(String s, JavaType keyType, JavaType valueType) {
        MapType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, keyType, valueType);
        try {
            HashMap<K, V> map = mapper.readValue(s, mapType);
            return map;
        } catch (IOException e) {
            log.warn("Json反序列化失败");
            e.printStackTrace();
            return null;
        }
    }

    public static <K, V> Map<K, V> deserializeMap(String s, Class<K> keyClass, Class<V> valueClass) {
        MapType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, keyClass, valueClass);
        try {
            HashMap<K, V> map = mapper.readValue(s, mapType);
            return map;
        } catch (IOException e) {
            log.warn("Json反序列化失败");
            e.printStackTrace();
            return null;
        }
    }
}
