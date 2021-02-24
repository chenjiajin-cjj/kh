package io.hk.webApp.Service;

import io.framecore.Frame.PageData;
import io.hk.webApp.Domain.Group;
import io.hk.webApp.Domain.User;
import io.hk.webApp.vo.GroupSortVO;

public interface IGroupService {
    /**
     * 添加分组
     *
     * @param group
     * @return
     */
    boolean add(Group group);

    /**
     * 重命名分组
     *
     * @param group
     * @return
     */
    boolean rename(Group group);

    /**
     * 删除分组
     *
     * @param id
     * @return
     */
    boolean delete(String id);

    /**
     * 排序
     *
     * @param vo
     * @return
     */
    boolean sort(GroupSortVO vo);

    /**
     * 展示分组及其下级信息
     *
     * @return
     */
    PageData<Group> search(User user);

    /**
     * 隐藏或显示分组
     *
     * @param id
     * @return
     */
    boolean showOrHide(String id);
}
