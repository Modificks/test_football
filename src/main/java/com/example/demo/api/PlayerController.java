package com.example.demo.api;

import com.example.demo.service.PlayerService;
import com.example.demo.viewLayer.dto.PlayerDTO;
import com.example.demo.viewLayer.dto.PlayerRegisterDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerDTO create(@Valid @RequestBody PlayerRegisterDTO playerDTO) {
        return playerService.createPlayer(playerDTO);
    }

    @GetMapping("/get-all-players")
    public List<PlayerDTO> getAllPlayers() {
        return playerService.getAll();
    }

    @GetMapping("/get")
    public PlayerDTO getPlayer(@RequestParam long id) {
        return playerService.getById(id);
    }

    @PatchMapping("/update")
    public PlayerDTO update(@Valid @RequestBody PlayerDTO playerDTO) {
        return playerService.updatePlayer(playerDTO);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deletePlayer(@RequestParam long id) {
        playerService.deleteById(id);

        return "Player with id " + id + " was successfully deleted";
    }
}
