import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ParkingSession {
    private String licensePlate;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private Duration totalDuration;
    private int sum;

    public void calculateSum() {
        if (exitTime == null) return; // сессия не закрыта

        LocalDateTime start = entryTime;
        LocalDateTime end = exitTime;

        int totalCents = 0;

        while (start.isBefore(end)) {
            int hour = start.getHour();
            if (hour >= 9 && hour < 21) { // платное время
                totalCents += 10; // 5 минут = 10 центов
            }
            start = start.plusMinutes(5);
        }

        // выставляем только если машина >30 минут
        if (totalDuration.toMinutes() >= 30) {
            this.sum = totalCents;
        } else {
            this.sum = 0;
        }
    }


    public int getSum() {
        return sum;
    }
    public void setSum(int sum) {
        this.sum = sum;
    }

    public void setExitTime(LocalDateTime exitTime) {
        if (this.exitTime != null) {
            throw new IllegalStateException("Parking session already closed");
        }
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
