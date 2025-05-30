package com.cuizhanming.multidatasource.gcp.controller;

import com.cuizhanming.multidatasource.gcp.repository.mysql.CredentialRepository;
import com.cuizhanming.multidatasource.gcp.exception.ResourceNotFoundException;
import com.cuizhanming.multidatasource.gcp.model.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mysql")
public class CredentialController {

    @Autowired
    CredentialRepository credentialRepository;

    @GetMapping("/credentials")
    public List<Credential> getAllCredentials() {
        return credentialRepository.findAll();
    }

    @PostMapping("/credential")
    public Credential createCredential(@Validated @RequestBody Credential credential) {
        return credentialRepository.save(credential);
    }

    @GetMapping("/credential/{id}")
    public Credential getCredentialById(@PathVariable(value = "id") String credentialId) {
        return credentialRepository.findById(credentialId)
                .orElseThrow(() -> new ResourceNotFoundException("Credential", "id", credentialId));
    }

    @PutMapping("/credentials/{id}")
    public Credential updateCredential(@PathVariable(value = "id") String credentialId,
                                 @Validated @RequestBody Credential credentialDetails) {

        Credential credential = credentialRepository.findById(credentialId)
                .orElseThrow(() -> new ResourceNotFoundException("Credential", "id", credentialId));

        credential.setAccessKey(credentialDetails.getAccessKey());
        credential.setSecretKey(credentialDetails.getSecretKey());

        Credential updatedCredential = credentialRepository.save(credential);
        return updatedCredential;
    }

    @DeleteMapping("/credentials/{id}")
    public ResponseEntity<?> deleteCredential(@PathVariable(value = "id") String credentialId) {
        Credential credential = credentialRepository.findById(credentialId)
                .orElseThrow(() -> new ResourceNotFoundException("Credential", "id", credentialId));

        credentialRepository.delete(credential);

        return ResponseEntity.ok().build();
    }
}