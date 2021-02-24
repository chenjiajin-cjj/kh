package io.hk.webApp.Service;

import io.framecore.Frame.PageData;
import io.hk.webApp.Domain.*;
import io.hk.webApp.Tools.TablePagePars;
import io.hk.webApp.vo.UpdateProductTagVO;

import java.util.List;

public interface IProductService {
    /**
     * 添加商品
     *
     * @param product
     * @return
     */
    boolean addProduct(Product product);

    /**
     * 列表查询
     *
     * @param pagePars
     * @return
     */
    PageData<Product> search(TablePagePars pagePars, User user);

    /**
     * 查询首页列表的分组
     *
     * @return
     */
    Object searchGroups(String factoryId);

    /**
     * 列表查询商品页的品牌
     *
     * @param id
     * @return
     */
    List<Brand> searchBrand(String id);

    /**
     * 修改商品
     *
     * @param product
     * @return
     */
    boolean update(Product product, User user);

    /**
     * 屏蔽/取消屏蔽商品
     *
     * @param productId
     * @return
     */
    boolean shield(String productId);

    /**
     * 根据id查询单个商品
     *
     * @param id
     * @return
     */
    Object getOne(String id, User user);

    /**
     * 删除商品
     *
     * @param id
     * @return
     */
    boolean delete(String id);

    /**
     * 查询后台商品
     *
     * @param pagePars
     * @return
     */
    Object searchBackGroupProducts(TablePagePars pagePars);

    /**
     * 违规上下架
     *
     * @param product
     * @return
     */
    boolean illegal(Product product, Admin admin, String ip);

    boolean update(Product product, Admin admin, String remoteAddr);

    /**
     * 修改后台商品上下架
     *
     * @param product
     * @param admin
     * @param remoteAddr
     * @return
     */
    boolean updateProductOnline(Product product, Admin admin, String remoteAddr);

    /**
     * 修改商品标签
     * @param vo
     * @param admin
     * @param remoteAddr
     * @return
     */
    boolean updateProductTag(UpdateProductTagVO vo, Admin admin, String remoteAddr);
}
