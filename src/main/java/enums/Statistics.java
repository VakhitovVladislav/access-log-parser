package main.java.enums;

import main.java.LogEntry;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;


public class Statistics {
    private int totalTraffic;
    public LocalDateTime minTime;
    public LocalDateTime maxTime;
    private int entryCount;
    private static final DateTimeFormatter APACHE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);

    public Statistics() {
        this.totalTraffic =0;
        this.entryCount = 0;
        this.minTime = null;
        this.maxTime = null;
    }
    public void addEntry(List<LogEntry> logs, String minTime, String maxTime){
        this.minTime = LocalDateTime.parse(minTime, APACHE_FORMATTER);
        this.maxTime = LocalDateTime.parse(maxTime, APACHE_FORMATTER);
        for (LogEntry log: logs) {
            LocalDateTime entryTime = log.getDateTime();
            if (this.minTime == null || entryTime.isBefore(this.minTime)) {
                this.minTime = entryTime;
            }
            if (this.maxTime == null || entryTime.isAfter(this.maxTime)) {
                this.maxTime = entryTime;
            }
            this.entryCount++;
            this.totalTraffic += log.getResponseSize();
        }
    }
    public double getTrafficRate() {
        Duration duration = Duration.between(minTime, maxTime);
        double hours = duration.toHours();
        System.out.println(totalTraffic);
        System.out.println(hours);
        if (hours <= 0.0){
            hours = 1.0;
        }
        return totalTraffic / hours;
    }

    public int getTotalTraffic() {
        return totalTraffic;
    }

    public LocalDateTime getMinTime() {
        return minTime;
    }

    public LocalDateTime getMaxTime() {
        return maxTime;
    }

    public int getEntryCount() {
        return entryCount;
    }
}
