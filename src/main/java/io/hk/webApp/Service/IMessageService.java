package io.hk.webApp.Service;

import io.framecore.Frame.PageData;
import io.hk.webApp.Domain.Messages;
import io.hk.webApp.Domain.User;
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

    /**
     * 查询消息中心标签
     * @param user
     * @return
     */
    Object searchTag(User user);

    /**
     * 查询头部未读消息数
     * @param user
     * @return
     */
    Object checkHeadMessage(User user);

    /**
     * 删除消息
     * @param id
     * @return
     */
    boolean delete(String id);
}
