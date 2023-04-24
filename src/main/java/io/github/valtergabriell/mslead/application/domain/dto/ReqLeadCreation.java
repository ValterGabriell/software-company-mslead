package io.github.valtergabriell.mslead.application.domain.dto;

import io.github.valtergabriell.mslead.application.domain.enumerations.Gender;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
public class ReqLeadCreation {
    private Long id;
    private String name;
    private String email;
    private String company;
    private String phone;
    private Boolean isActive;
    private Gender gender;
    private LocalDate bornDay;
    private LocalDate creationDate;
    private String password;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCompany() {
        return company;
    }

    public String getPhone() {
        return phone;
    }

    public Boolean getActive() {
        return isActive;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getBornDay() {
        return bornDay;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public String getPassword() {
        return password;
    }
}
