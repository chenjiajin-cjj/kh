package io.hk.webApp.Service;

import io.framecore.Frame.PageData;
import io.hk.webApp.Domain.Messages;
import io.hk.webApp.Tools.TablePagePars;

public interface IMessageService {
    /**
     * 初始化信息列表
     *
     * @param title
     * @return
     */
    PageData<Messages> init(String title, String userId, TablePagePars pagePars);

    /**
     * 标记为已读
     * @param id
     * @return
     */
    boolean read(String id);

    /**
     * 查询当前用户有多少条未读记录
     * @param id
     * @return
     */
    Long check(String id);
}
