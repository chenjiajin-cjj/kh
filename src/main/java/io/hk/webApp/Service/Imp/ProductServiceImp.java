package io.hk.webApp.Service.Imp;

import io.framecore.Frame.PageData;
import io.hk.webApp.DataAccess.*;
import io.hk.webApp.Domain.*;
import io.hk.webApp.Service.IProductService;
import io.hk.webApp.Tools.*;
import io.hk.webApp.Tools.iparea.IPSeekers;
import io.hk.webApp.vo.UpdateProductTagVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 供应商商品
 */
@Service
public class ProductServiceImp implements IProductService {

    @Autowired
    private ProductSet productSet;

    @Autowired
    private GroupSet groupSet;

    @Autowired
    private CategorySet categorySet;

    @Autowired
    private BrandSet brandSet;

    @Autowired
    private SaleGoodsSet saleGoodsSet;

    @Autowired
    private ClassifySet classifySet;

    @Autowired
    private OperationLogSet operationLogSet;

    /**
     * 添加商品
     *
     * @param product 供应商商品对象
     * @return
     */
    @Override
    public boolean addProduct(Product product) {
        if (StringUtils.isAnyEmpty(product.get("name").toString(),
                product.getBrandId(),
                product.getModel(),
                product.getBaseDetail(),
                product.getSellingPoint(),
                product.getDetail(),
                product.getBrandId(),
                product.getCategoryCode(),
                product.getClassifyId())) {
            throw new OtherExcetion("请完善必填项");
        }

        if (null == product.getSupplyPriceTax() ||
                null == product.getSupplyPriceTaxNo() ||
                null == product.getRetailPriceTax() ||
                null == product.getRetailPriceTaxNo() ||
                NumberUtils.isDoubleMinus(product.getSupplyPriceTaxNo(), product.getSupplyPriceTax(), product.getRetailPriceTax(), product.getRetailPriceTaxNo())) {
            throw new OtherExcetion("价格有误");
        }
        if (null != product.getPriceBegin1() && product.getPriceBegin1() > product.getRetailPriceTax()) {
            throw new OtherExcetion("预设价格1不含税不能比零售价不含税高");
        }
        if (null != product.getPriceEnd1() && product.getPriceEnd1() > product.getRetailPriceTaxNo()) {
            throw new OtherExcetion("预设价格1含税不能比零售价含税高");
        }

        if (null != product.getPriceBegin2() && product.getPriceBegin2() > product.getRetailPriceTax()) {
            throw new OtherExcetion("预设价格2不含税不能比零售价不含税高");
        }
        if (null != product.getPriceEnd2() && product.getPriceEnd2() > product.getRetailPriceTaxNo()) {
            throw new OtherExcetion("预设价格2含税不能比零售价含税高");
        }

        if (NumberUtils.isDoubleMinus(product.getTax())) {
            throw new OtherExcetion("请输入税率");
        }
        product.setIllegal(BaseType.Status.YES.getCode());
        product.setStatus(BaseType.Status.YES.getCode());
        product.setCreateTime(System.currentTimeMillis());
        product.setShield(BaseType.Status.YES.getCode());
        product.setTagHot(StringUtils.isEmpty(product.getTagHot()) ? "2" : product.getTagHot());
        product.setTagRec(StringUtils.isEmpty(product.getTagRec()) ? "2" : product.getTagRec());
        product.setTagNew(StringUtils.isEmpty(product.getTagNew()) ? "2" : product.getTagNew());
        return productSet.addProduct(product);
    }

    /**
     * 列表查询
     *
     * @param pagePars 分页参数对象
     * @param factory  经销商用户对象
     * @return
     */
    @Override
    public PageData<Product> search(TablePagePars pagePars, User factory) {
        pagePars.Pars.put("shield", BaseType.Status.YES.getCode());
        if (null != factory && StringUtils.isNotEmpty(factory.getId())) {
            pagePars.Pars.put("factoryId", factory.getId());
        }
        PageData<Product> pageData = productSet.search(pagePars.Pars, pagePars.PageSize, pagePars.PageIndex, pagePars.Order, factory);
        return pageData;
    }

    /**
     * 查询首页列表的分组
     *
     * @param factoryId 供应商id
     * @return
     */
    @Override
    public Object searchGroups(String factoryId) {
        List<Group> groupList = groupSet.Where("(factoryId=?)and(status=?)", factoryId, BaseType.Status.YES.getCode()).OrderBy("sort").ToList();
        List<Object> list = new ArrayList<>();
        groupList.forEach((a) -> {
            Map<String, Object> map = new HashMap<>();
            List<Category> categories = categorySet.Where("(fatherId=?)and(status=?)", a.getId(), BaseType.Status.YES.getCode()).OrderBy("sort").ToList();
            map.put("groupName", a.getName());
            map.put("categorys", categories);
            list.add(map);
        });
        return list;
    }

    /**
     * 列表查询商品页的品牌
     *
     * @param id 品牌id
     * @return
     */
    @Override
    public List<Brand> searchBrand(String id) {
        return brandSet.Where("(userId=?)and(status=?)", id, BaseType.Status.YES.getCode()).OrderByDesc("time").ToList();
    }

