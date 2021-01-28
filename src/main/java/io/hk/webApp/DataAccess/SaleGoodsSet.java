package io.hk.webApp.DataAccess;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import io.framecore.Frame.PageData;
import io.framecore.Mongodb.ExpCal;
import io.framecore.Mongodb.IMongoQuery;
import io.hk.webApp.Domain.*;
import io.hk.webApp.Tools.BaseType;
import io.hk.webApp.Tools.BsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


import io.framecore.Mongodb.Set;

import java.util.Hashtable;


@Repository(value = "SaleGoodsSet")
@Scope(value = "prototype")
public class SaleGoodsSet extends Set<SaleGoods> {

    public SaleGoodsSet() {
        super(_db.getDbName());
    }

    @Override
    public Class<SaleGoods> getType() {
        return SaleGoods.class;
    }

    @Autowired
    private ProductSet productSet;

    @Autowired
    private AuthSet authSet;

    @Autowired
    private UserSet userSet;

    @Autowired
    private CategorySet categorySet;

    @Autowired
    private BrandSet brandSet;

    @Autowired
    private ClassifySet classifySet;

    /**
     * 查询商品列表
     *
     * @param pars
     * @param pageSize
     * @param pageIndex
     * @param order
     * @param type
     * @return
     */
    public PageData<SaleGoods> search(Hashtable<String, Object> pars, int pageSize, int pageIndex, String order, User saler, String type) {
        BasicDBObject whereBson = buildWhere(pars, saler.getId(), type);
        IMongoQuery<SaleGoods> query = this.Where(whereBson);
        query.OrderByDesc("createTime");
        PageData<SaleGoods> data = new PageData<>();
        data.rows = query.Limit(pageSize, pageIndex).ToList();
        for (int i = 0; i < data.rows.size(); i++) {
            SaleGoods a = data.rows.get(i);
            Product product = productSet.Get(a.getProductId());
            if(null == product || !BaseType.Status.YES.getCode().equals(product.getIllegal())){
                continue;
            }
            if (null != a.getPrice() && a.getPrice() > 0) {
                product.setSupplyPriceTaxNo(a.getPrice());
                product.setSupplyPriceTax(a.getPriceTax());
            } else {

            }
            if (StringUtils.isNotEmpty(product.getFactoryId())) {
                User factory = userSet.Get(product.getFactoryId());
                if (null != factory && StringUtils.isNotEmpty(factory.getCompanyName())) {
                    a.setCName(factory.getCompanyName());
                }
            }
            a.setAuthentication(BaseType.Status.YES.getCode().equals(saler.getAuth()));
            Category category = categorySet.Get(product.getCategoryCode());
            String categoryName = "无";
            if (null != category && StringUtils.isNotEmpty(category.getName())) {
                categoryName = category.getName();
            }
            Brand brand = brandSet.Get(product.getBrandId());
            String brandName = "无";
            if (null != brand && StringUtils.isNotEmpty(brand.getName())) {
                brandName = brand.getName();
            }
            String classifyName = "无";
            Classify classify = classifySet.Get(a.getClassifyId());
            if (null != classify && StringUtils.isNotEmpty(classify.getName())) {
                classifyName = classify.getName();
            }
            product.setClassifyName(classifyName);
            product.setCategoryName(categoryName);
            product.setBrandName(brandName);
            a.setProduct(BsonUtil.toDocument(product));
        }
        data.total = this.Where(whereBson).Count();
        return data;
    }

    /**
     * 构建查询商品列表的 where
     *
     * @param where
     * @return
     * @throws Exception
     */
    private BasicDBObject buildWhere(Hashtable<String, Object> where, String salerId, String type) {
        BasicDBObject whereBson = new BasicDBObject();
        BasicDBList values = new BasicDBList();
        values.add(ExpCal.Analysis("salerId=?", salerId));
        if ("1".equals(type)) {
            values.add(ExpCal.Analysis("type=?", type));
        }
        if (null == where || where.size() == 0) {
            whereBson.append("$and", values);
            return whereBson;
        }
        for (String key : where.keySet()) {
            switch (key) {
                case "priceBegin1": {
                    values.add(ExpCal.Analysis("priceBegin1>=?", Double.parseDouble(where.get(key).toString())));
                    break;
                }
                case "categoryCode": {
                    values.add(ExpCal.Analysis("categoryCode=?", where.get(key).toString()));
                    break;
                }
                case "productGroup": {
                    values.add(ExpCal.Analysis("productGroup=?", where.get(key).toString()));
                    break;
                }
                case "tagRec": {
                    values.add(ExpCal.Analysis("tagRec=?", where.get(key).toString()));
                    break;
                }
                case "tagNew": {
                    values.add(ExpCal.Analysis("tagNew=?", where.get(key).toString()));
                    break;
                }
                case "tagHot": {
                    values.add(ExpCal.Analysis("tagHot=?", where.get(key).toString()));
                    break;
                }
                case "brandId": {
                    values.add(ExpCal.Analysis("brandId=?", where.get(key).toString()));
                    break;
                }
                case "name": {
                    values.add(ExpCal.Analysis("name like?", where.get(key).toString()));
                    break;
                }
            }
        }
        whereBson.append("$and", values);
        return whereBson;
    }
}
