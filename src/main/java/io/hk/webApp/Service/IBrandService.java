package io.hk.webApp.Service;

import io.framecore.Frame.PageData;
import io.hk.webApp.Domain.Brand;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Tools.TablePagePars;

public interface IBrandService {
    /**
     * 添加品牌
     * @param brand
     * @return
     */
    boolean addBrand(Brand brand,User user);

    /**
     * 查询品牌列表
     * @param pagePars
     * @return
     */
    PageData<Brand> search(TablePagePars pagePars, User user);

    /**
     * 修改品牌
     * @param brand
     * @return
     */
    boolean update(Brand brand);
}
