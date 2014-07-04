FROM dockerfile/java
MAINTAINER Dennis Vriend <dnvriend@gmail.com>

EXPOSE 8080
RUN mkdir /appl
RUN wget https://github.com/dnvriend/spray-crypter/releases/download/v0.0.2/spray-crypter_2.10-0.1-SNAPSHOT-one-jar.jar -O /appl/spray-crypter_2.10-0.1-SNAPSHOT-one-jar.jar
CMD java -jar /appl/spray-crypter_2.10-0.1-SNAPSHOT-one-jar.jar
