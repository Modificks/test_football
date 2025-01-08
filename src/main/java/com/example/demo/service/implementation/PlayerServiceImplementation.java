package com.example.demo.service.implementation;

import com.example.demo.domain.entity.Player;
import com.example.demo.domain.entity.Team;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.repository.TeamRepository;
import com.example.demo.service.PlayerService;
import com.example.demo.viewLayer.dto.PlayerDTO;
import com.example.demo.viewLayer.dto.PlayerRegisterDTO;
import com.example.demo.viewLayer.dto.TeamDTO;
import com.example.demo.viewLayer.mapper.PlayerMapper;
import com.example.demo.viewLayer.mapper.TeamMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PlayerServiceImplementation implements PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    private final PlayerMapper playerMapper;
    private final TeamMapper teamMapper;

    @Override
    public PlayerDTO createPlayer(PlayerRegisterDTO PlayerRegisterDTO) {
        Player player = playerMapper.toEntity(PlayerRegisterDTO);

        Team team = teamRepository.findById(PlayerRegisterDTO.getTeamId())
                        .orElseThrow(() -> new EntityNotFoundException("Team with id: '" + PlayerRegisterDTO.getTeamId() + "' not found"));

        player.setSalary(0L);
        player.setExperience(BigDecimal.ZERO);
        player.setTeam(team);

        playerRepository.save(player);

        PlayerDTO playerDTO = playerMapper.toDto(player);

        if (player.getTeam() != null) {
            TeamDTO teamDTO = teamMapper.toDto(player.getTeam());
            playerDTO.setTeamDTO(teamDTO);
        }

        return playerDTO;
    }

    @Override
    public List<PlayerDTO> getAll() {
        List<Player> players = playerRepository.findAll();

        return players.stream()
                .map(player -> {
                    PlayerDTO playerDTO = playerMapper.toDto(player);
                    playerDTO.setTeamDTO(teamMapper.toDto(player.getTeam()));

                    return playerDTO;
                })
                .toList();
    }

    @Override
    public PlayerDTO getById(long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Player with id '" + id + "' not found"));

        PlayerDTO playerDTO = playerMapper.toDto(player);

        if (player.getTeam() != null) {
            TeamDTO teamDTO = teamMapper.toDto(player.getTeam());
            playerDTO.setTeamDTO(teamDTO);
        }

        return playerDTO;
    }

    @Override
    public PlayerDTO updatePlayer(PlayerDTO playerDTO) {
        Player existedPlayer = playerRepository.findById(playerDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Player with id '" + playerDTO.getId() + "' not found"));

        Team currentTeam = existedPlayer.getTeam();

        BeanUtils.copyProperties(playerDTO, existedPlayer);

        existedPlayer.setId(playerDTO.getId());

        if (currentTeam == null || playerDTO.getTeamId() != currentTeam.getId()) {
            Team newTeam = teamRepository.findById(playerDTO.getTeamId())
                    .orElseThrow(() -> new EntityNotFoundException("Team with id '" + playerDTO.getTeamId() + "' not found"));
            existedPlayer.setTeam(newTeam);
        } else {
            existedPlayer.setTeam(currentTeam);
        }

        playerRepository.save(existedPlayer);

        return playerMapper.toDto(existedPlayer);
    }

    @Override
    public void deleteById(long id) {
        if (!playerRepository.existsById(id)) {
            throw new EntityNotFoundException("Team with id: '" + id + "' does not exist");
        }

        playerRepository.deleteById(id);
    }
}
