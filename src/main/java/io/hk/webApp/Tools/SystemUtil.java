package io.hk.webApp.Tools;

import io.hk.webApp.DataAccess.AdminSet;
import io.hk.webApp.DataAccess.UserSet;
import io.hk.webApp.Domain.Admin;
import io.hk.webApp.Domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class SystemUtil {

    @Autowired
    private UserSet userSet;

    @Autowired
    private AdminSet adminSet;

    /**
     * 通过 loginKey 获取 User 对象
     * @param request
     * @return
     */
    public User getUser(HttpServletRequest request){
        String loginKey  = request.getHeader("loginKey");
        if(StringUtils.isEmpty(loginKey)){
            throw new OtherExcetion("请求有误");
        }
        User user = userSet.Where("loginKey=?",loginKey).First();
        return user;
    }

    /**
     * 通过 loginKey 获取 Admin 对象
     * @param request
     * @return
     */
    public Admin getAdmin(HttpServletRequest request){
        String loginKey  = request.getHeader("loginKey");
        if(StringUtils.isEmpty(loginKey)){
            throw new OtherExcetion("请求有误");
        }
        Admin admin = adminSet.Where("loginKey=?",loginKey).First();
        return admin;
    }

}
