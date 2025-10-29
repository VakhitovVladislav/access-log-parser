package main.java;


import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;


public class Statistics {


    private long totalTraffic;
    public LocalDateTime minTime;
    public LocalDateTime maxTime;
    private int entryCount;
    private HashMap<String, Integer> osStatistic;
    private HashMap<String, Integer> browserStatistic;
    private HashMap<String, Integer> uniqueVisitorsList;
    private long uniqueVisitorsCount;
    private long realVisitorsCount;
    private long errorResponseCount;
    private final HashSet<String> notFoundPageList = new HashSet<>();

    private HashSet<String> pageList;
    private HashMap<String, Integer> uniqueVisitorsStatistic;

    public Statistics() {
        this.totalTraffic = 0;
        this.entryCount = 0;
        this.minTime = null;
        this.maxTime = null;
    }

    public void addEntry(List<LogEntry> logs) {
        browserStatistic = new HashMap<>();
        pageList = new HashSet<>();
        uniqueVisitorsList = new HashMap<>();
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
            createOKPagesList(log); // заполняем переменную ageList
            createNotFoundPageList(log); // заполняем переменную notFoundPageList
            operationSystemCount(log); // считаем общее число Операционных систем
            browsersCount(log); // считаем общее число браузеров
            realVisitorsCounter(log); // заполняем параметр realUsers
            errorResponseCounter(log); // считаем и заполняем переменную  errorResponseCount
            realVisitorsCounter(log); // считаем уникальных пользователей и добавляем их в HashMap

        }
    }
    private void realVisitorsCounter(LogEntry log){
        if(!log.getUserAgent().isBot()) {
            realVisitorsCount += 1;
            if (!uniqueVisitorsList.containsKey(log.getIp())){
                uniqueVisitorsList.put(log.getIp(), 1);
            } else {
                uniqueVisitorsList.replace(log.getIp(), uniqueVisitorsList.get(log.getIp())+1);
            }
        }

    }

    public long getUniqueVisitorsPerHour(){
        double uniqueIpCount = uniqueVisitorsList.values().size();
        return (long) ((double) realVisitorsCount /uniqueIpCount);
    }
    public double getVisitorStatisticPerHour(){
        return (double) realVisitorsCount / getHours();
    }
    public double getErrorStatisticPerHour(){
        return (double) errorResponseCount / getHours();
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

    public HashMap<String, Double> addOsStatistic(){
        HashMap<String, Double> result = new HashMap<>();
        double totalCount = osStatistic.values().stream()
                .mapToDouble(i -> i)
                .sum();
        osStatistic.forEach((key, value) -> result.put(key, value / totalCount));
        return result;
    }
    public HashMap<String, Double> addBrowsersStatistic(){
        HashMap<String, Double> result = new HashMap<>();
        double totalCount = browserStatistic.values().stream()
                .mapToDouble(i -> i)
                .sum();
        browserStatistic.forEach((key, value) -> result.put(key, value / totalCount));
        return result;
    }

    public void createNotFoundPageList(LogEntry log){
        if(log.getResponseCode().equals("404")){
            notFoundPageList.add(log.getUrl());
        }
    }
    public BigDecimal getTrafficRate() {
        double hours = getHours();
        return BigDecimal.valueOf(totalTraffic / hours);
    }

    private double getHours() {
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
        return hours;
    }
    private void errorResponseCounter(LogEntry log){
        if(log.getResponseCode().startsWith("4") || log.getResponseCode().startsWith("5")){
            errorResponseCount+=1;
        }
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
