package com.example.nosqlfinalhomework.Controller;

import EntityClass.items.Items;
import EntityClass.items.State;
import com.example.nosqlfinalhomework.SpringBootMongoDBConfig;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.mongodb.core.query.Query;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
public class itemController {
    @Autowired
    MongoTemplate mongoTemplate;


    @PostMapping("/items/insertTable") //插入物品文档
    public String insertTable(@RequestParam String itemName,@RequestParam float price,
                         @RequestParam float rent,@RequestParam String blSchool,@RequestParam List<String> schoolPlace,
                         @RequestParam String id,@RequestParam String isRent,
                         @RequestParam String position){
        Items item=new Items();
        State state=new State();
        List<State>states=new ArrayList<>();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setRent(rent);
        item.setBlSchool(blSchool);
        item.setSchoolPlace(schoolPlace);
        state.setId(id);
        state.setIsRent(isRent);
        state.setPosition(position);
        states.add(state);

        item.setState(states);
        mongoTemplate.save(item);
        return "插入成功";
    }
    @PostMapping("/items/insertUState") //插入雨伞状态信息
    public String insertUState(@RequestParam String id,@RequestParam String isRent,
                              @RequestParam String position){
        State state=new State();
        state.setId(id);
        state.setIsRent(isRent);
        state.setPosition(position);
        Query query= Query.query(Criteria.where("itemName").is("伞"));
        Update update1=Update.update("itemName","伞").addToSet("state",state);
        mongoTemplate.updateFirst(query,update1,Items.class);
        return "state插入成功";
    }
    @PostMapping("/items/insertPState") //插入充电宝状态信息
    public String insertPState(@RequestParam String id,@RequestParam String isRent,
                               @RequestParam String position){
        State state=new State();
        state.setId(id);
        state.setIsRent(isRent);
        state.setPosition(position);
        Query query= Query.query(Criteria.where("itemName").is("充电宝"));
        Update update1=Update.update("itemName","充电宝").addToSet("state",state);
        mongoTemplate.updateFirst(query,update1,Items.class);
        return "state插入成功";
    }
    @GetMapping("/items/queryTable") //查询blSchool=温州商学院的所有表
    public List<Items> queryTable(){
        List<Items> items= new ArrayList<>();
        Query query= Query.query(Criteria.where("blSchool").is("温州商学院"));
        items=mongoTemplate.find(query,Items.class);
        return items;
    }
    @PostMapping("/items/deleteUid") //通过雨伞id 删除
    public String deleteUid(@RequestParam String uid,@RequestParam String isRent,
                            @RequestParam String position){
        State state=new State();
        state.setId(uid);
        state.setIsRent(isRent);
        state.setPosition(position);
        Query query= Query.query(Criteria.where("itemName").is("伞"));
        Update update=Update.update("itemName","伞").pull("state",state);
        //用于判断是否存在该数据
        Query query1=Query.query(Criteria.where("state").is(state));
        long i = mongoTemplate.count(query1,Items.class);

        if(i>0) {
            mongoTemplate.updateFirst(query, update, Items.class);
            return "删除成功";
        }
        return "删除失败";
    }
    @PostMapping("/items/deletePid") //通过充电宝id 删除
    public String deletePid(@RequestParam String pid,@RequestParam String isRent,
                            @RequestParam String position){
        State state=new State();
        state.setId(pid);
        state.setIsRent(isRent);
        state.setPosition(position);
        Query query= Query.query(Criteria.where("itemName").is("充电宝"));
        Update update=Update.update("itemName","充电宝").pull("state",state);
        //用于判断是否存在该数据
        Query query1=Query.query(Criteria.where("state").is(state));
        long i = mongoTemplate.count(query1,Items.class);

        if(i>0) {
            mongoTemplate.updateFirst(query, update, Items.class);
            return "删除成功";
        }
        return "删除失败";
    }
    @PostMapping("/item/updateUPrice")
    public String updateUPrice(@RequestParam String itemName,@RequestParam float price){
        Query query=Query.query(Criteria.where("itemName").is(itemName));
        Update update=Update.update("itemName",itemName).set("price",price);
        if(mongoTemplate.count(query,Items.class)>0){
            mongoTemplate.updateFirst(query,update,Items.class);
            return "更改价格成功";
        }
        return  "更改价格失败 请确认是否存在数据";
    }
}
