package br.lucas.petspring.controller;

import br.lucas.petspring.dto.DonoDTO;
import br.lucas.petspring.exception.BadRequestException;
import br.lucas.petspring.service.DonoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/donos")
@RequiredArgsConstructor
@Validated
public class DonoController {
    private final DonoService donoService;

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
}
