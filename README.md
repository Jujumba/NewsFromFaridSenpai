# Telegram-based news aggregator
## Build
### Prerequisites:
1) __Java 17__
2) __Any supported DBMS (PostgreSQL by default. Add your required driver in [build.gradle](https://github.com/Jujumba/NewsFromFaridSenpai/blob/master/build.gradle) and change the corresponding keys in [application.properties](https://github.com/Jujumba/NewsFromFaridSenpai/blob/master/src/main/resources/application.properties))__
3) __Gradle (optionally)__

__Run [database.sql](https://github.com/Jujumba/NewsFromFaridSenpai/blob/master/src/main/resources/database.sql) (be careful, it may be not compatible with your DBMS!)__

```sh 
$ ./gradlew bootRun
```

# Note that:
1) __If you have API-keys for OpenAI's GPT-3 and Google Translate, you can paste them in the [application.properties](https://github.com/Jujumba/NewsFromFaridSenpai/blob/master/src/main/resources/application.properties). Once it's done, be sure to set the `has_translate` flag to true__
