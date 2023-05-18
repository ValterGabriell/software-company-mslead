package io.github.valtergabriell.mslead.infra.external.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "mscolaborator", path = "colaborator")
public interface EmployeesConnection {
    @DeleteMapping(value = "/delete-all-employees-by-manager", params = {"leadId"})
    void deleteAllEmployeesWhenLeadIsDeleted(@RequestParam("leadId") Long leadId);
}
