# curso-alura-jpa-introducao-hibernate
Curso de Persistência com JPA: Introdução ao Hibernate

## Módulo 01 - Introdução à JPA
### Aula 01.01 - Apresentação

### Aula 01.02 - Projeto inicial do treinamento
- [Projeto inicial](./2121-jpa-hibernate-persistencia-projeto_inicial.zip)

### Aula 01.03 - JDBC e seus problemas
- JDBC é uma especificação para acesso ao banco de dados em Java
- Drivers de banco de dados são implementações do JDBC. São aqueles arquivos `.jar` (drivers) que nos permitem acessar o banco de dados.
- Problemas:
  - Código muito verboso
  - Alto acoplamento com o banco de dados. Ao alterar a estrutura de uma tabela do banco de dados temos que alterar a classe DAO relacionada e todas outras classes que façam relacionamento com a tabela alterada.

### Aula 01.04 - Desvantagens do JDBC
Quais as principais desvantagens do JDBC, das quais a JPA foi criada para resolver?
- JDBC aumenta o acoplamento do código. O JDBC causa um acoplamento maior entre o código da aplicação e o banco de dados.
- Utilizar o JDBC implica em escrever um código de difícil manutenção. Códigos que utilizam JDBC são mais verbosos e isso aumenta a dificuldade de manutenção.

### Aula 01.05 - Hibernate e JPA
- Quando foi criado o Hibernate, em 2001, não existia a JPA.
- Hibernate foi alternativa ao JDBC/EJB 2
- Com o tempo foram surgindo várias alternativas ao Hibernate e não eram compatíveis ao alterar os `jars`, cada uma com suas próprias classes e métodos. A partir desse problema criaram uma especificação para ORM (Object Relational Mapping) em Java.
- Após a especificação o Hibernate implementou o JPA em 2010, utilizando o JPA 2.
- `Hibernate`, `EclipseLink` e `OpenJPA` são implementações do JPA.
- EclipseLink é a implementação de referência do JPA e é lançada junto as atualizações do JPA.
