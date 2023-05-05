package com.cuizhanming.template.identity.api;

import com.cuizhanming.template.identity.entity.dto.common.ListDTO;
import com.cuizhanming.template.identity.entity.dto.request.PrivateRequestCreateDTO;
import com.cuizhanming.template.identity.entity.dto.request.PrivateRequestUpdateDTO;
import com.cuizhanming.template.identity.entity.dto.response.PrivateResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/internal/identities")
public class PrivateController {

    @PostMapping
    public ResponseEntity<PrivateResponseDTO> createIdentity(@RequestBody PrivateRequestCreateDTO requestCreateDTO) {
        return null;
    }

    @PatchMapping
    public ResponseEntity<PrivateResponseDTO> updateIdentity(@RequestBody PrivateRequestUpdateDTO requestUpdateDTO) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PrivateResponseDTO> deleteIdentity(@PathVariable(name = "id") String id) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrivateResponseDTO> getIdentityById(@PathVariable(name = "id") String id) {
        PrivateResponseDTO responseBody = null;
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("")
    public ResponseEntity<ListDTO<PrivateResponseDTO>> getIdentityList() {
        return null;
    }
}
