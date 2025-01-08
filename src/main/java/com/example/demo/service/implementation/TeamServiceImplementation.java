package com.example.demo.service.implementation;

import com.example.demo.domain.entity.Player;
import com.example.demo.domain.entity.Team;
import com.example.demo.exception.EntityAlreadyExistsException;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.repository.TeamRepository;
import com.example.demo.service.TeamService;
import com.example.demo.viewLayer.dto.TeamDTO;
import com.example.demo.viewLayer.mapper.TeamMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamServiceImplementation implements TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    private final TeamMapper teamMapper;

    @Override
    public TeamDTO createTeam(TeamDTO teamDTO) {
        if (teamRepository.existsByName(teamDTO.getName())) {
            throw new EntityAlreadyExistsException("Team with the same name already exists");
        }

        Team team = teamMapper.toEntity(teamDTO);

        teamRepository.save(team);

        return teamMapper.toDto(team);
    }

    @Override
    public Set<TeamDTO> getTeams() {
        Set<Team> teams = teamRepository.findAll();

        return teams.stream()
                .map(teamMapper::toDto)
//                .map(team -> {
//                    TeamDTO teamDTO = teamMapper.toDto(team);
//                    teamDTO.setPlayerDTOS(team.getPlayers().stream()
//                            .map(playerMapper::toDto)
//                            .collect(Collectors.toList())
//                    );
//
//                    return teamDTO;
//                })
                .collect(Collectors.toSet());
    }

    @Override
    public TeamDTO getByName(String name) {

        String nameLowerCase = name.toLowerCase();

        Set<Team> teams = teamRepository.findAll();

        Team team = teams.stream()
                .filter(t -> t.getName().toLowerCase().equals(nameLowerCase))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Team with name: '" + name + "' does not exist"));

        return teamMapper.toDto(team);
    }

    @Override
    public TeamDTO updateTeam(TeamDTO updateDTO) {
        Team existingTeam = teamRepository.findByName(updateDTO.getName())
                .orElseThrow(() -> new EntityNotFoundException("Team with name: '" + updateDTO.getName() + "' does not exist"));

        Long teamId = existingTeam.getId();

        BeanUtils.copyProperties(updateDTO, existingTeam);

        existingTeam.setId(teamId);

        teamRepository.save(existingTeam);

        return teamMapper.toDto(existingTeam);
    }

    @Override
    public void deleteByName(long id) {
        if (!teamRepository.existsById(id)) {
            throw new EntityNotFoundException("Team with id: '" + id + "' does not exist");
        }

        List<Player> playersInTeam = playerRepository.findByTeamId(id);

        for (Player player : playersInTeam) {
            player.setTeam(null);
            playerRepository.save(player);
        }

        teamRepository.deleteById(id);
    }
}
