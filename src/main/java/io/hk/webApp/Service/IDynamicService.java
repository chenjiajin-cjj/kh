package io.hk.webApp.Service;

import io.framecore.Frame.PageData;
import io.hk.webApp.Domain.Dynamic;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Tools.TablePagePars;
import io.hk.webApp.dto.DynamicDTO;

public interface IDynamicService {
    /**
     * 添加厂商动态
      * @param dynamic
     * @return
     */
    boolean add(Dynamic dynamic);

    /**
     * 查询供应商自己的动态集合
     * @param pagePars
     * @return
     */
    PageData<DynamicDTO> search(TablePagePars pagePars, User user);

    /**
     * 查询所有动态活着合作未合作的动态
     */
    PageData<DynamicDTO> search(TablePagePars pagePars, String type, String salerId);

    /**
     * 获取厂商信息
     * @param factoryId
     * @return
     */
    Object getUserMsg(String factoryId);
}
