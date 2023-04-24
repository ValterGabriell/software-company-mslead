package io.github.valtergabriell.mslead.application.domain.dto;

import io.github.valtergabriell.mslead.application.domain.enumerations.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employees {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Boolean isActive;
    private Gender gender;
    private LocalDate bornDay;

    private LocalDate hireDate;
    private String password;
}
