# NSRP CHALLENGE

Aplicação com finalidade de cadastro de campanhas promocionais.

#### Sobre a aplicação

A utilização da aplicação será através das API's disponibilizadas, o payload e as URI's poderão ser consultadas na documentação
disponibilizada na ferramenta swagger.

O cadastro de campanha irá automáticamente cadastrar um time caso o mesmo não exista. Caso exista, a mesma referência será 
utilizada para o cadastro de outras campanhas para o mesmo time. 

A alteração no cadastro de campanhas será notificada através do tópico ``CampanhaUpdateTopic`` configurado no ActiveMQ. 
Para os interessados em consumir esta informação, será necessário "assinar" o tópico para receber a informação
no formato json.

A consulta de campanhas irá retornar apenas campanhas vigentes a partir da data da consulta, ou seja, data inicio superior 
a data atual e data fim inferior a data atual.

A aplicação possui uma validação para que as datas da campanha não sejam inválidas, ou seja, a data de inicio seja superior
a data final. A aplicação possui uma lógica para evitar colisão de datas no cadastro de campanhas.

#### Técnologias utilizadas

* Java 8
* Spring Boot
* Spring JPA
* Rest
* Active MQ
* JPA
* JMS
* Swagger
* Jacoco
* H2

#### Execução da aplicação

A execução da aplicação poderá ser feita através das formas abaixo: 

* Importar o projeto em qualquer IDE Java compatível com as tecnologias citadas, o start
  da aplicação será feito através da execução da classe _NsrpChallengeApplication_

* Na raiz do projeto, executar o comando `mvn spring-boot:run`

* Executar o comando `mvn clean install` e em seguida o comando `java -jar target/nsrp-challenge-campanha-0.0.1-SNAPSHOT.jar`

* A configuração padrão estará pronta para rodar em docker, juntamente com o docker-compose do projeto.
Para executar localmente sem depender do ambiente, basta adicionar a configuração de profile do spring `-Dspring.profiles.active=test`
antes de iniciar a aplicação para execução com base H2 em memória.

#### Cobertura de testes

A cobertura de testes unitários estará disponível na pasta target/site/ do projeto, para acessar o conteúdo será necessário
executar o comando

`mvn clean test`

O conteúdo poderá ser visualizado em qualquer navegador executando o arquivo disponível em _target/site/jacoco/index.html_

#### Documentação das API's

A ferramenta swagger foi utilizada para documentar a utilização das API's disponíveis, o acesso poderá ser feito através
do link [http://localhost:8080/nsrp-challenge-campanha/swagger-ui.html](http://localhost:8080/nsrp-challenge-campanha/swagger-ui.html)  quando a aplicação
estiver disponível.

#### Deploy

O build no maven irá gerar por padrão (caso os testes não falhem), uma imagem em docker com o nome `nsrp-challenge-campanha`
e tag `latest`. A imagem gerada estará pronta para ser utilizada no docker-compose configurado para trabalhar com os 
demais serviços disponíveis, o banco de dados Mysql e o ActiveMQ.



