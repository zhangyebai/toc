package com.zyb.toc.service.config;

import com.zyb.toc.service.listener.RedisListener;
import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Sinks;

import java.nio.charset.StandardCharsets;

/**
 * javadoc AppConfig
 * <p>
 *     application config
 * <p>
 * @author zhang yebai
 * @date 2021/5/11 5:46 PM
 * @version 1.0.0
 **/
@Configuration
public class AppConfig {

    private RedisProperties redisProperties;
    @Autowired
    public AppConfig setRedisProperties(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
        return this;
    }

    @Value(value = "${spring.application.instance:TS1}")
    private String instance;

    @Value(value = "${spring.redis.queue}")
    private String queue;


    /**
     * javadoc redissonClient
     * @apiNote 手动装配redisson client
     *
     * @return org.redisson.api.RedissonClient
     * @author zhang yebai
     * @date 2021/5/27 5:49 PM
     **/
    @Bean
    public RedissonClient redissonClient(){
        final var config = new Config();
        config.setCodec(new JsonJacksonCodec()).useSingleServer().setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort()).setPassword(redisProperties.getPassword()).setDatabase(redisProperties.getDatabase());
        return Redisson.create(config);
    }

    @Bean
    public RBlockingQueue<String> blockingQueue(RedissonClient redissonClient){
        return redissonClient.getBlockingQueue(instance + ":" + queue);
    }

    @Bean
    public RDelayedQueue<String> delayedQueue(RedissonClient redissonClient, RBlockingQueue<String> blockingQueue){
        return redissonClient.getDelayedQueue(blockingQueue);
    }

//    @Bean
//    public TocDelayQueue<String> tocDelayQueue(RDelayedQueue<String> delayedQueue){
//        return new TocDelayQueue<>(delayedQueue);
//    }

    @Bean
    public RedisListener redisListener(RBlockingQueue<String> blockingQueue, @Qualifier(value = "redisStreamProducerProcessor") Sinks.Many<String> processor){
        return new RedisListener(blockingQueue, processor);
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory(){
        final var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(7000);
        return factory;
    }

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory){
        final var restTemplate = new RestTemplate(clientHttpRequestFactory);
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }
}
