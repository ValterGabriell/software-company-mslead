package io.github.valtergabriell.mslead.application.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReqUpdateLead {
    private String name;
    private String email;
    private String company;
    private String phone;
    private String password;
}
