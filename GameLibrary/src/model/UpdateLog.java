package model;

public class UpdateLog {
    private String version;
    private String[] changes;
    private int numberOfChanges;
    private final int MAX_CHANGES = 10;

    public UpdateLog(String version) {
        this.version = version;
        this.changes = new String[MAX_CHANGES];
        this.numberOfChanges = 0;
    }

    public String getVersion() {
        return version;
    }

    public int getNumberOfChanges() {
        return numberOfChanges;
    }

    public String getChanges() {
        if (numberOfChanges == 0) {
            return "[]";
        }

        String result = "[";
        for (int i = 0; i < numberOfChanges; i++) {
            result += changes[i];
            if (i < numberOfChanges - 1) {
                result += ", ";
            }
        }
        result += "]";
        return result;
    }

    public void addChange(String change) {
        if (numberOfChanges < MAX_CHANGES) {
            changes[numberOfChanges] = change;
            numberOfChanges++;
        }
    }

    @Override
    public String toString() {
        return "Version " + version + " contains " + numberOfChanges + " changes " + getChanges();
    }
}
