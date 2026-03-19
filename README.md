## Descrição

Este projeto consiste no desenvolvimento de um microsserviço responsável pelo cadastro, login e autenticação de usuários em uma aplicação de Pokémon.

O serviço atuará como um *Authorization Server*, sendo responsável por emitir tokens de acesso que serão utilizados pelas demais APIs do sistema.

---

## Objetivo

- Permitir cadastro de usuários  
- Realizar autenticação (login)  
- Emitir tokens de acesso  
- Integrar com outras APIs da aplicação  

---

## Contexto da Arquitetura

O sistema segue o modelo baseado em OAuth2, com os seguintes elementos:

- *Authorization Server*: autentica e gera tokens  
- *Resource Server*: APIs que disponibilizam os dados  
- *Usuário*: quem utiliza a aplicação  
- *Client*: aplicações que solicitam acesso aos recursos  

---

## Fluxo de Autenticação

Será utilizado o fluxo *Client Credentials*, onde:

1. O client envia suas credenciais  
2. O servidor valida  
3. Um token de acesso é gerado  
4. O token é usado para acessar outras APIs  

---

## Casos de Uso

*Usuário*
- Cadastrar conta  
- Fazer login  

*Client*
- Solicitar token  
- Validar token  

*Administrador*
- Registrar client  

---
![use-case](https://github.com/user-attachments/assets/69837680-3d0c-4f1e-9fc2-0e2cadda4c6d)


## Observação

Este projeto é uma versão inicial e simplificada, com foco em modelagem conceitual para uma arquitetura de microsserviços.
