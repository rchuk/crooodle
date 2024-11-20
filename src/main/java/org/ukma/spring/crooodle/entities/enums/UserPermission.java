package org.ukma.spring.crooodle.entities.enums;

public enum UserPermission {
    // Country
    COUNTRY_CREATE,
    COUNTRY_EDIT,
    COUNTRY_VIEW,
    COUNTRY_DELETE,

    // World region
    WORLD_REGION_CREATE,
    WORLD_REGION_EDIT,
    WORLD_REGION_VIEW,
    WORLD_REGION_DELETE,

    // City
    CITY_CREATE,
    CITY_EDIT,
    CITY_VIEW,
    CITY_DELETE,

    CITY_VIEW_PUBLIC,

    // Hotel
    HOTEL_CREATE,
    HOTEL_REQUEST_CREATION,
    HOTEL_EDIT,
    HOTEL_VIEW,
    HOTEL_DELETE,
}
