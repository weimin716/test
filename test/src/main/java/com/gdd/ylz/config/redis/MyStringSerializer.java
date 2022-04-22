package com.gdd.ylz.config.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MyStringSerializer implements RedisSerializer<Object> {
    private final Charset charset;

    public MyStringSerializer(){
        this(StandardCharsets.UTF_8);
    }

    public MyStringSerializer(Charset charset){
        Assert.notNull(charset,"Charset must not be null!");
        this.charset=charset;
    }



    @Override
    public byte[] serialize(Object o) throws SerializationException {
              if(o==null){
                  return  new byte[0];
              }
              if(o instanceof String){
                  return o.toString().getBytes(this.charset);
              }else{
                  String s = JSON.toJSONString(o);
                  return s.getBytes(this.charset);
              }
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        return bytes==null?null:new String(bytes,this.charset);
    }
}
