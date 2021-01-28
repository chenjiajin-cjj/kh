package io.hk.webApp.Service;

import io.framecore.Frame.PageData;
import io.hk.webApp.Domain.FactoryScheme;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Tools.TablePagePars;
import io.hk.webApp.vo.FactorySchemeSubmissionVO;
import io.hk.webApp.vo.FactorySchemeUpdateMoneyVO;

public interface IFactorySchemeService {
    /**
     * 展示方案列表
     *
     * @param userId
     * @param pagePars
     * @return
     */
    PageData<FactoryScheme> search(String userId, TablePagePars pagePars);

    /**
     * 删除方案
     *
     * @param id
     * @return
     */
    boolean delete(String id, String userId);

    /**
     * 提报
     *
     * @param vo
     * @return
     */
    boolean submission(FactorySchemeSubmissionVO vo, User user);

    /**
     * 查询方案详情
     *
     * @param factorySchemeId
     * @return
     */
    Object getDetails(String factorySchemeId, TablePagePars pagePars);

    /**
     * 修改商品价格
     *
     * @param vo
     * @return
     */
    boolean updateMoney(FactorySchemeUpdateMoneyVO vo);
}
