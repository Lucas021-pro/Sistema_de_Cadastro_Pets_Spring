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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DonoServiceTest {

    @Mock
    private IDonoRepository donoRepository;

    @Mock
    private IPetRepository petRepository;

    @InjectMocks
    private DonoService donoService;

    private DonoEntity donoEntity;

    @BeforeEach
    void setUp() {
        donoEntity = DonoEntity.builder()
                .donoId(1)
                .nome("João")
                .cpf("123.456.789-00")
                .telefone("11999999999")
                .cidade("São Paulo")
                .rua("Rua A")
                .numero("100")
                .build();
    }

    private DonoDTO donoDtoValido() {
        return DonoDTO.builder()
                .cpf("123.456.789-00")
                .nome("João")
                .telefone("11999999999")
                .cidade("São Paulo")
                .rua("Rua A")
                .numero("100")
                .build();
    }

    @Nested
    @DisplayName("cadastrarDono")
    class CadastrarDono {

        @Test
        @DisplayName("deve cadastrar o dono quando o CPF ainda não existe")
        void deveCadastrarDonoComSucesso() {
            when(donoRepository.findByCpf("123.456.789-00")).thenReturn(Optional.empty());

            donoService.cadastrarDono(donoDtoValido());

            verify(donoRepository).save(any(DonoEntity.class));
        }

        @Test
        @DisplayName("deve lançar BadRequestException quando o CPF já está cadastrado")
        void deveLancarExcecaoQuandoCpfJaExiste() {
            when(donoRepository.findByCpf("123.456.789-00")).thenReturn(Optional.of(donoEntity));

            assertThatThrownBy(() -> donoService.cadastrarDono(donoDtoValido()))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage("Este Dono já existe");

            verify(donoRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("deletarDono")
    class DeletarDono {

        @Test
        @DisplayName("deve desvincular os pets do dono antes de excluí-lo")
        void deveDesvincularPetsAntesDeDeletar() {
            PetEntity pet = PetEntity.builder().petId(10).dono(donoEntity).build();

            when(donoRepository.findById(1)).thenReturn(Optional.of(donoEntity));
            when(petRepository.findByDonoDonoId(1)).thenReturn(List.of(pet));

            donoService.deletarDono(1);

            assertThat(pet.getDono()).isNull();
            verify(petRepository).save(pet);
            verify(donoRepository).delete(donoEntity);
        }

        @Test
        @DisplayName("deve lançar NotFoundException quando o dono não existe")
        void deveLancarExcecaoQuandoDonoNaoExiste() {
            when(donoRepository.findById(99)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> donoService.deletarDono(99))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("Dono não encontrado");

            verify(donoRepository, never()).delete(any());
        }
    }

    @Nested
    @DisplayName("buscarDonosPorId")
    class BuscarDonosPorId {

        @Test
        @DisplayName("deve retornar o DTO quando o dono existe")
        void deveRetornarDonoExistente() {
            when(donoRepository.findById(1)).thenReturn(Optional.of(donoEntity));

            DonoResponseDTO resultado = donoService.buscarDonosPorId(1);

            assertThat(resultado.getDonoId()).isEqualTo(1);
            assertThat(resultado.getCpf()).isEqualTo("123.456.789-00");
        }

        @Test
        @DisplayName("deve lançar NotFoundException quando o dono não existe")
        void deveLancarExcecaoQuandoNaoEncontrado() {
            when(donoRepository.findById(99)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> donoService.buscarDonosPorId(99))
                    .isInstanceOf(NotFoundException.class);
        }
    }

    @Nested
    @DisplayName("atualizarDono")
    class AtualizarDono {

        @Test
        @DisplayName("deve atualizar os campos editáveis sem alterar o CPF")
        void deveAtualizarDonoSemAlterarCpf() {
            when(donoRepository.findById(1)).thenReturn(Optional.of(donoEntity));

            DonoUpdateDto updateDTO = DonoUpdateDto.builder()
                    .nome("João Atualizado")
                    .telefone("11888888888")
                    .cidade("Campinas")
                    .rua("Rua B")
                    .numero("200")
                    .build();

            DonoResponseDTO resultado = donoService.atualizarDono(1, updateDTO);

            assertThat(resultado.getNome()).isEqualTo("João Atualizado");
            assertThat(resultado.getCidade()).isEqualTo("Campinas");
            assertThat(resultado.getCpf()).isEqualTo("123.456.789-00");
        }

        @Test
        @DisplayName("deve lançar NotFoundException quando o dono não existe")
        void deveLancarExcecaoQuandoDonoNaoExiste() {
            when(donoRepository.findById(99)).thenReturn(Optional.empty());

            DonoUpdateDto updateDTO = DonoUpdateDto.builder()
                    .nome("Qualquer")
                    .telefone("11888888888")
                    .build();

            assertThatThrownBy(() -> donoService.atualizarDono(99, updateDTO))
                    .isInstanceOf(NotFoundException.class);
        }
    }

    @Nested
    @DisplayName("listAllDonos")
    class ListAllDonos {

        @Test
        @DisplayName("deve retornar a página de donos convertida para DTO")
        void deveRetornarPaginaDeDonos() {
            Pageable pageable = PageRequest.of(0, 20);
            when(donoRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(donoEntity)));

            Page<DonoResponseDTO> resultado = donoService.listAllDonos(pageable);

            assertThat(resultado.getContent()).hasSize(1);
            assertThat(resultado.getContent().get(0).getNome()).isEqualTo("João");
        }
    }
}