package main.java.enums;

public enum OperationSystem {
    WINDOWS("Windows"),
    MACOS("macOS"),
    LINUX("Linux"),
    ANDROID("Android"),
    IOS("iOS"),
    OTHER_OS("Other_OS");
    private final String operationSystemName;

    OperationSystem(String operationSystemName) {
        this.operationSystemName = operationSystemName;
    }

    @Override
    public String toString() {
        return operationSystemName;
    }

    public String getOperationSystemName() {
        return operationSystemName;
    }
}
