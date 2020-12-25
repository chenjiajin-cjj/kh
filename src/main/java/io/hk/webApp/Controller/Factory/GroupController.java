package io.hk.webApp.Controller.Factory;

import io.framecore.Frame.Result;
import io.hk.webApp.Domain.Category;
import io.hk.webApp.Domain.Group;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Service.IGroupService;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.SystemUtil;
import io.hk.webApp.vo.GroupSortVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.Socket;
import java.util.List;

/**
 * 分组
 */
@RestController
@RequestMapping("api/v1/Factory/Group")
public class GroupController {

    @Autowired
    private IGroupService groupService;

    @Autowired(required = false)
    HttpServletRequest httpServletRequest;

    @Autowired
    private SystemUtil systemUtil;

    /**
     * 添加分组
     */
    @PostMapping("add")
    public Result addGroup(@RequestBody Group group) {
        User user = systemUtil.getUser(httpServletRequest);
        if (null != user) {
            group.setFactoryId(user.getId());
        }
        return groupService.add(group) ? Result.succeed("添加成功") : Result.failure("添加失败");
    }

    /**
     * 重命名分组
     *
     * @param group
     * @return
     */
    @PostMapping("rename")
    public Result rename(@RequestBody Group group) {
        User user = systemUtil.getUser(httpServletRequest);
        Group group1 = new Group().getById(group.getId());
        if(null == group1 || !user.getId().equals(group1.getFactoryId())){
            throw new OtherExcetion("只能修改自己发布分组");
        }
        return groupService.rename(group) ? Result.succeed("修改成功") : Result.failure("操作成功");
    }

    /**
     * 删除分组
     *
     * @param id
     * @return
     */
    @DeleteMapping("delete")
    public Result delete(String id) {
        User user = systemUtil.getUser(httpServletRequest);
        Group group1 = new Group().getById(id);
        if(null == group1 || !user.getId().equals(group1.getFactoryId())){
            throw new OtherExcetion("只能删除自己发布分组");
        }
        return groupService.delete(id) ? Result.succeed("删除成功") : Result.failure("删除失败");
    }

    /**
     * 分组排序
     *
     * @param vo
     * @return
     */
    @PostMapping("sort")
    public Result sort(@RequestBody GroupSortVO vo) {
        User user = systemUtil.getUser(httpServletRequest);
        return groupService.sort(vo) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 修改分组
     * @param group
     * @return
     */
    @PostMapping("update")
    public Result update(@RequestBody Group group) {
        User user = systemUtil.getUser(httpServletRequest);
        Group group1 = new Group().getById(group.getId());
        if(null == group1 || !user.getId().equals(group1.getFactoryId())){
            throw new OtherExcetion("只能修改自己发布分组");
        }
        return group.updateById() ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 展示分组及其下级的分类名
     */
    @GetMapping("search")
    public Result search(){
        User user = systemUtil.getUser(httpServletRequest);
        return Result.succeed(groupService.search(user.getId()));
    }

    /**
     * 隐藏分组
     */
    @PostMapping("showOrHide")
    public Result showOrHide(@RequestBody Group group){
        return groupService.showOrHide(group.getId()) ? Result.succeed("操作成功") : Result.failure("操作失败");
    }

    /**
     * 转移商品
     * @return
     */
    @PostMapping("transfer")
    public Result transfer(){
        return null;
    }


}
