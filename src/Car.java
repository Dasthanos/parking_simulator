import java.util.ArrayList;
import java.util.List;

public class Car {
    private CarState state;
    private String licensePlate;
    private ParkingSession currentSession;
    private List<ParkingSession> parkingHistory;

    public void setState(CarState state) {
        this.state = state;
    }

    public CarState getState() {
        return state;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public ParkingSession getCurrentSession() {
        return currentSession;
    }

    public List<ParkingSession> getParkingHistory() {
        return parkingHistory;
    }

    public void setCurrentSession(ParkingSession currentSession) {
        this.currentSession = currentSession;
    }

    public Car(String licensePlate) {
        this.state = CarState.ON_TRANSIT;
        this.licensePlate = licensePlate;
        this.parkingHistory = new ArrayList<>();
    }

}
