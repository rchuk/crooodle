package org.ukma.spring.crooodle.repository;

import org.ukma.spring.crooodle.entities.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Integer> {

}
