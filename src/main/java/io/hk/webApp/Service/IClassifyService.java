package io.hk.webApp.Service;

import io.hk.webApp.Domain.Admin;
import io.hk.webApp.Domain.Classify;
import io.hk.webApp.Tools.TablePagePars;

public interface IClassifyService {

    /**
     * 创建分类
     */
    Boolean addClassify(Classify classify, Admin admin, String ip);

    /**
     * 修改分类
     */
    Boolean updateClassify(Classify classify, Admin admin, String ip);

    /**
     * 删除分类
     */
    Boolean deleteClassify(Admin admin, String ip, String id);

    /**
     * 后台展示分类列表（分页）
     */
    Object searchClassify(TablePagePars pagePars);

    /**
     * 前台展示所有分类（不分页）
     */
    Object searchClassifyName();

    /**
     * 查询分类
     *
     * @param pagePars
     * @return
     */
    Object searchClassifyOne(TablePagePars pagePars, String type, String fatherId);
}
