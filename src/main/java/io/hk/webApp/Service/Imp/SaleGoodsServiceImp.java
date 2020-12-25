package io.hk.webApp.Service.Imp;

import io.framecore.Frame.PageData;
import io.hk.webApp.DataAccess.*;
import io.hk.webApp.Domain.*;
import io.hk.webApp.Service.IProductService;
import io.hk.webApp.Service.ISaleGoodsService;
import io.hk.webApp.Tools.*;
import io.hk.webApp.dto.ShareProduceDTO;
import io.hk.webApp.vo.ProductSharePriceVO;
import io.hk.webApp.vo.SaleGoodsOnlineOrNoVO;
import io.hk.webApp.vo.SaleGoodsUpdateProductVO;
import io.hk.webApp.vo.ShareProductAddVO;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SaleGoodsServiceImp implements ISaleGoodsService {

    @Autowired
    private ProductSet productSet;

    @Autowired
    private SaleGoodsSet saleGoodsSet;

    @Autowired
    private IProductService productService;

    @Autowired
    private FriendsSet friendsSet;

    @Autowired
    private BrandSet brandSet;

    @Autowired
    private ShareProducesSet shareProducesSet;

    @Autowired
    private MessagesSet messagesSet;

    /**
     * 经销商获得由供应商分享过来的商品
     *
     * @param vo
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
            SaleGoods info = saleGoodsSet.Where("productId=?", priceVO.getProductId()).First();
            if (null != product && null == info) {
                SaleGoods saleGoods = new SaleGoods();
                saleGoods.setSalerId(salerId);
//                saleGoods.setProduct(BsonUtil.toDocument(product));  TODO 问题集锦提到经销商获得的商品的信息应该是同步供应商商品 故只存id不存实体
                saleGoods.setProductId(priceVO.getProductId());
                saleGoods.setType("2");
                saleGoods.setRecommend(false);
                saleGoods.setPrice(priceVO.getNewMoney());
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
     * @param product
     * @return
     */
    @Override
    public boolean addProduct(Product product) {
        if (StringUtils.isAnyEmpty(product.get("name").toString(),
                product.getBrandId(),
                product.getModel(),
                product.getBaseDetail(),
                product.getSellingPoint(),
                product.getDetail())) {
            throw new OtherExcetion("请完善必填项");
        }

        if (NumberUtils.isDoubleMinus(product.getPriceBegin1(),
                product.getPriceEnd1(),
                product.getPriceBegin2(),
                product.getPriceEnd2(),
                product.getSupplyPriceTax(),
                product.getSupplyPriceTaxNo(),
                product.getRetailPriceTax(),
                product.getRetailPriceTaxNo())) {
            throw new OtherExcetion("价格输入有误");
        }
        product.setCreateTime(System.currentTimeMillis());
        Object id = productSet.Add(product);

        SaleGoods saleGoods = new SaleGoods();
        saleGoods.setSalerId(product.getSalerId());
//        saleGoods.setProduct(BsonUtil.toDocument(product));  TODO 根据问题集锦 方案数据不受商品信息变更而变更
        saleGoods.setProductId(id.toString());
        saleGoods.setType("1");
        saleGoods.setStatus(product.getStatus());
        saleGoods.setRecommend(false);
        saleGoodsSet.Add(saleGoods);
        return true;
    }

    /**
     * 查询商品
     *
     * @param pagePars
     * @param salerId
     * @param type
     * @return
     */
    @Override
    public PageData<SaleGoods> search(TablePagePars pagePars, String salerId, String type) {
        PageData<SaleGoods> pageData = saleGoodsSet.search(pagePars.Pars, pagePars.PageSize, pagePars.PageIndex, pagePars.Order, salerId, type);
        pageData.rows.forEach((a) -> {
            Product product = productSet.Get(a.getProductId());
//            Category category = new Category().getById(product.getCategoryCode());
//            String categoryName = "无";
//            if(null != category && StringUtils.isNotEmpty(category.getName())){
//                categoryName = category.getName();
//            }
//            Brand brand = new Brand().getById(product.getBrandId());
//            String brandName = "无";
//            if(null != brand && StringUtils.isNotEmpty(brand.getName())){
//                brandName = brand.getName();
//            }
//            product.setCategoryName(categoryName);
//            product.setBrandName(brandName);
            a.setProduct(BsonUtil.toDocument(product));
        });
        return pageData;
    }

    /**
     * 对自有商品进行上下架
     *
     * @param vo
     * @param id
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
     * @param salerId
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
     * @param id
     * @param salerId
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
     * @param id
     * @return
     */
    @Override
    public SaleGoods getById(String id) {
        return saleGoodsSet.Get(id);
    }

    /**
     * 设为主题
     *
     * @param id
     * @return
     */
    @Override
    public boolean recommend(String id) {
        SaleGoods saleGoods = saleGoodsSet.Get(id);
        if (null == saleGoods) {
            throw new OtherExcetion("不存在的商品");
        }
        if (saleGoods.getRecommend()) {
            saleGoods.setRecommend(false);
        } else {
            saleGoods.setRecommend(true);
        }
        return saleGoods.updateById();
    }

    /**
     * 查询所有合作厂商通过审核且未过期的所有品牌
     *
     * @param salerId
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
     * @param id
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
     * @param id
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
     * @param shareProductId
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
}
