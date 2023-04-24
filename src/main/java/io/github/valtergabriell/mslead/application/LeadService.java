package io.github.valtergabriell.mslead.application;

import io.github.valtergabriell.mslead.application.domain.Lead;
import io.github.valtergabriell.mslead.application.domain.dto.*;
import io.github.valtergabriell.mslead.application.domain.enumerations.TypePerson;
import io.github.valtergabriell.mslead.application.exception.RequestException;
import io.github.valtergabriell.mslead.application.helper.ModelMapperSingleton;
import io.github.valtergabriell.mslead.application.helper.PasswordEncoder;
import io.github.valtergabriell.mslead.application.helper.Validation;
import io.github.valtergabriell.mslead.infra.repository.LeadRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class LeadService {
    private final LeadRepository leadRepository;

    public LeadService(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    private static void validatingFields(ReqLeadCreation reqLeadCreation) {
        Validation validation = new Validation();
        validation.validate(reqLeadCreation.getEmail(), "- Email não pode ser nulo");
        validation.validate(reqLeadCreation.getPassword(), "- Senha não pode ser nula");
        validation.validate(reqLeadCreation.getGender().toString(), "- Gênero não pode ser nulo");
        validation.validate(reqLeadCreation.getPhone(), "- Telefone não pode ser nulo");
        validation.validate(reqLeadCreation.getName(), "- Nome não pode ser nulo");
        validation.validate(reqLeadCreation.getCompany(), "- Empresa não pode ser nula");
        validation.validateCnpjLenght(reqLeadCreation.getId(), " - CNPJ deve ter 14 dígitos");
    }


    public CreatedLeadResponse createNewLead(ReqLeadCreation reqLeadCreation) {
        boolean managerAlreadyPresentOnDatabase = leadRepository.findById(reqLeadCreation.getId()).isPresent();
        if (managerAlreadyPresentOnDatabase) {
            throw new RequestException("Usuário " + reqLeadCreation.getId() + " já existente no banco de dados");
        }

        validatingFields(reqLeadCreation);

        ModelMapper modelMapper = ModelMapperSingleton.getInstance();
        Lead lead = modelMapper.map(reqLeadCreation, Lead.class);
        LocalDate currentDate = LocalDate.now();
        lead.setAccountCreationDate(currentDate);
        lead.setPassword(PasswordEncoder.encodePassword(lead.getPassword()));
        lead.setActive(true);
        lead.setType(TypePerson.JURY_PERSON);
        leadRepository.save(lead);

        return modelMapper.map(lead, CreatedLeadResponse.class);
    }

    public List<Employees> findAllColaborators(Long id) {
        Lead manager = leadRepository.findById(id).orElseThrow(() -> new RequestException("Líder " + id + " não foi encontrado!"));
        //todo: chamar o metodo para trazer lista de colaboradores liderados por esse lider do mscolaborator
        return null;
    }

    public LeadUpdateResponse updateManagerById(Long cnpj, ReqUpdateLead reqUpdateLead) {
        Lead leadFounded = leadRepository.findById(cnpj).orElseThrow(() -> new RequestException("Líder não foi encontrado"));
        leadFounded.setName(reqUpdateLead.getName());
        leadFounded.setEmail(reqUpdateLead.getEmail());
        leadFounded.setPhone(reqUpdateLead.getPhone());
        leadFounded.setPassword(PasswordEncoder.encodePassword(reqUpdateLead.getPassword()));
        leadRepository.save(leadFounded);
        return ModelMapperSingleton.getInstance().map(leadFounded, LeadUpdateResponse.class);
    }


    public Lead findLeadById(Long id) {
        return leadRepository.findById(id)
                .orElseThrow(() -> new RequestException("Líder " + id + " não foi encontrado"));
    }

    public List<Lead> findAll() {
        return leadRepository.findAll();
    }

    public void deleteLeadById(Long id) {
        Optional<Lead> lead = leadRepository.findById(id);
        if (lead.isEmpty()) {
            throw new RequestException("Usuário " + id + " não foi encontrado");
        }
        leadRepository.delete(lead.get());
    }
}
