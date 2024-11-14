package org.ukma.spring.crooodle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class CacheController {

    private final CacheManager cacheManager;
    private static final Logger logger = LoggerFactory.getLogger(CacheController.class);

    @Autowired
    public CacheController(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /**
     * Очищення кешу за назвою або всіх кешів.
     * @param cacheNames список імен кешів для очищення (необов'язковий).
     * @return статус виконання очищення кешу.
     */
    @DeleteMapping("/api/cache/clear")
    public ResponseEntity<String> clearCache(@RequestParam(required = false) List<String> cacheNames) {
        try {
            if (cacheNames != null && !cacheNames.isEmpty()) {
                List<String> notFoundCaches = cacheNames.stream()
                        .filter(name -> cacheManager.getCache(name) == null)
                        .collect(Collectors.toList());

                // Очищення знайдених кешів
                cacheNames.stream()
                        .filter(name -> cacheManager.getCache(name) != null)
                        .forEach(name -> {
                            cacheManager.getCache(name).clear();
                            logger.info("Кеш '{}' успішно очищено", name);
                        });

                if (notFoundCaches.isEmpty()) {
                    return ResponseEntity.ok("Усі вказані кеші очищено: " + cacheNames);
                } else {
                    return ResponseEntity.ok("Очищено існуючі кеші: " + cacheNames.stream()
                            .filter(name -> !notFoundCaches.contains(name)).collect(Collectors.toList())
                            + ". Не знайдено кеші: " + notFoundCaches);
                }
            } else {
                cacheManager.getCacheNames().forEach(name -> {
                    Cache cache = cacheManager.getCache(name);
                    if (cache != null) {
                        cache.clear();
                        logger.info("Кеш '{}' успішно очищено", name);
                    }
                });
                return ResponseEntity.ok("Усі кеші успішно очищено");
            }
        } catch (Exception e) {
            logger.error("Помилка при очищенні кешу: ", e);
            return ResponseEntity.status(500).body("Сталася помилка при очищенні кешу");
        }
    }

    /**
     * Отримання списку всіх доступних кешів.
     * @return список імен доступних кешів.
     */
    @GetMapping("/api/cache/list")
    public ResponseEntity<Set<String>> listCaches() {
        Set<String> cacheNames = (Set<String>) cacheManager.getCacheNames();
        logger.info("Отримано список кешів: {}", cacheNames);
        return ResponseEntity.ok(cacheNames);
    }
}
