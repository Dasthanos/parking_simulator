import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        // 1️⃣ Создаём машины
        List<Car> cars = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            cars.add(new Car(generateLicensePlate(i)));
        }

        // 2️⃣ Создаём парковку
        Parking parking = new Parking(20);

        // 3️⃣ Настраиваем симуляцию
        LocalDateTime currentTime = LocalDateTime.of(2026, 1, 1, 0, 0);
        LocalDateTime endTime = currentTime.plusDays(30);
        Random random = new Random();

        // Список для отслеживания статистики по дням
        List<LocalDate> days = new ArrayList<>();

        while (currentTime.isBefore(endTime)) {
            // Добавляем уникальный день в список
            LocalDate day = currentTime.toLocalDate();
            if (!days.contains(day)) days.add(day);

            // Проходим по каждой машине
            for (Car car : cars) {
                if (car.getState() == CarState.ON_TRANSIT) {
                    // 3% шанс заехать
                    if (random.nextInt(100) < 3) {
                        parking.enter(car, currentTime);
                    }
                } else if (car.getState() == CarState.ON_PARKING) {
                    // 3% шанс выехать
                    if (random.nextInt(100) < 3) {
                        parking.exit(car, currentTime);
                    }
                }
            }

            // шаг 5 минут
            currentTime = currentTime.plusMinutes(5);
        }

        System.out.println("Симуляция завершена!");
        System.out.println("Общее количество сессий: " + parking.getCompletedSessions().size());
        System.out.println();

        // 4️⃣ Выводим статистику по каждому дню
        for (LocalDate day : days) {
            printDailyStats(parking, day);
            double occupancy = getAverageOccupancyPerDay(parking, day);
            System.out.printf("Средний процент загрузки парковки: %.2f%%\n", occupancy);
            System.out.println("--------------------------------------------------");
        }
        // Топ-10 машин по времени
        printTop10LongestParkedCars(parking);
    }














    // Генерация номера машины
    private static String generateLicensePlate(int index) {
        return String.format("KG%03dXYZ", index);
    }

    // Метод статистики за день
    private static void printDailyStats(Parking parking, LocalDate date) {
        int totalRevenue = 0;
        int shortParkingCount = 0;
        int totalCars = 0;

        for (ParkingSession session : parking.getCompletedSessions()) {
            LocalDate entryDate = session.getEntryTime().toLocalDate();
            LocalDate exitDate = session.getExitTime() != null ? session.getExitTime().toLocalDate() : null;

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

    // Средний процент загруженности парковки за день
    private static double getAverageOccupancyPerDay(Parking parking, LocalDate date) {
        int totalSteps = 0;
        int occupiedSum = 0;

        int capacity = 20; // твоя парковка

        for (ParkingSession session : parking.getCompletedSessions()) {
            LocalDate entryDate = session.getEntryTime().toLocalDate();
            LocalDate exitDate = session.getExitTime() != null ? session.getExitTime().toLocalDate() : null;

            if (entryDate.equals(date) || (exitDate != null && exitDate.equals(date))) {
                long minutes = session.getTotalDuration().toMinutes();
                int steps = (int) (minutes / 5); // шаг = 5 минут
                occupiedSum += steps;
                totalSteps += steps;
            }
        }

        int maxSteps = 24 * 12 * capacity; // 24 часа * 12 шагов в час * количество мест
        return maxSteps > 0 ? (double) occupiedSum / maxSteps * 100 : 0;
    }
    private static void printTop10LongestParkedCars(Parking parking) {
        Map<String, Long> totalDurationMap = new HashMap<>();

        // суммируем время по каждой машине
        for (ParkingSession session : parking.getCompletedSessions()) {
            totalDurationMap.put(
                    session.getLicensePlate(),
                    totalDurationMap.getOrDefault(session.getLicensePlate(), 0L) + session.getTotalDuration().toMinutes()
            );
        }

        // сортировка по времени и топ-10
        System.out.println("Топ-10 машин по общему времени на парковке:");
        totalDurationMap.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .forEach(entry -> System.out.println(
                        entry.getKey() + " - " + entry.getValue() + " минут"
                ));
    }
}

