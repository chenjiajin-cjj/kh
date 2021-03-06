package io.hk.webApp.Service.Imp;

import io.framecore.Frame.PageData;
import io.hk.webApp.DataAccess.ClassifySet;
import io.hk.webApp.DataAccess.OperationLogSet;
import io.hk.webApp.DataAccess.ProductSet;
import io.hk.webApp.Domain.Admin;
import io.hk.webApp.Domain.Classify;
import io.hk.webApp.Domain.OperationLog;
import io.hk.webApp.Service.IClassifyService;
import io.hk.webApp.Tools.BaseType;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.TablePagePars;
import io.hk.webApp.Tools.iparea.IPSeekers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品分类
 */
@Service
public class ClassifyServiceImp implements IClassifyService {


    @Autowired
    private ClassifySet classifySet;

    @Autowired
    private OperationLogSet operationLogSet;

    @Autowired
    private ProductSet productSet;

    /**
     * 添加分类
     *
     * @param classify 分类对象
     * @param ip       ip
     * @param admin    管理员对象
     * @return
     */
    @Override
    public Boolean addClassify(Classify classify, Admin admin, String ip) {
        if (StringUtils.isEmpty(classify.getName())) {
            throw new OtherExcetion("请输入分类名");
        }
        if (StringUtils.isEmpty(classify.getFatherId())) {
            classify.setLv("1");
        } else {
            classify.setLv("2");
        }
        if (StringUtils.isEmpty(classify.getStatus())) {
            classify.setStatus(BaseType.Status.YES.getCode());
        }
        addOperationLog(admin, ip, "新增分类");
        return classifySet.add(classify);
    }

    /**
     * 修改分类
     *
     * @param classify 分类对象
     * @param ip       ip
     * @param admin    管理员对象
     * @return
     */
    @Override
    public Boolean updateClassify(Classify classify, Admin admin, String ip) {
        if (StringUtils.isEmpty(classify.getId())) {
            throw new OtherExcetion("请选择要修改的分类");
        }
        addOperationLog(admin, ip, "修改分类");
        return classify.updateById();
    }

    /**
     * 删除分类
     *
     * @param id    分类id
     * @param ip    ip
     * @param admin 管理员对象
     * @return
     */
    @Override
    public Boolean deleteClassify(Admin admin, String ip, String id) {
        if (StringUtils.isEmpty(id)) {
            throw new OtherExcetion("请选择要删除的分类");
        }
        addOperationLog(admin, ip, "删除分类");
        return classifySet.Delete(id) > 0;
    }

    /**
     * 分页查询分类列表
     *
     * @param pagePars 分页参数对象
     * @return
     */
    @Override
    public Object searchClassify(TablePagePars pagePars) {
        PageData<Classify> pageData = new PageData<>();
        List<Classify> list = classifySet.Limit(pagePars.PageSize, pagePars.PageIndex).OrderByDesc("_ctime").ToList();
        long count = classifySet.Count();
        pageData.rows = list;
        pageData.total = count;
        return pageData;
    }

    /**
     * 查询分类名
     *
     * @return
     */
    @Override
    public Object searchClassifyName() {
        List<Classify> fathers = classifySet.Where("(status=?)and(lv=?)", BaseType.Status.YES.getCode(), "1").OrderBy("sort").ToList();
        fathers.forEach(a -> {
            List<Classify> sons = classifySet.Where("(status=?)and(lv=?)and(fatherId=?)", BaseType.Status.YES.getCode(), "2", a.getId()).OrderBy("sort").ToList();
            a.setSons(sons);
        });
        return fathers;
    }

    /**
     * 查询后台分类
     *
     * @param pagePars 分页参数对象
     * @return
     */
    @Override
    public Object searchClassifyOne(TablePagePars pagePars, String type, String fatherId) {
        if (StringUtils.isEmpty(type)) {
            type = "1";
        }
        PageData<Classify> pageData = new PageData<>();
        List<Classify> list;
        long count;
        if ("1".equals(type)) {
            list = classifySet.Where("lv=?", type).OrderByDesc("sort").ToList();
            count = classifySet.Where("lv=?", type).Count();
        } else {
            if (StringUtils.isEmpty(fatherId)) {
                throw new OtherExcetion("请选择上级");
            }
            list = classifySet.Where("(lv=?)and(fatherId=?)", type, fatherId).OrderByDesc("sort").ToList();
            count = classifySet.Where("(lv=?)and(fatherId=?)", type, fatherId).Count();
        }
        list.forEach((a) -> {
            //查询商品数量
            long number = productSet.Where("classifyId like?", a.getId()).Count();
            a.setProductNumber(number);
        });
        pageData.rows = list;
        pageData.total = count;
        return pageData;
    }

    /**
     * 添加操作记录
     *
     * @param admin   管理员对象
     * @param ip      ip
     * @param content 操作内容
     */
    public void addOperationLog(Admin admin, String ip, String content) {
        OperationLog operationLog = new OperationLog();
        operationLog.setAccount(admin.getAccount());
        operationLog.setIp(ip);
        String addr = IPSeekers.getInstance().getAddress(ip);
        operationLog.setAddr(StringUtils.isEmpty(addr) ? "未知" : addr);
        operationLog.setName(admin.getName());
        operationLog.setCtime(System.currentTimeMillis());
        operationLog.setContent(content);
        operationLogSet.Add(operationLog);
    }
}
