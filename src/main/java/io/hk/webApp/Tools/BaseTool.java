package io.hk.webApp.Tools;

import io.hk.webApp.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class BaseTool {

    @Autowired
    private SystemUtil systemUtil;

    /**
     * 供应商权限才能操作
     */
    public void isFactory(HttpServletRequest request){
        User user = systemUtil.getUser(request);
        if(null == user){
            throw new OtherExcetion(-2,"请重新登陆");
        }
        if(!BaseType.UserType.FACTORY.getCode().equals(user.getType())){
            throw new OtherExcetion("供应商才能操作");
        }
    }

    /**
     * 经销商权限才能操作
     */
    public void isSaler(HttpServletRequest request){
        User user = systemUtil.getUser(request);
        if(null == user){
            throw new OtherExcetion(-2,"请重新登陆");
        }
        if(!BaseType.UserType.SALER.getCode().equals(user.getType())){
            throw new OtherExcetion("经销商才能操作");
        }
    }
}
