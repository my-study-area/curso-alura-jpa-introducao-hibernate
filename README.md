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

### Aula 01.06 - Diferença entre Hibernate e JPA
Qual a diferença entre Hibernate e JPA?  
`R:` JPA é uma especificação e Hibernate é uma de suas implementações

### Aula 01.07 - Criando um projeto com JPA
Com um projeto maven criado adicione o código abaixo no `pom.xml`:
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.8.0</version>
            <configuration>
                <release>8</release>
            </configuration>
        </plugin>
    </plugins>
</build>

<dependencies>
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-entitymanager</artifactId>
        <version>5.4.27.Final</version>
    </dependency>

    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <version>1.4.200</version>
    </dependency>
</dependencies>
```

### Aula 01.08 - Faça como eu fiz

### Aula 01.09 - O que aprendemos?
- As desvantagens de se utilizar o JDBC para acessar bancos de dados em Java;
- A história de criação do Hibernate e da JPA;
- Como criar uma aplicação Maven e adicionar o Hibernate como dependência.

## Módulo 02 - Configurações e EntityManager

### Aula 02.01 - Arquivo persistence.xml
- Crie o arquivo `src/main/resources/META-INF/persistence.xml`.
- O arquivo incial do `persistence.xml` possui a seguinte estrutura inicial:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.2"
    xmlns="http://xmlns.jcp.org/xml/ns/persistence"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd">
</persistence>
```
- Para iniciar a configuração devemos adicionar a tag `persistence-unit`, dentro do `persistence.xml`, que possui duas propriedades: `name` e `transaction-type`. O `name` pode ser adicionado livremente, já a propriedade `transaction-type` pode recebe dois valores: `RESOURCE_LOCAL` ou `JTA`. No `JTA` (Java Transaction API) as transações são controladas por um servidor de aplicação como Wildfly ou Payara, já no `RESOURCE_LOCAL` são controladas pelo desenvolvedor. 
```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.2"
    xmlns="http://xmlns.jcp.org/xml/ns/persistence"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd">

    <persistence-unit name="loja" transaction-type="RESOURCE_LOCAL">
    </persistence-unit>
</persistence>
```
- Devemos ter um `persistence-unit` para cada banco de dados.
- Dentro do `persistence-unit` possuímos algumas propriedades (`properties`) como driver, URL de conexão com o banco de dados, usuário e senha do bancos dados e etc.
```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.2"
    xmlns="http://xmlns.jcp.org/xml/ns/persistence"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd">

    <persistence-unit name="loja" transaction-type="RESOURCE_LOCAL">
        <properties>
            <property name="javax.persistence.jdbc.driver" value="org.h2.Driver"/>
            <property name="javax.persistence.jdbc.url" value="jdbc:h2:mem:loja"/>
            <property name="javax.persistence.jdbc.user" value="sa"/>
            <property name="javax.persistence.jdbc.password" value=""/>

            <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/>
        </properties>
    </persistence-unit>
</persistence>
```

### Aula 02.02 - Persistence Unit
Qual o objetivo da tag `<persistence-unit>` no arquivo persistence.xml?  
`R:` Agrupar as configurações de uma unidade de persistência, que representa um banco de dados utilizado pela aplicação

### Aula 02.03 - Mapeando uma entidade
- Para mapearmos uma entidade utilizamos as anotações: `@Entity`
`@Table(name = "produtos")`, `@Id`, `@GeneratedValue(strategy = GenerationType.IDENTITY)`.
- Ao mapearmos uma entidade é interessante utilizarmos as anotações da especificação no lugar das anotações das implementações. Exemplo: `javax.persistence.Entity;`
```java
package br.com.alura.loja.modelo;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "produtos")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String descricao;
	private BigDecimal preco;

  // getters e setters
}
```
- Também precisamos informar ao `persintence.xml` as classes mapeadas através da tag `<class>br.com.alura.modelo.Produto</class>` dentro do `<persistence-unit>`. No caso no Hibernate esse passo não é necessário e é realizado automaticamente, mas não podemos garantir que as outras implementações realizam o mesmo comportamento.
```xml
<persistence-unit name="loja" transaction-type="RESOURCE_LOCAL">
    <class>br.com.alura.modelo.Produto</class>
    <properties>
        <property name="javax.persistence.jdbc.driver" value="org.h2.Driver"/>
        <property name="javax.persistence.jdbc.url" value="jdbc:h2:mem:loja"/>
        <property name="javax.persistence.jdbc.user" value="sa"/>
        <property name="javax.persistence.jdbc.password" value=""/>

        <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/>
    </properties>
</persistence-unit>
```

### Aula 02.04 - Entidades da JPA
Qual a melhor definição de uma entidade JPA?  
`R:` É uma classe que faz o mapeamento de uma tabela do banco de dados. Uma entidade JPA funciona como um espelho de uma tabela no banco de dados.

