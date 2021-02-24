package io.hk.webApp.Service.Imp;

import io.framecore.Frame.PageData;
import io.hk.webApp.DataAccess.FriendsSet;
import io.hk.webApp.DataAccess.MessagesSet;
import io.hk.webApp.Domain.Messages;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Service.IMessageService;
import io.hk.webApp.Tools.BaseType;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.TablePagePars;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 经销商、供应商消息
 */
@Service
public class MessageServiceImp implements IMessageService {

    @Autowired
    private MessagesSet messagesSet;

    @Autowired
    private FriendsSet friendsSet;

    /**
     * 初始化信息列表
     *
     * @param title    消息标签
     * @param pagePars 分页参数对象
     * @param userId   用户id
     * @return
     */
    @Override
    public PageData<Messages> init(String title, String userId, TablePagePars pagePars) {
        if (StringUtils.isEmpty(title)) {
            throw new OtherExcetion("请选择要查询的信息");
        }
        return messagesSet.init(title, userId, pagePars);
    }

    /**
     * 标记为已读
     *
     * @param id 消息id
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
     * @param id 消息id
     * @return
     */
    @Override
    public Long check(String id) {
        return messagesSet.Where("(receiveId=?)and(status=?)", id, "2").Count();
    }

    /**
     * 查询消息中心标签
     *
     * @param user 用户对象
     * @return
     */
    @Override
    public Object searchTag(User user) {
        List<Object> list = new ArrayList<>();
        BaseType.Message[] all = BaseType.Message.values();
        for (int i = 0; i < all.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", all[i].getMsg());
            map.put("title", all[i].getCode());
            long count = messagesSet.Where("(title=?)and(receiveId=?)and(status=?)", all[i].getCode(), user.getId(), BaseType.Status.NO.getCode()).Count();
            map.put("count", count);
            list.add(map);
        }
        return list;
    }

    /**
     * 查询头部未读消息数
     *
     * @param user 用户对象
     * @return
     */
    @Override
    public Object checkHeadMessage(User user) {
        Map<String, Object> map = new HashMap<>();
        long message = messagesSet.Where("(receiveId=?)and(status=?)", user.getId(), BaseType.Status.NO.getCode()).Count();
        long friend = friendsSet.Where("(friendId=?)and(status=?)", user.getId(), BaseType.Consent.BASE.getCode()).Count();
        map.put("message", message);
        map.put("friend", friend);
        if (BaseType.UserType.FACTORY.getCode().equals(user.getType())) {

        } else {

        }
        return map;
    }

    /**
     * 删除消息
     *
     * @param id 消息id
     * @return
     */
    @Override
    public boolean delete(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new OtherExcetion("请选择要删除的消息");
        }
        return messagesSet.Delete(id) > 0;
    }
}
