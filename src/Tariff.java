import java.time.Duration;

public class Tariff {
    private int pricePerHour;

    public Tariff(int pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public int calculate(Duration duration) {
        long hours = Math.max(1, duration.toHours());
        return (int) hours * pricePerHour;
    }
}