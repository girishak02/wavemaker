
package Model;

public class Address {
    private String location;
    private Integer pin;

    public Address(String location, Integer pin) {
        this.location = location;
        this.pin = pin;
    }

    public String getLocation() {

        return location;
    }

    public void setLocation(String location) {

        this.location = location;
    }

    public Integer getPin() {

        return pin;
    }

    public void setPin(Integer pin) {

        this.pin = pin;
    }

    @Override
    public String toString()
    {
        return String.format("%-20s %-10d", location, pin);
    }

    public int getId() {
        return 0;
    }
}
