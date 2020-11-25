package io.hk.webApp.DataAccess;

import java.util.Hashtable;

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


	public PageData<Product> search(Hashtable<String, Object> where, int pageSize, int pageIndex, String order) {
		PageData<Product> data =new PageData<Product>();
		data.rows=this.ToList();
		data.total=this.Count();
		
		return data;
	}

}
