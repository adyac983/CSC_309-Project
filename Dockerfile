FROM eclipse-temurin:20-alpine
LABEL authors="mathleap"
WORKDIR /app
COPY target/CSC_309-Project-1.0-SNAPSHOT.jar /app

FROM openjdk:11
# Install necessary packages
RUN apt-get update && apt-get install -y \
    xfce4 \
    xfce4-goodies \
    tightvncserver \
    xterm \
    && rm -rf /var/lib/apt/lists/*
# Set environment variables for VNC
ENV USER root
ENV VNC_PASSWORD = "a12134111111111111"

# Create a VNC startup script
RUN mkdir ~/.vnc
RUN echo -e "#!/bin/sh\nxrdb $HOME/.Xresources\nstartxfce4 &" > ~/.vnc/xstartup
RUN chmod +x ~/.vnc/xstartup
RUN echo $VNC_PASSWORD | vncpasswd -f > /root/.vnc/passwd

# Expose the VNC port
EXPOSE 5901
# Add application JAR file
ADD target/CSC_309-Project-1.0-SNAPSHOT.jar /usr/src/myapp/CSC_309-Project-1.0-SNAPSHOT.jar
WORKDIR /usr/src/myapp
# Start VNC server and run your application
CMD vncserver :1 -geometry 1024x768 -depth 24 && \
    java -jar CSC_309-Project-1.0-SNAPSHOT.jar

