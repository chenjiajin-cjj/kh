package io.hk.webApp.Tools;

import com.alibaba.fastjson.JSON;

import com.mongodb.BasicDBObject;

import org.bson.Document;

import org.bson.json.JsonWriterSettings;

public class BsonUtil{
    public static  <T> T toBean(BasicDBObject dbObject,Class<T> clzss){
        String realJson = dbObject.toJson(JsonWriterSettings.builder().build());
        T obj = JSON.parseObject(realJson,clzss);
        return obj;
    }

    public static  <T> T toBean(Document document,Class<T> clzss){
        String realJson = document.toJson(JsonWriterSettings.builder().build());
        T obj = JSON.parseObject(realJson,clzss);
        return obj;
    }

    public static <T> BasicDBObject toDBObject(T object){
        String json = JSON.toJSONString(object);
        BasicDBObject basicDBObject = BasicDBObject.parse(json);
        return basicDBObject;
    }

    public static <T> Document toDocument(T object){
        String json = JSON.toJSONString(object);
        Document document = Document.parse(json);
        return document;
    }
}
