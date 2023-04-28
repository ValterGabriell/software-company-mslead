package io.github.valtergabriell.mslead.application.helper;




import io.github.valtergabriell.mslead.exception.RequestExceptions;

import java.time.LocalDate;

public class Validation extends HandleValidation implements ValidationFields {
    @Override
    public void validate(String field, String message) {
        if (field.isEmpty()) {
            throw new RequestExceptions(message);
        }
    }

    @Override
    public void validateCnpjLenght(Long cnpj, String message) {
        if (cnpj.toString().length() != 14) {
            throw new RequestExceptions(message);
        }
    }

    @Override
    public void validateDate(LocalDate currentDate, LocalDate reqDate) {
        if (reqDate.isAfter(currentDate) || reqDate.isEqual(currentDate)){
            throw new RequestExceptions("Insira uma data válida!");
        }
    }
}
