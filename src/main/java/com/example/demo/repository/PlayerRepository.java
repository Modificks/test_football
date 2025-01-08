package com.example.demo.repository;

import com.example.demo.domain.entity.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {

    List<Player> findAll();

    List<Player> findByTeamId(long id);
}
