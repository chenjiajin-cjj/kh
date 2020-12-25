package io.hk.webApp.Service;

import io.framecore.Frame.PageData;
import io.hk.webApp.Domain.Brand;
import io.hk.webApp.Domain.Product;
import io.hk.webApp.Domain.SaleGoods;
import io.hk.webApp.Tools.TablePagePars;
import io.hk.webApp.vo.SaleGoodsAddVO;
import io.hk.webApp.vo.SaleGoodsOnlineOrNoVO;
import io.hk.webApp.vo.SaleGoodsUpdateProductVO;
import io.hk.webApp.vo.ShareProductAddVO;

import java.util.List;
import java.util.Set;

public interface ISaleGoodsService {
    /**
     * 经销商获得由供应商分享过来的商品
     * @param vo
     * @param id
     * @return
     */
    boolean add(ShareProductAddVO vo, String id);

    /**
     * 经销商添加自有商品
     * @param product
     * @return
     */
    boolean addProduct(Product product);

    /**
     *  查询商品列表
     * @param pagePars
     * @param salerId
     * @return
     */
    PageData<SaleGoods> search(TablePagePars pagePars, String salerId,String type);

    /**
     * 对自有商品进行上下架
     * @param vo
     * @param id
     * @return
     */
    boolean onlineOrNo(SaleGoodsOnlineOrNoVO vo, String id);

    /**
     * 修改自有商品
     * @param vo
     * @param id
     * @return
     */
    boolean update(SaleGoodsUpdateProductVO vo, String id);

    /**
     * 删除商品
     * @param id
     * @param salerId
     * @return
     */
    boolean delete(String id, String salerId);

    /**
     * 根据id查询单个
     * @param id
     * @return
     */
    SaleGoods getById(String id);

    /**
     * 设为主推
     * @param id
     * @return
     */
    boolean recommend(String id);

    /**
     *
     * @param salerId
     * @return
     */
    Set<Brand> searchBrandForFactory(String salerId);

    /**
     * 查询合作厂商们的所有品牌
     * @param id
     * @return
     */
    List<Brand> getBrandForFriends(String id);

    /**
     * 查询分享的商品列表
     * @param id
     * @return
     */
    Object searchShare(TablePagePars pagePars,String id);

    /**
     * 查询分享过来的商品的详情
     * @param shareProductId
     * @return
     */
    Object searchShareDetails(String shareProductId);
}
