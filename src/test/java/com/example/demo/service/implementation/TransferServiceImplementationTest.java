package com.example.demo.service.implementation;

import com.example.demo.domain.entity.Player;
import com.example.demo.domain.entity.Team;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransferServiceImplementationTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TransferServiceImplementation transferService;

    private Player player;
    private Team fromTeam;
    private Team toTeam;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        player = new Player();
        player.setId(1L);
        player.setDateOfBirth(LocalDate.of(1995, 1, 1));
        player.setExperience(BigDecimal.valueOf(5));

        fromTeam = new Team();
        fromTeam.setId(1L);
        fromTeam.setBudget(BigDecimal.valueOf(1_000_000));
        fromTeam.setCommission(BigDecimal.valueOf(10));
        fromTeam.setPlayers(new ArrayList<>());
        fromTeam.getPlayers().add(player);

        toTeam = new Team();
        toTeam.setId(2L);
        toTeam.setBudget(BigDecimal.valueOf(1_000_000));
        toTeam.setCommission(BigDecimal.valueOf(15));
    }

    @Test
    void testTransferPlayer_Success() {
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(teamRepository.findById(2L)).thenReturn(Optional.of(toTeam));
        when(teamRepository.findById(1L)).thenReturn(Optional.of(fromTeam));

        BigDecimal transferAmount = BigDecimal.valueOf(550_000);
        BigDecimal fromTeamCommissionAmount = transferAmount.multiply(fromTeam.getCommission().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
        BigDecimal adjustedTransferAmount = transferAmount.subtract(fromTeamCommissionAmount);

        System.out.println("Transfer Amount: " + transferAmount);
        System.out.println("From Team Commission Amount: " + fromTeamCommissionAmount);
        System.out.println("Adjusted Transfer Amount: " + adjustedTransferAmount);

        fromTeam.setBudget(fromTeam.getBudget().add(adjustedTransferAmount));
        toTeam.setBudget(toTeam.getBudget().subtract(adjustedTransferAmount));

        transferService.transferPlayer(1L, 1L, 2L);

        assertEquals(toTeam, player.getTeam());
        assertEquals(fromTeam.getBudget(), fromTeam.getBudget());
        assertEquals(toTeam.getBudget(), toTeam.getBudget());

        verify(playerRepository).save(player);
        verify(teamRepository).save(fromTeam);
        verify(teamRepository).save(toTeam);
    }

    @Test
    void testTransferPlayer_PlayerNotFound() {
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> transferService.transferPlayer(1L, 1L, 2L));

        assertEquals("Player with id '1' not found", exception.getMessage());
    }

    @Test
    void testTransferPlayer_TeamNotFound() {
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(teamRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> transferService.transferPlayer(1L, 1L, 2L));

        assertEquals("Team with id '2' not found", exception.getMessage());
    }

    @Test
    void testTransferPlayer_SameTeamTransfer() {
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(teamRepository.findById(2L)).thenReturn(Optional.of(toTeam));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> transferService.transferPlayer(1L, 2L, 2L));

        assertEquals("The player can’t be transferred to the same team", exception.getMessage());
    }
}
