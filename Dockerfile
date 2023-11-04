FROM maven:3.8.5-openjdk-17 as maven-build
RUN echo '== start building MFX =='
RUN mvn --version
WORKDIR /workspace
COPY ./ ./
RUN mvn install -Paliyun -f pom.xml -Dmaven.test.skip=true
RUN echo 'done building MFX'