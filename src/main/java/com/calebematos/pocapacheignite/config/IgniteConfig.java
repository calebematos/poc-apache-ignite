package com.calebematos.pocapacheignite.config;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cluster.ClusterState;
import org.apache.ignite.configuration.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IgniteConfig {

    @Bean
    public Ignite ignite(){
        IgniteConfiguration config = new IgniteConfiguration();
        config.setDataStorageConfiguration(getDataStorageConfiguration());
        config.setCacheConfiguration(getCacheConfiguration());

        Ignite ignite = Ignition.start(config);
        ignite.cluster().state(ClusterState.ACTIVE); // need it to persist locally the cache data

        return ignite;
    }

    private CacheConfiguration getCacheConfiguration() {
        CacheConfiguration<String, String> cc = new CacheConfiguration<>();
        cc.setName("dummy");
        cc.setOnheapCacheEnabled(false); // not use the Java heap
        cc.setBackups(1);
        cc.setCacheMode(CacheMode.REPLICATED);
        cc.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);

        return cc;
    }

    @NotNull
    private static DataStorageConfiguration getDataStorageConfiguration() {
        DataRegionConfiguration drc = new DataRegionConfiguration();
        drc.setName("my-data-region");
        drc.setInitialSize(10 * 1024 * 1024); // 10MB
        drc.setMaxSize(40 *1024 *1024); // 40Mb
        drc.setPageEvictionMode(DataPageEvictionMode.RANDOM_2_LRU);

        drc.setPersistenceEnabled(true); // save the cache locally

        DataStorageConfiguration dst = new DataStorageConfiguration();
        dst.setDataRegionConfigurations(drc);
        return dst;
    }
}
