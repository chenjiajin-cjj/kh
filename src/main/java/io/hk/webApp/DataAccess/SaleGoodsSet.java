package io.hk.webApp.DataAccess;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import io.framecore.Frame.PageData;
import io.framecore.Mongodb.ExpCal;
import io.framecore.Mongodb.IMongoQuery;
import io.hk.webApp.Domain.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

 
import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.SaleGoods;

import java.util.Hashtable;


@Repository(value="SaleGoodsSet")
@Scope(value="prototype")
public class SaleGoodsSet extends Set<SaleGoods> {

	public SaleGoodsSet() {
		super(_db.getDbName());
	}

	@Override
	public Class<SaleGoods> getType() {
		return SaleGoods.class;
	}

	/**
	 * 查询商品列表
	 * @param pars
	 * @param pageSize
	 * @param pageIndex
	 * @param order
	 * @param salerId
	 * @param type
	 * @return
	 */
    public PageData<SaleGoods> search(Hashtable<String, Object> pars, int pageSize, int pageIndex, String order, String salerId, String type) {
		BasicDBObject whereBson = buildWhere(pars,salerId,type);
		IMongoQuery<SaleGoods> query = this.Where(whereBson);
		query.OrderByDesc("createTime");
		PageData<SaleGoods> data =new PageData<>();
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
	private BasicDBObject buildWhere(Hashtable<String, Object> where, String salerId,String type){
		BasicDBObject whereBson = new BasicDBObject();
		BasicDBList values = new BasicDBList();
		if(null == where || where.size() == 0){
			return whereBson;
		}
		values.add(ExpCal.Analysis("salerId=?",salerId));
		if ("1".equals(type)) {
			values.add(ExpCal.Analysis("type=?",type));
		}
		for (String key : where.keySet()){
			switch (key){
				case "priceBegin1":{
					values.add(ExpCal.Analysis("priceBegin1>=?",Double.parseDouble(where.get(key).toString())));
					break;
				}
			}
		}
		whereBson.append("$and",values);
		return whereBson;
	}
}
