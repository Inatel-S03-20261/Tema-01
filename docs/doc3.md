# Design Patterns Aplicados no Projeto

# Repository Pattern

## O que é Repository Pattern?

O Repository Pattern é um padrão de projeto responsável por abstrair a camada de persistência, separando a lógica de negócio da lógica de acesso ao banco de dados.

No projeto, esse padrão é implementado através do **Spring Data JPA**, que fornece uma camada de abstração para operações CRUD e consultas customizadas.

---

## Por que utilizamos?

* Separação entre regra de negócio e persistência;
* Centralização do acesso aos dados;
* Facilidade para testes;
* Baixo acoplamento;
* Integração transparente com o JPA.

---

## Implementação

```java
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsernameOrEmail(
            String username,
            String email);

}
```

---

## Como aparece na arquitetura

```text
Cliente
     ↓
Controller
     ↓
Service
     ↓
UserRepository
     ↓
PostgreSQL
```

---

## Fluxo de Dados

1. O Controller recebe a requisição HTTP;
2. O Service processa a regra de negócio;
3. O Repository realiza a consulta no banco de dados;
4. O resultado retorna para o Service;
5. O Controller retorna a resposta ao cliente.

---

## Benefícios

* Centralização do acesso aos dados;
* Facilidade para testes unitários;
* Menor acoplamento;
* Maior manutenibilidade;
* Integração transparente com Spring Data JPA.

---

# DTO Pattern

## O que é?

DTO (Data Transfer Object) é um padrão utilizado para transportar dados entre as camadas da aplicação.

Seu objetivo é evitar que as entidades do domínio sejam expostas diretamente ao cliente.

---

## Por que utilizamos?

* Desacoplamento entre API e entidades;
* Segurança dos dados expostos;
* Melhor organização;
* Facilidade para evolução da aplicação.

---

## Fluxo

```text
Request HTTP
      ↓
DTO
      ↓
Service
      ↓
Entity
```

---

## Benefícios

* Desacoplamento;
* Segurança;
* Facilidade de manutenção;
* Independência das entidades do banco;
* Maior controle sobre os dados trafegados.

---

# Mapper Pattern

## O que é?

O Mapper Pattern é responsável pela conversão entre diferentes representações de objetos.

No projeto, utiliza-se o **MapStruct**, responsável por converter DTOs em entidades e vice-versa.

---

## Por que utilizamos?

* Evitar código repetitivo;
* Centralizar as conversões;
* Facilitar a manutenção;
* Melhorar a legibilidade.

---

## Implementação

```java
@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRegisterDTO dto);

}
```

---

## Fluxo

```text
UserRegisterDTO
       ↓
UserMapper
       ↓
User Entity
```

---

## Benefícios

* Redução de código boilerplate;
* Conversões centralizadas;
* Maior legibilidade;
* Facilidade para manutenção.

---
