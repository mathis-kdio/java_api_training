package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;

class BoatTest {

    @Test
    void boatPositions_should_return_positions() {
        ArrayList<List<Integer>> positions = new ArrayList<>();
        List<Integer> position = new ArrayList<>();
        position.add(0);
        position.add(0);
        positions.add(position);
        Boat boat = new Boat(Boat.BoatType.TORPILLEUR, new String[]{"A1"});
        Assertions.assertThat(boat.boatPositions()).as("return positions")
            .isEqualTo(positions);
    }
}
