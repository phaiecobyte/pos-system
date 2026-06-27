# ==================================================
# Configuration
# ==================================================
ARG UBUNTU_VERSION=24.04

FROM ubuntu:${UBUNTU_VERSION}

# ==================================================
# Metadata
# ==================================================
LABEL maintainer="PHAIECOBYTE"
LABEL project="POS System"
LABEL description="Spring Boot Development Environment"

# ==================================================
# Configuration
# ==================================================
ARG JAVA_VERSION=jdk-25.0.0+36
ARG JAVA_HOME=/opt/java

ARG MAVEN_VERSION=3.9.16
ARG MAVEN_HOME=/opt/maven

# ==================================================
# Environment
# ==================================================
ENV DEBIAN_FRONTEND=noninteractive

ENV JAVA_HOME=${JAVA_HOME}
ENV PATH="${JAVA_HOME}/bin:${PATH}"

ENV MAVEN_HOME=${MAVEN_HOME}
ENV PATH="${MAVEN_HOME}/bin:${PATH}"

# ==================================================
# Working Directory
# ==================================================
WORKDIR /app

# ==================================================
# System Packages
# ==================================================
RUN apt-get update && \
    apt-get install -y \
        curl \
        wget \
        git \
        unzip \
        vim \
        ca-certificates \
        tar && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# ==================================================
# Install Eclipse Temurin JDK 25
# ==================================================
RUN mkdir -p ${JAVA_HOME} && \
    wget -O /tmp/jdk.tar.gz \
    "https://api.adoptium.net/v3/binary/latest/25/ga/linux/x64/jdk/hotspot/normal/eclipse" && \
    tar -xzf /tmp/jdk.tar.gz --strip-components=1 -C ${JAVA_HOME} && \
    rm /tmp/jdk.tar.gz

# ==================================================
# Install Apache Maven
# ==================================================
RUN mkdir -p ${MAVEN_HOME} && \
    wget -O /tmp/maven.tar.gz \
    https://dlcdn.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz && \
    tar -xzf /tmp/maven.tar.gz --strip-components=1 -C ${MAVEN_HOME} && \
    rm /tmp/maven.tar.gz

# ==================================================
# Verify Installation
# ==================================================
RUN java -version && \
    javac -version && \
    mvn -version

CMD ["bash"]