    /**
     * 修改商品
     *
     * @param product 供应商商品对象
     * @param user    用户对象
     * @return
     */
    @Override
    public boolean update(Product product, User user) {
        boolean result;
        try {
            result = product.updateProduct();
        } catch (Exception e) {
            throw new OtherExcetion("商品:[" + product.getName() + "]已存在");
        }
        List<SaleGoods> saleGoods = saleGoodsSet.Where("(factoryId=?)and(productId=?)", user.getId(), product.getId()).ToList();
        saleGoods.forEach((a) -> {
            boolean res = false;
            //以下做冗余经销商商品检索条件用
            if (StringUtils.isNotEmpty(product.getTagRec())) {
                a.setTagRec(product.getTagRec());
                res = true;
            }
            if (StringUtils.isNotEmpty(product.getTagNew())) {
                a.setTagNew(product.getTagNew());
                res = true;
            }
            if (StringUtils.isNotEmpty(product.getTagHot())) {
                a.setTagHot(product.getTagHot());
                res = true;
            }
            if (StringUtils.isNotEmpty(product.getProductGroup())) {
                a.setProductGroup(product.getProductGroup());
                res = true;
            }
            if (StringUtils.isNotEmpty(product.getBrandId())) {
                a.setBrandId(product.getBrandId());
                res = true;
            }
            if (StringUtils.isNotEmpty(product.getCategoryCode())) {
                a.setCategoryCode(product.getCategoryCode());
                res = true;
            }
            if (StringUtils.isNotEmpty(product.getName())) {
                a.setName(product.getName());
                res = true;
            }
            if (StringUtils.isNotEmpty(product.getClassifyId())) {
                a.setClassifyId(product.getClassifyId());
                res = true;
            }
            if (res) {
                a.updateById();
            }
        });
        return result;
    }

    /**
     * 屏蔽/取消屏蔽商品
     *
     * @param productId 商品id
     * @return
     */
    @Override
    public boolean shield(String productId) {
        if (StringUtils.isEmpty(productId)) {
            throw new OtherExcetion("请选择要操作的商品");
        }
        Product product = productSet.Get(productId);
        if (null == product) {
            throw new OtherExcetion("不存在的商品");
        }
        product.setShield(BaseType.Status.YES.getCode().equals(product.getShield()) ? BaseType.Status.NO.getCode() : BaseType.Status.YES.getCode());
        return product.updateProduct();
    }

    /**
     * 根据id查询单个商品
     *
     * @param id   商品id
     * @param user 用户对象
     * @return
     */
    @Override
    public Object getOne(String id, User user) {
        if (StringUtils.isEmpty(id)) {
            throw new OtherExcetion("请选择要查询的商品");
        }
        Product product = productSet.Get(id);
        if (null == product) {
            throw new OtherExcetion("不存在的商品");
        }
//        if (!BaseType.Status.YES.getCode().equals(product.getIllegal())) {
//            throw new OtherExcetion("该商品已被违规下架，请联系后台管理员");
//        }
        Brand brand = brandSet.Get(product.getBrandId());
        if (null != brand && StringUtils.isNotEmpty(brand.getName())) {
            product.setBrandName(brand.getName());
        } else {
            product.setBrandName("无");
        }
        Category category = categorySet.Get(product.getCategoryCode());
        if (null != category && StringUtils.isNotEmpty(category.getName())) {
            product.setCategoryName(category.getName());
        } else {
            product.setCategoryName("无");
        }
        String classifyName = "";
        String[] classifyIds = product.getClassifyId().split(",");
        if (classifyIds.length == 0) {
            classifyName = "无";
        }
        for (int j = 0; j < classifyIds.length; j++) {
            Classify classify = classifySet.Get(classifyIds[j]);
            if (null != classify && StringUtils.isNotEmpty(classify.getName())) {
                if (j == classifyIds.length - 1) {
                    classifyName += classify.getName();
                } else {
                    classifyName += classify.getName() + ",";
                }
            }
        }
        product.setClassifyName(classifyName);
        if (StringUtils.isNotEmpty(user.getName())) {
            product.setPname(user.getName());
        } else {
            product.setPname("无");
        }
        if (StringUtils.isNotEmpty(user.getTel())) {
            product.setTel(user.getTel());
        } else {
            product.setTel("无");
        }
        product.setAuthentication(BaseType.Status.YES.getCode().endsWith(user.getAuth()));
        return product;
    }

