package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

class GameTest {
    public final Boat.BoatType[] availableBoats = {Boat.BoatType.TORPILLEUR};
    String[][] positionsBoats = {{"B1", "B2"}};
    public final Boat boat;
    public final Game game;

    public GameTest() {
        this.game = new Game(this.availableBoats, this.positionsBoats);
        this.boat = this.game.boatList.get(0);
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
        Assertions.assertThat(this.game.boatOnPosition(new int[]{1,1})).as("boatOnPosition boat")
            .isEqualTo(this.boat);
    }

    @Test
    void boatOnPosition_should_return_null() {
        Assertions.assertThat(this.game.boatOnPosition(new int[]{0,0})).as("boatOnPosition null")
            .isEqualTo(null);
    }
}
