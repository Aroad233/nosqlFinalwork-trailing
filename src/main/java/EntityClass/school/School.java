package EntityClass.school;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class School {
    private String schoolName;
    private List<String> schoolItems;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public List<String> getSchoolItems() {
        return schoolItems;
    }

    public void setSchoolItems(List<String> schoolItems) {
        this.schoolItems = schoolItems;
    }
}
