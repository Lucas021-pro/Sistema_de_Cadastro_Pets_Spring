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

    @NotBlank(message = "O nome é obrigatório.")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "O nome não pode conter caracteres especiais ou números.")
    private String nome;

    @NotBlank(message = "O sobrenome é obrigatório.")
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
    @DecimalMin(value = "0.0", message = "A idade não pode ser negativa.")
    @DecimalMax(value = "20.0", message = "A idade máxima permitida é de 20 anos.")
    private Double idade;

    @Size(max = 80, message = "A raça não pode ter mais de 80 caracteres.")
    private String raca;

    @Size(max = 80, message = "A cidade não pode ter mais de 80 caracteres.")
    private String cidade;

    @Size(max = 100, message = "A rua não pode ter mais de 100 caracteres.")
    private String rua;

    @Size(max = 10, message = "O número não pode ter mais de 10 caracteres.")
    private String numero;
}
