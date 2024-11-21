package org.ukma.spring.crooodle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ukma.spring.crooodle.entities.CountryEntity;

public interface CountryRepository extends JpaRepository<CountryEntity, Integer> {

}
