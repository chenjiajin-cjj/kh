package io.hk.webApp.DataAccess;

import io.framecore.Frame.PageData;
import io.hk.webApp.Domain.Product;
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
    public PageData<Brand> search(Hashtable<String, Object> pars, int pageSize, int pageIndex, String order) {
		PageData<Brand> data =new PageData<>();
		data.rows=this.ToList();
		data.total=this.Count();
		return data;
    }
}
