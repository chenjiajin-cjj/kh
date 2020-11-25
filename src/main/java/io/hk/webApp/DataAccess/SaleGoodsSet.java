package io.hk.webApp.DataAccess;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

 
import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.SaleGoods;
 
 

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
	
	
	

	

}
