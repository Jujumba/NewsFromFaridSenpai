# Telegram-based news aggregator

## Build

### Prerequisites:

1) __Java 8+__
2) __Any supported DBMS (PostgreSQL by default. Add your required driver in [build.gradle](https://github.com/Jujumba/NewsFromFaridSenpai/blob/master/build.gradle) and change the corresponding keys in [application.properties](https://github.com/Jujumba/NewsFromFaridSenpai/blob/master/src/main/resources/application.properties))__
3) __Gradle (optionally)__

__Run [database.sql](https://github.com/Jujumba/NewsFromFaridSenpai/blob/master/src/main/resources/database.sql) (be careful, it may be not compatible with your DBMS!)__

```sh 
$ ./gradlew bootRun
```

# Note that:

1) __If you have API-keys for OpenAI's GPT-3 and Google Translate, you can paste them in the [application.properties](https://github.com/Jujumba/NewsFromFaridSenpai/blob/master/src/main/resources/application.properties). Once it's done, be sure to set the `has_translate` flag to true__

# Api

__You can retrieve news from project's Rest-API__

__An example:__

```http request
curl -H 'Authorization: <API-KEY FROM YOUR PROFILE>' -H 'Content-Type: application/json' -d 'amount: <Amount>' localhost:8080/api/v1
```
__Params:__
1) Your API-KEY
2) Amount of news (optionally, 1 by default) specifies amount of *__latest__* to be returned

__If you want for more functionality, I'll be glad to leave this task for you<(^.^)>. Or simply: code it yourself. And be sure to create a pull request!__

__Check out [request.py](https://github.com/Jujumba/NewsFromFaridSenpai/blob/master/src/main/resources/request.py) to see simple Python example__

# Adding your source

1) __Get the link of channel to be parsed (``https://t.me/s/<name_of_channel>``)__
2) __In [parser's package](https://github.com/Jujumba/NewsFromFaridSenpai/tree/master/src/main/java/dev/jujumba/newsfromfaridsenpai/logic/parsers/telegram) create new Java/Kotlin class and mark it with `@Component` annotation__
3) __Extend it from [Abstract Telegram Parser](https://github.com/Jujumba/NewsFromFaridSenpai/blob/master/src/main/java/dev/jujumba/newsfromfaridsenpai/logic/parsers/telegram/AbstractTelegramParser.java)__
4) __Override constructor with the following pattern__
```java
public YourClassName(TextHandler textHandler, NewsService newsService, ImagesService imagesService) {
    super(textHandler, newsService, imagesService);
    setUrl("<URL_FROM_STEP_1>");
}
```
__Result should be like this:__
```java
/*
 * imports...
 */

@Component
public class YourClassName extends AbstractTelegramParser {
    public YourClassName(TextHandler textHandler, NewsService newsService, ImagesService imagesService) {
        super(textHandler, newsService, imagesService);
        setUrl("<URL_FROM_STEP_1>");
    }
}
```
