import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Parking {
    private final int capacity;
    private List<Car> parkedCars;
    private List<ParkingSession> completedSessions;

    public List<ParkingSession> getCompletedSessions() {
        return completedSessions;
    }

    private List<LogEntry> log;

    public Parking(int capacity) {
        this.capacity = capacity;
        this.parkedCars = new ArrayList<>();
        this.completedSessions = new ArrayList<>();
        this.log = new ArrayList<>();
    }

    public boolean enter(Car car, LocalDateTime localDateTime){
        if(parkedCars.size()<capacity && car.getState() == CarState.ON_TRANSIT){
            ParkingSession parkingSession = new ParkingSession(car.getLicensePlate(), localDateTime);
            car.setState(CarState.ON_PARKING);
            car.setCurrentSession(parkingSession);
            parkedCars.add(car);
            LogEntry logEntry = new LogEntry(car.getLicensePlate(), localDateTime, LogType.ENTRY);
            log.add(logEntry);
            return true;
        }
            return false;
    }

    public boolean exit(Car car, LocalDateTime localDateTime) {
        if (!parkedCars.contains(car)) return false;
        if (car.getState() != CarState.ON_PARKING) return false;
        if (car.getCurrentSession() == null) return false;

        ParkingSession session = car.getCurrentSession();

        session.setExitTime(localDateTime);
        session.calculateSum();
        car.setState(CarState.ON_TRANSIT);

        parkedCars.remove(car);
        completedSessions.add(session);
        car.getParkingHistory().add(session);

        log.add(new LogEntry(car.getLicensePlate(), localDateTime, LogType.EXIT));

        car.setCurrentSession(null);
        return true;
    }




}
