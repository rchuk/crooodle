package org.ukma.spring.crooodle.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.dto.CountryCreateRequestDto;
import org.ukma.spring.crooodle.dto.WorldRegionCreateRequestDto;
import org.ukma.spring.crooodle.repository.WorldRegionRepository;
import org.ukma.spring.crooodle.service.CountryService;
import org.ukma.spring.crooodle.service.TestDataSeederService;
import org.ukma.spring.crooodle.service.WorldRegionService;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class TestDataSeederServiceImpl implements TestDataSeederService {
    private final CountryService countryService;
    private final WorldRegionService worldRegionService;
    private final WorldRegionRepository worldRegionRepository;

    private final WorldRegionCreateRequestDto[] worldRegions = {
        WorldRegionCreateRequestDto.builder().name("Europe").build(),
        WorldRegionCreateRequestDto.builder().name("Asia").build(),
        WorldRegionCreateRequestDto.builder().name("America").build(),
        WorldRegionCreateRequestDto.builder().name("Africa").build(),
        WorldRegionCreateRequestDto.builder().name("Oceania").build()
    };


    @Override
    public void seed() {
        tryRun(t1 -> this.seedWorldRegions());
        tryRun(t1 -> this.seedCountries());
    }

    private void seedCountries() {
        final int europe = worldRegionRepository.findByName("Europe").orElseThrow().getId();
        final int asia = worldRegionRepository.findByName("Asia").orElseThrow().getId();
        final int america = worldRegionRepository.findByName("America").orElseThrow().getId();
        final int africa = worldRegionRepository.findByName("Africa").orElseThrow().getId();
        final int oceania = worldRegionRepository.findByName("Oceania").orElseThrow().getId();

        var countries = new CountryCreateRequestDto[] {
            CountryCreateRequestDto.builder().name("Ukraine").worldRegionId(europe).build(),
            CountryCreateRequestDto.builder().name("United States").worldRegionId(america).build(),
            CountryCreateRequestDto.builder().name("Germany").worldRegionId(europe).build(),
            CountryCreateRequestDto.builder().name("Japan").worldRegionId(asia).build(),
            CountryCreateRequestDto.builder().name("Australia").worldRegionId(oceania).build(),
            CountryCreateRequestDto.builder().name("Brazil").worldRegionId(america).build(),
            CountryCreateRequestDto.builder().name("South Africa").worldRegionId(africa).build(),
            CountryCreateRequestDto.builder().name("India").worldRegionId(asia).build(),
            CountryCreateRequestDto.builder().name("United Kingdom").worldRegionId(europe).build(),
            CountryCreateRequestDto.builder().name("Canada").worldRegionId(america).build(),
            CountryCreateRequestDto.builder().name("France").worldRegionId(europe).build(),
            CountryCreateRequestDto.builder().name("China").worldRegionId(asia).build(),
            CountryCreateRequestDto.builder().name("Mexico").worldRegionId(america).build(),
            CountryCreateRequestDto.builder().name("Italy").worldRegionId(europe).build(),
            CountryCreateRequestDto.builder().name("Egypt").worldRegionId(africa).build(),
            CountryCreateRequestDto.builder().name("Argentina").worldRegionId(asia).build(),
            CountryCreateRequestDto.builder().name("Saudi Arabia").worldRegionId(asia).build(),
            CountryCreateRequestDto.builder().name("South Korea").worldRegionId(asia).build(),
            CountryCreateRequestDto.builder().name("Nigeria").worldRegionId(africa).build()
        };

        for (var request : countries)
            countryService.create(request);
    }

    private void seedWorldRegions() {
        for (var request : worldRegions)
            worldRegionService.create(request);
    }

    private void tryRun(Consumer<Void> fn) {
        try {
            fn.accept(null);
        } catch (Exception ee) {}
    }
}
