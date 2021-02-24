package io.hk.webApp.Service.Imp;

import io.framecore.Frame.PageData;
import io.hk.webApp.DataAccess.*;
import io.hk.webApp.Domain.*;
import io.hk.webApp.Service.ISaleGoodsService;
import io.hk.webApp.Tools.*;
import io.hk.webApp.dto.ShareProduceDTO;
import io.hk.webApp.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 经销商商品
 */
@Service
public class SaleGoodsServiceImp implements ISaleGoodsService {

    @Autowired
    private ProductSet productSet;

    @Autowired
    private SaleGoodsSet saleGoodsSet;

    @Autowired
    private FriendsSet friendsSet;

    @Autowired
    private BrandSet brandSet;

    @Autowired
    private ShareProducesSet shareProducesSet;

    @Autowired
    private MessagesSet messagesSet;

    @Autowired
    private ChooseSet chooseSet;

    @Autowired
    private ClassifySet classifySet;

    /**
     * 经销商获得由供应商分享过来的商品
     *
     * @param vo
     * @param salerId 经销商id
     * @return
     */
    @Override
    public boolean add(ShareProductAddVO vo, String salerId) {
        if (StringUtils.isEmpty(vo.getShareProductId())) {
            throw new OtherExcetion("操作错误");
        }
        if (null == vo.getList() || vo.getList().size() == 0) {
            throw new OtherExcetion("请选择确认入库的商品");
        }
        ShareProduces shareProduces = shareProducesSet.Get(vo.getShareProductId());
        if (null == shareProduces) {
            throw new OtherExcetion("已失效");
        }
        if (!"1".equals(shareProduces.getStatus())) {
            throw new OtherExcetion("该方案已完成");
        }
        for (ProductSharePriceVO priceVO : vo.getList()) {
            Product product = productSet.Get(priceVO.getProductId());
            SaleGoods info = saleGoodsSet.Where("(productId=?)and(salerId=?)", priceVO.getProductId(), salerId).First();
            if (null != product && null == info) {
                SaleGoods saleGoods = new SaleGoods();
                saleGoods.setSalerId(salerId);
                saleGoods.setFactoryId(shareProduces.getFactoryId());
//                saleGoods.setProduct(BsonUtil.toDocument(product));  TODO 问题集锦提到经销商获得的商品的信息应该是同步供应商商品 故只存id不存实体
                saleGoods.setProductId(priceVO.getProductId());
                saleGoods.setType("2");
                saleGoods.setRecommend("2");
                saleGoods.setShield(BaseType.Status.YES.getCode());
                saleGoods.setStatus(BaseType.Status.YES.getCode());
                saleGoods.setPrice(priceVO.getNewMoney());
                saleGoods.setPriceTax(priceVO.getNewMoneyTax());
                saleGoods.setBrandId(product.getBrandId());
                saleGoods.setProductGroup(product.getProductGroup());
                saleGoods.setTagHot(product.getTagHot());
                saleGoods.setTagNew(product.getTagNew());
                saleGoods.setTagRec(product.getTagRec());
                saleGoods.setCategoryCode(product.getCategoryCode());
                saleGoods.setName(product.getName());
                saleGoods.setClassifyId(product.getClassifyId());
                saleGoods.setIllegal(BaseType.Status.YES.getCode());
                saleGoodsSet.Add(saleGoods);
            } else {
                {
                    Messages messages = new Messages();
                    messages.setTitle(BaseType.Message.SYSTEM.getCode());
                    messages.setStatus("2");
                    messages.setTime(System.currentTimeMillis());
                    messages.setUserId("1");
                    messages.setSource("系统");
                    messages.setReceiveId(salerId);
                    messages.setContent("商品：" + product.getName() + "已存在，故不添加");
                    messages.setData(null);
                    messages.setImg(Arrays.asList(product.getImage1()));
                    messagesSet.Add(messages);
                }
            }
        }
        List<Document> list = new ArrayList<>();
        shareProduces.getProducts().forEach((a) -> {
            ShareProduceDTO shareProduceDTO = BsonUtil.toBean(a, ShareProduceDTO.class);
            vo.getList().forEach((b) -> {
                if (shareProduceDTO.getProductId().equals(b.getProductId())) {
                    shareProduceDTO.setStatus("1");
                }
            });
            list.add(BsonUtil.toDocument(shareProduceDTO));
        });
        shareProduces.setProducts(list);
        shareProduces.setStatus("2");
        return shareProducesSet.Update(shareProduces.getId(), shareProduces) > 0;
    }

