package io.hk.webApp.DataAccess;

import java.util.Hashtable;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import io.framecore.Mongodb.ExpCal;
import io.framecore.Mongodb.IMongoQuery;
import io.framecore.Mongodb.Query;
import io.hk.webApp.Tools.OtherExcetion;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import io.framecore.Aop.Holder;
import io.framecore.Frame.PageData;
import io.framecore.Frame.Result;
import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.Product;

@Repository(value="ProductSet")
@Scope(value="prototype")
public class ProductSet extends Set<Product> {

	public ProductSet() {
		super(_db.getDbName());
	}

	@Override
	public Class<Product> getType() {
		return Product.class;
	}

	/**
	 * 新增商品
	 * @param pro
	 * @return
	 */
	public boolean addProduct(Product pro) {
		if(this.Where("code=?",pro.getCode()).Exist()) {
			throw new OtherExcetion(("商品["+pro.getCode()+"]已经存在"));
		}
		this.Add(pro);
		return true;
	}


	public PageData<Product> search(Hashtable<String, Object> where, int limitFrom, int limitLen, String order,String factoryId) {
		BasicDBObject whereBson = buildWhere(where,factoryId);
		IMongoQuery<Product> query = this.Where(whereBson);
		query.OrderByDesc("createTime");
		PageData<Product> data =new PageData<>();
		data.rows = query.Limit(limitFrom,limitLen).ToList();
		data.total = this.Where(whereBson).Count();
		return data;
	}

	/**
	 * 构建查询商品列表的 where
	 * @param where
	 * @return
	 * @throws Exception
	 */
	private BasicDBObject buildWhere(Hashtable<String, Object> where,String factoryId){
		BasicDBObject whereBson = new BasicDBObject();
		BasicDBList values = new BasicDBList();
		if(null == where){
			return whereBson;
		}
		values.add(ExpCal.Analysis("factoryId=?",factoryId));
		for (String key : where.keySet()){
			switch (key){
//				case "priceBegin1":{
//					values.add(ExpCal.Analysis("priceBegin1>=?",Double.parseDouble(where.get(key).toString())));
//					break;
//				}
//				case "priceBegin2":{
//					values.add(ExpCal.Analysis("priceBegin2<=?",Double.parseDouble(where.get(key).toString())));
//					break;
//				}
				case "brandId":{
					values.add(ExpCal.Analysis("brandId=?",where.get(key).toString()));
					break;
				}
			}
		}
		whereBson.append("$and",values);
		return whereBson;
	}

}
