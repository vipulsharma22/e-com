package com.nitsoft.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private MessageService messageService;

    @Autowired
    @Qualifier("redisStringTemplate")
    RedisTemplate redisTemplate;

    private static final Random random = new Random();

    public boolean sendOtp(String phoneNumber){
        String otp = String.format("%04d", random.nextInt(10000));
        redisTemplate.opsForValue().set(getKey(phoneNumber),otp);
        return messageService.sendMessage(otp,phoneNumber);
    }


    public boolean verifyOtp(String phoneNumber,String otp){
        String otpRedis = (String) redisTemplate.opsForValue().get(getKey(phoneNumber));
        if(otpRedis != null && otpRedis.equals(otp.trim())){
         return true;
        }
        return false;
    }

    private String getKey(String phoneNumber){
        StringBuilder otpKey = new StringBuilder(phoneNumber);
        otpKey.append("_");
        otpKey.append("OTP");
        return otpKey.toString();
    }
}
