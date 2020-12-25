package io.hk.webApp.Controller;

import io.framecore.Frame.Result;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Service.IMessageService;
import io.hk.webApp.Tools.SystemUtil;
import io.hk.webApp.Tools.TablePagePars;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/Message")
public class MessageController {

    @Autowired
    private IMessageService messageService;

    @Autowired(required = false)
    HttpServletRequest httpServletRequest;

    @Autowired
    private SystemUtil systemUtil;

    /**
     * 初始化信息列表
     * @param title
     * @return
     */
    @GetMapping("init")
    public Result init(String title){
        User user = systemUtil.getUser(httpServletRequest);
        TablePagePars pagePars = new TablePagePars(httpServletRequest);
        return Result.succeed(messageService.init(title,user.getId(),pagePars));
    }

    /**
     * 标记为已读
     * @param id
     * @return
     */
    @GetMapping("read")
    public Result read(String id){
        return messageService.read(id) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查询这个用户还有多少条未读
     */
    @GetMapping("check")
    public Result check(){
        User user = systemUtil.getUser(httpServletRequest);
        return Result.succeed(messageService.check(user.getId()));
    }
}
