FROM hub.c.163.com/dwyane/openjdk:8
VOLUME /tmp
ADD target/*.jar erp-api.jar
RUN echo "Asia/Shanghai" > /etc/timezone
EXPOSE 80
ENV spring.profiles.active="prod"
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","erp-api.jar"]