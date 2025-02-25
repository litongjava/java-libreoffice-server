FROM litongjava/jdk:8u391-libreoffice

# 设置工作目录
WORKDIR /app

# 复制 jar 文件到容器中
COPY target/java-libreoffice-server-1.0.0.jar /app/

# 运行 jar 文件
CMD ["java", "-jar", "java-libreoffice-server-1.0.0.jar", "--app.env=prod"]
