package io.hk.webApp.Service;

import io.framecore.Frame.PageData;
import io.hk.webApp.Domain.Brand;
import io.hk.webApp.Tools.TablePagePars;

public interface IBrandService {
    /**
     * 添加品牌
     * @param brand
     * @return
     */
    boolean addBrand(Brand brand);

    /**
     * 根据id查询单个品牌
     * @param id
     * @return
     */
    Brand selectBrandById(String id);

    /**
     * 修改品牌
     * @param brand
     * @return
     */
    boolean modify(Brand brand);

    /**
     * 删除品牌
     * @param id
     * @return
     */
    boolean delete(String id);

    /**
     * 查询品牌列表
     * @param pagePars
     * @return
     */
    PageData<Brand> search(TablePagePars pagePars);
}
