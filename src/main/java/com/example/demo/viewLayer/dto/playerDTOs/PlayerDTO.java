package com.example.demo.viewLayer.dto.playerDTOs;

import com.example.demo.viewLayer.dto.TeamDTO;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PlayerDTO extends PlayerRegisterDTO {

    private Long id;
    private TeamDTO teamDTO;

    @Min(value = 0, message = "Experience can`t be an negative number")
    private BigDecimal experience;

    @Min(value = 0, message = "Salary can`t be an negative number")
    private Long salary;
}
