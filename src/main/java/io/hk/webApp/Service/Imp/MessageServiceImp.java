package io.hk.webApp.Service.Imp;

import io.framecore.Frame.PageData;
import io.hk.webApp.DataAccess.MessagesSet;
import io.hk.webApp.Domain.Messages;
import io.hk.webApp.Service.IMessageService;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.TablePagePars;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImp implements IMessageService {

    @Autowired
    private MessagesSet messagesSet;

    /**
     * 初始化信息列表
     *
     * @param title
     * @return
     */
    @Override
    public PageData<Messages> init(String title, String userId, TablePagePars pagePars) {
        if (StringUtils.isEmpty(title)) {
            throw new OtherExcetion("请选择要查询的信息");
        }
        return messagesSet.init(title, userId,pagePars);
    }

    /**
     * 标记为已读
     *
     * @param id
     * @return
     */
    @Override
    public boolean read(String id) {
        Messages messages = new Messages();
        messages.setId(id);
        messages.setStatus("1");
        return messagesSet.Update(messages.getId(), messages) > 0;

    }

    /**
     * 查询用户有多少条未读记录
     *
     * @param id
     * @return
     */
    @Override
    public Long check(String id) {
        return messagesSet.Where("(userId=?)and(status=?)", id, "2").Count();
    }
}
