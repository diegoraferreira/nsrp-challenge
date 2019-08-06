FROM java:8-jdk-alpine

COPY ./target/nsrp-challenge-campanha-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch nsrp-challenge-campanha-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","nsrp-challenge-campanha-0.0.1-SNAPSHOT.jar"]