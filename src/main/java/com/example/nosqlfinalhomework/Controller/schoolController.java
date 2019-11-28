package com.example.nosqlfinalhomework.Controller;

import EntityClass.school.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@RestController
@CrossOrigin
public class schoolController {
    @Autowired
    MongoTemplate mongoTemplate;

    @PostMapping("/school/insertSchool")// 插入学校信息
    public String insertSchool(@RequestParam String schoolName, @RequestParam List<String> schoolItems) throws Exception{
        School school=new School();
        school.setSchoolName(schoolName);
        school.setSchoolItems(schoolItems);
        Query query=Query.query(Criteria.where("schoolName").is(schoolName));
        long i =mongoTemplate.count(query,School.class);
        if(i==0){
            mongoTemplate.save(school);
            return "插入成功";
        }
        return "插入失败,数据已存在请转修改";
    }
    @PostMapping("/school/insertSI")//插入学校中配置的物品
    public String insertSI(@RequestParam String schoolName, @RequestParam String schoolItems) throws Exception{
        Query query1=Query.query(Criteria.where("schoolName").is(schoolName));
        long i =mongoTemplate.count(query1,School.class);
        if(i>0) {
            Update update = Update.update("schoolName", schoolName).addToSet("schoolItems", schoolItems);
            mongoTemplate.updateFirst(query1, update, School.class);
            return "插入成功";
        }
        return "插入失败,该学校没有任何记录";
    }
    @PostMapping("/school/deleteSI")// 删除学校配置的物品
    public String deleteSI(@RequestParam String schoolName, @RequestParam String schoolItems) throws Exception{
        Query query1=Query.query(Criteria.where("schoolName").is(schoolName));
        long i =mongoTemplate.count(query1,School.class);
        if(i>0) {
            Update update = Update.update("schoolName", schoolName).pull("schoolItems", schoolItems);
            mongoTemplate.updateFirst(query1, update, School.class);
            return "删除成功成功";
        }
        return "删除失败,该学校没有任何记录";
    }
}
