package com.example.demo.repository;

import com.example.demo.domain.entity.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {

    Set<Team> findAll();

    boolean existsByName(String name);

    Optional<Team> findByName(String name);
}
