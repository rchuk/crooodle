package org.ukma.spring.crooodle.app;

import lombok.NonNull;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@EnableCaching
public class CustomMapCacheManager implements CacheManager {

    private final Map<String, Cache> caches = new ConcurrentHashMap<>();

    @Override
    public Cache getCache(@NonNull String name) {
        return caches.computeIfAbsent(name, ConcurrentMapCache::new);
    }

    @NonNull
    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(caches.keySet());
    }

    public void clearCache(String cacheName) {
        var cache = caches.get(cacheName);
        if (cache != null)
            cache.clear();
    }

    public void clearAllCaches() {
        caches.values().forEach(Cache::clear);
    }
}
