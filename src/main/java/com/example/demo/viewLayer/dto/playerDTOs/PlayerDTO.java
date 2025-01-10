package com.example.demo.viewLayer.dto.playerDTOs;

import com.example.demo.viewLayer.dto.TeamDTO;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO extends PlayerRegisterDTO {

    private Long id;
    private TeamDTO teamDTO;

    @Min(value = 0, message = "Experience can`t be an negative number")
    private BigDecimal experience;

    @Min(value = 0, message = "Salary can`t be an negative number")
    private Long salary;

    @Builder
    public PlayerDTO(Long id, String name, String surname, LocalDate dateOfBirth, long teamId, TeamDTO teamDTO, BigDecimal experience, Long salary) {
        super(name, surname, dateOfBirth, teamId);
        this.id = id;
        this.teamDTO = teamDTO;
        this.experience = experience;
        this.salary = salary;
    }
}
