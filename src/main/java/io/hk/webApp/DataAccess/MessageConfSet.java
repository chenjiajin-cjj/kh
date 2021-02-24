package io.hk.webApp.DataAccess;

import io.framecore.Aop.Holder;
import io.framecore.Mongodb.Set;
import io.hk.webApp.Domain.MessageConf;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository(value = "MessageConfSet")
@Scope(value = "prototype")
public class MessageConfSet extends Set<MessageConf> {

    public MessageConfSet() {
        super(_db.getDbName());
    }

    @Override
    public Class<MessageConf> getType() {
        return MessageConf.class;
    }

    public static void main(String[] args) {
        MessageConfSet messageConfSet = Holder.getBean(MessageConfSet.class);
        List<String> list = Arrays.asList("[user]","[data]","[from]");
        messageConfSet.ToList().forEach(a->{
            a.setInstationContextTag(list);
            a.setSmallContextTag(list);
            messageConfSet.Update(a.getId(),a);
        });
    }
}

class TestAddMessageConf {
    public static void main(String[] args) {
        MessageConfSet messageConfSet = Holder.getBean(MessageConfSet.class);
        List<MessageConf> list = new ArrayList<>();
        MessageConf m = new MessageConf();
        m.setName("注册成功时");
        m.setType("系统消息");
        m.setInstation("1");
        list.add(m);
        m = new MessageConf();
        m.setName("修改密码");
        m.setType("系统消息");
        m.setInstation("1");
        list.add(m);
        m = new MessageConf();
        m.setName("账号认证审核通过");
        m.setType("系统消息");
        m.setInstation("1");
        list.add(m);
        m = new MessageConf();
        m.setName("账号认证审核失败");
        m.setType("系统消息");
        m.setInstation("1");
        list.add(m);
        m = new MessageConf();
        m.setName("经销商/供应商申请合作");
        m.setType("新的朋友");
        m.setInstation("1");
        list.add(m);
        m = new MessageConf();
        m.setName("供应商/经销商合作申请审核通过");
        m.setType("新的朋友");
        m.setInstation("1");
        list.add(m);
        m = new MessageConf();
        m.setName("供应商/经销商合作申请审核失败");
        m.setType("新的朋友");
        m.setInstation("1");
        list.add(m);
        m = new MessageConf();
        m.setName("供应商商品品牌审核通过");
        m.setType("系统消息");
        m.setInstation("1");
        list.add(m);
        m = new MessageConf();
        m.setName("供应商商品品牌审核失败");
        m.setType("系统消息");
        m.setInstation("1");
        list.add(m);
        m = new MessageConf();
        m.setName("供应商商品违规下架");
        m.setType("系统消息");
        m.setInstation("1");
        list.add(m);
        m = new MessageConf();
        m.setName("供应商发送新的商品报价给经销商");
        m.setType("系统消息");
        m.setInstation("1");
        list.add(m);
        m = new MessageConf();
        m.setName("经销商邀请供应商提报商品");
        m.setType("系统消息");
        m.setInstation("1");
        list.add(m);
        m = new MessageConf();
        m.setName("供应商提交商品到方案");
        m.setType("系统消息");
        m.setInstation("1");
        list.add(m);
        m = new MessageConf();
        m.setName("经销商通知供应商报备方案商品");
        m.setType("系统消息");
        m.setInstation("1");
        list.add(m);
        list.forEach(messageConfSet::Add);
    }
}