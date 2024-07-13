# 基础镜像
#FROM openjdk:8u141-jre-slim

# ARM64
FROM openjdk:8-jre-slim@sha256:885d7cea2430cd637b3592118e1d52abdad90300e2e491e7b457319edd39123d

# AMD64
#FROM openjdk:8-jre-slim@sha256:285c61a1e5e6b7b3709729b69558670148c5fdc6eb7104fae7dd370042c51430




# 作者
MAINTAINER Hhy

# 配置
ENV PARAMS=""

# 时区
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 添加应用
ADD target/marketing-app.jar /marketing-app.jar
z
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /marketing-app.jar $PARAMS"]