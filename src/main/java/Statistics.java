package main.java;


import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


public class Statistics {


    private long totalTraffic;
    public LocalDateTime minTime;
    public LocalDateTime maxTime;
    private int entryCount;
    private HashMap<String, Integer> osStatistic;

    private HashSet<String> pageList;

    public Statistics() {
        this.totalTraffic = 0;
        this.entryCount = 0;
        this.minTime = null;
        this.maxTime = null;
    }

    public void addEntry(List<LogEntry> logs) {
        pageList = new HashSet<>();
        osStatistic = new HashMap<>();
        for (LogEntry log : logs) {
            LocalDateTime entryTime = log.getDateTime();
            if (this.minTime == null || entryTime.isBefore(this.minTime)) {
                this.minTime = entryTime;
            }
            if (this.maxTime == null || entryTime.isAfter(this.maxTime)) {
                this.maxTime = entryTime;
            }
            this.entryCount++;
            this.totalTraffic += log.getResponseSize();
            if(log.getResponseCode().equals("200")){
                pageList.add(log.getUrl());
            }
            if (!osStatistic.containsKey(log.getUserAgent().getOperationalSystem().toString())){
                osStatistic.put(log.getUserAgent().getOperationalSystem().getOperationSystemName(), 1);
            } else {
                osStatistic.replace(log.getUserAgent().getOperationalSystem().toString(),
                        osStatistic.get(log.getUserAgent().getOperationalSystem().getOperationSystemName())+1);
            }
        }
        getOsStatistic(osStatistic);
        System.out.println(getOsStatistic(osStatistic));
    }
    public HashMap<String, Double> getOsStatistic(HashMap<String, Integer> osStatistic){
        HashMap<String, Double> result = new HashMap<>();
        double allPersents = osStatistic.values().stream()
                .mapToDouble(i -> i)
                .sum();
        osStatistic.forEach((key, value) -> result.put(key, value / allPersents));
        return result;
    }


    public BigDecimal getTrafficRate() {
        if (minTime==null){
            minTime = LocalDateTime.MIN;
        }
        if (maxTime == null){
            maxTime = LocalDateTime.MAX;
        }
        Duration duration = Duration.between(minTime, maxTime);
        double hours = duration.toHours();
        if (hours <= 0.0) {
            hours = 1.0;
        }
        System.out.println(hours);
        System.out.println(totalTraffic);
        return BigDecimal.valueOf(totalTraffic / hours);
    }

    public long getTotalTraffic() {
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
    private HashSet<String> getPageList() {
        return pageList;
    }


}
