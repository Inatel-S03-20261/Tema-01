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

## Observação

Este projeto representa uma versão **simplificada**, com foco na **modelagem conceitual da arquitetura**.
