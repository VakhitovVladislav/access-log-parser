package main.java;


import main.java.enums.HttpMethods;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LogEntry {
    private final String ip;
    private final LocalDateTime dateTime;
    private final HttpMethods httpMethod;
    private final String url;
    private final String responseCode;
    private final long responseSize;

    private final String refer;
    private final UserAgent userAgent;
    private static final DateTimeFormatter APACHE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);

    public LogEntry(String line) {
        this.ip = extractIp(line);
        line = line.substring(line.indexOf(ip));
        this.dateTime = extractDateTime(line);
        line = line.substring(line.indexOf("\""));
        this.httpMethod =extractHttpMethods(line);
        line = line.substring(line.indexOf(" ")).trim();
        this.url =extractUrl(line);
        line = line.substring(line.indexOf("\" ")+1).trim();
        this.responseCode = extractResponseCode(line);
        line = line.substring(line.indexOf(" ")).trim();
        this.responseSize = extractResponseSize(line);
        this.refer = extractRefer(line);
        this.userAgent = new UserAgent(line);
    }
//15.40.185.139 - - [25/Sep/2022:06:25:13 +0300] "GET /recruitment/november-reports/anthropology/6378/62/d46438?print=1 HTTP/1.0" 200 16862 "-" "Mozilla/5.0 (compatible; YandexBot/3.0; +http://yandex.com/bots)"
    private String extractIp(String line){
        return line.substring(0, line.indexOf(" ")).trim();
        }
        private LocalDateTime extractDateTime(String line){
            String date = (line.substring(line.indexOf("["), line.indexOf("]")));
            date = date.replace("[", "").replace("]", "").trim();

            return LocalDateTime.parse(date, APACHE_FORMATTER);
        }
        private HttpMethods extractHttpMethods(String line){
            return HttpMethods.valueOf(line.substring(line.indexOf("\""), line.indexOf(" ")).replace("\"",""));
        }
        private String extractUrl(String line){
            return line.substring(line.indexOf("/")+1, line.indexOf(" "));
        }
        private String extractResponseCode(String line){
            return line.substring(0, line.indexOf(" "));
        }
        private Long extractResponseSize(String line){
            return Long.parseLong(line.substring(0, line.indexOf(" ")));
        }
        private String extractRefer(String line){
        if(line.indexOf("http://") == -1){
            return null;
        }else
            return line.substring(line.indexOf("http://")).replace(")", "");
        }
    public String getIp() {
        return ip;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public HttpMethods getHttpMethod() {
        return httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public long getResponseSize() {
        return responseSize;
    }

    public String getRefer() {
        return refer;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "ip='" + ip + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", httpMethod=" + httpMethod +
                ", url='" + url + '\'' +
                ", responseCode='" + responseCode + '\'' +
                ", responseSize=" + responseSize +
                ", refer='" + refer + '\'' +
                ", userAgent=" + userAgent.toString() +
                '}';
    }
}