    /**
     * 经销商添加自有商品
     *
     * @param product 商品对象
     * @return
     */
    @Override
    public boolean addProduct(Product product) {
        if (StringUtils.isAnyEmpty(product.get("name").toString(),
//                product.getBrandId(),
//                product.getModel(),
                product.getBaseDetail(),
                product.getSellingPoint(),
                product.getDetail(),
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
        product.setCreateTime(System.currentTimeMillis());
        product.setIllegal(BaseType.Status.YES.getCode());
        product.setShield(BaseType.Status.YES.getCode());
        product.setStatus(BaseType.Status.YES.getCode());
        Object id = null;
        try {
            id = productSet.Add(product);
        } catch (Exception e) {
            throw new OtherExcetion("商品名重复");
        }

        SaleGoods saleGoods = new SaleGoods();
        saleGoods.setSalerId(product.getSalerId());
//        saleGoods.setProduct(BsonUtil.toDocument(product));  TODO 根据问题集锦 方案数据不受商品信息变更而变更
        saleGoods.setProductId(id.toString());
        saleGoods.setType("1");
        saleGoods.setStatus(product.getStatus());
        saleGoods.setRecommend("2");
        saleGoods.setShield(BaseType.Status.YES.getCode());
        saleGoods.setPrice(0.0);
        saleGoods.setPriceTax(0.0);
        //做冗余 检索商品用
        saleGoods.setTagHot(StringUtils.isEmpty(product.getTagHot()) ? "2" : product.getTagHot());
        saleGoods.setTagNew(StringUtils.isEmpty(product.getTagNew()) ? "2" : product.getTagNew());
        saleGoods.setTagRec(StringUtils.isEmpty(product.getTagRec()) ? "2" : product.getTagRec());
        saleGoods.setBrandId(StringUtils.isEmpty(product.getBrandId()) ? "" : product.getBrandId());
        saleGoods.setProductGroup(StringUtils.isEmpty(product.getProductGroup()) ? "" : product.getProductGroup());
        saleGoods.setCategoryCode(StringUtils.isEmpty(product.getCategoryCode()) ? "" : product.getCategoryCode());
        saleGoods.setName(StringUtils.isEmpty(product.getName()) ? "" : product.getName());
        saleGoods.setClassifyId(StringUtils.isEmpty(product.getClassifyId()) ? "" : product.getClassifyId());
        saleGoodsSet.Add(saleGoods);
        return true;
    }

    /**
     * 查询商品
     *
     * @param pagePars 分页参数对象
     * @param type     1查供应商 2查经销商
     * @return
     */
    @Override
    public PageData<SaleGoods> search(TablePagePars pagePars, User user, String type) {
        PageData<SaleGoods> pageData = saleGoodsSet.search(pagePars.Pars, pagePars.PageSize, pagePars.PageIndex, pagePars.Order, user, type);
        return pageData;
    }

    /**
     * 对自有商品进行上下架
     *
     * @param vo
     * @param id 经销商商品id
     * @return
     */
    @Override
    public boolean onlineOrNo(SaleGoodsOnlineOrNoVO vo, String id) {
        SaleGoods saleGoods = saleGoodsSet.Get(vo.getId());
        if (null == saleGoods || !id.equals(saleGoods.getSalerId()) || !"1".equals(saleGoods.getType())) {
            throw new OtherExcetion("不是可操作状态");
        }
        if (BaseType.Status.YES.getCode().equals(saleGoods.getStatus())) {
            saleGoods.setStatus(BaseType.Status.NO.getCode());
        } else {
            saleGoods.setStatus(BaseType.Status.YES.getCode());
        }
        return saleGoods.updateById();
    }

    /**
     * 修改自有商品
     *
     * @param vo
     * @param salerId 经销商id
     * @return
     */
    @Override
    public boolean update(SaleGoodsUpdateProductVO vo, String salerId) {
        SaleGoods saleGoods = saleGoodsSet.Get(vo.getSaleGoodsId());
        if (null == saleGoods || !saleGoods.getSalerId().equals(saleGoods.getSalerId()) || !"1".equals(saleGoods.getType())) {
            throw new OtherExcetion("当前不是可操作状态");
        }
        Product product = vo.getProduct();
//        saleGoods.setProduct(BsonUtil.toDocument(product));
        product.setId(saleGoods.getProductId());
        return product.updateProduct();
    }

    /**
     * 删除商品
     *
     * @param id      经销商商品id
     * @param salerId 经销商id
     * @return
     */
    @Override
    public boolean delete(String id, String salerId) {
        SaleGoods saleGoods = saleGoodsSet.Get(id);
        if (null == saleGoods || !saleGoods.getSalerId().equals(saleGoods.getSalerId()) || !"1".equals(saleGoods.getType())) {
            throw new OtherExcetion("当前不是可操作状态");
        }
        return saleGoods.deleteById();
    }

    /**
     * 根据id查询单个
     *
     * @param id   经销商商品
     * @param user 用户对象
     * @return
     */
    @Override
    public SaleGoods getById(String id, User user) {
        if (StringUtils.isEmpty(id)) {
            throw new OtherExcetion("请选择要查询的商品");
        }
        SaleGoods saleGoods = saleGoodsSet.Get(id);
        Product product = productSet.Get(saleGoods.getProductId());
        if (null == product) {
            throw new OtherExcetion("不存在的商品");
        }
        Brand brand = brandSet.Get(product.getBrandId());
        if (null != brand && StringUtils.isNotEmpty(brand.getName())) {
            product.setBrandName(brand.getName());
        } else {
            product.setBrandName("无");
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
        if (StringUtils.isNotEmpty(user.getCompanyName())) {
            product.setCname(user.getCompanyName());
        } else {
            product.setCname("无");
        }
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
        product.setAuthentication(BaseType.Status.YES.getCode().equals(user.getAuth()));
        saleGoods.setProduct(BsonUtil.toDocument(product));
        return saleGoods;
    }

    /**
     * 设为主题
     *
     * @param id 经销商商品id
     * @return
     */
    @Override
    public boolean recommend(String id) {
        SaleGoods saleGoods = saleGoodsSet.Get(id);
        if (null == saleGoods) {
            throw new OtherExcetion("不存在的商品");
        }
        if ("1".equals(saleGoods.getRecommend())) {
            saleGoods.setRecommend("2");
        } else {
            saleGoods.setRecommend("1");
        }
        return saleGoods.updateById();
    }

    /**
     * 查询所有合作厂商通过审核且未过期的所有品牌
     *
     * @param salerId 经销商id
     * @return
     */
    @Override
    public Set<Brand> searchBrandForFactory(String salerId) {
        List<Friends> friendsList = friendsSet.Where("userId=?", salerId).ToList();
        Set<Brand> set = new HashSet<>();
        if (null != friendsList && friendsList.size() > 0) {
            friendsList.forEach((a) -> {
                List<Brand> list = brandSet.Where("(userId=?)and(status=?)and(time>=?)", a.getFriendId(), BaseType.Consent.PASS.getCode(), System.currentTimeMillis()).ToList();
                if (null != list && list.size() > 0) {
                    set.addAll(list);
                }
            });
        }
        return set;
    }

    /**
     * 查询合作厂商们的所有品牌 不重复
     *
     * @param id 用户id
     * @return
     */
    @Override
    public List<Brand> getBrandForFriends(String id) {
        List<Friends> friendsList = friendsSet.Where("userId=?", id).ToList();
        List<Brand> list = new ArrayList<>();
        friendsList.forEach((a) -> {
            List<Brand> brandList = brandSet.Where("(userId=?)and(status=?)", a.getFriendId(), BaseType.Consent.PASS.getCode()).ToList();
            list.addAll(brandList);
        });
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 1; j < list.size(); j++) {
                if (list.get(i).getName().equals(list.get(j).getName())) {
                    list.remove(j);
                }
            }
        }
        List<Brand> brandList = new ArrayList<>();
        list.forEach((a) -> {
            if ("1".equals(a.getPerpetual()) || a.getTime() >= System.currentTimeMillis()) {
                brandList.add(a);
            }
        });
        return brandList;
    }

    /**
     * 查询分享的商品列表
     *
     * @param id 经销商id
     * @return
     */
    @Override
    public Object searchShare(TablePagePars pagePars, String id) {
        List<ShareProduces> shareProduces = shareProducesSet.Where("salerId=?", id).Limit(pagePars.PageSize, pagePars.PageIndex).OrderByDesc("_ctime").ToList();
        long count = shareProducesSet.Where("salerId=?", id).Count();

//        shareProduces.forEach((a)->{
//            List<Document> list = new ArrayList<>();
//            a.getProducts().forEach((b)->{
//                ShareProduceDTO shareProduceDTO = BsonUtil.toBean(b,ShareProduceDTO.class);
//                shareProduceDTO.setProduct(productSet.Get(shareProduceDTO.getProductId()));
//                list.add(BsonUtil.toDocument(shareProduceDTO));
//            });
//            a.setProducts(list);
//        });

        PageData<ShareProduces> producesPageData = new PageData<>();
        producesPageData.rows = shareProduces;
        producesPageData.total = count;
        return producesPageData;
    }

    /**
     * 查询分享过来的商品的详情
     *
     * @param shareProductId 分享商品表的id
     * @return
     */
    @Override
    public Object searchShareDetails(String shareProductId) {
        if (StringUtils.isEmpty(shareProductId)) {
            throw new OtherExcetion("请选择要查看的商品");
        }
        ShareProduces shareProduces = shareProducesSet.Get(shareProductId);
        if (null == shareProduces) {
            throw new OtherExcetion("不存在的商品");
        }
        List<Document> list = new ArrayList<>();
        shareProduces.getProducts().forEach((a) -> {
            ShareProduceDTO shareProduceDTO = BsonUtil.toBean(a, ShareProduceDTO.class);
            Product product = productSet.Get(shareProduceDTO.getProductId());
            shareProduceDTO.setProduct(product);
            list.add(BsonUtil.toDocument(shareProduceDTO));
        });
        shareProduces.setProducts(list);
        return shareProduces;
    }

    /**
     * 添加商品到选品库
     *
     * @param vo
     * @param user 用户对象
     * @return
     */
    @Override
    public boolean addToChoose(AddToChooseVO vo, User user) {
        vo.getList().forEach((a) -> {
            a.setSalerId(user.getId());
            if (StringUtils.isEmpty(a.getSaleGoodsId())) {
                throw new OtherExcetion("请选择商品");
            }
            SaleGoods saleGoods = saleGoodsSet.Get(a.getSaleGoodsId());
            if (null == saleGoods) {
                throw new OtherExcetion("不存在的商品");
            }
            if (!user.getId().equals(saleGoods.getSalerId())) {
                throw new OtherExcetion("不能添加不是你本人的商品");
            }
            long count = chooseSet.Where("salerId=?", a.getSalerId()).Count();
            if (count > 200) {
                throw new OtherExcetion("选品库已超最大数量限制了");
            }
            Product product = productSet.Get(saleGoods.getProductId());
            if (null == product) {
                throw new OtherExcetion("不存在的商品");
            }
            if (StringUtils.isNotEmpty(product.getImage1())) {
                a.setProductImg(product.getImage1());
            }
            chooseSet.Add(a);
        });
        return true;
    }

    /**
     * 查询选品库
     *
     * @param id 经销商id
     * @return
     */
    @Override
    public Object searchChoose(String id) {
        List<Choose> list = chooseSet.Where("salerId=?", id).OrderByDesc("_ctime").ToList();
        Map<String, Object> map = new HashMap<>();
        map.put("rows", list);
        map.put("total", list.size());
        return map;
    }

    /**
     * 删除选品库的商品
     *
     * @param user 用户对象
     * @return
     */
    @Override
    public boolean deleteChoose(DeleteChooseVO vo, User user) {
        vo.getChooseIds().forEach((a) -> {
            Choose choose = chooseSet.Get(a);
            if (null == choose) {
                throw new OtherExcetion("不存在的商品");
            }
            if (!user.getId().equals(choose.getSalerId())) {
                throw new OtherExcetion("不能删除被人的商品");
            }
            chooseSet.Delete(a);
        });
        return true;
    }

    /**
     * 查询合作厂商的分享给经销商的商品
     *
     * @param factoryId 供应商id
     * @param user      用户对象
     * @param pagePars  分页参数对象
     * @return
     */
    @Override
    public Object searchProducts(String factoryId, User user, TablePagePars pagePars) {
        List<SaleGoods> list = saleGoodsSet.Where("(salerId=?)and(factoryId=?)", user.getId(), factoryId).Limit(pagePars.PageSize, pagePars.PageIndex).ToList();
        long count = saleGoodsSet.Where("(salerId=?)and(factoryId=?)", user.getId(), factoryId).Count();
        List<SaleGoods> saleGoods = new ArrayList<>();
        list.forEach((a) -> {
            Product product = productSet.Get(a.getProductId());
            a.setProduct(BsonUtil.toDocument(product));
            saleGoods.add(a);
        });
        if (saleGoods.size() < pagePars.PageIndex) {
            pagePars.PageIndex = saleGoods.size();
        }
        PageData<SaleGoods> pageData = new PageData<>();
        pageData.total = count;
        pageData.rows = list;
        return pageData;
    }

    /**
     * 删除指定经销商中指定供应商的所有商品
     *
     * @param salerId   经销商id
     * @param factoryId 供应商id
     * @return
     */
    @Override
    public boolean removeSalerProductFromFactory(String salerId, String factoryId) {
        List<SaleGoods> saleGoods = saleGoodsSet.Where("(salerId=?)and(factoryId=?)", salerId, factoryId).ToList();
        int[] res = {0};
        saleGoods.forEach((a) -> {
            if (null != a && StringUtils.isNotEmpty(a.getId())) {
                saleGoodsSet.Delete(a.getId());
                res[0]++;
            }
        });
        return res[0] == saleGoods.size();
    }
}
