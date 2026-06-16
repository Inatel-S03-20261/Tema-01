# Auth Service - Microsserviço de Autenticação e Autorização

## Descrição

O **Auth Service** é um microsserviço responsável pelo gerenciamento da autenticação e autorização dos usuários da aplicação.

Sua principal função é atuar como porta de entrada para o sistema, realizando:

- Cadastro de usuários;
- Login; 
- Validação de credenciais;
- Emissão de tokens JWT;
- Integração com outras APIs do ecossistema.

As demais aplicações utilizam o token gerado por este serviço para controlar o acesso aos recursos protegidos.

---
## Objetivos

- Centralizar o processo de autenticação;
- Garantir a segurança das aplicações do sistema;
- Fornecer uma API independente para emissão e validação de tokens;
- Permitir integração com outros microsserviços;
- Facilitar futuras expansões da arquitetura.

---

## Contexto

O sistema é composto por múltiplas aplicações que se comunicam entre si.

Neste cenário:

- Este serviço é responsável pela **autenticação dos usuários**  
- As demais APIs utilizam o **token gerado** para liberar acesso aos recursos  

---

## Fluxo de Autenticação

1. O usuário realiza login  
2. O sistema valida as credenciais  
3. Um token de acesso é gerado  
4. O token é utilizado para acessar outras aplicações  

---

## Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT
- Docker
- Maven

---

## Como Rodar o Banco de Dados Localmente

### Pré-requisitos

- Docker instalado na máquina

---

### 1. Verificar se a porta `5432` está livre

#### PowerShell

```powershell
netstat -ano | findstr :5432
```

#### Bash

```bash
lsof -i :5432
```

ou

```bash
ss -tulnp | grep 5432
```

Caso a porta esteja ocupada, altere a porta do PostgreSQL nos arquivos:

- `docker-compose.yaml`
- `application-dev.yaml`

---

### 2. Subir o banco de dados

Execute os comandos **dentro do diretório `auth-service`**.

#### Subir o PostgreSQL 16

```bash
docker-compose up -d
```

#### Encerrar o PostgreSQL 16

```bash
docker-compose down
```

#### Encerrar e remover todos os dados do banco

```bash
docker-compose down -v
```

---

### Acessar o PostgreSQL via terminal (`psql`)

```bash
docker exec -it auth-service-postgres psql -U postgres -d auth_service
```

### Sair do `psql`

```sql
\q
```

---

## Possível Fluxo de Integração com Outras APIs

```text
Jogador
   ↓
Cliente
   ↓ 
Auth Service (Login)
   ↓
JWT Token
   ↓
Demais Clientes validam o token
   ↓
Acesso liberado aos recursos conforme permissão
```

[Primeira versão](docs/doc1.md)
[Segunda versão](docs/doc2.md)
[Terceira versão](docs/doc3.md)


## Vídeo sobre Design Patterns

Neste vídeo, apresentamos uma explicação simples dos principais **Design Patterns** aplicados no projeto, mostrando como eles ajudam na organização do código, na separação de responsabilidades e na manutenção da aplicação.

Foram abordados padrões como:

- Repository Pattern;
- DTO Pattern;
- Mapper Pattern com MapStruct.

[![Assista ao vídeo sobre Design Patterns](https://img.youtube.com/vi/jN6hGeDHAOA/0.jpg)](https://www.youtube.com/watch?v=jN6hGeDHAOA)
