package com.example.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player player;

    @BeforeEach
    public void setup() {
        player = new Player(1, "Alice");
    }

    @Test
    public void playerInitializesWithZeroScore() {
        assertEquals(0, player.getScore());
    }

    @Test
    public void getPlayerIdReturnsCorrectId() {
        assertEquals(1, player.getPlayerID());
    }

    @Test
    public void getNameReturnsCorrectName() {
        assertEquals("Alice", player.getName());
    }

    @Test
    public void updateScoreAddsPositivePoints() {
        player.updateScore(100);
        assertEquals(100, player.getScore());
    }

    @Test
    public void updateScoreSubtractsNegativePoints() {
        player.updateScore(200);
        player.updateScore(-100);
        assertEquals(100, player.getScore());
    }

    @Test
    public void updateScoreAllowsNegativeTotal() {
        player.updateScore(-50);
        assertEquals(-50, player.getScore());
    }

    @Test
    public void multipleScoreUpdatesAccumulate() {
        player.updateScore(100);
        player.updateScore(200);
        player.updateScore(-50);
        assertEquals(250, player.getScore());
    }
}
