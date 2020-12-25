package io.hk.webApp.DataAccess;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import io.framecore.Frame.PageData;
import io.framecore.Mongodb.ExpCal;
import io.framecore.Mongodb.IMongoQuery;
import io.hk.webApp.Domain.Product;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Tools.OtherExcetion;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

 
import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.Brand;

import java.util.Hashtable;


@Repository(value="BrandSet")
@Scope(value="prototype")
public class BrandSet extends Set<Brand> {

	public BrandSet() {
		super(_db.getDbName());
	}

	@Override
	public Class<Brand> getType() {
		return Brand.class;
	}

	/**
	 * 添加品牌
	 * @param brand
	 * @return
	 */
	public boolean addBrand(Brand brand) {
		if(this.Where("name=?",brand.getName()).Exist()) {
			throw new OtherExcetion(("品牌["+brand.getName()+"]已经存在"));
		}
		this.Add(brand);
		return true;
	}

	/**
	 * 查询品牌列表
	 * @param pars
	 * @param pageSize
	 * @param pageIndex
	 * @param order
	 * @return
	 */
    public PageData<Brand> search(Hashtable<String, Object> pars, int pageSize, int pageIndex, String order, User user) {
		PageData<Brand> data =new PageData<>();
		data.rows=this.Where("userId=?",user.getId()).Limit(pageSize,pageIndex).ToList();
		data.total=this.Where("userId=?",user.getId()).Count();
		return data;
    }

    public PageData<Brand> search(Hashtable<String, Object> where, int pageSize, int pageIndex, String order) {
		BasicDBObject whereBson = buildWhere(where);
		IMongoQuery<Brand> query = this.Where(whereBson);
		query.OrderByDesc("_ctime");
		PageData<Brand> data =new PageData<>();
		data.rows = query.Limit(pageSize,pageIndex).ToList();
		data.total = this.Where(whereBson).Count();
		return data;
    }

	/**
	 * 构建查询商品列表的 where
	 * @param where
	 * @return
	 * @throws Exception
	 */
	private BasicDBObject buildWhere(Hashtable<String, Object> where){
		BasicDBObject whereBson = new BasicDBObject();
		BasicDBList values = new BasicDBList();
		if(null == where || where.size() == 0){
			return whereBson;
		}
		for (String key : where.keySet()){
			switch (key){
				case "name":{
					values.add(ExpCal.Analysis("name=?",where.get(key).toString()));
					break;
				}
				case "status":{
					values.add(ExpCal.Analysis("status=?",where.get(key).toString()));
					break;
				}
			}
		}
		whereBson.append("$and",values);
		return whereBson;
	}

}
