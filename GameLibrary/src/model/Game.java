package model;

public class Game {
    private String title;
    private UpdateLog[] updateLogs;
    private int updateCount;
    private int[] ratings;
    private int ratingCount;
    private final int MAX_UPDATES = 20;
    private final int maxRatings;

    public Game(String title, int maxRatings) {
        this.title = title;
        this.maxRatings = maxRatings;
        this.updateLogs = new UpdateLog[MAX_UPDATES];
        this.updateCount = 0;
        this.ratings = new int[maxRatings];
        this.ratingCount = 0;
    }

    public String getTitle() {
        return title;
    }

    public void addUpdate(String version) {
        if (updateCount < MAX_UPDATES) {
            updateLogs[updateCount] = new UpdateLog(version);
            updateCount++;
        }
    }

    public UpdateLog[] getUpdateLogs() {
        UpdateLog[] history = new UpdateLog[updateCount];
        for (int i = 0; i < updateCount; i++) {
            history[i] = updateLogs[i];
        }
        return history;
    }

    public UpdateLog getUpdateLog(String version) {
        for (int i = 0; i < updateCount; i++) {
            if (updateLogs[i].getVersion().equals(version)) {
                return updateLogs[i];
            }
        }
        return null;
    }

    public String getLatestUpdate() {
        if (updateCount == 0) {
            return "n/a";
        }
        return updateLogs[updateCount - 1].toString();
    }

    public void submitRating(int rating) {
        if (ratingCount < maxRatings) {
            ratings[ratingCount] = rating;
            ratingCount++;
        }
    }

    public String getRatingSummary() {
        if (ratingCount == 0) {
            return "No ratings submitted so far!";
        }

        double total = 0;
        int[] scoreCounts = new int[6]; // Array to count scores (1-5)

        for (int i = 0; i < ratingCount; i++) {
            total += ratings[i];
            scoreCounts[ratings[i]]++;
        }

        double average = total / ratingCount;

        return String.format(
            "Average of %d ratings: %.1f (Score 5: %d, Score 4: %d, Score 3: %d, Score 2: %d, Score 1: %d)",
            ratingCount, average, scoreCounts[5], scoreCounts[4], scoreCounts[3], scoreCounts[2], scoreCounts[1]
        );
    }

    @Override
    public String toString() {
        String latestVersion = (updateCount > 0) ? getLatestUpdate() : "n/a";
        String averageRating = (ratingCount > 0) ? String.format("%.1f", getAverageRating()) : "n/a";
        return title + " (Current Version: " + latestVersion + "; Average Rating: " + averageRating + ")";
    }

    private double getAverageRating() {
        if (ratingCount == 0) {
            return 0;
        }

        double total = 0;
        for (int i = 0; i < ratingCount; i++) {
            total += ratings[i];
        }
        return total / ratingCount;
    }
}
