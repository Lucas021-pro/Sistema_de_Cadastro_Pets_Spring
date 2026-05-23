package br.lucas.petspring.controller;

import br.lucas.petspring.dto.PetDTO;
import br.lucas.petspring.dto.PetResponseDTO;
import br.lucas.petspring.service.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/pets")
@RequiredArgsConstructor
@Validated
public class PetController {
    private final PetService petService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarPet(@Valid @RequestBody PetDTO petDTO) throws RuntimeException {
        petService.criarPet(petDTO);
    }

    @DeleteMapping("/{petId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerPet(@PathVariable Integer petId) throws Exception {
        petService.deletarPet(petId);
    }

    @GetMapping
    public ResponseEntity<List<PetResponseDTO>> listAllPets() {
        return ResponseEntity.ok(petService.listAllPets());
    }

    @GetMapping("/{petId}")
    public ResponseEntity<PetResponseDTO> buscarPetPorId(@PathVariable Integer petId) {
        return ResponseEntity.ok(petService.buscarPetPorId(petId));
    }

    @PutMapping("/{petId}/adocao/{donoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adotarPet(@PathVariable Integer petId, @PathVariable Integer donoId){
        petService.adotarPet(petId, donoId);
    }
}