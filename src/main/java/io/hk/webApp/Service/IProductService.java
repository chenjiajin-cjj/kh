package io.hk.webApp.Service;

import io.framecore.Frame.PageData;
import io.hk.webApp.Domain.Brand;
import io.hk.webApp.Domain.Category;
import io.hk.webApp.Domain.Product;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Tools.TablePagePars;
import io.hk.webApp.vo.ProductShareVO;

import java.util.List;
import java.util.Map;

public interface IProductService {
    /**
     * 添加商品
     * @param product
     * @return
     */
    boolean addProduct(Product product);

    /**
     * 列表查询
     * @param pagePars
     * @return
     */
    PageData<Product> search(TablePagePars pagePars,String factoryId);

    /**
     * 查询首页列表的分组
     * @return
     */
    List<Category> searchGroups(String factoryId);

    /**
     * 列表查询商品页的品牌
     * @param id
     * @return
     */
    List<Brand> searchBrand(String id);

    /**
     * 修改商品
     * @param product
     * @return
     */
    boolean update(Product product);
}
