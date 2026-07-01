package br.lucas.petspring.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DonoUpdateDto {

    @NotBlank(message = "O nome é obrigatório.")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "O nome não pode conter caracteres especiais ou números.")
    private String nome;

    @NotBlank(message = "O telefone é obrigatório.")
    private String telefone;

    @Size(max = 80, message = "A cidade não pode ter mais de 80 caracteres.")
    private String cidade;

    @Size(max = 100, message = "A rua não pode ter mais de 100 caracteres.")
    private String rua;

    @Size(max = 10, message = "O número não pode ter mais de 10 caracteres.")
    private String numero;
}
