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
    private final HashMap<String, Integer> osStatistic;
    private final HashMap<String, Integer> browserStatistic;
    private final HashMap<String, Integer> uniqueVisitorsList;
    private long realVisitorsCount;
    private long errorResponseCount;
    private final HashSet<String> notFoundPageList = new HashSet<>();
    private final HashMap<LocalDateTime, Integer> visitsPerSecondList;
    private final HashSet<String> pageList;
    private final HashSet<String> referThisSiteList;



    public Statistics() {
        this.totalTraffic = 0;
        this.entryCount = 0;
        this.minTime = null;
        this.maxTime = null;
        this.browserStatistic = new HashMap<>();
        this.pageList = new HashSet<>();
        this.uniqueVisitorsList = new HashMap<>();
        this.osStatistic = new HashMap<>();
        this.referThisSiteList = new HashSet<>();
        this.visitsPerSecondList = new HashMap<>();

    }

    public void addEntry(List<LogEntry> logs) {

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
            createOKPagesList(log); // заполняем переменную pageList
            createNotFoundPageList(log); // заполняем переменную notFoundPageList
            operationSystemCount(log); // считаем общее число Операционных систем
            browsersCount(log); // считаем общее число браузеров// заполняем параметр realUsers
            errorResponseCounter(log); // считаем и заполняем переменную  errorResponseCount
            uniqueVisitorsCounter(log); // считаем уникальных пользователей и добавляем их в HashMap
            addReferThisSiteList(log); // наполняем список доменов, которые поситили сайт
            addVisitsCountPerSecond(log);
            addUniqueVisitorsList(log);


        }
    }

    public Map.Entry<String, Integer> getMaxVisitfoVisitor(){
        return uniqueVisitorsList.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);
    }
    public Map.Entry<LocalDateTime, Integer> getVisitsCountPerSecond(){
        return visitsPerSecondList.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);
    }
    private void addVisitsCountPerSecond(LogEntry log){
        if (!log.getUserAgent().isBot())
            visitsPerSecondList.compute(log.getDateTime(), (k, v)-> v == null ? v=1 : v + 1);
    }

    private void fromHourToSecond(){
        long seconds = Duration.between(minTime, maxTime).getSeconds();
        Stream.iterate(minTime, date -> date.plusSeconds(1))
                .limit(seconds +1)
                .forEach(date -> visitsPerSecondList.put(date, 1));

    }
    private void uniqueVisitorsCounter(LogEntry log){
        if(!log.getUserAgent().isBot()) {
            realVisitorsCount += 1;
        }
    }

    private void addUniqueVisitorsList(LogEntry log){
        if(!log.getUserAgent().isBot()) {
            uniqueVisitorsList.compute(log.getIp(), (k, v)-> v == null ? v = 1 : v+1);
        }
    }
    private void addReferThisSiteList(LogEntry log){
        if(!log.getUserAgent().isBot())
            referThisSiteList.add(log.getRefer());
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
        osStatistic.compute(log.getUserAgent().getOperationalSystem().toString(), (k,v)-> v == null ? v=1 : v+1);
    }

    private void browsersCount(LogEntry log) {
        browserStatistic.compute(log.getUserAgent().getBrowser().getBrowserName(), (k,v)-> v == null ? v=1 : v+1);
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

    private void errorResponseCounter(LogEntry log){
        if(log.getResponseCode().startsWith("4") || log.getResponseCode().startsWith("5")){
            errorResponseCount+=1;
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

    public HashSet<String> getReferThisSiteList() {
        return referThisSiteList;
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
