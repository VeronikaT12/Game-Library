package tests;

import static org.junit.Assert.*;


import org.junit.Test;

import model.*;

public class GameLibraryTests {

    /* Tests related to the UpdateLog class */

    @Test
    public void test_update_log_01() {
        UpdateLog log = new UpdateLog("1.0.1");

        assertEquals("1.0.1", log.getVersion());
        assertEquals(0, log.getNumberOfChanges());
        assertEquals("[]", log.getChanges());
        assertEquals("Version 1.0.1 contains 0 changes []", log.toString());
    }

    @Test
    public void test_update_log_02() {
        UpdateLog log = new UpdateLog("1.2.0");

        log.addChange("Improved graphics rendering");
        log.addChange("Fixed multiplayer lobby bug");

        assertEquals("1.2.0", log.getVersion());
        assertEquals(2, log.getNumberOfChanges());
        assertEquals("[Improved graphics rendering, Fixed multiplayer lobby bug]", log.getChanges());
        assertEquals("Version 1.2.0 contains 2 changes [Improved graphics rendering, Fixed multiplayer lobby bug]", log.toString());
    }

    /* Tests related to the Game class */

    @Test
    public void test_game_01() {
        Game game = new Game("Battle Royale", 10);

        assertEquals("Battle Royale", game.getTitle());
        assertEquals("n/a", game.getLatestUpdate());
        assertTrue(game.getUpdateLogs().length == 0);
        assertNull(game.getUpdateLog("1.0.0"));
        assertEquals("No ratings submitted so far!", game.getRatingSummary());
        assertEquals("Battle Royale (Current Version: n/a; Average Rating: n/a)", game.toString());
    }

    @Test
    public void test_game_02() {
        Game game = new Game("Adventure Quest", 15);

        game.addUpdate("2.0.0");
        game.addUpdate("2.1.0");

        game.getUpdateLogs()[0].addChange("Added new questlines");
        game.getUpdateLogs()[1].addChange("Fixed combat mechanics");
        game.getUpdateLogs()[1].addChange("Improved NPC interactions");

        assertEquals("Adventure Quest", game.getTitle());
        assertEquals("Version 2.1.0 contains 2 changes [Fixed combat mechanics, Improved NPC interactions]", game.getLatestUpdate());
        assertTrue(game.getUpdateLogs().length == 2);
        assertEquals("Version 2.0.0 contains 1 changes [Added new questlines]", game.getUpdateLog("2.0.0").toString());
        assertEquals("Version 2.1.0 contains 2 changes [Fixed combat mechanics, Improved NPC interactions]", game.getUpdateLog("2.1.0").toString());
    }

    @Test
    public void test_game_ratings() {
        Game game = new Game("Puzzle Mania", 20);

        game.submitRating(5);
        game.submitRating(3);
        game.submitRating(4);

        assertEquals("Average of 3 ratings: 4.0 (Score 5: 1, Score 4: 1, Score 3: 1, Score 2: 0, Score 1: 0)", game.getRatingSummary());
        assertEquals("Puzzle Mania (Current Version: n/a; Average Rating: 4.0)", game.toString());
    }

    /* Tests related to the GameLibrary class */

    @Test
    public void test_game_library_01() {
        GameLibrary library = new GameLibrary("US", 50);

        assertEquals("US", library.getRegion());
        assertNull(library.getGame("Racing Legends"));
        assertTrue(library.getStableGames(2).length == 0);
    }

    @Test
    public void test_game_library_02() {
        GameLibrary library = new GameLibrary("Europe", 50);

        Game game1 = new Game("RPG Chronicles", 15);
        Game game2 = new Game("Space Explorer", 20);
        Game game3 = new Game("City Builder", 10);

        game1.addUpdate("1.1.0");
        game1.addUpdate("1.2.0");
        game1.getUpdateLogs()[0].addChange("Improved inventory system");
        game1.getUpdateLogs()[1].addChange("Fixed quest rewards bug");

        game2.addUpdate("3.0.0");
        game2.getUpdateLogs()[0].addChange("Overhauled graphics engine");

        game3.addUpdate("2.0.0");
        game3.addUpdate("2.1.0");
        game3.addUpdate("2.2.0");

        library.addGame(game1);
        library.addGame(game2);
        library.addGame(game3);

        assertEquals("RPG Chronicles (2 versions; Current Version: Version 1.2.0 contains 1 changes [Fixed quest rewards bug])", library.getStableGames(2)[0]);
        assertEquals("City Builder (3 versions; Current Version: Version 2.2.0 contains 0 changes [])", library.getStableGames(2)[1]);
    }

    /* Tests related to the PlayerAccount class */

    @Test
    public void test_player_account_01() {
        GameLibrary library = new GameLibrary("Asia", 100);

        Game game = new Game("Fantasy Battles", 10);
        library.addGame(game);

        PlayerAccount account = new PlayerAccount("Aria", library);

        assertEquals("Player account Aria linked to Asia library.", account.toString());
        assertTrue(account.getDownloadedGames().length == 0);

        account.downloadGame("Fantasy Battles");
        assertEquals("Fantasy Battles successfully downloaded for Aria.", account.toString());
        assertEquals(1, account.getDownloadedGames().length);

        account.uninstallGame("Fantasy Battles");
        assertEquals("Fantasy Battles successfully uninstalled for Aria.", account.toString());
        assertTrue(account.getDownloadedGames().length == 0);
    }

    @Test
    public void test_player_account_ratings() {
        GameLibrary library = new GameLibrary("Global", 50);
        Game game = new Game("Mystery Dungeon", 15);
        library.addGame(game);

        PlayerAccount account = new PlayerAccount("Liam", library);
        account.downloadGame("Mystery Dungeon");
        account.submitRating("Mystery Dungeon", 5);

        assertEquals("Rating 5 submitted by Liam for Mystery Dungeon.", account.toString());
        assertEquals("Average of 1 ratings: 5.0 (Score 5: 1, Score 4: 0, Score 3: 0, Score 2: 0, Score 1: 0)", game.getRatingSummary());
    }
}