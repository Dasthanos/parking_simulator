import java.time.LocalDate;
import java.util.List;

public class Statistics {

    // Возвращает статистику за конкретный день
    public static void printDailyStats(Parking parking, LocalDate date) {
        List<ParkingSession> sessions = parking.getCompletedSessions();

        int totalRevenue = 0;
        int shortParkingCount = 0;
        int totalCars = 0;

        for (ParkingSession session : sessions) {
            LocalDate entryDate = session.getEntryTime().toLocalDate();
            LocalDate exitDate = session.getExitTime() != null ? session.getExitTime().toLocalDate() : null;

            // Берём только сессии, которые хотя бы частично попадают в этот день
            if (entryDate.equals(date) || (exitDate != null && exitDate.equals(date))) {
                totalRevenue += session.getSum();
                totalCars++;
                if (session.getTotalDuration().toMinutes() < 30) {
                    shortParkingCount++;
                }
            }
        }

        double avgRevenue = totalCars > 0 ? (double) totalRevenue / totalCars : 0;

        System.out.println("Статистика за " + date);
        System.out.println("Общее количество машин: " + totalCars);
        System.out.println("Количество машин <30 минут: " + shortParkingCount);
        System.out.println("Общий заработок (центов): " + totalRevenue);
        System.out.printf("Средний заработок на машину: %.2f центов\n", avgRevenue);
    }
}