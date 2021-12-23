package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class LauncherTest {

    @Test
    void Launcher_with_1_arg() throws IOException {
        Launcher.main(new String[]{"9876"});
    }

    @Test
    void Launcher_with_2_arg() throws IOException {
        Launcher.main(new String[]{"8795", "http://localhost:9876"});
    }
}
