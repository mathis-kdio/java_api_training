package fr.lernejo.navy_battle;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

public class AffichageTest {
    public final Boat.BoatType[] availableBoats = {Boat.BoatType.TORPILLEUR};
    String[][] positionsBoats = {{"B1", "B2"}};
    public final Game game;
    public final Affichage affichage = new Affichage();

    public AffichageTest() {
        this.game = new Game(this.availableBoats, this.positionsBoats);
    }

    @Test
    void showGrid_no_hit() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        this.affichage.showGrid(this.game.adversaryGrid);

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
        this.game.addAttackOnGrid("hit", Arrays.asList(0, 0));
        this.affichage.showGrid(this.game.adversaryGrid);
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

    @Test
    void resultAttack_hit() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        this.affichage.resultAttack("hit");
        Assertions.assertThat(outContent.toString()).as("show success message")
            .isEqualTo("Le tire a réussi\n");
    }

    @Test
    void resultAttack_sunk() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        this.affichage.resultAttack("sunk");
        Assertions.assertThat(outContent.toString()).as("show sunk message")
            .isEqualTo("Le bateau est coulé\n");
    }

    @Test
    void resultAttack_miss() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        this.affichage.resultAttack("miss");
        Assertions.assertThat(outContent.toString()).as("show miss message")
            .isEqualTo("Le tire a manqué\n");
    }
}
