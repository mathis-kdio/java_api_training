package fr.lernejo.navy_battle;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
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
    void getCoo_1st_time_should_return_string() {
        System.setIn(new ByteArrayInputStream("A1".getBytes()));
        Scanner scanner = new Scanner(System.in);
        System.setIn(System.in);
        ArrayList<List<Integer>> positions = new ArrayList<>();

        Assertions.assertThat(initGame.getCoo(scanner, positions)).as("get coo success 1st time")
            .isEqualTo(Arrays.asList(0, 0));
    }

    @Test
    void getCoo_2nd_time_should_return_string() {
        String simulatedUserInput = "Z1" + System.getProperty("line.separator") + "A1" + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        System.setIn(System.in);
        Scanner scanner = new Scanner(System.in);

        ArrayList<List<Integer>> positions = new ArrayList<>();

        Assertions.assertThat(initGame.getCoo(scanner, positions)).as("get coo success 2nd time")
            .isEqualTo(Arrays.asList(0, 0));
    }

    @Test
    void isCooPossible_1st_position_should_return_List() {
        ArrayList<List<Integer>> positions = new ArrayList<>();
        Assertions.assertThat(initGame.isCooPossible("A1", positions)).as("A1 = [0,0]")
            .isEqualTo(Arrays.asList(0, 0));
    }

    @Test
    void isCooPossible_2nd_position_should_return_List() {
        ArrayList<List<Integer>> positions = new ArrayList<>();
        positions.add(Arrays.asList(2, 2)); //3C = "C3"
        Assertions.assertThat(initGame.isCooPossible("C2", positions)).as("C2 = [1,2]")
            .isEqualTo(Arrays.asList(1, 2));
        Assertions.assertThat(initGame.isCooPossible("C4", positions)).as("C4 = [3,2]")
            .isEqualTo(Arrays.asList(3, 2));
        Assertions.assertThat(initGame.isCooPossible("B3", positions)).as("B3 = [2,1]")
            .isEqualTo(Arrays.asList(2, 1));
        Assertions.assertThat(initGame.isCooPossible("D3", positions)).as("D3 = [2,3]")
            .isEqualTo(Arrays.asList(2, 3));
    }

    @Test
    void isCooPossible_should_return_null() {
        ArrayList<List<Integer>> positions = new ArrayList<>();

        Assertions.assertThat(initGame.isCooPossible("A", positions)).as("A incorrecte")
            .isEqualTo(null);
        Assertions.assertThat(initGame.isCooPossible("A1A1", positions)).as("A1A1 incorrect")
            .isEqualTo(null);
        Assertions.assertThat(initGame.isCooPossible("A11", positions)).as("A11 incorrect")
            .isEqualTo(null);
        Assertions.assertThat(initGame.isCooPossible("A0", positions)).as("A0 incorrect")
            .isEqualTo(null);
        Assertions.assertThat(initGame.isCooPossible("Z1", positions)).as("Z1 incorrect")
            .isEqualTo(null);


        ArrayList<List<Integer>> newBoatPositions = new ArrayList<>();
        newBoatPositions.add(Arrays.asList(9, 9));
        Boat newboat = initGame.addBoat(Boat.BoatType.TORPILLEUR, newBoatPositions);
        initGame.boatList.add(newboat);
        Assertions.assertThat(initGame.isCooPossible("J10", positions)).as("J10 déjà bateau")
            .isEqualTo(null);

        positions.add(Arrays.asList(2, 2)); //3C = "C3"
        Assertions.assertThat(initGame.isCooPossible("A3", positions)).as("A3 pas dans la continuité de C3")
            .isEqualTo(null);
        Assertions.assertThat(initGame.isCooPossible("C1", positions)).as("C1 pas dans la continuité de C3")
            .isEqualTo(null);
        Assertions.assertThat(initGame.isCooPossible("C5", positions)).as("C5 pas dans la continuité de C3")
            .isEqualTo(null);
        Assertions.assertThat(initGame.isCooPossible("E3", positions)).as("E3 pas dans la continuité de C3")
            .isEqualTo(null);

        Assertions.assertThat(initGame.isCooPossible("J9", positions)).as("J9 pas dans la continuité de C3")
            .isEqualTo(null);
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
