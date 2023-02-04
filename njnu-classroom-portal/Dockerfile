FROM openjdk:18-slim

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' > /etc/timezone

ADD /target/njnu-classroom-portal-4.0.0.jar /server.jar
