import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ParkingSession {
    private String licensePlate;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private Duration totalDuration;

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
        this.totalDuration = Duration.between(entryTime, exitTime);
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public Duration getTotalDuration() {
        return totalDuration;
    }

    public ParkingSession(String licensePlate, LocalDateTime entryTime) {
        this.licensePlate = licensePlate;
        this.entryTime = entryTime;
        this.exitTime = null;
        this.totalDuration = Duration.ZERO;
    }
}
