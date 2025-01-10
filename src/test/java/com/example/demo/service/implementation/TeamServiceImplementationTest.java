package com.example.demo.service.implementation;

import com.example.demo.domain.entity.Team;
import com.example.demo.exception.EntityAlreadyExistsException;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.repository.TeamRepository;
import com.example.demo.viewLayer.dto.TeamDTO;
import com.example.demo.viewLayer.mapper.TeamMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceImplementationTest {
    @Mock
    private TeamRepository teamRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TeamMapper teamMapper;

    private TeamServiceImplementation teamService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        teamService = new TeamServiceImplementation(teamRepository, playerRepository, teamMapper);
    }

    @Test
    void testCreateTeamWhenTeamAlreadyExists() {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setName("Test Team");
        when(teamRepository.existsByName("Test Team")).thenReturn(true);

        EntityAlreadyExistsException exception = assertThrows(EntityAlreadyExistsException.class, () -> teamService.createTeam(teamDTO));

        assertEquals("Team with name: 'Test Team' already exists", exception.getMessage());
    }

    @Test
    void testCreateTeamSuccessfully() {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setName("New Team");
        Team team = new Team();
        team.setName("New Team");

        when(teamRepository.existsByName("New Team")).thenReturn(false);
        when(teamMapper.toEntity(teamDTO)).thenReturn(team);
        when(teamRepository.save(team)).thenReturn(team);
        when(teamMapper.toDto(team)).thenReturn(teamDTO);

        TeamDTO result = teamService.createTeam(teamDTO);

        assertNotNull(result);
        assertEquals("New Team", result.getName());
    }

    @Test
    void testGetByNameWhenTeamDoesNotExist() {
        String teamName = "Nonexistent Team";
        when(teamRepository.findAll()).thenReturn(Set.of());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> teamService.getByName(teamName));

        assertEquals("Team with name: 'Nonexistent Team' not found", exception.getMessage());
    }

    @Test
    void testGetByNameSuccessfully() {
        String teamName = "Test Team";

        Team team = new Team();
        team.setName("Test Team");

        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setName("Test Team");

        when(teamRepository.findAll()).thenReturn(Set.of(team));
        when(teamMapper.toDto(team)).thenReturn(teamDTO);

        TeamDTO result = teamService.getByName(teamName);

        assertNotNull(result);
        assertEquals("Test Team", result.getName());
    }

    @Test
    void testUpdateTeamWhenTeamNotFound() {
        TeamDTO updateDTO = new TeamDTO();
        updateDTO.setName("Nonexistent Team");

        when(teamRepository.findByName("Nonexistent Team")).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> teamService.updateTeam(updateDTO));

        assertEquals("Team with name: 'Nonexistent Team' not found", exception.getMessage());
    }

    @Test
    void testUpdateTeamSuccessfully() {
        TeamDTO updateDTO = new TeamDTO();
        updateDTO.setName("Updated Team");

        Team existingTeam = new Team();
        existingTeam.setName("Updated Team");

        when(teamRepository.findByName("Updated Team")).thenReturn(Optional.of(existingTeam));
        when(teamMapper.toDto(existingTeam)).thenReturn(updateDTO);

        TeamDTO result = teamService.updateTeam(updateDTO);

        assertNotNull(result);
        assertEquals("Updated Team", result.getName());
    }

    @Test
    void testDeleteByNameWhenTeamNotFound() {
        long teamId = 1L;

        when(teamRepository.existsById(teamId)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> teamService.deleteByName(teamId));

        assertEquals("Team with id: '1' not found", exception.getMessage());
    }

    @Test
    void testDeleteByNameSuccessfully() {
        long teamId = 1L;

        Team team = new Team();
        team.setId(teamId);

        when(teamRepository.existsById(teamId)).thenReturn(true);
        when(playerRepository.findByTeamId(teamId)).thenReturn(List.of());  // Ensure this returns a List

        doNothing().when(teamRepository).deleteById(teamId);

        teamService.deleteByName(teamId);

        verify(teamRepository, times(1)).deleteById(teamId);
    }
}
