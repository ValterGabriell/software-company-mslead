package io.github.valtergabriell.mslead.infra.repository;

import io.github.valtergabriell.mslead.application.domain.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeadRepository extends JpaRepository<Lead, Long> {
}
