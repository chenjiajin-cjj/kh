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
		if(this.Where("name=?",pro.getName()).Exist()) {
			throw new OtherExcetion(("商品["+pro.getName()+"]已经存在"));
		}
		this.Add(pro);
		return true;
	}

	/**
	 * 列表查询
	 * @param where
	 * @param limitFrom
	 * @param limitLen
	 * @param order
	 * @param factoryId
	 * @return
	 */
	public PageData<Product> search(Hashtable<String, Object> where, int limitFrom, int limitLen, String order,String factoryId) {
		BasicDBObject whereBson = buildWhere(where,factoryId);
		IMongoQuery<Product> query = this.Where(whereBson);
		query.OrderByDesc("_ctime");
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
		if(null == where || where.size() == 0){
			return whereBson;
		}
		values.add(ExpCal.Analysis("factoryId=?",factoryId));
		for (String key : where.keySet()){
			switch (key){
				case "name":{
					values.add(ExpCal.Analysis("name like?",where.get(key).toString()));
					break;
				}
				case "categoryId":{
					values.add(ExpCal.Analysis("categoryCode=?",where.get(key).toString()));
					break;
				}
				case "brandId":{
					values.add(ExpCal.Analysis("brandId=?",where.get(key).toString()));
					break;
				}
				case "tagHot":{
					values.add(ExpCal.Analysis("tagHot=?",where.get(key).toString()));
					break;
				}
				case "tagRec":{
					values.add(ExpCal.Analysis("tagRec=?",where.get(key).toString()));
					break;
				}
				case "tagNew":{
					values.add(ExpCal.Analysis("tagNew=?",where.get(key).toString()));
					break;
				}
				case "priceBegin1":{
					values.add(ExpCal.Analysis("priceBegin1>=?",Double.parseDouble(where.get(key).toString())));
					break;
				}
				case "priceBegin2":{
					values.add(ExpCal.Analysis("priceBegin2<=?",Double.parseDouble(where.get(key).toString())));
					break;
				}
			}
		}
		whereBson.append("$and",values);
		return whereBson;
	}

}
