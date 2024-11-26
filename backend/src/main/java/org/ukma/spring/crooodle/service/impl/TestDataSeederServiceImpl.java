package org.ukma.spring.crooodle.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.dto.CountryCreateRequestDto;
import org.ukma.spring.crooodle.service.CountryService;
import org.ukma.spring.crooodle.service.TestDataSeederService;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class TestDataSeederServiceImpl implements TestDataSeederService {
    private final CountryService countryService;

    private final CountryCreateRequestDto[] countries = {
        CountryCreateRequestDto.builder().name("Ukraine").build(),
        CountryCreateRequestDto.builder().name("United States").build(),
        CountryCreateRequestDto.builder().name("Germany").build(),
        CountryCreateRequestDto.builder().name("Japan").build(),
        CountryCreateRequestDto.builder().name("Australia").build(),
        CountryCreateRequestDto.builder().name("Brazil").build(),
        CountryCreateRequestDto.builder().name("South Africa").build(),
        CountryCreateRequestDto.builder().name("India").build(),
        CountryCreateRequestDto.builder().name("United Kingdom").build(),
        CountryCreateRequestDto.builder().name("Canada").build(),
        CountryCreateRequestDto.builder().name("France").build(),
        CountryCreateRequestDto.builder().name("China").build(),
        CountryCreateRequestDto.builder().name("Mexico").build(),
        CountryCreateRequestDto.builder().name("Italy").build(),
        CountryCreateRequestDto.builder().name("Egypt").build(),
        CountryCreateRequestDto.builder().name("Argentina").build(),
        CountryCreateRequestDto.builder().name("Saudi Arabia").build(),
        CountryCreateRequestDto.builder().name("South Korea").build(),
        CountryCreateRequestDto.builder().name("Nigeria").build()
    };

    @Override
    public void seed() {
        tryRun(t1 -> {
            for (var country : countries)
                countryService.create(country);
        });
    }

    private void tryRun(Consumer<Void> fn) {
        try {
            fn.accept(null);
        } catch (Exception ee) {}
    }
}
