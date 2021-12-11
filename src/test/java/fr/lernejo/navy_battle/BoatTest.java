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
        position.add(1);
        position.add(1);
        positions.add(position);
        Boat boat = new Boat(Boat.BoatType.TORPILLEUR, positions);
        Assertions.assertThat(boat.boatPositions()).as("return positions")
            .isEqualTo(positions);
    }
}
