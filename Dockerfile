FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","/app.jar"]

#FROM ubi8/ubi-minimal/openjdk-17-runtime:latest
#COPY --chown=1001:0 target/*.jar /home/jboss/app.jar
#EXPOSE 8083
#ENTRYPOINT ["java","-Duser.timezone=Asia/Riyadh","-jar","/home/jboss/app.jar"]