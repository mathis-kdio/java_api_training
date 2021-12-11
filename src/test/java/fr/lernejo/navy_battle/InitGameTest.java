package fr.lernejo.navy_battle;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class InitGameTest {
    Boat.BoatType[] availableBoats = new Boat.BoatType[]{Boat.BoatType.TORPILLEUR};
    public final InitGame initGame;

    public InitGameTest() {
        this.initGame = new InitGame(availableBoats);
    }

    @Test
    void addBoat_should_return_boat() {
        ArrayList<List<Integer>> positions = new ArrayList<>();
        positions.add(Arrays.asList(1, 1));
        Boat boat = new Boat(Boat.BoatType.TORPILLEUR, positions);
        Assertions.assertThat(initGame.addBoat(Boat.BoatType.TORPILLEUR, positions)).as("addBoat return boat")
            .isEqualTo(boat);
    }

}
