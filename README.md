# NSRP CHALLENGE

Aplicação com finalidade de cadastro de campanhas promocionais.

#### Técnologias utilizadas

* Java 8
* Spring Boot
* Spring JPA
* Rest
* Active MQ
* JPA
* Jacoco
* H2

#### Execução da aplicação

A execução da aplicação poderá ser feita através das formas abaixo: 

* Importar o projeto em qualquer IDE Java compatível com as tecnologias citadas, o start
  da aplicação será feito através da execução da classe _NsrpChallengeApplication_

* Na raiz do projeto, executar o comando `mvn spring-boot:run`

* Executar o comando `mvn clean install` e em seguida o comando `java -jar target/nsrp-challenge-0.0.1-SNAPSHOT.jar`

#### Cobertura de testes

A cobertura de testes unitários estará disponível na pasta target do projeto, para acessar o conteúdo será necessário
executar o comando

`mvn clean test`

O conteúdo poderá ser visualizado em qualquer navegador executando o arquivo disponível em _target/site/jacoco/index.html_

#### To-do

* Analisar a viabilidade de trocar a implementação de Rest para WebFlux.


