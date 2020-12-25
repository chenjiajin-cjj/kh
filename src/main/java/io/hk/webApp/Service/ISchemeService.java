package io.hk.webApp.Service;

import io.framecore.Frame.PageData;
import io.hk.webApp.Domain.Scheme;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Tools.TablePagePars;
import io.hk.webApp.vo.*;

public interface ISchemeService {
    /**
     * 经销商添加方案
     * @param scheme
     * @return
     */
    boolean add(Scheme scheme);

    /**
     * 查询经销商自己的方案列表
     * @param salerId
     * @param pagePars
     * @return
     */
    PageData<Scheme> search(String salerId, TablePagePars pagePars);

    /**
     * 添加商品到方案
     * @param vo
     * @param id
     * @return
     */
    boolean addProduct(SchemeAddProductVO vo, String id);

    /**
     * 重命名方案
     * @param scheme
     * @param id
     * @return
     */
    boolean rename(Scheme scheme, String id);

    /**
     * 删除方案
     * @param id
     * @return
     */
    boolean delete(String id);

    /**
     * 查询邀请报价页面的合作厂商信息
     * @param id
     * @param pagePars
     * @return
     */
    Object searchFactorys(String id, TablePagePars pagePars,String schemeId);

    /**
     * 邀请厂商报价
     * @param vo
     * @return
     */
    boolean quotation(SchemeQuotationVO vo, User user);

    /**
     * 查询方案详情
     * @param schemeId
     * @return
     */
    Object getDetails(String schemeId);

    /**
     * 查询供应商分享过来的方案列表
     * @param id
     * @param pagePars
     * @return
     */
    Object searchFactorySchemes(String id, TablePagePars pagePars);

    /**
     * 查询供应商提报的方案详情
     * @param factorySchemeId
     * @return
     */
    Object getFactorySchemeDetails(String factorySchemeId);

    /**
     * 删除商品提报管理的方案
     * @param factorySchemeId
     * @return
     */
    boolean deleteFactoryScheme(String factorySchemeId);

    /**
     * 修改价格
     * @param vo
     * @return
     */
    boolean updateMoneyForAll(SalerUpdateMoneyVO vo);

    /**
     * 通知供应商来提报商品
     * @param vo
     * @return
     */
    boolean inform(SchemeInformVO vo, User user);
}
