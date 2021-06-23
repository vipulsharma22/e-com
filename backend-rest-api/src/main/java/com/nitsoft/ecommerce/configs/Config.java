package com.nitsoft.ecommerce.configs;

import com.nitsoft.ecommerce.client.Message91Client;
import com.nitsoft.ecommerce.client.PickrrClient;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Properties;

@Configuration
public class Config {


    @Value("${email.username}")
    private String emailUserName;

    @Value("${email.password}")
    private String emailPassword;

    @Value("${secret.key}")
    private String secretKey;

    @Value("${secret.id}")
    private String secretId;

    @Bean
    public Message91Client getMessage91Client(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.msg91.com/api/")//todo: set message91 base url
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        Message91Client message91Client = retrofit.create(Message91Client.class);
        return message91Client;
    }

    @Bean
    public RazorpayClient getRazorpayClient() throws RazorpayException {
        return new RazorpayClient(secretId,secretKey);
    }


    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(emailUserName);
        mailSender.setPassword(emailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }

    @Bean
    public PickrrClient getPickrrClient(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pickrr.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        return retrofit.create(PickrrClient.class);
    }

}
