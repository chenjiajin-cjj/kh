package io.hk.webApp.DataAccess;

import java.util.Hashtable;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import io.framecore.Mongodb.ExpCal;
import io.framecore.Mongodb.IMongoQuery;
import io.framecore.Mongodb.Query;
import io.hk.webApp.Domain.*;
import io.hk.webApp.Tools.BaseType;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.TablePagePars;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import io.framecore.Aop.Holder;
import io.framecore.Frame.PageData;
import io.framecore.Frame.Result;
import io.framecore.Mongodb.Set;

@Repository(value = "ProductSet")
@Scope(value = "prototype")
public class ProductSet extends Set<Product> {

    public ProductSet() {
        super(_db.getDbName());
    }

    @Override
    public Class<Product> getType() {
        return Product.class;
    }

    @Autowired
    private CategorySet categorySet;

    @Autowired
    private BrandSet brandSet;

    @Autowired
    private ClassifySet classifySet;

    @Autowired
    private UserSet userSet;

    /**
     * 新增商品
     *
     * @param pro
     * @return
     */
    public boolean addProduct(Product pro) {
        if (this.Where("(name=?)and(factoryId=?)", pro.getName(), pro.getFactoryId()).Exist()) {
            throw new OtherExcetion(("商品[" + pro.getName() + "]已经存在"));
        }
        this.Add(pro);
        return true;
    }

    /**
     * 列表查询
     *
     * @param where
     * @param limitFrom
     * @param limitLen
     * @param order
     * @return
     */
    public PageData<Product> search(Hashtable<String, Object> where, int limitFrom, int limitLen, String order, User factory) {
        BasicDBObject whereBson = buildWhere(where);
        IMongoQuery<Product> query = this.Where(whereBson);
        query.OrderByDesc("_ctime");
        PageData<Product> data = new PageData<>();
        data.rows = query.Limit(limitFrom, limitLen).ToList();
        for (Product a : data.rows) {
            Category category = categorySet.Get(a.getCategoryCode());
            String categoryName = "无";
            if (null != category && StringUtils.isNotEmpty(category.getName())) {
                categoryName = category.getName();
            }
            Brand brand = brandSet.Get(a.getBrandId());
            String brandName = "无";
            if (null != brand && StringUtils.isNotEmpty(brand.getName())) {
                brandName = brand.getName();
            }
            String classifyName = "无";
            Classify classify = classifySet.Get(a.getClassifyId());
            if (null != classify && StringUtils.isNotEmpty(classify.getName())) {
                classifyName = classify.getName();
            }
            if (StringUtils.isNotEmpty(a.getSalerId())) {
                User saler = userSet.Get(a.getSalerId());
                if (null != saler && StringUtils.isNotEmpty(saler.getCompanyName())) {
                    a.setCname(saler.getCompanyName());
                }
            } else {
                if (null == factory && StringUtils.isNotEmpty(a.getSalerId())) {
                    factory = userSet.Get(a.getSalerId());
                } else {
                    factory = userSet.Get(a.getFactoryId());
                }
                a.setCname(factory.getCompanyName());
            }
            a.setClassifyName(classifyName);
            a.setCategoryName(categoryName);
            a.setBrandName(brandName);
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
    private BasicDBObject buildWhere(Hashtable<String, Object> where) {
        BasicDBObject whereBson = new BasicDBObject();
        BasicDBList values = new BasicDBList();
        values.add(ExpCal.Analysis("illegal=?", BaseType.Status.YES.getCode()));
        if (null == where || where.size() == 0) {
            whereBson.append("$and", values);
            return whereBson;
        }
        for (String key : where.keySet()) {
            switch (key) {
                case "factoryId": {
                    values.add(ExpCal.Analysis("factoryId=?", where.get(key).toString()));
                    break;
                }
                case "name": {
                    values.add(ExpCal.Analysis("name like?", where.get(key).toString()));
                    break;
                }
                case "categoryId": {
                    values.add(ExpCal.Analysis("categoryCode=?", where.get(key).toString()));
                    break;
                }
                case "brandId": {
                    values.add(ExpCal.Analysis("brandId=?", where.get(key).toString()));
                    break;
                }
                case "tagHot": {
                    values.add(ExpCal.Analysis("tagHot=?", where.get(key).toString()));
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
                case "priceBegin1": {
                    values.add(ExpCal.Analysis("priceBegin1>=?", Double.parseDouble(where.get(key).toString())));
                    break;
                }
                case "priceBegin2": {
                    values.add(ExpCal.Analysis("priceBegin2<=?", Double.parseDouble(where.get(key).toString())));
                    break;
                }
                case "shield": {
                    values.add(ExpCal.Analysis("shield=?", where.get(key).toString()));
                    break;
                }
            }
        }
        whereBson.append("$and", values);
        return whereBson;
    }
}
