package com.example.demo.viewLayer.dto.playerDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerRegisterDTO {

    @NotBlank(message = "Name should contains attlist one symbol")
    @Size(message = "Name should be up to 256 letter", max = 256)
    private String name;

    @NotBlank(message = "Surname should contains attlist one symbol")
    @Size(message = "Surname should be up to 256 letter", max = 256)
    private String surname;

    @Past(message = "Please provide correct date of birth")
    private LocalDate dateOfBirth;

    private long teamId;
}
