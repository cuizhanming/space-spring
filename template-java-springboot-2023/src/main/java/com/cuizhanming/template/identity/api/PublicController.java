package com.cuizhanming.template.identity.api;

import com.cuizhanming.template.identity.entity.dto.common.ListDTO;
import com.cuizhanming.template.identity.entity.dto.request.PublicRequestCreateDTO;
import com.cuizhanming.template.identity.entity.dto.request.PublicRequestUpdateDTO;
import com.cuizhanming.template.identity.entity.dto.response.PublicResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/identities")
public class PublicController {

    @PostMapping
    public ResponseEntity<PublicResponseDTO> createIdentity(@RequestBody PublicRequestCreateDTO requestCreateDTO) {
        return null;
    }

    @PatchMapping
    public ResponseEntity<PublicResponseDTO> updateIdentity(@RequestBody PublicRequestUpdateDTO requestUpdateDTO) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PublicResponseDTO> deleteIdentity(@PathVariable(name = "id") String id) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicResponseDTO> getIdentityById(@PathVariable(name = "id") String id) {
        PublicResponseDTO responseBody = null;
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("")
    public ResponseEntity<ListDTO<PublicResponseDTO>> getIdentityList() {
        return null;
    }
}
