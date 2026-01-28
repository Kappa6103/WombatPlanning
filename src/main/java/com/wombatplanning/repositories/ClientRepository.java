package com.wombatplanning.repositories;

import com.wombatplanning.models.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findAllByUserId(Long id);
}
