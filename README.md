## Descrição

Este projeto consiste no desenvolvimento de um microsserviço responsável pelo **cadastro, login e autenticação de usuários** em uma aplicação de Pokémon.

A aplicação atua como **porta de entrada do sistema**, sendo responsável por **emitir tokens de acesso** utilizados pelas demais APIs.

---

## Objetivo

- Permitir cadastro de usuários  
- Realizar login  
- Emitir tokens de acesso  
- Permitir integração com outras aplicações  

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

## Casos de Uso

**Usuário**
- Cadastrar conta  
- Fazer login  

**Sistema**
- Gerar token de acesso  
- Validar credenciais  


---

## Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- Docker
- JWT
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
Usuário
   ↓
Auth Service (Login)
   ↓
JWT Token
   ↓
Demais APIs validam o token
   ↓
Acesso liberado aos recursos conforme role (permissão)
```
