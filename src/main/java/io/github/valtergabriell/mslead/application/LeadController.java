package io.github.valtergabriell.mslead.application;

import io.github.valtergabriell.mslead.application.domain.Lead;
import io.github.valtergabriell.mslead.application.domain.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("lead")
public class LeadController {
    private final LeadService leadService;


    @Autowired
    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<CreatedLeadResponse> createNewLead(@RequestBody ReqLeadCreation reqLeadCreation) {
        CreatedLeadResponse newLead = leadService.createNewLead(reqLeadCreation);
        return new ResponseEntity<>(newLead, HttpStatus.CREATED);
    }

    @GetMapping(value = "/find-all-employees", params = {"cnpj"})
    public ResponseEntity<List<Employees>> findAllEmployeeByLead(@RequestParam("cnpj") Long cnpj) {
        List<Employees> allColaborators = leadService.findAllColaborators(cnpj);
        return new ResponseEntity<>(allColaborators, HttpStatus.OK);
    }

    @GetMapping(value = "/find-by-id", params = {"cnpj"})
    public ResponseEntity<Lead> findLeadById(@RequestParam("cnpj") Long cnpj) {
        Lead leadById = leadService.findLeadById(cnpj);
        return new ResponseEntity<>(leadById, HttpStatus.OK);
    }
    @PutMapping(value = "/update", params = {"cnpj"})
    public ResponseEntity<LeadUpdateResponse> updateLeadFieldsWithoutListEmployers(@RequestParam("cnpj") Long cnpj, @RequestBody ReqUpdateLead reqUpdateLead) {
        LeadUpdateResponse leadUpdateResponse = leadService.updateManagerById(cnpj, reqUpdateLead);
        return new ResponseEntity<>(leadUpdateResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete", params = {"cnpj"})
    public ResponseEntity deleteLeadByCnpj(@RequestParam("cnpj") Long cnpj) {
        leadService.deleteLeadById(cnpj);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