### Aula 02.05 - Persistindo uma entidade
- adicionamos a propriedade`<property name="hibernate.show_sql" value="true"/>` no aquivo `src/main/resources/META-INF/persistence.xml` para dizer ao `Hibernate` para mostrar todas as instruções SQL no console.
- adicionamos a propriedade `<property name="hibernate.hbm2ddl.auto" value="update"/>` no aquivo `src/main/resources/META-INF/persistence.xml` para dizer ao `Hibernate` qual será a forma de criação das tabelas do banco de dados.

### Aula 02.06 - Transação
Quando devemos iniciar e comitar uma transação ao persistir uma entidade?  
`R:` Ao realizar operações de escrita no banco de dados, como insert, update e delete. Operações de escrita exigem o uso de transações.

### Aula 02.07 - Faça como eu fiz

### Aula 02.08 - O que aprendemos?
- Como configurar a JPA via arquivo `persistence.xml`;
- Como mapear entidades JPA;
- Como utilizar o EntityManager para persistir entidades no banco de dados.

## Módulo 03 - Mapeando Entidades
### Aula 03.01 - Projeto da aula anterior

### Aula 03.02 - Organizando o código

### Aula 03.03 - Mais mapeamentos
- Ao mapearmos uma propriedade de uma entidade com Enum, por padrão, os dados inseridos no banco serão armazenados pela ordem de inserção dos itens no Enum. É uma boa prática anotarmos uma entidade com Enum usando `@Enumerated(EnumType.STRING)` e evitarmos que a alteração dos valores de enum sejam alterados.

### Aula 03.04 - Tipos de atributos
Quais tipos de atributos podemos mapear sem a necessidade de configurações adicionais via anotações da JPA?  
`R:` Os tipos primitivos, atributos do tipo String e algumas classes do Java, como LocalDate e BigDecimal. Esses tipos podem ser mapeados automaticamente, sem a necessidade de configurações adicionais.

### Aula 03.05 - Mapeando relacionamentos
- Utilizamos a anotação `@ManyToOne` para criar um relacionamento de muitos para um.

### Aula 03.06 - Cardinalidade do relacionamento
Aprendemos a utilizar a anotação @ManyToOne para indicar a cardinalidade de um relacionamento. Qual a cardinalidade padrão assumida pela JPA, no caso de um atributo que representa um relacionamento não possuir anotações?  
`R:` É obrigatório adicionar alguma anotação de cardinalidade em todos os atributos que representam relacionamentos. A JPA não assume uma cardinalidade padrão quando não anotamos um atributo.

### Aula 03.07 - Faça como eu fiz

### Aula 03.08 - O que aprendemos?
- Como escrever uma classe DAO utilizando a JPA;
- Como mapear atributos do tipo Enum em uma entidade;
- Como mapear um relacionamento entre entidades.

## Módulo 04 - Ciclo de vida de uma entidade
### Aula 04.01 - Projeto da aula anterior

### Aula 04.02 - Estados no insert da entidade
- No ciclo de vida das entidade JPA, no insert, temos: `transient`, `managed` e `detached`.
- `Transient`: nunca foi persistida e não é gerenciada pelo JPA. Todas as alterações do objeto não são salvas no banco de dados. È o estado inicial de objeto, quando criamos um novo objeto.
- `Managed`: é estado de um objeto ao passarmos para o método `.persist(obj)` do `EntityManager`. O objeto é gerenciado pelo JPA e todas alterações do objeto são refletidas no banco de dados quando utilizamos o método `commit()` do EntityManager. O método `flush()`  pode ser utilizado para refletir as alterações do objeto no banco de dados antes de executar o método `commit()`.
- `Detached`: é o estado de um objeto que foi gerenciado pelo JPA, mas após o uso do método `.clear()` ou `.close()` do EntityManager ele deixa de sincronizar com o banco de dados.

### Aula 04.03 - Estados no update da entidade
- A atualização dos dados no banco de dados de um objeto ocorre quando alteramos os valores de um objeto no estado `managed`, porém se o objeto estiver no estado `detached` precisamos passar o objeto para o estado `managed` utilizando o método `merge(obj)`.
- Uma curiosidade do método `merge(obj)` é que ele não altera o objeto passado como parâmetro para o estado `managed`, e sim, retorna uma objeto `managed`. Então precisamos atribuir o retorno do método `merge(obj)` para uma variável para que seja possível alterar o valores e então persistir as alterações do objeto no banco de dados. Ex:
```java
em.getTransaction().begin();
em.persist(celulares);
celulares.setNome("update");
em.flush();
em.clear();

celulares = em.merge(celulares);
celulares.setNome("update 2");
em.flush();

em.getTransaction().commit();
em.close();
```

### Aula 04.04 - Método merge
Analise o seguinte trecho de código e indique qual o resultado esperado:
```java
em.getTransaction().begin();
Produto produto = em.find(Produto.class, 1l);
produto.setDescricao(“Teste 1”);
em.flush();
produto.setDescricao(“Teste 2”);
em.merge(produto);
produto.setDescricao(“Teste 3”);
em.getTransaction().commit();
em.close();
```
`R:` A entidade produto será atualizada no banco de dados com a descrição **Teste 3**. No código anterior o merge acabou sendo indiferente, pois a entidade já estava no estado Managed
