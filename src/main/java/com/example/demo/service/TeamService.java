package com.example.demo.service;

import com.example.demo.viewLayer.dto.TeamDTO;

import java.util.Set;

public interface TeamService {

    TeamDTO createTeam(TeamDTO teamDTO);

    Set<TeamDTO> getTeams();

    TeamDTO getByName(String name);

    TeamDTO updateTeam(TeamDTO updateDTO);

    void deleteByName(long id);
}
