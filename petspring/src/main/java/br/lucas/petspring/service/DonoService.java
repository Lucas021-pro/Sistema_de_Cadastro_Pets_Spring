package br.lucas.petspring.service;

import br.lucas.petspring.database.model.DonoEntity;
import br.lucas.petspring.database.model.PetEntity;
import br.lucas.petspring.database.repository.IDonoRepository;
import br.lucas.petspring.database.repository.IPetRepository;
import br.lucas.petspring.dto.DonoDTO;
import br.lucas.petspring.dto.DonoResponseDTO;
import br.lucas.petspring.dto.DonoUpdateDto;
import br.lucas.petspring.exception.BadRequestException;
import br.lucas.petspring.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonoService {

    private final IDonoRepository donoRepository;
    private final IPetRepository petRepository;

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
    public void deletarDono(Integer donoId) {
        DonoEntity dono = donoRepository.findById(donoId)
                .orElseThrow(() -> new NotFoundException("Dono não encontrado"));

        List<PetEntity> petsDoDono = petRepository.findByDonoDonoId(donoId);

        for (PetEntity pet : petsDoDono) {
            pet.setDono(null);
            petRepository.save(pet);
        }

        donoRepository.delete(dono);
    }

    public Page<DonoResponseDTO> listAllDonos(Pageable pageable) {
        return donoRepository.findAll(pageable)
                .map(this::converterParaDTO);
    }

    public DonoResponseDTO buscarDonosPorId(Integer id) {
        DonoEntity dono = donoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dono não encontrado"));
        return converterParaDTO(dono);
    }

    public DonoResponseDTO atualizarDono(Integer donoId, DonoUpdateDto donoUpdateDtoDTO) {
        DonoEntity dono = donoRepository.findById(donoId)
                .orElseThrow(() -> new NotFoundException("Dono não encontrado"));

        dono.setNome(donoUpdateDtoDTO.getNome().trim());
        dono.setTelefone(donoUpdateDtoDTO.getTelefone().trim());
        dono.setCidade(donoUpdateDtoDTO.getCidade());
        dono.setRua(donoUpdateDtoDTO.getRua());
        dono.setNumero(donoUpdateDtoDTO.getNumero());

        donoRepository.save(dono);

        return converterParaDTO(dono);
    }

    private DonoResponseDTO converterParaDTO(DonoEntity dono) {
        return DonoResponseDTO.builder()
                .donoId(dono.getDonoId())
                .cpf(dono.getCpf())
                .nome(dono.getNome())
                .telefone(dono.getTelefone())
                .cidade(dono.getCidade())
                .rua(dono.getRua())
                .numero(dono.getNumero())
                .build();
    }
}