package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class GameTest {
    public final Boat.BoatType[] availableBoats = new Boat.BoatType[]{Boat.BoatType.TORPILLEUR};
    public final ArrayList<List<Integer>> positions = new ArrayList<>();
    public final Boat boat;
    public final ArrayList<Boat> boatList = new ArrayList<>();
    public final Game game;

    public GameTest() {
        positions.add(Arrays.asList(1, 1));
        this.boat = new Boat(Boat.BoatType.TORPILLEUR, positions);
        this.boatList.add(boat);
        this.game = new Game(boatList, availableBoats);
    }

    @Test
    void shipLeft_should_return_true() {
        Assertions.assertThat(this.game.shipLeft()).as("shipLeft true")
            .isEqualTo(true);
    }

    @Test
    void shipLeft_should_return_false() {
        this.game.boatLosesLife(this.boat);
        this.game.boatLosesLife(this.boat);
        Assertions.assertThat(this.game.shipLeft()).as("shipLeft false")
            .isEqualTo(false);
    }

    @Test
    void boatOnPosition_should_return_boat() {
        Assertions.assertThat(this.game.boatOnPosition(Arrays.asList(1, 1))).as("boatOnPosition boat")
            .isEqualTo(this.boat);
    }

    @Test
    void boatOnPosition_should_return_null() {
        Assertions.assertThat(this.game.boatOnPosition(Arrays.asList(0, 0))).as("boatOnPosition null")
            .isEqualTo(null);
    }

    @Test
    void showGrid_no_hit() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        game.showGrid(game.adversaryGrid);

        String expectedOutput =
            """
                  A B C D E F G H I J\s
                1 * * * * * * * * * *\s
                2 * * * * * * * * * *\s
                3 * * * * * * * * * *\s
                4 * * * * * * * * * *\s
                5 * * * * * * * * * *\s
                6 * * * * * * * * * *\s
                7 * * * * * * * * * *\s
                8 * * * * * * * * * *\s
                9 * * * * * * * * * *\s
                10 * * * * * * * * * *\s
                """;
        Assertions.assertThat(outContent.toString()).as("grid with no hit")
            .isEqualTo(expectedOutput);
    }

    @Test
    void showGrid_1_hit() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        game.addAttackOnGrid("hit", Arrays.asList(0, 0));
        game.showGrid(game.adversaryGrid);
        String expectedOutput =
            """
                  A B C D E F G H I J\s
                1 X * * * * * * * * *\s
                2 * * * * * * * * * *\s
                3 * * * * * * * * * *\s
                4 * * * * * * * * * *\s
                5 * * * * * * * * * *\s
                6 * * * * * * * * * *\s
                7 * * * * * * * * * *\s
                8 * * * * * * * * * *\s
                9 * * * * * * * * * *\s
                10 * * * * * * * * * *\s
                """;
        Assertions.assertThat(outContent.toString()).as("grid with 1 hit")
            .isEqualTo(expectedOutput);
    }
}
