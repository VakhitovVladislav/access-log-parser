
package main.java;

import main.java.enums.Browsers;
import main.java.enums.OperationSystem;

public class UserAgent {
    private final Browsers browser;
    private final OperationSystem operationalSystem;

    public UserAgent(String userAgent) {
        this.browser = checkBrowser(userAgent);
        this.operationalSystem = checkOS(userAgent);
    }

    private OperationSystem checkOS(String userAgentString) {
        String checkString = userAgentString.toLowerCase();
        if (checkString.contains("windows")) {
            return OperationSystem.WINDOWS;
        } else if (checkString.contains("mac os") || checkString.contains("iPhone") || checkString.contains("iPad") || checkString.contains("OPiOS")) {
            return OperationSystem.MACOS;
        } else if (checkString.contains("android")) {
            return OperationSystem.ANDROID;
        } else if (checkString.contains("linux")) {
            return OperationSystem.LINUX;
        } else {
            return OperationSystem.OTHER_OS;
        }
    }

    private Browsers checkBrowser(String userAgentString) {
        String checkString = userAgentString.toLowerCase();
        if (checkString.contains("edg/") || checkString.contains("edge/")) {
            return Browsers.EDGE;
        } else if (checkString.contains("fireFox")) {
            return Browsers.FIREFOX;
        } else if (checkString.contains("opera") || checkString.contains("opr/")) {
            return Browsers.OPERA;
        } else if (checkString.contains("safari")) {
            return Browsers.SAFARI;
        } else if (checkString.contains("chrome")) {
            return Browsers.CHROME;
        } else {
            return Browsers.OTHER;
        }
    }

    //Mozilla/5.0 AppleWebKit/537.36 (KHTML, like Gecko; compatible; bingbot/2.0; +http://www.bing.com/bingbot.htm) Chrome/103.0.5060.134 Safari/537.36"
    //"Mozilla/5.0 (Linux; Android 7.0; Moto G (5) Build/NPPS25.137-93-14; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/69.0.3497.100 Mobile Safari/537.36 [FB_IAB/FB4A;FBAV/100.238.238.226.101;]"
    //"Mozilla/5.0 (Linux; Android 6.0.1; Nexus 5X Build/MMB29P) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.5195.125 Mobile Safari/537.36 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)"
    //"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36 Edge/18.18362"
    //"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36 OPR/67.0.3575.79"
    //"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36"
    //"Mozilla/5.0 (iPhone; CPU iPhone OS 8_1_2 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) OPiOS/9.1.0.86723 Mobile/12B440 Safari/9537.53"
    //"Mozilla/5.0 (feeder.co; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36"
    public Browsers getBrowser() {
        return browser;
    }

    public OperationSystem getOperationalSystem() {
        return operationalSystem;
    }
    @Override
    public String toString() {
        return "UserAgent{" +
                "browser='" + browser + '\'' +
                ", operationalSystem='" + OperationSystem.valueOf(String.valueOf(operationalSystem)) + '\'' +
                '}';
    }
}

