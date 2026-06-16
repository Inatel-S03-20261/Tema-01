# Arquitetura e padrões de projeto

# Arquitetura do Sistema

O serviço segue uma arquitetura em camadas, visando maior organização, desacoplamento e facilidade de manutenção.

```text
Cliente
    ↓
Controller
    ↓
Facade
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

---

# Fluxo de Autenticação

```text
Usuário
    ↓
POST /auth/login
    ↓
AuthController
    ↓
AuthFacade
    ↓
AuthService
    ↓
UserRepository
    ↓
PostgreSQL

Credenciais válidas
    ↓
JwtAuthenticationStrategy
    ↓
Token JWT
    ↓
Usuário

Demais APIs
    ↓
Recebem o token
    ↓
Validam o JWT
    ↓
Liberam acesso aos recursos
```

---

# Estrutura do Projeto

```text
src
└── main
    └── java
        └── br.com.authservice
            ├── controllers
            ├── dto
            ├── entities
            ├── repositories
            ├── services
            ├── facades
            ├── factories
            ├── strategies
            ├── security
            ├── config
            ├── exceptions
            └── utils
```

---

# Padrões de Projeto Utilizados

## Repository Pattern

Responsável pela abstração da camada de acesso aos dados.

### Benefícios

* Centralização das operações de persistência;
* Maior desacoplamento;
* Melhor testabilidade;
* Facilidade para troca de banco de dados.

### Exemplo

```java
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}
```

---

## Factory Method Pattern

Utilizado para encapsular a criação de objetos do domínio.

### Benefícios

* Centraliza a criação das entidades;
* Reduz duplicação de código;
* Facilita futuras alterações.

### Exemplo

```java
User user = UserFactory.create(
    username,
    email,
    encryptedPassword
);
```

---

## Strategy Pattern

Permite a utilização de diferentes estratégias de autenticação.

### Benefícios

* Flexibilidade;
* Fácil extensão;
* Baixo acoplamento.

### Estrutura

```text
AuthenticationStrategy
        │
        ├── JwtAuthenticationStrategy
        ├── ApiKeyAuthenticationStrategy
        └── OAuthAuthenticationStrategy
```

---

## Facade Pattern

Responsável por simplificar a comunicação entre os controladores e os serviços.

### Benefícios

* Redução da complexidade;
* Melhor organização;
* Maior legibilidade.

### Estrutura

```text
AuthController
       ↓
AuthFacade
       ↓
AuthService
```
