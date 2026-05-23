package br.lucas.petspring.dto;

import br.lucas.petspring.enums.SexoPet;
import br.lucas.petspring.enums.TipoPet;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PetDTO {

    @NotBlank (message = "O nome é obrigatório.")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "O nome não pode conter caracteres especiais ou números.")
    private String nome;

    @NotBlank (message = "O sobrenome é obrigatório.")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "O sobrenome não pode conter caracteres especiais ou números.")
    private String sobrenome;

    @NotNull(message = "O tipo (CACHORRO ou GATO) do pet é onrigatório")
    private TipoPet tipo;

    @NotNull(message = "O sexo(MACHO ou FEMEA) do pet é onrigatório")
    private SexoPet sexo;

    @NotNull(message = "O peso do pet é onrigatório")
    @Min(value = 0, message = "O peso não pode ser negativo.")
    @DecimalMin(value = "0.5", message = "O peso mínimo permitido é 0.5kg.")
    @DecimalMax(value = "60.0", message = "O peso máximo permitido é 60kg.")
    private Double peso;

    @NotNull(message = "A idade é obrigatória.")
    @DecimalMax(value = "20.0", message = "A idade máxima permitida é de 20 anos.")
    private Double idade;

    private String raca;
    private String cidade;
    private String rua;
    private String numero;
}
