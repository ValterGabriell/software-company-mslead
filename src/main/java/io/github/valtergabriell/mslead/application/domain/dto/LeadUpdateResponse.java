package io.github.valtergabriell.mslead.application.domain.dto;

import io.github.valtergabriell.mslead.application.domain.enumerations.Gender;
import io.github.valtergabriell.mslead.application.domain.enumerations.TypePerson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LeadUpdateResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Boolean active;
    private Gender gender;
    private LocalDate bornDay;
    private TypePerson type;
    private LocalDate accountCreationDate;
    private String password;
}
