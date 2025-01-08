package com.example.demo.api;

import com.example.demo.service.TeamService;
import com.example.demo.viewLayer.dto.TeamDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/create-team")
    @ResponseStatus(HttpStatus.CREATED)
    public TeamDTO createTeam(@Valid @RequestBody TeamDTO teamDTO) {
        return teamService.createTeam(teamDTO);
    }

    @GetMapping("/get-teams")
    public Set<TeamDTO> getTeams() {
        return teamService.getTeams();
    }

    @GetMapping("/get-by-name")
    public TeamDTO getTeamByName(@RequestParam String name) {
        return teamService.getByName(name);
    }

    @PatchMapping("/update")
    public TeamDTO updateTeam(@Valid @RequestBody TeamDTO teamDTO) {
        return teamService.updateTeam(teamDTO);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteTeam(@RequestParam long id) {
        teamService.deleteByName(id);

        return "Team with id: '" + id + "' was successfully deleted";
    }
}
