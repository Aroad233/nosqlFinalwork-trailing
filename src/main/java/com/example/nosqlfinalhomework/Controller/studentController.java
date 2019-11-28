package com.example.nosqlfinalhomework.Controller;

import EntityClass.items.Items;
import EntityClass.items.State;
import EntityClass.school.School;
import EntityClass.student.StuMessage;
import EntityClass.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class studentController {
    @Autowired
    MongoTemplate mongoTemplate;

    @PostMapping("/student/insertStudent")
    public String insertStudent(@RequestParam String schoolName, @RequestParam String studentId,
                                @RequestParam String studentName,@RequestParam float balance,
                                @RequestParam int credit){
        StuMessage stuMessage=new StuMessage();
        List<StuMessage> stuMessages=new ArrayList<>();
        Student student=new Student();
        student.setSchoolName(schoolName);
        stuMessage.setStudentName(studentName);
        stuMessage.setStudentID(studentId);
        stuMessage.setBalance(balance);
        stuMessage.setCredit(credit);
        stuMessages.add(stuMessage);
        student.setStuMessage(stuMessages);
        Query query=Query.query(Criteria.where("schoolName").is(schoolName));
        long i=mongoTemplate.count(query,Student.class);
        if(i==0) {
            mongoTemplate.save(student);
            return "插入成功";
        }
        return "插入失败";
    }
    @PostMapping("/student/insertStuMessage") //
    public String insertStuMessage(@RequestParam String schoolName,@RequestParam String studentId,
                               @RequestParam String studentName,@RequestParam float balance,
                               @RequestParam int credit){
        StuMessage stuMessage =new StuMessage();
        stuMessage.setStudentName(studentName);
        stuMessage.setStudentID(studentId);
        stuMessage.setBalance(balance);
        stuMessage.setCredit(credit);
        Query query= Query.query(Criteria.where("schoolName").is(schoolName));
        Update update=Update.update("schoolName",schoolName).addToSet("stuMessage",stuMessage);
        Query query1= Query.query(Criteria.where("stuMessage.studentID").is(studentId));
        if(mongoTemplate.count(query1,Student.class)==0) {
            mongoTemplate.updateFirst(query, update, Student.class);
            return "插入成功";
        }
        return "已存在学生信息";
    }
    @PostMapping("/student/deleteStuId") //
    public String deleteStuId(@RequestParam String schoolName,@RequestParam String studentId,
                                   @RequestParam String studentName,@RequestParam float balance,
                                   @RequestParam int credit){
        StuMessage stuMessage =new StuMessage();
        stuMessage.setStudentName(studentName);
        stuMessage.setStudentID(studentId);
        stuMessage.setBalance(balance);
        stuMessage.setCredit(credit);
        Query query= Query.query(Criteria.where("schoolName").is(schoolName));
        Update update=Update.update("schoolName",schoolName).pull("stuMessage",stuMessage);
        Query query1= Query.query(Criteria.where("stuMessage.studentID").is(studentId));
        if(mongoTemplate.count(query1,Student.class)>0) {
            mongoTemplate.updateFirst(query, update, Student.class);
            return "删除成功";
        }
        return "不存在学生信息";
    }
    @PostMapping("/student/updateBalanceReduce") //余额扣除 balance 为要扣除的余额
    public String updateBalanceReduce(@RequestParam String schoolName,@RequestParam String studentId,@RequestParam float money){
        Query query= Query.query(Criteria.where("schoolName").is(schoolName).and("stuMessage.studentID").is(studentId));
        Update update=Update.update("stuMessage.$.studentID",studentId).inc("stuMessage.$.balance",-money);
        Query query1= Query.query(Criteria.where("stuMessage.studentID").is(studentId));
        if(mongoTemplate.count(query1,Student.class)>0) {
            mongoTemplate.updateFirst(query, update, Student.class);
            return "余额扣除成功";
        }
        return "不存在学生信息";
    }
    @PostMapping("/student/updateBalanceIncrease") //余额增加 balance 为要增加的余额
    public String updateBalanceIncrease(@RequestParam String schoolName,@RequestParam String studentId,@RequestParam float money){
        Query query= Query.query(Criteria.where("schoolName").is(schoolName).and("stuMessage.studentID").is(studentId));
        Update update=Update.update("stuMessage.$.studentID",studentId).inc("stuMessage.$.balance",money);
        Query query1= Query.query(Criteria.where("stuMessage.studentID").is(studentId));
        if(mongoTemplate.count(query1,Student.class)>0) {
            mongoTemplate.updateFirst(query, update, Student.class);
            return "余额充值成功";
        }
        return "不存在学生信息";
    }
    @PostMapping("/student/updateCreditReduce") //信用扣除 credit 为要扣除的信用
    public String updateCreditReduce(@RequestParam String schoolName,@RequestParam String studentId,@RequestParam int credit){
        Query query= Query.query(Criteria.where("schoolName").is(schoolName).and("stuMessage.studentID").is(studentId));
        Update update=Update.update("stuMessage.$.studentID",studentId).inc("stuMessage.$.credit",-credit);
        Query query1= Query.query(Criteria.where("stuMessage.studentID").is(studentId));
        if(mongoTemplate.count(query1,Student.class)>0) {
            mongoTemplate.updateFirst(query, update, Student.class);
            return "信用扣除成功";
        }
        return "不存在学生信息";
    }
    @PostMapping("/student/updateCreditIncrease") //信用增加 credit为要增加的信用
    public String updateCreditIncrease(@RequestParam String schoolName,@RequestParam String studentId,@RequestParam int credit){
        Query query= Query.query(Criteria.where("schoolName").is(schoolName).and("stuMessage.studentID").is(studentId));
        Update update=Update.update("stuMessage.$.studentID",studentId).inc("stuMessage.$.credit",credit);
        Query query1= Query.query(Criteria.where("stuMessage.studentID").is(studentId));
        if(mongoTemplate.count(query1,Student.class)>0) {
            mongoTemplate.updateFirst(query, update, Student.class);
            return "信用增加成功";
        }
        return "不存在学生信息";
    }
    @GetMapping("/student/queryAllStudent")//所有学生信息的查询
    public List<Student> queryAllStudent(@RequestParam String schoolName){
        List<Student> students=new ArrayList<>();
        Query query=Query.query(Criteria.where("schoolName").is(schoolName));
        students=mongoTemplate.find(query,Student.class);
        return students;
    }
}
