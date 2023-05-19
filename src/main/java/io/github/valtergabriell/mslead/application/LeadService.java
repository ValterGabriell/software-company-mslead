package io.github.valtergabriell.mslead.application;

import io.github.valtergabriell.mslead.application.domain.Lead;
import io.github.valtergabriell.mslead.application.domain.dto.*;
import io.github.valtergabriell.mslead.application.domain.enumerations.TypePerson;
import io.github.valtergabriell.mslead.application.helper.ModelMapperSingleton;
import io.github.valtergabriell.mslead.application.helper.PasswordEncoder;
import io.github.valtergabriell.mslead.application.helper.Validation;
import io.github.valtergabriell.mslead.exception.RequestExceptions;
import io.github.valtergabriell.mslead.infra.external.openfeign.EmployeesConnection;
import io.github.valtergabriell.mslead.infra.external.send.ClientQueue;
import io.github.valtergabriell.mslead.infra.repository.LeadRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class LeadService {
    private final LeadRepository leadRepository;
    private final ClientQueue clientQueue;
    private final EmployeesConnection employeesConnection;

    public LeadService(LeadRepository leadRepository, ClientQueue clientQueue, EmployeesConnection employeesConnection) {
        this.leadRepository = leadRepository;
        this.clientQueue = clientQueue;
        this.employeesConnection = employeesConnection;
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
        ModelMapper modelMapper = ModelMapperSingleton.getInstance();
        URI headerLocation = ServletUriComponentsBuilder
                .fromHttpUrl("http://localhost:9090")
                .path("account")
                .query("cpf={id}")
                .buildAndExpand(reqLeadCreation.getId())
                .toUri();


        boolean managerAlreadyPresentOnDatabase = leadRepository.findById(reqLeadCreation.getId()).isPresent();
        if (managerAlreadyPresentOnDatabase) {
            throw new RequestExceptions("Usuário " + reqLeadCreation.getId() + " já existente no banco de dados");
        }
        validatingFields(reqLeadCreation);
        Lead lead = createNewLeadAndSaveAtDatabase(reqLeadCreation, modelMapper);
        sendLeadToQueueForCreateClient(reqLeadCreation, modelMapper, lead);
        CreatedLeadResponse createdLeadResponse = modelMapper.map(lead, CreatedLeadResponse.class);
        createdLeadResponse.setUri(headerLocation.toString());
        return createdLeadResponse;
    }

    private void sendLeadToQueueForCreateClient(ReqLeadCreation reqLeadCreation, ModelMapper modelMapper, Lead lead) {
        ClientAccount clientAccount = modelMapper.map(lead, ClientAccount.class);
        clientAccount.setIncome(reqLeadCreation.getIncome());
        clientQueue.createNewClient(clientAccount);
    }

    private Lead createNewLeadAndSaveAtDatabase(ReqLeadCreation reqLeadCreation, ModelMapper modelMapper) {
        Lead lead = modelMapper.map(reqLeadCreation, Lead.class);
        LocalDate currentDate = LocalDate.now();
        lead.setAccountCreationDate(currentDate);
        lead.setPassword(PasswordEncoder.encodePassword(lead.getPassword()));
        lead.setActive(true);
        lead.setType(TypePerson.JURY_PERSON);
        leadRepository.save(lead);
        return lead;
    }

    public List<Employees> findAllColaborators(Long id) {
        Lead manager = leadRepository.findById(id).orElseThrow(() -> new RequestExceptions("Líder " + id + " não foi encontrado!"));
        //todo: chamar o metodo para trazer lista de colaboradores liderados por esse lider do mscolaborator
        return null;
    }

    public LeadUpdateResponse updateManagerById(Long cnpj, ReqUpdateLead reqUpdateLead) {
        Lead leadFounded = leadRepository.findById(cnpj).orElseThrow(() -> new RequestExceptions("Líder não foi encontrado"));
        leadFounded.setName(reqUpdateLead.getName());
        leadFounded.setEmail(reqUpdateLead.getEmail());
        leadFounded.setPhone(reqUpdateLead.getPhone());
        leadFounded.setPassword(PasswordEncoder.encodePassword(reqUpdateLead.getPassword()));
        leadRepository.save(leadFounded);
        return ModelMapperSingleton.getInstance().map(leadFounded, LeadUpdateResponse.class);
    }


    public Response<Lead> findLeadById(Long id) {
        Optional<Lead> lead = leadRepository.findById(id);
        Response<Lead> response = new Response<>();
        if (lead.isEmpty()) {
            response.setMessage("Líder com id " + id + " não encontrado");
        } else {
            response.setData(lead.get());
            response.setMessage("Sucesso!");
        }
        return response;
    }

    public List<Lead> findAll() {
        return leadRepository.findAll();
    }

    public void deleteLeadById(Long id) {
        Optional<Lead> lead = leadRepository.findById(id);
        if (lead.isEmpty()) {
            throw new RequestExceptions("Usuário " + id + " não foi encontrado");
        }
        employeesConnection.deleteAllEmployeesWhenLeadIsDeleted(id);
        clientQueue.deleteClient(id);
        leadRepository.delete(lead.get());
    }
}
