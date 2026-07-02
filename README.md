# PetSpring API - Sistema de Gestão para Abrigo de Animais

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)
![JUnit5](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)

API RESTful desenvolvida em Java com Spring Boot para o gerenciamento de um abrigo de animais. O sistema permite o cadastro de pets, adoções, gerenciamento de donos (adotantes) e buscas avançadas, aplicando rigorosos padrões de arquitetura e qualidade de software.

## Tecnologias e Arquitetura

* **Linguagem & Framework:** Java (JDK 21+) e Spring Boot 3.x
* **Banco de Dados:** MySQL & Spring Data JPA (Hibernate)
* **Testes:** JUnit 5, Mockito e AssertJ (Cobertura de regras de negócio isoladas)
* **Design Patterns & Boas Práticas:**
    * Arquitetura em Camadas (Controller, Service, Repository)
    * Data Transfer Object (DTO) Pattern para encapsulamento e proteção de dados sensíveis da Entidade.
    * *Defensive Programming* com Bean Validation (`@Size`, `@NotBlank`, etc.) blindando a entrada de dados.
    * Global Exception Handling (`@RestControllerAdvice`) para formatação padronizada de erros.
    * Paginação nativa (`Pageable`) para alta performance em consultas.
    * Separação de Ambientes via Spring Profiles (`application-dev.yaml` vs `application.yaml`).

## Funcionalidades Principais

* **Gestão de Pets:** Cadastro, listagem paginada, atualização e deleção.
* **Gestão de Donos (Adotantes):** CRUD completo com regras de negócio estritas (ex: CPF torna-se imutável após o cadastro através de DTOs específicos de Update).
* **Tratamento de Erros Padronizado:** Interceptação automática de `MethodArgumentNotValidException`, retornando ao cliente uma lista clara e formatada de quais campos falharam na validação, evitando erros 500 desnecessários.
* **Deleção Segura:** Regras de negócio que desvinculam os animais (retornando-os ao abrigo) antes da exclusão do perfil de um dono.

## Como Executar o Projeto Localmente

### Pré-requisitos
* Java JDK 21 ou superior
* Maven instalado
* Banco de dados MySQL rodando na porta 3306

### Passos para iniciar
1. Clone o repositório:
   ```bash
   git clone [https://github.com/SEU_USUARIO/petspring.git](https://github.com/SEU_USUARIO/petspring.git)
