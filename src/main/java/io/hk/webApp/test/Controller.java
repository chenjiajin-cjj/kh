package io.hk.webApp.test;

import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mongodb")
public class Controller {

    @Autowired
    private Db db;

    @PostMapping("add")
    public Object add(@RequestBody DoMain doMain){
        System.out.println(doMain.get("account"));
        return db.add(doMain);
    }

    @PostMapping("one")
    public Object one(String account){
        return db.selectOne(account);
    }

    @PostMapping("list")
    public Object list(){
        return db.selectList();
    }

    @PostMapping("update")
    public Object update(String _id){
        return db.updateById(_id);
    }

    @PostMapping("remove")
    public Object remove(String _id){
        return db.removeById(_id);
    }

}
