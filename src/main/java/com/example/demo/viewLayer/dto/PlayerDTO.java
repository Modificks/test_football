package com.example.demo.viewLayer.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PlayerDTO extends PlayerRegisterDTO{

    private Long id;
    private TeamDTO teamDTO;

    @Min(value = 0, message = "Experience can not be an negative number")
    private BigDecimal experience;

    @Min(value = 0, message = "Salary can not be an negative number")
    private Long salary;
}
