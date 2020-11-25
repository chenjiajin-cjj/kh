package io.hk.webApp.test;

import io.framecore.Mongodb.Set;
import io.hk.webApp.DataAccess._db;
import io.hk.webApp.Domain.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository(value="Db")
@Scope(value="prototype")
public class Db extends Set<DoMain> {

    public Db() {
        super(_db.getDbName());
    }

    @Override
    public Class<DoMain> getType() {
        return DoMain.class;
    }

    /**
     * 新增
     * @param doMain
     * @return
     */
    public Object add(DoMain doMain){
        return this.Add(doMain);
    }

    /**
     * 查询单个
     * @param account
     * @return
     */
    public Object selectOne(String account){
        DoMain doMain = this.Where("account=?").First();
        return doMain;
    }

    /**
     * 列表查询
     * @return
     */
    public Object selectList(){
        return this.Where("").Limit(0,5).OrderByDesc("account").ToList();
    }

    /**
     * 根据id修改
     * @param id
     * @return
     */
    public Object updateById(String id){
        System.out.println(id);
        DoMain doMain = new DoMain();
        doMain.setAccount("测试修改账号");
        doMain.setPassword("测试修改密码");
        return this.Update(id,doMain);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    public Object removeById(String id){
        return this.removeById(id);
    }

}
