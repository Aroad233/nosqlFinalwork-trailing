package com.example.nosqlfinalhomework.Controller;

import EntityClass.Default.DefaultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
public class defaultController {
    @Autowired
    MongoTemplate mongoTemplate;
    @PostMapping("/default/insertDefault") //插入违约信息
    public String insertDefault(@RequestParam String studentID,@RequestParam String description,@RequestParam String handle){
        Date udate = new Date();
        Date sdate = new Date(udate.getTime());
        DefaultMessage defaultMessage=new DefaultMessage();
        defaultMessage.setStudentId(studentID);
        defaultMessage.setDescription(description);
        defaultMessage.setHandle(handle);
        defaultMessage.setTime(sdate);
        mongoTemplate.save(defaultMessage);
        return "插入成功";
    }
    @GetMapping("/default/queryDefault") //查询违约信息
    public List<DefaultMessage> queryDefault(){
        List<DefaultMessage> defaultMessage=new ArrayList<>();
        defaultMessage=mongoTemplate.findAll(DefaultMessage.class);
        return defaultMessage;
    }
    @GetMapping("/default/queryDefaultStuID") //通过学生ID查询违约信息
    public List<DefaultMessage> queryDefaultStuID(@RequestParam String studentID){
        List<DefaultMessage> defaultMessage=new ArrayList<>();
        Query query=Query.query(Criteria.where("studentId").is(studentID));
        defaultMessage=mongoTemplate.find(query,DefaultMessage.class);
        return defaultMessage;
    }
}
