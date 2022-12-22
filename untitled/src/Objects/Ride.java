package Objects;

import java.util.Date;

public class Ride {

    private int id;
    private int trip_id;
    private String car_number;
    private Date dateBegin;
    private Date dateEnd;
    private float cargoWeight;

    public Ride(int id, int trip_id, String car_number, Date dateBegin, Date dateEnd, float cargoWeight) {
        this.id = id;
        this.trip_id = trip_id;
        this.car_number = car_number;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.cargoWeight = cargoWeight;
    }

    public int getId() {
        return id;
    }

    public int getTrip_id() {
        return trip_id;
    }

    public String getCar_number() {
        return car_number;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public float getCargoWeight() {
        return cargoWeight;
    }
}