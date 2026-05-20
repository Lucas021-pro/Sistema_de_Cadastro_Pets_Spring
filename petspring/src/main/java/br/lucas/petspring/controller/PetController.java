package br.lucas.petspring.controller;

import br.lucas.petspring.dto.PetDTO;
import br.lucas.petspring.service.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/pets")
@RequiredArgsConstructor
@Validated
public class PetController {
    private final PetService petService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarPet(@Valid @RequestBody PetDTO petDTO) throws RuntimeException{
        petService.criarPet(petDTO);
    }

    @DeleteMapping("/{petId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerPet(@PathVariable Integer petId) throws Exception{
        petService.deletarPet(petId);
    }
}
