package com.mazebert.simulation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;
import static org.jusecase.Builders.date;

class Game_April1stTest {
    Game game = new Game();

    @Test
    void noTimestamp() {
        game.timestamp = 0;
        assertThat(game.isAprilFoolsGame()).isFalse();
    }

    @Test
    void april1st() {
        game.timestamp = a(date("2020-04-01").withTimezone("UTC")).getTime();
        assertThat(game.isAprilFoolsGame()).isTrue();
    }

    @Test
    void april2nd() {
        game.timestamp = a(date("2020-04-02").withTimezone("UTC")).getTime();
        assertThat(game.isAprilFoolsGame()).isFalse();
    }

    @Test
    void march1st() {
        game.timestamp = a(date("2020-03-01").withTimezone("UTC")).getTime();
        assertThat(game.isAprilFoolsGame()).isFalse();
    }
}