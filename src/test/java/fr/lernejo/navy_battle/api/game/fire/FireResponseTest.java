package fr.lernejo.navy_battle.api.game.fire;

import fr.lernejo.navy_battle.Boat;
import fr.lernejo.navy_battle.Game;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


public class FireResponseTest {
    public final FireResponse fireResponse;
    public final Game game;
    public FireResponseTest() {
        Boat.BoatType[] availableBoats = {Boat.BoatType.TORPILLEUR,};
        String[][] positionsBoats = {{"A1", "A2"}};
        this.game = new Game(availableBoats, positionsBoats);
        this.fireResponse = new FireResponse(this.game);
    }

    @Test
    void convertCooStrToInt_should_return_00() {
        Assertions.assertThat(this.fireResponse.convertCooStrToInt("A1")).as("A1 = {0,0}")
            .isEqualTo(new int[]{0, 0});
    }

    @Test
    void convertCooStrToInt_should_return_90() {
        Assertions.assertThat(this.fireResponse.convertCooStrToInt("A10")).as("A1 = {9,0}")
            .isEqualTo(new int[]{9, 0});
    }

    @Test
    void returnConsequence_should_return_hit() {
        Boat boat = this.game.boatOnPosition(new int[]{0, 0});
        Assertions.assertThat(this.fireResponse.returnConsequence(boat)).as("A1 = {9,0}")
            .isEqualTo("hit");
    }

    @Test
    void returnConsequence_should_return_sunk() {
        Boat boat = this.game.boatOnPosition(new int[]{0, 0});
        this.game.boatLosesLife(boat);
        Assertions.assertThat(this.fireResponse.returnConsequence(boat)).as("A1 = {9,0}")
            .isEqualTo("sunk");
    }

    @Test
    void returnConsequence_should_return_miss() {
        Boat boat = this.game.boatOnPosition(new int[]{2, 2});
        Assertions.assertThat(this.fireResponse.returnConsequence(boat)).as("A1 = {9,0}")
            .isEqualTo("miss");
    }
}
