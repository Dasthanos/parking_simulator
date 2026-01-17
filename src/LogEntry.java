import java.time.LocalDateTime;

public class LogEntry {
    private String licensePlate;
    private LocalDateTime time;
    private LogType type;

    public LogEntry(String licensePlate, LocalDateTime time, LogType type) {
        this.licensePlate = licensePlate;
        this.time = time;
        this.type = type;
    }
    public String getLicensePlate() { return licensePlate; }
    public LocalDateTime getTime() { return time; }
    public LogType getType() { return type; }
}
