package hhplus.tdd.infra.repository;

import hhplus.tdd.infra.entity.RegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationJpaRepository extends JpaRepository<RegistrationEntity, Long> {
}
