FROM openjdk:8-jre
RUN mkdir /usr/app
COPY target/*.jar /usr/app
WORKDIR /usr/app
CMD ["java","-jar","buenos_libritos_bl.jar"]
