package dev.jujumba.newsfromfaridsenpai.logic.parsers;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
/**
 * @author Jujumba
 */
@FunctionalInterface
public interface Parser {
    void parse();

    @SneakyThrows
    default void sleep(long seconds) {
        TimeUnit.SECONDS.sleep(seconds);
    }

    @SneakyThrows
    default void sleep(double hours) {
        TimeUnit.HOURS.sleep((long) hours);
    }
}
