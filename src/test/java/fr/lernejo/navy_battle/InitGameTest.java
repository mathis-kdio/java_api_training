package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.api.game.fire.FireRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


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
        Assertions.assertThat(initGame.addBoat(Boat.BoatType.TORPILLEUR, positions).positions).as("addBoat return boat")
            .isEqualTo(boat.positions);
    }

    @Test
    void getCoo_should_return_string() {
        System.setIn(new ByteArrayInputStream("A1".getBytes()));
        Scanner scanner = new Scanner(System.in);
        System.setIn(System.in);
        ArrayList<List<Integer>> positions = new ArrayList<>();

        Assertions.assertThat(initGame.getCoo(scanner, positions)).as("shipLeft true")
            .isEqualTo(Arrays.asList(0, 0));
    }

    @Test
    void addAllBoats_should_return_boatList() {
        String simulatedUserInput = "A1" + System.getProperty("line.separator") + "A2" + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        System.setIn(System.in);
        Scanner scanner = new Scanner(System.in);

        ArrayList<List<Integer>> positions = new ArrayList<>();
        positions.add(Arrays.asList(0, 0));
        positions.add(Arrays.asList(1, 0));

        List<Boat> boatList = new ArrayList<>();
        Boat boat = new Boat(Boat.BoatType.TORPILLEUR, positions);
        boatList.add(boat);

        Assertions.assertThat(initGame.addAllBoats(scanner).get(0).positions).as("return boatlist")
            .isEqualTo(boatList.get(0).positions);
    }
}
