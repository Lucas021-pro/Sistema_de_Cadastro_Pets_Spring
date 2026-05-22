package br.lucas.petspring.service;

import br.lucas.petspring.database.model.DonoEntity;
import br.lucas.petspring.database.repository.IDonoRepository;
import br.lucas.petspring.dto.DonoDTO;
import br.lucas.petspring.exception.BadRequestException;
import br.lucas.petspring.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(rollbackFor = Exception.class)
    public void deletarDono(Integer donoId) throws Exception {
        DonoEntity dono = donoRepository.findById(donoId)
                .orElseThrow(() -> new NotFoundException("Dono não encontrado"));
        donoRepository.delete(dono);
    }
}