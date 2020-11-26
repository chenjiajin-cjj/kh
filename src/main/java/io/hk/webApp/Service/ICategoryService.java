package io.hk.webApp.Service;

import io.hk.webApp.Domain.Category;
import io.hk.webApp.vo.CategorySortVO;

public interface ICategoryService {
    /**
     * 添加子分类
     * @param category
     * @return
     */
    boolean add(Category category);

    /**
     * 重命名子分组
     * @param category
     * @return
     */
    boolean rename(Category category);

    /**
     * 子分类排序
     * @param vo
     * @return
     */
    boolean sort(CategorySortVO vo);

    /**
     * 显示或隐藏子分类
     * @param category
     * @return
     */
    boolean showOrHide(Category category);
}
