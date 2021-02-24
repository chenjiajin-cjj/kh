package io.hk.webApp.Service.Imp;

import io.framecore.Frame.PageData;
import io.hk.webApp.DataAccess.BrandSet;
import io.hk.webApp.Domain.Brand;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Service.IBrandService;
import io.hk.webApp.Tools.BaseType;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.TablePagePars;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 品牌
 */
@Service
public class BrandServiceImp implements IBrandService {

    @Autowired
    private BrandSet brandSet;

    /**
     * 添加品牌
     *
     * @param brand 品牌对象
     * @param user  user对象
     * @return
     */
    @Override
    public boolean addBrand(Brand brand, User user) {
        brand.setDetails("无");
        brand.setPerpetual(BaseType.Status.YES.getCode());
        if (StringUtils.isAnyEmpty(brand.getName(), brand.getDetails(), brand.getPerpetual())) {
            throw new OtherExcetion("请完善必填项");
        }
        if (null == brand.getTime()) {
            brand.setTime(System.currentTimeMillis());
        }
        brand.setStatus(BaseType.Consent.BASE.getCode());
        brand.setUserName(user.getCompanyName());
        return brandSet.addBrand(brand);
    }

    /**
     * 查询品牌列表
     *
     * @param pagePars  分页参数对象
     * @return
     */
    @Override
    public PageData<Brand> search(TablePagePars pagePars, User user) {
        return brandSet.search(pagePars.Pars, pagePars.PageSize, pagePars.PageIndex, pagePars.Order, user);
    }

    @Override
    public boolean update(Brand brand) {
        return brandSet.Update(brand.getId(), brand) > 0;
    }
}
