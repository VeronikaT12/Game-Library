package model;

public class GameLibrary {
    private String region;
    private Game[] games;
    private int gameCount;
    private final int maxGames;

    public GameLibrary(String region, int maxGames) {
        this.region = region;
        this.maxGames = maxGames;
        this.games = new Game[maxGames];
        this.gameCount = 0;
    }

    public String getRegion() {
        return region;
    }

    public boolean addGame(Game game) {
        if (getGame(game.getTitle()) == null && gameCount < maxGames) {
            games[gameCount] = game;
            gameCount++;
            return true;
        }
        return false;
    }

    public Game getGame(String title) {
        for (int i = 0; i < gameCount; i++) {
            if (games[i].getTitle().equals(title)) {
                return games[i];
            }
        }
        return null;
    }

    public String[] getStableGames(int minUpdates) {
        String[] stableGames = new String[gameCount];
        int stableCount = 0;

        for (int i = 0; i < gameCount; i++) {
            if (games[i].getUpdateLogs().length >= minUpdates) {
                String description = games[i].getTitle() + " (" + games[i].getUpdateLogs().length +
                        " versions; Current Version: " + games[i].getLatestUpdate() + ")";
                stableGames[stableCount] = description;
                stableCount++;
            }
        }

        String[] result = new String[stableCount];
        for (int i = 0; i < stableCount; i++) {
            result[i] = stableGames[i];
        }
        return result;
    }
}
