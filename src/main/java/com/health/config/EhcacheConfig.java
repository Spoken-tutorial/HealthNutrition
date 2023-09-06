package com.health.config;

import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/*
 * Author: Alok Kumar
 */

@Configuration
public class EhcacheConfig {

    /*
     * @Bean CacheManager cacheManager(){ return new
     * EhCacheCacheManager(ehCacheManager()); //return CacheManager.create(); }
     * 
     * private net.sf.ehcache.CacheManager ehCacheManager() {
     * EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
     * factoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
     * factoryBean.setShared(true); return factoryBean.getObject(); }
     */

    @Bean
    public EhCacheCacheManager ehCacheCacheManager() {

        return new EhCacheCacheManager(ehCacheManagerFactoryBean().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {

        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();

        cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cacheManagerFactoryBean.setShared(true);

        return cacheManagerFactoryBean;
    }

}