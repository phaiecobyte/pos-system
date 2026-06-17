FROM ubuntu:24.04

LABEL authors="phai"

##################################################
# Package Installation Configuration
##################################################
ENV DEBIAN_FRONTEND=noninteractive

##################################################
# Development Tools
##################################################
RUN apt update && apt install -y \
    openjdk-25-jdk \
    maven \
    vim \
    git \
    curl \
    && apt clean \
    && rm -rf /var/lib/apt/lists/*

##################################################
# Application Workspace
##################################################
WORKDIR /workspace

COPY docker/run.sh /run.sh
RUN chmod +x /run.sh

CMD ["/run.sh"]
