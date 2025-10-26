package main.java.enums;

import main.java.LogEntry;
import main.java.Statistics;

public enum Browsers {
    EDGE("Edge"),
    FIREFOX("Firefox"),
    CHROME("Chrome"),
    SAFARI("Safari"),
    OPERA("Opera"),
    OTHER("Other");

    Browsers(String browserName) {
        this.browserName = browserName;
    }
    private final String browserName;
    public String getBrowserName() {
        return browserName;
    }

    @Override
    public String toString() {
        return browserName;
    }
}
