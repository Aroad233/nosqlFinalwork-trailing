package EntityClass.student;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Student {
    private String schoolName;
    private List<StuMessage> stuMessage;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public List<StuMessage> getStuMessage() {
        return stuMessage;
    }

    public void setStuMessage(List<StuMessage> stuMessage) {
        this.stuMessage = stuMessage;
    }
}
