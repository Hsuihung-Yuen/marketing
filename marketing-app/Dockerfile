# 基础镜像
#FROM openjdk:8u141-jre-slim

# xfg
#FROM openjdk:8-jre-slim

#瞎找的镜像，只支持amd64
FROM anapsix/alpine-java:8_server-jre_unlimited



# 作者
MAINTAINER Hhy

# 配置
ENV PARAMS=""

# 时区
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 添加应用
ADD target/marketing-app.jar /marketing-app.jar

ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /marketing-app.jar $PARAMS"]