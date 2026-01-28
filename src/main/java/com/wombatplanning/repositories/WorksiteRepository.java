package com.wombatplanning.repositories;

import com.wombatplanning.models.entities.Worksite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorksiteRepository extends JpaRepository<Worksite, Long> {
    List<Worksite> findAllByUserId(Long id);
}
