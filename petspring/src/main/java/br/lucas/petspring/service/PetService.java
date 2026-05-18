package br.lucas.petspring.service;

import br.lucas.petspring.database.model.PetEntity;
import br.lucas.petspring.database.repository.IPetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetService {

    private final IPetRepository petRepository;

    public void criarPet(PetEntity pet)
}
