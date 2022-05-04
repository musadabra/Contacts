FROM openjdk:17
ADD target/contacts.jar contacts.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "/contacts.jar"]