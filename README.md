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

Você só precisa ter o **Docker Desktop** instalado e em execução. **Não** é necessário ter Java, Maven ou PostgreSQL instalados na sua máquina — tudo é compilado e executado dentro dos containers.

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) (já inclui o Docker Compose)

Para confirmar que está instalado, rode:

```bash
docker --version
docker compose version
```

### Iniciar a aplicação

A partir da raiz do projeto (a pasta que contém o `docker-compose.yml`):

```bash
docker compose up --build
```

Esse único comando faz tudo:

1. Constrói a imagem da aplicação a partir do `Dockerfile` (baixa as dependências e compila o jar dentro do container).
2. Sobe o container do **PostgreSQL** e espera até ele estar realmente pronto para aceitar conexões.
3. Sobe o container do **auth-service**, que executa as migrações do Flyway e em seguida inicia a API.

A primeira execução demora alguns minutos, pois baixa as imagens base e as dependências do Maven. As execuções seguintes são bem mais rápidas graças ao cache de camadas do Docker.

> **Dica:** Adicione `-d` para rodar em segundo plano e liberar o seu terminal:
> ```bash
> docker compose up --build -d
> ```

### Verificar se está rodando

Quando aparecer a linha de log indicando que a aplicação iniciou, abra a documentação da API no navegador:

```
http://localhost:8080/swagger-ui.html
```

Se o Swagger UI carregar e mostrar os endpoints disponíveis, a aplicação está no ar e acessível.

## Acessando o banco de dados

Há duas formas de acessar o banco. Use a que preferir.

### Opção 1 — Cliente gráfico (DBeaver, IntelliJ, etc.)

Conecte usando estas configurações:

| Configuração | Valor          |
|--------------|----------------|
| Host         | `localhost`    |
| Porta        | `5433`         |
| Banco        | `auth_service` |
| Usuário      | `postgres`     |
| Senha        | `postgres`     |

> Repare que a porta é a **5433**, e não a 5432 padrão — essa é a porta na qual o banco está exposto para a sua máquina.

### Opção 2 — Direto pelo terminal (psql dentro do container)

Com os containers rodando, você pode abrir um terminal `psql` diretamente dentro do container do Postgres, sem instalar nada:

```bash
docker compose exec postgres psql -U postgres -d auth_service
```

Uma vez dentro do `psql`, alguns comandos úteis:

```sql
-- Listar todas as tabelas
\dt

-- Ver a estrutura da tabela de usuários (descobrir os nomes exatos das colunas)
\d users

-- Listar os usuários cadastrados
SELECT * FROM users;

-- Sair do psql
\q
```

> O comando `\d users` é importante: ele mostra os nomes reais das colunas e da tabela, que você vai precisar na próxima seção. Ajuste os nomes (`users`, `role`, `email`...) caso no seu schema sejam diferentes.

## Criando o primeiro usuário administrador

O endpoint de registro cria apenas usuários comuns, e promover alguém a administrador exigiria já estar autenticado como um admin. Ou seja: o **primeiro** admin não pode ser criado pelas requisições da API — é preciso criá-lo diretamente no banco. Abaixo estão duas formas de fazer isso.

### Método recomendado — Registrar e promover

Esse é o caminho mais seguro, porque a própria aplicação gera o hash BCrypt da senha do jeito correto. Você só precisa promover o cargo depois.

**Passo 1.** Registre um usuário normalmente pela API (via Swagger ou cURL), usando o endpoint de registro da aplicação:

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email": "admin@exemplo.com", "password": "suaSenhaForte123"}'
```

> Ajuste a URL e o corpo (`body`) do JSON para corresponder ao seu endpoint e aos seus campos reais.

**Passo 2.** Acesse o banco (Opção 1 ou 2 acima) e promova esse usuário a administrador com um único `UPDATE`:

```sql
UPDATE users
SET role = 'ADMIN'
WHERE email = 'admin@exemplo.com';
```

> Use o valor de cargo exato que o seu enum define. Pode ser `'ADMIN'` ou `'ROLE_ADMIN'`, por exemplo — confira na sua entidade ou rodando um `SELECT DISTINCT role FROM users;`.

Pronto. Como a senha foi cadastrada pela aplicação, o login desse admin já funciona normalmente pela API.

### Método alternativo — Inserir direto via SQL

Caso você prefira não passar pelo endpoint de registro, é possível inserir o usuário direto no banco. O detalhe é que a senha **precisa estar com hash BCrypt** — não dá para gravar texto puro, senão o login falha. A imagem do PostgreSQL já traz a extensão `pgcrypto`, que gera hashes BCrypt compatíveis com o Spring Security.

Conectado ao banco, rode:

```sql
-- Habilita a extensão (só precisa rodar uma vez)
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Insere o admin com a senha já em hash BCrypt
INSERT INTO users (email, password, role)
VALUES (
  'admin@exemplo.com',
  crypt('suaSenhaForte123', gen_salt('bf', 10)),
  'ADMIN'
);
```

O `gen_salt('bf', 10)` gera um salt BCrypt com custo 10, o mesmo padrão do `BCryptPasswordEncoder` do Spring, então o login funciona normalmente.

> Adapte os nomes das colunas (`email`, `password`, `role`) e adicione outras colunas obrigatórias que a sua tabela tenha (por exemplo, `id`, `created_at`, etc.) conforme o que apareceu no `\d users`.

## Parando e reiniciando

### Parar a aplicação

Se estiver rodando em primeiro plano, pressione `Ctrl + C` no terminal.

Se estiver em modo desanexado (`-d`), pare com:

```bash
docker compose stop
```

Em ambos os casos, os containers são parados **mantendo os dados do banco**.

### Rodar novamente depois

A partir da mesma pasta do projeto:

```bash
docker compose up -d
```

Você só precisa do `--build` de novo se tiver alterado o código-fonte da aplicação ou o `Dockerfile`. Para uma reinicialização normal, o `up` simples reaproveita a imagem já construída e é bem mais rápido.

### Resetar o banco de dados (cuidado)

Os dados do banco persistem entre as execuções em um volume do Docker. Se algum dia você quiser um banco **completamente limpo** — re-executando todas as migrações do zero — remova o volume:

```bash
docker compose down -v
```

> ⚠️ A flag `-v` **apaga todos os dados do banco**. Use apenas quando quiser intencionalmente começar do zero. Sem o `-v`, o `docker compose down` preserva os seus dados.

## Configuração

A aplicação lê suas configurações a partir de variáveis de ambiente, com valores padrão para desenvolvimento local definidos no `application.yaml`. Ao rodar via Docker Compose, as configurações de conexão com o banco são fornecidas automaticamente no `docker-compose.yml`, então nenhuma configuração manual é necessária.

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
