FROM maven:3.9.15-eclipse-temurin-25
RUN echo '== start building MFX =='
RUN mvn --version
WORKDIR /workspace
COPY ./ ./
RUN mvn install -Paliyun -f pom.xml -Dmaven.test.skip=true
RUN echo 'done building MFX'
