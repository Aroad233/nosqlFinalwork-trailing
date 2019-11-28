package EntityClass.rent;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Rent {
    private String studentID;
    private List<ItemState> itemState;

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public List<ItemState> getItemState() {
        return itemState;
    }

    public void setItemState(List<ItemState> itemState) {
        this.itemState = itemState;
    }
}
