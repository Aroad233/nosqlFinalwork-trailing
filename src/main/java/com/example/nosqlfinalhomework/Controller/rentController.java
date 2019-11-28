package com.example.nosqlfinalhomework.Controller;

import EntityClass.items.Items;
import EntityClass.rent.ItemState;
import EntityClass.rent.Rent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
public class rentController {
    @Autowired
    MongoTemplate mongoTemplate;
    @PostMapping("/rent/insertBrrow") //初始插入表与信息
    public String insertBrrow(@RequestParam String studentID, @RequestParam String itemName,
                              @RequestParam String Id/*, @RequestParam Date brrowDate,
                              @RequestParam Date returnDate,@RequestParam float price*/){
        Rent rent=new Rent();
        Date udate = new Date();
        Date sdate = new Date(udate.getTime());
        List<ItemState> itemStates=new ArrayList<>();
        ItemState itemState =new ItemState();
        itemState.setItemName(itemName);
        itemState.setId(Id);
        itemState.setBrrowDate(sdate);
        itemState.setReturnDate(null);
        itemState.setNeedMoney(0);
        itemStates.add(itemState);
        rent.setStudentID(studentID);
        rent.setItemState(itemStates);
        Query query=Query.query(Criteria.where("studentID").is(studentID).and("itemState.returnDate").is(null)
                .and("itemState.itemName").is(itemName));
        if(mongoTemplate.count(query,Rent.class)>0) {
            return "您还未归还不能重复租借";
        }
        mongoTemplate.save(rent);
        return "租借信息插入成功";
    }
    @PostMapping("/rent/insertReturn") //归还并插入金额
    public String insertReturn(@RequestParam String studentID, @RequestParam String itemName,
                              @RequestParam String Id) throws Exception {
        //获取当前退租的时间
        Date udate = new Date();
        Date sdate = new Date(udate.getTime());
        Criteria criteria=new Criteria();
        criteria.andOperator(Criteria.where("studentID").is(studentID),Criteria.where("itemState.returnDate").is(null),Criteria.where("itemState.itemName").is(itemName));
        Query query=Query.query(criteria);
        query.fields().include("itemState.brrowDate");
        Rent rent=mongoTemplate.findOne(query,Rent.class);
        //获取时间并计算费用
        ItemState itemState=rent.getItemState().get(0); //获取到第一个list的值
        Date brrowDate=itemState.getBrrowDate();
        Date returnTime=sdate;
        long hours=0;
        if((returnTime.getTime()-brrowDate.getTime())%(1000*60*60)>0) {
//            hours = (returnTime.getTime() - brrowDate.getTime()) / (1000 * 60 * 60)+1;
//        }else {.
            hours = (returnTime.getTime() - brrowDate.getTime()) / (1000 * 60 * 60)+1;
        }
        double needMoney=0;
        if(itemName.equals("伞")){
            needMoney=hours*0.1;
        }else{
            needMoney=hours*0.3;
        }
        if(mongoTemplate.count(query,Rent.class)>0) {
            Query query1=Query.query(Criteria.where("studentID").is(studentID).and("itemState.returnDate").is(null).and("itemState.Id").is(Id));
            Update update=Update.update("itemState.$.returnDate",sdate).set("itemState.$.needMoney",needMoney);
            mongoTemplate.updateFirst(query1,update,Rent.class);
            return "成功归还";
        }
        return "归还失败";
    }
    @PostMapping("/rent/insertBrrowItem")//插入租借信息
    public String insertBrrowItem(@RequestParam String studentID, @RequestParam String itemName,
                                  @RequestParam String Id){
        Date udate = new Date();
        Date sdate = new Date(udate.getTime());
        ItemState itemState =new ItemState();
        itemState.setItemName(itemName);
        itemState.setId(Id);
        itemState.setBrrowDate(sdate);
        itemState.setReturnDate(null);
        itemState.setNeedMoney(0);
        Criteria criteria=new Criteria();
        criteria.andOperator(Criteria.where("studentID").is(studentID),Criteria.where("itemState.returnDate").is(null),Criteria.where("itemState.itemName").is(itemName));
        Query query=Query.query(criteria);
        if(mongoTemplate.count(query,Rent.class)>0) {
            return "您还未归还不能重复租借";
        }else{
            Query query1=Query.query(Criteria.where("studentID").is(studentID));
            Update update=Update.update("studentID",studentID).addToSet("itemState",itemState);
            mongoTemplate.updateFirst(query1,update,Rent.class);
            return "租借信息插入成功";
        }
    }
    @GetMapping("/rent/queryStudentAllRM") //查询单个学生的所有租借信息
    public List<ItemState> queryStudentAllRM(@RequestParam String studentID){
        List<ItemState> itemStates=new ArrayList<>();
        Query query=Query.query(Criteria.where("studentID").is(studentID));
        query.fields().include("itemState");
        ItemState itemState=new ItemState();
        Rent rent=mongoTemplate.findOne(query,Rent.class);
        itemStates=rent.getItemState();
        return itemStates;
    }
    @GetMapping("/rent/queryStudenttOneRM") //查询所有学生的所有租借信息
    public List<Rent> queryStudentOneRM(){
        List<ItemState> itemStates=new ArrayList<>();
        ItemState itemState=new ItemState();
        List<Rent> rent=mongoTemplate.findAll(Rent.class);
        return rent;
    }
    @GetMapping("/rent/queryStudentNeedReturn") //根据学生id查询是否有要归还的东西
    public List<ItemState> queryStudentNeedReturn(@RequestParam String studentID){
        List<ItemState> itemStates=new ArrayList<>();
        Query query=Query.query(Criteria.where("studentID").is(studentID).and("itemState.returnDate").is(null));
        query.fields().include("itemState.$");
        ItemState itemState=new ItemState();
        Rent rent=mongoTemplate.findOne(query,Rent.class);
        itemStates=rent.getItemState();
        if(!itemStates.isEmpty())
            return itemStates;
        return null;
    }
}

