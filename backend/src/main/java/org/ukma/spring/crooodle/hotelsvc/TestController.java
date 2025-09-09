package org.ukma.spring.crooodle.hotelsvc;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {
    private final HotelSvc hotelSvc;

    @GetMapping("/test")
    void test() {
        hotelSvc.create(HotelUpsertDto.builder().name("123").address("Address").build());
    }
}
