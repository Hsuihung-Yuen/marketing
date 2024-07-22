# 普通镜像构建，随系统版本构建 amd/arm
docker build -t thehhy/marketing-app:2.0 -f ./Dockerfile .

# mac构建amd版本docker
#docker buildx build --push --platform liunx/arm64 -t thehhy/marketing-app:1.0-SNAPSHOT .