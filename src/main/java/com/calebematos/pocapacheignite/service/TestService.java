package com.calebematos.pocapacheignite.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {

    private final Ignite ignite;
    @Async
    public void saveInCache(String name) throws InterruptedException {
        IgniteCache<String, String> igniteCache = ignite.getOrCreateCache("dummy");

        String cachedName = igniteCache.get(name);

        if(cachedName == null){
            log.info("The name {} is not on the cache", name);
            igniteCache.put(name, name);
        }else {
            log.info("The name {} is already on the cache", name);
        }

        Thread.sleep(10000);

        log.info("End of method");

    }
}
