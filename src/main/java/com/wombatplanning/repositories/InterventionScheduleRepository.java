package com.wombatplanning.repositories;

import com.wombatplanning.models.entities.InterventionSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterventionScheduleRepository extends JpaRepository<InterventionSchedule, Long> {
    List<InterventionSchedule> findAllByUserIdAndYear(Long id, int year);
}
