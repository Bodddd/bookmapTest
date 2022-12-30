package bookmap.storage;

public class ReportContainer {
    private static final String NEW_LINE = System.lineSeparator();
    private static final StringBuilder stringBuilder = new StringBuilder();

    public static String add(String line) {
        stringBuilder.append(line).append(NEW_LINE);
        return line;
    }

    public static String get() {
        return stringBuilder.toString();
    }
}
