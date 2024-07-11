# 普通镜像构建，随系统版本构建 amd/arm
#docker build -t thehhy/marketing-app:1.0-SNAPSHOT -f ./Dockerfile .

# mac构建amd版本docker
docker buildx build --push --platform liunx/amd64 -t thehhy/marketing-app:1.0-SNAPSHOT -f ./Dockerfile .