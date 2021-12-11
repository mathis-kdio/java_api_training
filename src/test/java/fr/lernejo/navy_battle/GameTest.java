package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

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
}
