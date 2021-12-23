package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Test;

import org.assertj.core.api.Assertions;

class BoatTest {
    @Test
    void boat_init_lastCoo() {
        Boat boat = new Boat(Boat.BoatType.TORPILLEUR, new String[]{"J9", "J10"});
        Assertions.assertThat(boat.positions).as("test last coo init boat")
            .isEqualTo(new int[][]{{8,9},{9,9}});
    }
}
