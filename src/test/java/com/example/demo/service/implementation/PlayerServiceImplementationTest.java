package com.example.demo.service.implementation;

import com.example.demo.domain.entity.Player;
import com.example.demo.domain.entity.Team;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.repository.TeamRepository;
import com.example.demo.viewLayer.dto.playerDTOs.PlayerDTO;
import com.example.demo.viewLayer.dto.playerDTOs.PlayerRegisterDTO;
import com.example.demo.viewLayer.dto.TeamDTO;
import com.example.demo.viewLayer.mapper.PlayerMapper;
import com.example.demo.viewLayer.mapper.TeamMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceImplementationTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private PlayerMapper playerMapper;

    @Mock
    private TeamMapper teamMapper;

    @InjectMocks
    private PlayerServiceImplementation playerService;

    private Player player;
    private Team team;
    private PlayerDTO playerDTO;
    private PlayerRegisterDTO playerRegisterDTO;
    private TeamDTO teamDTO;

    @BeforeEach
    void setUp() {
        team = new Team();
        team.setName("Test Team");
        team.setCountry("Test Country");

        player = new Player();
        player.setId(1L);
        player.setName("Test Player");
        player.setSurname("Test Surname");
        player.setDateOfBirth(LocalDate.of(1990, 1, 1));
        player.setTeam(team);
        player.setSalary(0L);
        player.setExperience(BigDecimal.ZERO);

        playerDTO = new PlayerDTO(1L, "Test Player", "Test Surname", LocalDate.of(1990, 1, 1), 1L, null, BigDecimal.ZERO, 0L);
        playerRegisterDTO = new PlayerRegisterDTO("Test Player", "Test Surname", LocalDate.of(1990, 1, 1), 1L);

        teamDTO = new TeamDTO("Test Team", "Test Country", BigDecimal.ZERO, BigDecimal.ZERO);
    }

    @Test
    void createPlayer_ShouldReturnPlayerDTO_WhenPlayerIsCreated() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        when(playerMapper.toEntity(playerRegisterDTO)).thenReturn(player);
        when(playerRepository.save(player)).thenReturn(player);
        when(playerMapper.toDto(player)).thenReturn(playerDTO);
        when(teamMapper.toDto(team)).thenReturn(teamDTO);

        PlayerDTO result = playerService.createPlayer(playerRegisterDTO);

        assertNotNull(result);
        assertEquals("Test Player", result.getName());
        assertEquals("Test Surname", result.getSurname());
        assertEquals("Test Team", result.getTeamDTO().getName());
        verify(playerRepository, times(1)).save(player);
    }

    @Test
    void createPlayer_ShouldThrowEntityNotFoundException_WhenTeamDoesNotExist() {
        when(teamRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> playerService.createPlayer(playerRegisterDTO));
    }

    @Test
    void getAll_ShouldReturnListOfPlayerDTOs() {
        when(playerRepository.findAll()).thenReturn(List.of(player));
        when(playerMapper.toDto(player)).thenReturn(playerDTO);
        when(teamMapper.toDto(team)).thenReturn(teamDTO);

        List<PlayerDTO> result = playerService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Player", result.get(0).getName());
    }

    @Test
    void getById_ShouldReturnPlayerDTO_WhenPlayerExists() {
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(playerMapper.toDto(player)).thenReturn(playerDTO);
        when(teamMapper.toDto(team)).thenReturn(teamDTO);

        PlayerDTO result = playerService.getById(1L);

        assertNotNull(result);
        assertEquals("Test Player", result.getName());
    }

    @Test
    void getById_ShouldThrowEntityNotFoundException_WhenPlayerDoesNotExist() {
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> playerService.getById(1L));
    }

    @Test
    void updatePlayer_ShouldThrowEntityNotFoundException_WhenPlayerDoesNotExist() {
        PlayerDTO updatedPlayerDTO = new PlayerDTO(1L, "Updated Player", "Updated Surname", LocalDate.of(1990, 1, 1), 1L, null, BigDecimal.TEN, 100L);
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> playerService.updatePlayer(updatedPlayerDTO));
    }

    @Test
    void deleteById_ShouldDeletePlayer_WhenPlayerExists() {
        when(playerRepository.existsById(1L)).thenReturn(true);

        playerService.deleteById(1L);

        verify(playerRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_ShouldThrowEntityNotFoundException_WhenPlayerDoesNotExist() {
        when(playerRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> playerService.deleteById(1L));
    }
}
