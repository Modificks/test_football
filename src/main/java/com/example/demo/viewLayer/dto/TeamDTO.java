package com.example.demo.viewLayer.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {

    @NotBlank(message = "Name of team should contains attlist one symbol")
    @Size(message = "Name of team should be up to 256 letter", max = 256)
    private String name;

    @NotBlank(message = "Name of country should contains attlist one symbol")
    @Size(message = "Name of team country be up to 256 letter", max = 256)
    private String country;

    @Min(value = 0, message = "Budget can`t be an negative number")
    private BigDecimal budget;

    @Max(value = 10, message = "Max size of commission must be 10%")
    @Min(value = 0, message = "Min size of commission must be 0%")
    private BigDecimal commission;

//    private List<PlayerDTO> playerDTOS;
}
