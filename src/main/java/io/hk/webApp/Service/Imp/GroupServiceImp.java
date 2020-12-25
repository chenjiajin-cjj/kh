package io.hk.webApp.Service.Imp;

import io.framecore.Frame.PageData;
import io.hk.webApp.DataAccess.CategorySet;
import io.hk.webApp.DataAccess.GroupSet;
import io.hk.webApp.Domain.Category;
import io.hk.webApp.Domain.Group;
import io.hk.webApp.Service.IGroupService;
import io.hk.webApp.Tools.BaseType;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.vo.GroupSortVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.MediaSize;
import java.util.List;

@Service
public class GroupServiceImp implements IGroupService {

    @Autowired
    private GroupSet groupSet;

    @Autowired
    private CategorySet categorySet;

    /**
     * 添加分组
     *
     * @param group
     * @return
     */
    @Override
    public boolean add(Group group) {
        long count = groupSet.Where("factoryId=?", group.getFactoryId()).Count();
        if (2 <= count) {
            throw new OtherExcetion("已超过创立分组的最大限制");
        }
        if (StringUtils.isAnyEmpty(group.getName())) {
            throw new OtherExcetion("请输入分组名");
        }
        if (0 == count) {
            group.setSort(0);
        } else {
            group.setSort(1);
        }
        groupSet.Add(group);
        return true;
    }

    /**
     * 重命名分组
     *
     * @param group
     * @return
     */
    @Override
    public boolean rename(Group group) {
        if (StringUtils.isEmpty(group.getId())) {
            throw new OtherExcetion("请选择要重命名的分组");
        }
        if (StringUtils.isEmpty(group.getName())) {
            throw new OtherExcetion("请输入新名");
        }
        Group group1 = new Group();
        group1.setId(group.getId());
        group1.setName(group.getName());
        return group1.updateById();
    }

    /**
     * 删除分组
     *
     * @param id
     * @return
     */
    @Override
    public boolean delete(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new OtherExcetion("请选择要删除的分组");
        }
        Group group = new Group();
        boolean result = group.deleteById(id);
        if (result) {
            group = groupSet.First();
            if (null != group) {
                //如果只剩下一个分组则把这个分组设置为第一个
                group.setSort(0);
                return group.updateById();
            }
        }
        return result;
    }

    /**
     * 排序
     *
     * @param vo
     * @return
     */
    @Override
    public boolean sort(GroupSortVO vo) {
        List<Group> list = vo.getList();
        if (list.size() == 0) {
            return true;
        }
        List<Group> list1 = groupSet.Where("factory=?", list.get(0).getFactoryId()).ToList();
        if (1 == list1.size()) {
            return true;
        }
        final int[] count = {0};
        list.forEach((a) -> {
            Group group = new Group();
            group.setId(a.getId());
            group.setSort(a.getSort());
            boolean result = group.updateById();
            if (result) {
                count[0]++;
            }
        });
        if (count[0] == list.size()) {
            return true;
        }
        return false;
    }

    /**
     * 展示分组及其下级信息
     *
     * @return
     */
    @Override
    public PageData<Group> search(String factoryId) {
        PageData<Group> pageData = new PageData<>();
        List<Group> list = groupSet.Where("factoryId=?", factoryId).OrderBy("sort").ToList();
        long count = groupSet.Where("factoryId=?", factoryId).Count();
        list.forEach((a) -> {
            List<Category> sonList = categorySet.Where("fatherId=?", a.getId()).OrderBy("sort").ToList();
            a.setSonList(sonList);
        });
        pageData.rows = list;
        pageData.total = count;
        return pageData;
    }

    /**
     * 隐藏或显示分组
     *
     * @param id
     * @return
     */
    @Override
    public boolean showOrHide(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new OtherExcetion("请选择要操作的分组");
        }
        Group group = groupSet.Get(id);
        if (null == group) {
            throw new OtherExcetion("不存在的分组");
        }
        group.setStatus(BaseType.Status.YES.getCode().equals(group.getStatus()) ? BaseType.Status.NO.getCode() : BaseType.Status.YES.getCode());
        return group.updateById();
    }
}
