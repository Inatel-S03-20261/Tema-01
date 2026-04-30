# Sistema de Autenticação de Usuários

## 📌 Descrição do Projeto

Este projeto consiste na modelagem arquitetural de um sistema de autenticação de usuários desenvolvido na disciplina de Arquitetura de Software.

O objetivo principal foi projetar um sistema capaz de realizar:

* cadastro de usuários;
* autenticação (login);
* geração de token de acesso;
* verificação de token para autorização.

Mais do que implementar funcionalidades, o foco do projeto foi aplicar boas práticas de engenharia de software, priorizando baixo acoplamento, alta coesão e facilidade de evolução do sistema.

O desenvolvimento foi guiado pelos conceitos apresentados no livro **Engenharia de Software Moderna**, de Marco Túlio Valente.

---

## 🧠 Visão Arquitetural

O sistema foi organizado seguindo uma arquitetura em camadas, separando responsabilidades entre:

* **Controllers** → comunicação com o cliente;
* **Repositories** → acesso a dados;
* **Entidades de Domínio** → representação do usuário.

Essa divisão permite que mudanças na infraestrutura não impactem diretamente as regras de negócio.

### Estrutura Geral

Controller → Repository → Entidade

---

## 🎯 Casos de Uso do Sistema

O sistema possui três funcionalidades principais:

### ✔ Cadastro de Usuário

Responsável pela criação e persistência de novos usuários.

### ✔ Autenticação de Usuário (Login)

Valida as credenciais do usuário e gera um token de acesso.

A geração de token é modelada como um caso obrigatório do processo de autenticação.

### ✔ Verificação de Token

Permite validar sessões e controlar o acesso a recursos protegidos.

Essa abordagem possibilita autenticação **stateless**, facilitando escalabilidade.

---

## 🧩 Diagrama de Classes

O diagrama foi estruturado para separar claramente responsabilidades:

### AuthController

Responsável pelas operações de autenticação:

* login;
* verificação de token.

### UserController

Responsável pelo gerenciamento de usuários:

* cadastro;
* busca;
* atualização;
* remoção.

### UserRepository (Interface)

Define o contrato de acesso aos dados do usuário.

Os controllers dependem desta abstração, e não da implementação concreta do banco.

### Implementação do Repository

Responsável pela persistência e comunicação com o banco de dados.

### Entidade User

Representa o domínio do sistema contendo apenas os dados do usuário.

---

## 🏗️ Repository Pattern

Foi aplicado o **Repository Pattern** para desacoplar regras de negócio da tecnologia de persistência.

Benefícios obtidos:

* independência do banco de dados;
* facilidade para testes unitários;
* substituição de tecnologias sem alteração dos controllers;
* maior organização arquitetural.

---

## 🔷 Princípios SOLID Aplicados no Projeto

O projeto foi estruturado com o objetivo de reduzir acoplamento, melhorar organização e permitir evolução futura do sistema.

### DIP — Princípio da Inversão de Dependência

Define que módulos de alto nível não devem depender de módulos de baixo nível, mas sim de abstrações.

Inicialmente, os controllers dependeriam diretamente do banco de dados, tornando o sistema rígido e difícil de manter.

Para resolver isso, foi introduzida a abstração `UserRepository`, fazendo com que:

* Controllers dependam de interfaces;
* A infraestrutura torne-se apenas um detalhe de implementação;
* A troca de SGBD não impacte a lógica de negócio.

---

### ISP — Princípio da Segregação de Interfaces

Estabelece que nenhuma classe deve depender de métodos que não utiliza.

A separação entre `AuthController` e `UserController` garante que cada componente utilize apenas as operações necessárias ao seu contexto.

Isso evita interfaces grandes e reduz dependências desnecessárias.

---

## ✅ Benefícios Arquiteturais Obtidos

* baixo acoplamento entre camadas;
* alta coesão entre responsabilidades;
* facilidade de manutenção;
* melhor testabilidade;
* preparação para evolução futura do sistema.

---

## 👥 Contexto Acadêmico

Projeto desenvolvido como atividade prática da disciplina de Arquitetura de Software, com foco na aplicação de padrões arquiteturais e princípios de design orientado a objetos.
