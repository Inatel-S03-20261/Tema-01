## Principios SOLID Aplicados no Projeto


O diagrama de classes do sistema de autenticação foi estruturado com o objetivo de reduzir acoplamento, fizemos melhorias  organização e permitir evolução futura do sistema. Assim, foram aplicados dois princípios do SOLID

![alt text](image.png)

### DIP — Princípio da Inversão de Dependência  
Diz que a regra de negócio não deve depender de detalhes técnicos

A classe AuthController dependesse diretamente do banco, caso troque o SGBD exigiria mudar controllers, mudar testes unitários e o sistema ficaria rígido.


Foi introduzida uma abstração de repositório (AuthRepository e UserRepository) para que os controllers dependam de interfaces e não da implementação concreta de persistência (DatabaseRepository).

---

### ISP — Princípio da Segregação de Interfaces
Diz que nenhuma classe deve ser obrigada a conhecer métodos que não usa.

O repositório foi dividido em interfaces específicas de autenticação e gestão de usuários, garantindo que cada controller dependa apenas das operações necessárias ao seu contexto funcional
