package br.lucas.petspring.service;

import br.lucas.petspring.database.model.DonoEntity;
import br.lucas.petspring.database.repository.IDonoRepository;
import br.lucas.petspring.dto.DonoDTO;
import br.lucas.petspring.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DonoService {

    private final IDonoRepository donoRepository;

    public void cadastrarDono(DonoDTO donoDTO) {
        donoRepository.findByCpf(donoDTO.getCpf())
                .ifPresent(d -> {
                    throw new BadRequestException("Este Dono já existe");
                });

        DonoEntity novoDono = DonoEntity.builder()
                .nome(donoDTO.getNome())
                .cpf(donoDTO.getCpf())
                .telefone(donoDTO.getTelefone())
                .cidade(donoDTO.getCidade())
                .rua(donoDTO.getRua())
                .numero(donoDTO.getNumero())
                .build();

        donoRepository.save(novoDono);
    }
}