    /**
     * 删除商品
     *
     * @param id 商品id
     * @return
     */
    @Override
    public boolean delete(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new OtherExcetion("请选择要删除的商品");
        }
        //删除分享给经销商的商品
        List<SaleGoods> list = saleGoodsSet.Where("productId=?", id).ToList();
        list.forEach((a) -> {
            if (StringUtils.isNotEmpty(a.getId())) {
                saleGoodsSet.Delete(a.getId());
            }
        });
        return productSet.Delete(id) > 0;
    }

    /**
     * 查询后台商品
     *
     * @param pagePars 分页查询参数对象
     * @return
     */
    @Override
    public Object searchBackGroupProducts(TablePagePars pagePars) {
        return this.search(pagePars, null);
    }

    /**
     * 违规上下架
     *
     * @param product 商品对象
     * @param admin   管理员对象
     * @param ip      ip
     * @return
     */
    @Override
    public boolean illegal(Product product, Admin admin, String ip) {
        if (StringUtils.isEmpty(product.getId())) {
            throw new OtherExcetion("请选择要操作的商品");
        }
        product = productSet.Get(product.getId());
        if (null == product) {
            throw new OtherExcetion("不存在的商品");
        }
        product.setIllegal(BaseType.Status.YES.getCode().equals(product.getIllegal()) ? BaseType.Status.NO.getCode() : BaseType.Status.YES.getCode());
        List<SaleGoods> list = saleGoodsSet.Where("productId=?", product.getId()).ToList();
        for (SaleGoods a : list) {
            a.setIllegal(product.getIllegal());
            a.updateById();
        }
        addOperationLog(admin, ip, "操作违规上下架商品");
        return product.updateProduct();
    }

    /**
     * 修改商品
     *
     * @param product 商品对象
     * @param admin   管理员对象
     * @param ip      ip
     * @return
     */
    @Override
    public boolean update(Product product, Admin admin, String ip) {
        if (StringUtils.isEmpty(product.getId())) {
            throw new OtherExcetion("请选择要修改的商品");
        }
        addOperationLog(admin, ip, "修改商品");
        return product.updateProduct();
    }

    /**
     * 修改后台商品上下架
     *
     * @param product 商品对象
     * @param admin   管理员对象
     * @param ip      ip
     * @return
     */
    @Override
    public boolean updateProductOnline(Product product, Admin admin, String ip) {
        if (StringUtils.isEmpty(product.getId())) {
            throw new OtherExcetion("请选择要修改的商品");
        }
        product = productSet.Get(product.getId());
        if (null == product) {
            throw new OtherExcetion("不存在的商品");
        }
        product.setStatus(BaseType.Status.YES.getCode().equals(product.getStatus()) ? BaseType.Status.NO.getCode() : BaseType.Status.YES.getCode());
        String context = BaseType.Status.YES.getCode().equals(product.getStatus()) ? "修改商品上架" : "修改商品下架";
        addOperationLog(admin, ip, context);
        return product.updateProduct();
    }

    /**
     * 修改商品标签
     *
     * @param vo
     * @param admin 管理员对象
     * @param ip    ip
     * @return
     */
    @Override
    public boolean updateProductTag(UpdateProductTagVO vo, Admin admin, String ip) {
        if (StringUtils.isEmpty(vo.get_id())) {
            throw new OtherExcetion("请选择要修改的商品");
        }
        Product product = productSet.Get(vo.get_id());
        if (null == product) {
            throw new OtherExcetion("不存在的商品");
        }
        boolean res = false;
        String context = "";
        switch (vo.getTagType()) {
            case "1": {
                product.setTagRec(BaseType.Status.YES.getCode().equals(product.getTagRec()) ? BaseType.Status.NO.getCode() : BaseType.Status.YES.getCode());
                res = true;
                context = "修改商品推荐标签";
                break;
            }
            case "2": {
                product.setTagHot(BaseType.Status.YES.getCode().equals(product.getTagHot()) ? BaseType.Status.NO.getCode() : BaseType.Status.YES.getCode());
                res = true;
                context = "修改商品热搜标签";
                break;
            }
            case "3": {
                product.setTagNew(BaseType.Status.YES.getCode().equals(product.getTagNew()) ? BaseType.Status.NO.getCode() : BaseType.Status.YES.getCode());
                res = true;
                context = "修改商品新品标签";
                break;
            }
        }
        if (StringUtils.isNotEmpty(product.getSalerId())) {
            SaleGoods saleGoods = saleGoodsSet.Where("productId=?", product.getId()).First();
            if (null != saleGoods) {
                switch (vo.getTagType()) {
                    case "1": {
                        saleGoods.setTagRec(BaseType.Status.YES.getCode().equals(saleGoods.getTagRec()) ? BaseType.Status.NO.getCode() : BaseType.Status.YES.getCode());
                        break;
                    }
                    case "2": {
                        saleGoods.setTagHot(BaseType.Status.YES.getCode().equals(saleGoods.getTagHot()) ? BaseType.Status.NO.getCode() : BaseType.Status.YES.getCode());
                        break;
                    }
                    case "3": {
                        saleGoods.setTagNew(BaseType.Status.YES.getCode().equals(saleGoods.getTagNew()) ? BaseType.Status.NO.getCode() : BaseType.Status.YES.getCode());
                        break;
                    }
                }
                saleGoods.updateById();
            }
        }
        if (res) {
            addOperationLog(admin, ip, context);
            return product.updateProduct();
        }
        return false;
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
