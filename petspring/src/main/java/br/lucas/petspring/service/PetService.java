package br.lucas.petspring.service;

import br.lucas.petspring.database.model.PetEntity;
import br.lucas.petspring.database.repository.IPetRepository;
import br.lucas.petspring.dto.PetDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PetService {

    private final IPetRepository petRepository;
    private static final String NAO_INFORMADO = "NÃO INFORMADO";

    public void criarPet(PetDTO petDTO) {
        petRepository.findByName(petDTO.getNome())
                .ifPresent(a -> {
                    throw new RuntimeException("Pet já cadastrado");
                });

        PetEntity novoPet = PetEntity.builder()
                .nome(petDTO.getNome().trim())
                .sobrenome(petDTO.getSobrenome().trim())
                .tipo(petDTO.getTipo())
                .sexo(petDTO.getSexo())
                .idade(petDTO.getIdade())
                .peso(petDTO.getPeso())
                .raca(tratarCampoTexto(petDTO.getRaca()))
                .cidade(tratarCampoTexto(petDTO.getCidade()))
                .rua(tratarCampoTexto(petDTO.getRua()))
                .numero(tratarCampoTexto(petDTO.getNumero()))
                .build();

        petRepository.save(novoPet);
    }

    private String tratarCampoTexto(String valor) {
        return (valor == null || valor.trim().isEmpty()) ? NAO_INFORMADO : valor.trim();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletarPet(Integer petId) throws Exception {
        PetEntity pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));
        petRepository.delete(pet);
    }
}