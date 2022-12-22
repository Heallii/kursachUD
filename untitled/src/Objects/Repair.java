package Objects;

public class Repair {

    private int id;
    private String type;
    private int time;

    public Repair(int id, String type, int time) {
        this.id = id;
        this.type = type;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getTime() {
        return time;
    }
}
