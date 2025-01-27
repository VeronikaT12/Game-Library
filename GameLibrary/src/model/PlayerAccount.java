package model;

public class PlayerAccount {
    private String playerName;
    private GameLibrary linkedLibrary;
    private Game[] downloadedGames;
    private int downloadCount;
    private String successStatus;
    private String errorStatus;

    public PlayerAccount(String playerName, GameLibrary linkedLibrary) {
        this.playerName = playerName;
        this.linkedLibrary = linkedLibrary;
        this.downloadedGames = new Game[50]; // Maximum 50 downloaded games
        this.downloadCount = 0;
        this.successStatus = "";
        this.errorStatus = "";
    }

    public void downloadGame(String gameTitle) {
        Game game = linkedLibrary.getGame(gameTitle);
        if (game != null && !isGameDownloaded(game)) {
            downloadedGames[downloadCount] = game;
            downloadCount++;
            successStatus = gameTitle + " successfully downloaded for " + playerName + ".";
            errorStatus = "";
        } else if (game == null) {
            errorStatus = "Error: " + gameTitle + " is not available in the library.";
            successStatus = "";
        } else {
            errorStatus = "Error: " + gameTitle + " has already been downloaded for " + playerName + ".";
            successStatus = "";
        }
    }

    public void uninstallGame(String gameTitle) {
        for (int i = 0; i < downloadCount; i++) {
            if (downloadedGames[i].getTitle().equals(gameTitle)) {
                for (int j = i; j < downloadCount - 1; j++) {
                    downloadedGames[j] = downloadedGames[j + 1];
                }
                downloadedGames[downloadCount - 1] = null;
                downloadCount--;
                successStatus = gameTitle + " successfully uninstalled for " + playerName + ".";
                errorStatus = "";
                return;
            }
        }
        errorStatus = "Error: " + gameTitle + " has not been downloaded for " + playerName + ".";
        successStatus = "";
    }

    public void submitRating(String gameTitle, int rating) {
        Game game = getGameByTitle(gameTitle);
        if (game != null) {
            game.submitRating(rating);
            successStatus = "Rating " + rating + " submitted by " + playerName + " for " + gameTitle + ".";
            errorStatus = "";
        } else {
            errorStatus = "Error: " + gameTitle + " is not a downloaded game for " + playerName + ".";
            successStatus = "";
        }
    }

    public String[] getDownloadedGameTitles() {
        String[] titles = new String[downloadCount];
        for (int i = 0; i < downloadCount; i++) {
            titles[i] = downloadedGames[i].getTitle();
        }
        return titles;
    }

    public Game[] getDownloadedGames() {
        Game[] games = new Game[downloadCount];
        for (int i = 0; i < downloadCount; i++) {
            games[i] = downloadedGames[i];
        }
        return games;
    }

    private boolean isGameDownloaded(Game game) {
        for (int i = 0; i < downloadCount; i++) {
            if (downloadedGames[i].getTitle().equals(game.getTitle())) {
                return true;
            }
        }
        return false;
    }

    private Game getGameByTitle(String title) {
        for (int i = 0; i < downloadCount; i++) {
            if (downloadedGames[i].getTitle().equals(title)) {
                return downloadedGames[i];
            }
        }
        return null;
    }

    @Override
    public String toString() {
        if (!errorStatus.isEmpty()) {
            return errorStatus;
        } else if (!successStatus.isEmpty()) {
            return successStatus;
        } else {
            return "Player account " + playerName + " linked to " + linkedLibrary.getRegion() + " library.";
        }
    }
}
