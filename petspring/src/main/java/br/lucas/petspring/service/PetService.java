package br.lucas.petspring.service;

import br.lucas.petspring.database.model.DonoEntity;
import br.lucas.petspring.database.model.PetEntity;
import br.lucas.petspring.database.repository.IDonoRepository;
import br.lucas.petspring.database.repository.IPetRepository;
import br.lucas.petspring.dto.PetDTO;
import br.lucas.petspring.dto.PetResponseDTO;
import br.lucas.petspring.exception.BadRequestException;
import br.lucas.petspring.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {

    private final IPetRepository petRepository;
    private final IDonoRepository donoRepository;
    private static final String NAO_INFORMADO = "NÃO INFORMADO";

    public void criarPet(PetDTO petDTO) {
        PetEntity novoPet = PetEntity.builder()
                .protocolo(UUID.randomUUID().toString())
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
                .orElseThrow(() -> new NotFoundException("Pet não encontrado"));
        petRepository.delete(pet);
    }

    public List<PetResponseDTO> listAllPets() {
        return petRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public PetResponseDTO buscarPetPorId(Integer id) {
        PetEntity pet = petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet não encontrado"));

        return converterParaDTO(pet);
    }

    public void adotarPet(Integer petId, Integer donoId) {
        PetEntity pet = petRepository.findById(petId)
                .orElseThrow(() -> new NotFoundException("Pet não encontrado"));
        if (pet.getDono() != null) throw new BadRequestException("Este pet já foi adotado");

        DonoEntity donoEncontrado = donoRepository.findById(donoId)
                .orElseThrow(() -> new NotFoundException("Dono não encontrado"));
        pet.setDono(donoEncontrado);
        petRepository.save(pet);
    }

    public PetResponseDTO atualizarPet(Integer petId, PetDTO petDTO) {
        PetEntity pet = petRepository.findById(petId)
                .orElseThrow(() -> new NotFoundException("Pet não encontrado"));

        pet.setNome(petDTO.getNome());
        pet.setSobrenome(petDTO.getSobrenome());
        pet.setTipo(petDTO.getTipo());
        pet.setSexo(petDTO.getSexo());
        pet.setRaca(petDTO.getRaca());
        pet.setPeso(petDTO.getPeso());
        pet.setIdade(petDTO.getIdade());
        pet.setCidade(petDTO.getCidade());
        pet.setRua(petDTO.getRua());
        pet.setNumero(petDTO.getNumero());

        petRepository.save(pet);

        return converterParaDTO(pet);
    }

    public List<PetResponseDTO> listarPetsPorDono(Integer donoId) {
        if (!donoRepository.existsById(donoId)) {
            throw new NotFoundException("Dono não encontrado");
        }
        return petRepository.findByDonoDonoId(donoId).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    private PetResponseDTO converterParaDTO(PetEntity pet) {
        return PetResponseDTO.builder()
                .petId(pet.getPetId())
                .protocolo(pet.getProtocolo())
                .nome(pet.getNome())
                .sobrenome(pet.getSobrenome())
                .tipo(pet.getTipo())
                .sexo(pet.getSexo())
                .raca(pet.getRaca())
                .peso(pet.getPeso())
                .idade(pet.getIdade())
                .cidade(pet.getCidade())
                .rua(pet.getRua())
                .numero(pet.getNumero())
                .build();
    }
}