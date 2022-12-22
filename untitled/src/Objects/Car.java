package Objects;

public class Car {

    private int id;
    private String number;
    private String brand;
    private String type;
    private String color;

    public Car(int id, String number, String brand, String type, String color) {
        this.id = id;
        this.number = number;
        this.brand = brand;
        this.type = type;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getBrand() {
        return brand;
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }
}
