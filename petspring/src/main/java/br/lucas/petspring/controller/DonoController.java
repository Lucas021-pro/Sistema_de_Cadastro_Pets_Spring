package br.lucas.petspring.controller;

import br.lucas.petspring.dto.DonoDTO;
import br.lucas.petspring.dto.DonoResponseDTO;
import br.lucas.petspring.dto.DonoUpdateDto;
import br.lucas.petspring.dto.PetResponseDTO;
import br.lucas.petspring.exception.BadRequestException;
import br.lucas.petspring.service.DonoService;
import br.lucas.petspring.service.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/donos")
@RequiredArgsConstructor
@Validated
public class DonoController {
    private final DonoService donoService;
    private final PetService petService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarDono(@Valid @RequestBody DonoDTO donoDTO) throws BadRequestException {
        donoService.cadastrarDono(donoDTO);
    }

    @DeleteMapping("/{donoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarDono(@PathVariable Integer donoId) throws Exception {
        donoService.deletarDono(donoId);
    }

    @GetMapping
    public ResponseEntity<Page<DonoResponseDTO>> listAllDonos(
            @PageableDefault(size = 20, sort = "donoId") Pageable pageable) {
        return ResponseEntity.ok(donoService.listAllDonos(pageable));
    }

    @GetMapping("/{donoId}")
    public ResponseEntity<DonoResponseDTO> buscarDonosPorId(@PathVariable Integer donoId) {
        return ResponseEntity.ok(donoService.buscarDonosPorId(donoId));
    }

    @PutMapping("/{donoId}")
    public ResponseEntity<DonoResponseDTO> atualizarDono(@PathVariable Integer donoId,
                                                         @Valid @RequestBody DonoUpdateDto donoUpdateDto) {
        return ResponseEntity.ok(donoService.atualizarDono(donoId, donoUpdateDto));
    }

    @GetMapping("/{donoId}/pets")
    public ResponseEntity<List<PetResponseDTO>> listarPetsPorDono(@PathVariable Integer donoId) {
        return ResponseEntity.ok(petService.listarPetsPorDono(donoId));
    }
}
