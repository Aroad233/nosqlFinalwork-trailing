package EntityClass.items;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public class Items implements Serializable {
    private String itemName;
    private float price;
    private float rent;
    private String blSchool;
    private List<State> state;
    private List<String> schoolPlace;

    public List<String> getSchoolPlace() {
        return schoolPlace;
    }

    public void setSchoolPlace(List<String> schoolPlace) {
        this.schoolPlace = schoolPlace;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getRent() {
        return rent;
    }

    public void setRent(float rent) {
        this.rent = rent;
    }

    public String getBlSchool() {
        return blSchool;
    }

    public void setBlSchool(String blSchool) {
        this.blSchool = blSchool;
    }

    public List<State> getState() {
        return state;
    }

    public void setState(List<State> state) {
        this.state = state;
    }
}
