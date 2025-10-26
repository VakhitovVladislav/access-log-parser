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
    private HashMap<String, Integer> browserStatistic;
    

    private HashSet<String> notFoundPageList;

    private HashSet<String> pageList;

    public Statistics() {
        this.totalTraffic = 0;
        this.entryCount = 0;
        this.minTime = null;
        this.maxTime = null;
    }

    public void addEntry(List<LogEntry> logs) {
        notFoundPageList = new HashSet<>();
        browserStatistic = new HashMap<>();
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
            createOKPagesList(log);
            createNotFoundPageList(log);
            operationSystemCount(log);
            browsersCount(log);
            getOsStatistic(osStatistic);
            getBrowsersStatistic(browserStatistic);
        }
        getOsStatistic(osStatistic);
    }

    private void operationSystemCount(LogEntry log) {
        if (!osStatistic.containsKey(log.getUserAgent().getOperationalSystem().toString())){
            osStatistic.put(log.getUserAgent().getOperationalSystem().getOperationSystemName(), 1);
        } else {
            osStatistic.replace(log.getUserAgent().getOperationalSystem().toString(),
                    osStatistic.get(log.getUserAgent().getOperationalSystem().getOperationSystemName())+1);
        }
    }

    private void browsersCount(LogEntry log) {
        if (!browserStatistic.containsKey(log.getUserAgent().getBrowser().getBrowserName())){
            browserStatistic.put(log.getUserAgent().getBrowser().getBrowserName(), 1);
        } else {
            browserStatistic.replace(log.getUserAgent().getBrowser().getBrowserName(),
                    browserStatistic.get(log.getUserAgent().getBrowser().getBrowserName())+1);
        }
    }

    private void createOKPagesList(LogEntry log) {
        if(log.getResponseCode().equals("200")){
            pageList.add(log.getUrl());
        }
    }

    public HashMap<String, Double> getOsStatistic(HashMap<String, Integer> osStatistic){
        HashMap<String, Double> result = new HashMap<>();
        double allPersents = osStatistic.values().stream()
                .mapToDouble(i -> i)
                .sum();
        osStatistic.forEach((key, value) -> result.put(key, value / allPersents));
        return result;
    }
    public HashMap<String, Double> getBrowsersStatistic(HashMap<String, Integer> browsersStatistic){
        HashMap<String, Double> result = new HashMap<>();
        double allPersents = browsersStatistic.values().stream()
                .mapToDouble(i -> i)
                .sum();
        browsersStatistic.forEach((key, value) -> result.put(key, value / allPersents));
        return result;
    }

    public void createNotFoundPageList(LogEntry log){
        if(log.getResponseCode().equals("404")){
            notFoundPageList.add(log.getUrl());
        }
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

    public HashMap<String, Integer> getOsStatistic() {
        return osStatistic;
    }

    public HashSet<String> getNotFoundPageList() {
        return notFoundPageList;
    }


}
