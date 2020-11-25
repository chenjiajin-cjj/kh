package io.hk.webApp.Service.impl;

import io.framecore.Frame.PageData;
import io.hk.webApp.DataAccess.BrandSet;
import io.hk.webApp.Domain.Brand;
import io.hk.webApp.Service.IBrandService;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.TablePagePars;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImp implements IBrandService {

    @Autowired
    private BrandSet brandSet;

    /**
     * 添加品牌
     * @param brand
     * @return
     */
    @Override
    public boolean addBrand(Brand brand) {

        if(StringUtils.isAnyEmpty(brand.getName(),
                brand.getDetails())){
            throw new OtherExcetion("请完善必填项");
        }

        if(null == brand.getTime()){
            brand.setTime(System.currentTimeMillis());
        }
        brand.setStatus("2");
        return brandSet.addBrand(brand);
    }

    /**
     * 根据id查询单个品牌
     * @param id
     * @return
     */
    @Override
    public Brand selectBrandById(String id) {
        if(StringUtils.isEmpty(id)){
            throw new OtherExcetion("请选择要查询的品牌");
        }
        return brandSet.Get(id);
    }

    /**
     * 修改品牌
     * @param brand
     * @return
     */
    @Override
    public boolean modify(Brand brand) {
        if(StringUtils.isEmpty(brand.getId())){
            throw new OtherExcetion("请选择要修改的品牌");
        }
        return brandSet.Update(brand.getId(),brand) > 0;
    }

    /**
     * 删除品牌
     * @param id
     * @return
     */
    @Override
    public boolean delete(String id) {
        if(StringUtils.isEmpty(id)){
            throw new OtherExcetion("请选择要删除的品牌");
        }
        return brandSet.Delete(id) > 0;
    }

    /**
     * 查询品牌列表
     * @param pagePars
     * @return
     */
    @Override
    public PageData<Brand> search(TablePagePars pagePars) {
        return brandSet.search(pagePars.Pars,pagePars.PageSize,pagePars.PageIndex,pagePars.Order);
    }
}
