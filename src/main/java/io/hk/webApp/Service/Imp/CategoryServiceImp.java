package io.hk.webApp.Service.Imp;

import io.framecore.Frame.PageData;
import io.hk.webApp.DataAccess.CategorySet;
import io.hk.webApp.DataAccess.GroupSet;
import io.hk.webApp.Domain.Category;
import io.hk.webApp.Domain.Group;
import io.hk.webApp.Service.ICategoryService;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.vo.CategorySortVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements ICategoryService {

    @Autowired
    private GroupSet groupSet;

    @Autowired
    private CategorySet categorySet;

    /**
     * 添加子分类
     *
     * @param category
     * @return
     */
    @Override
    public boolean add(Category category) {
        if(StringUtils.isEmpty(category.getName())){
            throw new OtherExcetion("请输入子分类名");
        }
        if(StringUtils.isEmpty(category.getFatherId())){
            throw new OtherExcetion("你想在哪个分组下添加分类？");
        }
        Group group = groupSet.Get(category.getFatherId());
        if(null == group){
            throw new OtherExcetion("不存在的分组");
        }
        List<Category> list = categorySet.Where("fatherId=?",category.getFatherId()).OrderByDesc("sort").ToList();
        category.setFactoryId(group.getFactoryId());
        if(0 == list.size()){
            category.setSort(0);
        }else{
            category.setSort(list.get(0).getSort()+1);
        }
        category.setNumber((long)0);
        categorySet.Add(category);
        return true;
    }

    /**
     * 重命名子分组
     * @param category
     * @return
     */
    @Override
    public boolean rename(Category category) {
        if(StringUtils.isAnyEmpty(category.getId(),category.getName())){
            throw new OtherExcetion("请完善必填项");
        }
        Category category1 = new Category();
        category1.setId(category.getId());
        category1.setName(category.getName());
        return category1.updateById();
    }

    /**
     * 子分类排序
     * @param vo
     * @return
     */
    @Override
    public boolean sort(CategorySortVO vo) {
        List<Category> list = vo.getList();
        if(list.size() == 0){
            return true;
        }
        String fatherId = new Category().getById(list.get(0).getId()).getFatherId();
        List<Category> list1 = categorySet.Where("fatherId=?", fatherId).ToList();
        if(1 == list1.size()){
            return true;
        }
        final int count [] = {0};
        list.forEach((a)->{
            Category category = new Category();
            category.setId(a.getId());
            category.setSort(a.getSort());
            boolean result = category.updateById();
            if(result){
                count[0] ++;
            }
        });
        if(count[0] == list.size()){
            return true;
        }
        return false;
    }

    /**
     * 显示或隐藏子分类
     * @param category
     * @return
     */
    @Override
    public boolean showOrHide(Category category) {
        String id = category.getId();
        if(StringUtils.isEmpty(id)){
            throw new OtherExcetion("请选择要隐藏的子分类");
        }
        category = category.getById(category.getId());
        String status = category.getStatus();
        category = new Category();
        category.setId(id);
        if("1".equals(status)){
            category.setStatus("2");
        }else{
            category.setStatus("1");
        }
        return category.updateById();
    }

    /**
     * 删除子分类
     * @param id
     * @return
     */
    @Override
    public boolean delete(String id) {
        if(StringUtils.isEmpty(id)){
            throw new OtherExcetion("请选择要删除的分类");
        }
        return categorySet.Delete(id) > 0;
    }

}
