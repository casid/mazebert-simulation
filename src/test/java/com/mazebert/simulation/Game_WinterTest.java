package com.mazebert.simulation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;
import static org.jusecase.Builders.date;

class Game_WinterTest {
    Game game = new Game();

    @Test
    void noTimestamp() {
        game.timestamp = 0;
        assertThat(game.isWinter()).isFalse();
    }

    @Test
    void november() {
        game.timestamp = a(date("2020-11-01").withTimezone("UTC")).getTime();
        assertThat(game.isWinter()).isFalse();
    }

    @Test
    void december() {
        game.timestamp = a(date("2019-12-01").withTimezone("UTC")).getTime();
        assertThat(game.isWinter()).isTrue();
    }

    @Test
    void january() {
        game.timestamp = a(date("2020-01-01").withTimezone("UTC")).getTime();
        assertThat(game.isWinter()).isTrue();
    }

    @Test
    void february() {
        game.timestamp = a(date("2020-02-01").withTimezone("UTC")).getTime();
        assertThat(game.isWinter()).isTrue();
    }

    @Test
    void march() {
        game.timestamp = a(date("2020-03-01").withTimezone("UTC")).getTime();
        assertThat(game.isWinter()).isFalse();
    }
}