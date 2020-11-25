package io.hk.webApp.Controller.Factory;

import io.hk.webApp.Service.IGroupService;
import io.hk.webApp.Tools.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/Factory/groups")
public class GroupsController {

    @Autowired
    private IGroupService groupsService;
    @Autowired(required = false)
    HttpServletRequest httpServletRequest;

    @Autowired
    private SystemUtil systemUtil;




}
