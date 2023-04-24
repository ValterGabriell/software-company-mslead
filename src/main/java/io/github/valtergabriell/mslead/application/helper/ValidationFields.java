package io.github.valtergabriell.mslead.application.helper;

import java.time.LocalDate;

public interface ValidationFields {
    public void validateCnpjLenght(Long cnpj, String message);
    public void validateDate(LocalDate currentDate, LocalDate reqDate);


}
