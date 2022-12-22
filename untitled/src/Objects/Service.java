package Objects;

import java.util.Date;

public class Service {

    private int id;
    private int car_id;
    private int repair_id;
    private Date date;

    public Service(int id, int car_id, int repair_id, Date date) {
        this.id = id;
        this.car_id = car_id;
        this.repair_id = repair_id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getCar_id() {
        return car_id;
    }

    public int getRepair_id() {
        return repair_id;
    }

    public Date getDate() {
        return date;
    }
}
