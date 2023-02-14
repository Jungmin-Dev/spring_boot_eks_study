FROM openjdk:17
WORKDIR /usr/src/myapp
COPY build/libs/spring_boot_eks-0.0.1-SNAPSHOT.jar /usr/src/myapp
CMD ["java", "-jar", "spring_boot_eks-0.0.1-SNAPSHOT.jar"]