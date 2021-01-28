package io.hk.webApp.DataAccess;

import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.Category;
import io.hk.webApp.Domain.Classify;
import io.hk.webApp.Tools.BaseType;
import io.hk.webApp.Tools.OtherExcetion;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


@Repository(value = "ClassifySet")
@Scope(value = "prototype")
public class ClassifySet extends Set<Classify> {

    public ClassifySet() {
        super(_db.getDbName());
    }

    @Override
    public Class<Classify> getType() {
        return Classify.class;
    }

    /**
     * 添加分类
     *
     * @param classify
     * @return
     */
    public Boolean add(Classify classify) {
//        Classify info = this.Where("name=?", classify.getName()).First();
//        if (null != info) {
//            throw new OtherExcetion("商品[" + classify.getName() + "]已存在");
//        }
        Classify info;
        if("1".equals(classify.getLv())){
            info = this.Where("lv=?",classify.getLv()).OrderByDesc("sort").First();
        }else{
            info = this.Where("(lv=?)and(fatherId=?)",classify.getLv(),classify.getFatherId()).OrderByDesc("sort").First();
        }
        if(null != info && null != info.getSort() && info.getSort() >= 0){
            classify.setSort(info.getSort() + 1);
        }else{
            classify.setSort(0);
        }
        return null != this.Add(classify);
    }
}
