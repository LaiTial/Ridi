package org.example.repository;

import org.example.entity.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgreementRepository
        extends JpaRepository<Agreement, String> {
}
