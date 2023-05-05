package com.cuizhanming.multidatasource.gcp.repository.mysql;

import com.cuizhanming.multidatasource.gcp.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {
    Optional<Credential> findById(String id);
}