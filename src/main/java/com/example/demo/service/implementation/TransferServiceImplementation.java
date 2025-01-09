package com.example.demo.service.implementation;

import com.example.demo.domain.entity.Player;
import com.example.demo.domain.entity.Team;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.repository.TeamRepository;
import com.example.demo.service.TransferService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class TransferServiceImplementation implements TransferService {

    private static final BigDecimal COEFFICIENT = BigDecimal.valueOf(100_000);

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    @Transactional
    @Override
    public void transferPlayer(Long playerId, Long fromTeamId, Long toTeamId) {
        if (fromTeamId.equals(toTeamId)) {
            throw new IllegalArgumentException("The player can not be transferred to the same team");
        }

        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player with id '" + playerId + "' not found"));

        Team fromTeam = teamRepository.findById(fromTeamId)
                .orElseThrow(() -> new EntityNotFoundException("Team with id '" + fromTeamId + "' not found"));

        Team toTeam = teamRepository.findById(toTeamId)
                .orElseThrow(() -> new EntityNotFoundException("Team with id '" + toTeamId + "' not found"));

        if (!fromTeam.getPlayers().contains(player)) {
            throw new EntityNotFoundException("Player with id '" + playerId + "' not found in team with id: '" + fromTeamId + "'");
        }

        BigDecimal fullTransferAmount = countTransferFee(player, fromTeam);

        if (toTeam.getBudget().compareTo(fullTransferAmount) < 0) {
            throw new UnsupportedOperationException("No enough funds to transfer. Transfer cost: " + fullTransferAmount + ". Available funds: " + toTeam.getBudget());
        }

        player.setTeam(toTeam);
        fromTeam.setBudget(fromTeam.getBudget().add(fullTransferAmount));
        toTeam.setBudget(toTeam.getBudget().subtract(fullTransferAmount));

        playerRepository.save(player);
        teamRepository.save(fromTeam);
        teamRepository.save(toTeam);
    }

    private BigDecimal countTransferFee(Player player, Team team) {
        BigDecimal ageOfPlayer = BigDecimal.valueOf(ChronoUnit.YEARS.between(player.getDateOfBirth(), LocalDate.now()));

        BigDecimal transferValue = player.getExperience()
                .multiply(COEFFICIENT)
                .divide(ageOfPlayer, 2, RoundingMode.HALF_UP);

        BigDecimal transferCommission = transferValue
                .multiply(team.getCommission().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));

        return transferValue.add(transferCommission);
    }
}
