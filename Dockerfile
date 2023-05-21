FROM gradle:7.2.0-jdk17

WORKDIR /news

COPY . ./

EXPOSE 8080
CMD [ "gradle", "--stacktrace","bootRun" ]