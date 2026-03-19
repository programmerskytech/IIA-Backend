package com.astro.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Cache Configuration for the application.
 * Enables caching for LOV (List of Values) to improve performance.
 *
 * Cache Names:
 * - allForms: All forms
 * - activeForms: Active forms
 * - formById: Form by ID
 * - formByName: Form by name
 * - designatorsByFormId: Designators by form ID
 * - activeDesignatorsByFormId: Active designators by form ID
 * - designatorById: Designator by ID
 * - designatorByFormAndName: Designator by form and name
 * - lovsByDesignatorId: LOVs by designator ID
 * - activeLovsByDesignatorId: Active LOVs by designator ID
 * - lovsByFormAndField: LOVs by form and field name
 * - lovById: LOV by ID
 * - dependentLovs: Dependent LOVs for cascading dropdowns
 * - allDropdownsForForm: All dropdowns for a form
 */
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
            // Form caches
            new ConcurrentMapCache("allForms"),
            new ConcurrentMapCache("activeForms"),
            new ConcurrentMapCache("formById"),
            new ConcurrentMapCache("formByName"),

            // Designator caches
            new ConcurrentMapCache("designatorsByFormId"),
            new ConcurrentMapCache("activeDesignatorsByFormId"),
            new ConcurrentMapCache("designatorById"),
            new ConcurrentMapCache("designatorByFormAndName"),

            // LOV caches
            new ConcurrentMapCache("lovsByDesignatorId"),
            new ConcurrentMapCache("activeLovsByDesignatorId"),
            new ConcurrentMapCache("lovsByFormAndField"),
            new ConcurrentMapCache("activeLovsByFormAndField"),
            new ConcurrentMapCache("lovById"),
            new ConcurrentMapCache("dependentLovs"),
            new ConcurrentMapCache("activeDependentLovs"),
            new ConcurrentMapCache("allDropdownsForForm"),
            new ConcurrentMapCache("activeDropdownsForForm"),
            new ConcurrentMapCache("totalActiveLOVCount")
        ));
        return cacheManager;
    }
}
