package io.hk.webApp.Service.Imp;

import io.framecore.Frame.PageData;
import io.hk.webApp.DataAccess.*;
import io.hk.webApp.Domain.*;
import io.hk.webApp.Service.IFactorySchemeService;
import io.hk.webApp.Service.ISchemeService;
import io.hk.webApp.Tools.*;
import io.hk.webApp.dto.*;
import io.hk.webApp.test.PPTUtil;
import io.hk.webApp.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 方案
 */
@Service
public class SchemeServiceImp implements ISchemeService {

    @Autowired
    private SchemeSet schemeSet;

    @Autowired
    private SaleGoodsSet saleGoodsSet;

    @Autowired
    private ProductSet productSet;

    @Autowired
    private FriendsSet friendsSet;

    @Autowired
    private FactorySchemeSet factorySchemeSet;

    @Autowired
    private UserSet userSet;

    @Autowired
    private MessagesSet messagesSet;

    @Autowired
    private IFactorySchemeService iFactorySchemeService;

    @Autowired
    private BrandSet brandSet;

    @Autowired
    private ChooseSet chooseSet;

    private final Logger LOG = LoggerFactory.getLogger(SchemeServiceImp.class);

    /**
     * 新建方案
     *
     * @param scheme 经销商方案对象
     * @return
     */
    @Override
    public boolean add(Scheme scheme) {
        if (StringUtils.isAnyEmpty(scheme.getName())) {
            throw new OtherExcetion("请完善必填项");
        }
        if (null == scheme.getValidity()) {
            throw new OtherExcetion("请输入正确的有效期");
        }
        scheme.setStatus(BaseType.SchemeStatus.UNDERWAY.getCode());
        scheme.setFactoryIds(new ArrayList<>());
        scheme.setSalesGoods(new ArrayList<>());
        schemeSet.Add(scheme);
        return true;
    }

    /**
     * 查询经销商自己的方案列表
     *
     * @param salerId  经销商id
     * @param pagePars 分页参数对象
     * @return
     */
    @Override
    public PageData<Scheme> search(String salerId, TablePagePars pagePars) {
        PageData<Scheme> pageData = schemeSet.search(pagePars.PageSize, pagePars.PageIndex, pagePars.Pars, salerId);
        pageData.rows.forEach((a) -> {
            List<FactoryScheme> list = factorySchemeSet.Where("schemeId=?", a.getId()).ToList();
            long[] count = {0};
            list.forEach((b) -> {
                if (null != b.getFactoryProductIds()) {
                    count[0] += b.getFactoryProductIds().size();
                }
            });
            a.setNumber(count[0] + a.getSalesGoods().size());
        });
        return pageData;
    }

    /**
     * 添加商品到方案
     *
     * @param vo
     * @param userId 用户id
     * @return
     */
    @Override
    public boolean addProduct(SchemeAddProductVO vo, String userId) {
        if (StringUtils.isEmpty(vo.getSchemeId())) {
            throw new OtherExcetion("你要为哪个方案添加商品");
        }
        Scheme scheme = schemeSet.Get(vo.getSchemeId());
        if (null == scheme) {
            throw new OtherExcetion("不存在的方案");
        }
        if (!BaseType.SchemeStatus.UNDERWAY.getCode().equals(scheme.getStatus())) {
            throw new OtherExcetion("不是正在进行中的方案");
        }
        List<Document> list;
        try {
            list = scheme.getSalesGoods();
        } catch (Exception e) {
            //如果是新方案可能会报空指针
            list = new ArrayList<>();
            LOG.info("新方案添加商品");
        }
        if (null == list || list.size() == 0) {
            list = new ArrayList<>();
        }
        //查询经销商选品库的所有商品
        List<Choose> chooses = chooseSet.Where("salerId=?", userId).ToList();
        if (chooses.size() == 0) {
            return true;
        }
        //查询到经销商方案所有有关的productId
        List<String> productIds = new ArrayList<>();
        list.forEach((a) -> {
            FactorySchemeSaleGoodDTO dto = BsonUtil.toBean(a, FactorySchemeSaleGoodDTO.class);
            productIds.add(dto.getSaleGoods().getProductId());
        });
        //获取分享到经销商那边的所有商品的id
        List<FactoryScheme> factorySchemes = factorySchemeSet.Where("schemeId=?", vo.getSchemeId()).ToList();
        factorySchemes.forEach((a) -> {
            List<Document> documents;
            try {
                documents = a.getFactoryProductIds();
            } catch (Exception e) {
                documents = new ArrayList<>();
            }
            if (null == documents || documents.size() == 0) {
                documents = new ArrayList<>();
            }
            documents.forEach((b) -> {
                FactorySchemeProductDTO dto = BsonUtil.toBean(b, FactorySchemeProductDTO.class);
                productIds.add(dto.getProductIds());
            });
        });
        //去除重复商品
        List<Choose> chooses1 = new ArrayList<>(chooses);
        for (Choose value : chooses1) {
            for (String productId : productIds) {
                SaleGoods saleGoods = saleGoodsSet.Get(value.getSaleGoodsId());
                if (null != saleGoods && userId.equals(saleGoods.getSalerId()) && saleGoods.getProductId().equals(productId)) {
                    chooses.remove(value);
                    chooseSet.Delete(value.getId());
                    break;
                }
            }
        }
        // TODO 问题集锦提到 放到方案中的商品不会因为供应商的更改而更改
        for (Choose choose : chooses) {
            SaleGoods saleGoods = saleGoodsSet.Get(choose.getSaleGoodsId());
            if (null != saleGoods && userId.equals(saleGoods.getSalerId())) {
                Product product = productSet.Get(saleGoods.getProductId());
                if (null == product) {
                    continue;
                }
                saleGoods.setProductBean(product);
                saleGoods.setProduct(BsonUtil.toDocument(product));
                FactorySchemeSaleGoodDTO dto = new FactorySchemeSaleGoodDTO();
                dto.setSaleGoods(saleGoods);
                dto.setSaleGoodId(saleGoods.getId());
                dto.setSalerNewMoneyTax(saleGoods.getPrice());
                dto.setSalerNewMoneyTaxNo(saleGoods.getPriceTax());
                list.add(BsonUtil.toDocument(dto));
                chooseSet.Delete(choose.getId());
            }
        }
        scheme.setSalesGoods(list);
        return schemeSet.Update(scheme.getId(), scheme) > 0;
    }

    /**
     * 重命名方案
     *
     * @param scheme 经销商方案对象
     * @return
     */
    @Override
    public boolean rename(Scheme scheme, String id) {
        if (StringUtils.isEmpty(scheme.getId())) {
            throw new OtherExcetion("请选择要重命名的方案");
        }
        if (StringUtils.isEmpty(scheme.getName())) {
            throw new OtherExcetion("请输入新的方案名");
        }
        Scheme info = schemeSet.Get(scheme.getId());
        if (null == info) {
            throw new OtherExcetion("不存在的方案");
        }
        if (!BaseType.SchemeStatus.UNDERWAY.getCode().equals(info.getStatus())) {
            throw new OtherExcetion("不能修改非进行中的方案");
        }
        Scheme scheme1 = new Scheme();
        scheme1.setId(scheme.getId());
        scheme1.setName(scheme.getName());
        List<FactoryScheme> list = factorySchemeSet.Where("schemeId=?", scheme.getId()).ToList();
        list.forEach((a) -> {
            a.setName(scheme.getName());
            a.updateById();
        });
        return scheme1.updateById();
    }

    /**
     * 删除方案
     *
     * @param id 方案id
     * @return
     */
    @Override
    public boolean delete(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new OtherExcetion("请选择要删除的方案");
        }
        return schemeSet.Delete(id) > 0;
    }

    /**
     * 查询邀请报价页面的合作厂商信息
     *
     * @param userId   用户id
     * @param pagePars 分页参数对象
     * @return
     */
    @Override
    public Object searchFactorys(String userId, TablePagePars pagePars, String schemeId) {
        Scheme scheme = schemeSet.Get(schemeId);
        if (null == scheme) {
            throw new OtherExcetion("不存在的方案");
        }
        List<SchemeFriendsDTO> list = new ArrayList<>();
        List<Friends> friendsList = friendsSet.searchFactorys(pagePars.Pars, pagePars.PageSize, pagePars.PageIndex, userId);
        friendsList.forEach((a) -> {
            SchemeFriendsDTO dto = new SchemeFriendsDTO();
            dto.setFriends(a);
            User friend = userSet.Get(a.getFriendId());
            if (null != friend) {
                String addr = (null == friend.getProvince() ? friend.getProvince() : "") + (null != friend.getAddr() ? friend.getAddr() : "");
                dto.setAddr(addr);
                dto.setCname(null != friend.getCompanyName() ? friend.getCompanyName() : "");
                dto.setName(null != friend.getName() ? friend.getName() : "");
                dto.setRealPhone(null != friend.getRealPhone() ? friend.getRealPhone() : "");
                dto.setTel(null != friend.getTel() ? friend.getTel() : "");
                dto.setImg(null != friend.getImg() ? friend.getImg() : "");
            }
            if (null != scheme.getFactoryIds() && scheme.getFactoryIds().size() > 0) {
                scheme.getFactoryIds().forEach((b) -> {
                    if (a.getFriendId().equals(b)) {
                        dto.setFlag(true);
                    }
                });
            }
            list.add(dto);
        });
        return list;
    }

    /**
     * 邀请厂商报价
     *
     * @param vo
     * @param user 用户对象
     * @return
     */
    @Override
    public boolean quotation(SchemeQuotationVO vo, User user) {
        FactoryScheme factoryScheme = factorySchemeSet.Where("(factoryId=?)and(schemeId=?)and(salerId=?)", vo.getFactoryId(), vo.getSchemeId(), user.getId()).First();
        if (null != factoryScheme) {
            throw new OtherExcetion("请勿重复分享此方案");
        }
        if (StringUtils.isEmpty(user.getCompanyName())) {
            throw new OtherExcetion(-10, "完善个人资料后方可操作");
        }
        factoryScheme = new FactoryScheme();
        Scheme scheme = schemeSet.Get(vo.getSchemeId());
        if (null == scheme) {
            throw new OtherExcetion("不存在的方案");
        }
        if (!BaseType.SchemeStatus.UNDERWAY.getCode().equals(scheme.getStatus())) {
            throw new OtherExcetion("不是正在进行中的方案");
        }
        if (scheme.getValidity() < System.currentTimeMillis()) {
            scheme.setStatus(BaseType.SchemeStatus.PAST.getCode());
            scheme.updateById();
            throw new OtherExcetion("此方案已失效");
        }
        factoryScheme.setName(scheme.getName());
        factoryScheme.setFactoryId(vo.getFactoryId());
        factoryScheme.setSalerId(user.getId());
        factoryScheme.setSchemeId(scheme.getId());
        factoryScheme.setSalerName(user.getCompanyName());
        factoryScheme.setValidity(scheme.getValidity());
        factoryScheme.setNumber("0");
        factoryScheme.setUpdateTime(System.currentTimeMillis());
        factoryScheme.setStatus(BaseType.Status.NO.getCode());
        factoryScheme.setScheme(BaseType.Status.NO.getCode());
        factorySchemeSet.Add(factoryScheme);
        List<String> list = scheme.getFactoryIds();
        if (null == list || list.size() == 0) {
            list = new ArrayList<>();
        }
        list.add(vo.getFactoryId());
        scheme.setFactoryIds(list);
        {
            //完成提报后发送消息提醒经销商
            Messages messages = new Messages();
            messages.setTitle(BaseType.Message.SUBMISSION.getCode());
            messages.setStatus("2");
            messages.setTime(System.currentTimeMillis());
            messages.setUserId(user.getId());
            messages.setSource(user.getCompanyName());
            messages.setReceiveId(vo.getFactoryId());
            messages.setContent(user.getCompanyName() + "：邀请你报价");
            messages.setData(scheme.getId());
            messagesSet.Add(messages);
        }
        return scheme.updateById();
    }

    /**
     * 查询方案详情
     *
     * @param schemeId 方案id
     * @param pagePars 分页参数对象
     * @param user     用户对象
     * @param type
     * @return
     */
    @Override
    public FactorySchemeDTO getDetails(String schemeId, User user, TablePagePars pagePars, String type) {
        if (StringUtils.isEmpty(schemeId)) {
            throw new OtherExcetion("请选择方案");
        }
        Scheme scheme = schemeSet.Get(schemeId);
        if (null == scheme) {
            throw new OtherExcetion("不存在的方案");
        }
        List<FactoryScheme> factorySchemes = factorySchemeSet.Where("schemeId=?", schemeId).ToList();
        FactorySchemeDTO factorySchemeDTO = new FactorySchemeDTO();
        factorySchemeDTO.setSchemeId(schemeId);
        factorySchemeDTO.setCreateTime(scheme.getCreatetime().getTime());
        factorySchemeDTO.setDetails(scheme.getSynopsis());
        factorySchemeDTO.setValidity(scheme.getValidity());
        factorySchemeDTO.setName(scheme.getName());
        final List<ProductDTO> productList = new ArrayList<>();
        if (null != scheme.getSalesGoods() && scheme.getSalesGoods().size() > 0) {
            //添加经销商的商品
            scheme.getSalesGoods().forEach((a) -> {
                FactorySchemeSaleGoodDTO dto = BsonUtil.toBean(a, FactorySchemeSaleGoodDTO.class);
                ProductDTO productDTO = new ProductDTO();
//                Product product = productSet.Get(dto.getSaleGoods().getProductId()); //动态获取
                Product product = dto.getSaleGoods().getProductBean();//拿实体
                System.out.println(product);
                if (null != product) {
                    productDTO.setProduct(product);
                    productDTO.setFactoryNewMoneyTax(product.getSupplyPriceTax());
                    productDTO.setFactoryNewMoneyTaxNo(product.getSupplyPriceTaxNo());
                } else {
                    productDTO.setProduct(new Product());
                    productDTO.setFactoryNewMoneyTax(0);
                    productDTO.setFactoryNewMoneyTaxNo(0);
                }
                productDTO.setSalerNewMoneyTax(dto.getSalerNewMoneyTax());
                productDTO.setSalerNewMoneyTaxNo(dto.getSalerNewMoneyTaxNo());
                productDTO.setCname(StringUtils.isEmpty(user.getCompanyName()) ? "" : user.getCompanyName());
                productDTO.setType(BaseType.UserType.SALER.getCode());
                productList.add(productDTO);
                factorySchemeDTO.setNumbers(factorySchemeDTO.getNumbers() + 1);
            });
        }
        //添加供应商的商品
        factorySchemes.forEach((a) -> {
            if (null == a.getFactoryProductIds() || a.getFactoryProductIds().size() == 0) {
                a.setFactoryProductIds(new ArrayList<>());
            }
            factorySchemeDTO.setNumbers(factorySchemeDTO.getNumbers() + a.getFactoryProductIds().size());
            a.getFactoryProductIds().forEach((b) -> {
                FactorySchemeProductDTO dto = BsonUtil.toBean(b, FactorySchemeProductDTO.class);
                Product product = dto.getProduct(); //拿实体
//                Product product = productSet.Get(dto.getProductIds()); //动态获取
                ProductDTO productDTO = new ProductDTO();
                productDTO.setFactoryNewMoneyTax(dto.getFactoryNewMoneyTax());
                productDTO.setFactoryNewMoneyTaxNo(dto.getFactoryNewMoneyTaxNo());
                productDTO.setSalerNewMoneyTax(dto.getSalerNewMoneyTax());
                productDTO.setSalerNewMoneyTaxNo(dto.getSalerNewMoneyTaxNo());
                productDTO.setProduct(product);
                productDTO.setFactorySchemeId(a.getId());
                productDTO.setType(BaseType.UserType.FACTORY.getCode());
                User factory = userSet.Get(product.getFactoryId());
                if (null != factory) {
                    productDTO.setCname(StringUtils.isEmpty(factory.getCompanyName()) ? "" : factory.getCompanyName());
                }
                productList.add(productDTO);
            });
        });
        factorySchemeDTO.setTotal(productList.size());
        if ("0".equals(type)) {
            factorySchemeDTO.setProductList(productList);
        } else {
            if (productList.size() < pagePars.PageIndex) {
                pagePars.PageIndex = productList.size();
            }
            factorySchemeDTO.setProductList(productList.subList(pagePars.PageSize, pagePars.PageIndex += pagePars.PageSize));
        }
        factorySchemeDTO.setStatus(scheme.getStatus());
        return factorySchemeDTO;
    }

    /**
     * 查询供应商分享过来的方案列表
     *
     * @param id       经销商id
     * @param pagePars 分页参数对象
     * @return
     */
    @Override
    public Object searchFactorySchemes(String id, TablePagePars pagePars) {
        List<FactoryScheme> factorySchemes = factorySchemeSet.Where("(salerId=?)and(status=?)", id, BaseType.Status.YES.getCode()).Limit(pagePars.PageSize, pagePars.PageIndex).OrderByDesc("_uptime").ToList();
        long count = factorySchemeSet.Where("(salerId=?)and(status=?)", id, BaseType.Status.YES.getCode()).Count();
        User saler = userSet.Get(id);
        List<SalerFactorySchemeDTO> list = new ArrayList<>();
        factorySchemes.forEach((a) -> {
            SalerFactorySchemeDTO dto = new SalerFactorySchemeDTO();
            User factory = userSet.Get(a.getFactoryId());
            dto.setFactoryName(factory.getName());
            dto.setSalerName(saler.getName());
            dto.setFactorySchemeId(a.getId());
            Scheme scheme = schemeSet.Get(a.getSchemeId());
            dto.setSchemeName(scheme.getName());
            List<String> imgList = new ArrayList<>();
            a.getFactoryProductIds().forEach((b) -> {
                FactorySchemeProductDTO factorySchemeProductDTO = BsonUtil.toBean(b, FactorySchemeProductDTO.class);
                Product product = productSet.Get(factorySchemeProductDTO.getProductIds());
                String[] imgs = product.getImage1().split(",");
                imgList.addAll(Arrays.asList(imgs));
            });
            dto.setImgList(imgList);
            list.add(dto);
        });
        PageData<SalerFactorySchemeDTO> pageData = new PageData<>();
        pageData.rows = list;
        pageData.total = count;
        return pageData;
    }

    /**
     * 查询供应商提报的方案详情
     *
     * @param factorySchemeId 供应商方案id
     * @param pagePars        分页参数对象
     * @return
     */
    @Override
    public Object getFactorySchemeDetails(String factorySchemeId, TablePagePars pagePars) {
        return iFactorySchemeService.getDetails(factorySchemeId, pagePars);
    }

    /**
     * 删除商品提报管理的方案
     *
     * @param factorySchemeId 供应商方案id
     * @return
     */
    @Override
    public boolean deleteFactoryScheme(String factorySchemeId) {
        if (StringUtils.isEmpty(factorySchemeId)) {
            throw new OtherExcetion("请选择要删除的方案");
        }
        FactoryScheme factoryScheme = factorySchemeSet.Get(factorySchemeId);
        if (null == factoryScheme) {
            return true;
        }
        factoryScheme.setStatus(BaseType.Status.NO.getCode());
        return factoryScheme.updateById();
    }

    /**
     * 修改价格
     *
     * @param vo
     * @return
     */
    @Override
    public boolean updateMoneyForAll(SalerUpdateMoneyVO vo) {
        if (StringUtils.isAnyEmpty(vo.getProductId())) {
            throw new OtherExcetion("请完善必填项");
        }
        if (vo.getSalerNewMoneyTax() <= 0 || vo.getSalerNewMoneyTaxNo() <= 0) {
            throw new OtherExcetion("请输入正确的价格");
        }
        if (StringUtils.isEmpty(vo.getFactorySchemeId())) {
            vo.setFactorySchemeId(vo.getSchemeId());
        }
        FactoryScheme factoryScheme = factorySchemeSet.Get(vo.getFactorySchemeId());
        if (null != factoryScheme) {
            List<Document> list = factoryScheme.getFactoryProductIds();
            if (null == list || list.size() == 0) {
                throw new OtherExcetion("没有可供修改的商品");
            }
            for (int i = 0; i < list.size(); i++) {
                FactorySchemeProductDTO dto = BsonUtil.toBean(list.get(i), FactorySchemeProductDTO.class);
                if (dto.getProductIds().equals(vo.getProductId())) {
                    dto.setSalerNewMoneyTax(vo.getSalerNewMoneyTax());
                    dto.setSalerNewMoneyTaxNo(vo.getSalerNewMoneyTaxNo());
                    list.set(i, BsonUtil.toDocument(dto));
                    break;
                }
            }
            factoryScheme.setFactoryProductIds(list);
            return factoryScheme.updateById();
        } else {
            Scheme scheme = schemeSet.Get(vo.getSchemeId());
            if (null == scheme) {
                throw new OtherExcetion("不存在的方案");
            }
            if (!BaseType.SchemeStatus.UNDERWAY.getCode().equals(scheme.getStatus())) {
                throw new OtherExcetion("此方案已结束,不能修改");
            }
            List<Document> documents = scheme.getSalesGoods();
            List<Document> list1 = new ArrayList<>();
            documents.forEach((a) -> {
                FactorySchemeSaleGoodDTO dto = BsonUtil.toBean(a, FactorySchemeSaleGoodDTO.class);
                if (vo.getProductId().equals(dto.getSaleGoods().getProductId())) {
                    dto.setSalerNewMoneyTax(vo.getSalerNewMoneyTax());
                    dto.setSalerNewMoneyTaxNo(vo.getSalerNewMoneyTaxNo());
                }
                list1.add(BsonUtil.toDocument(dto));
            });
            scheme.setSalesGoods(list1);
            return scheme.updateById();
        }
    }

    /**
     * 通知供应商提报商品
     *
     * @param vo
     * @param user 用户对象
     * @return
     */
    @Override
    public boolean inform(SchemeInformVO vo, User user) {
        if (StringUtils.isEmpty(user.getCompanyName())) {
            throw new OtherExcetion("完善资料后可进行");
        }
        if (StringUtils.isEmpty(vo.getId())) {
            throw new OtherExcetion("请选择方案");
        }
        if (vo.getList().size() == 0) {
            throw new OtherExcetion("请选择商品");
        }
        Scheme scheme = schemeSet.Get(vo.getId());
        if (null == scheme) {
            throw new OtherExcetion("不存在的方案");
        }
        if (!BaseType.SchemeStatus.UNDERWAY.getCode().equals(scheme.getStatus())) {
            throw new OtherExcetion("该方案已结束");
        }
        List<Map<String, Object>> list = new ArrayList<>();
        Set<String> set = new HashSet<>();
        for (int i = 0; i < vo.getList().size(); i++) {
            SchemeInfornVO inform = vo.getList().get(i);
            if (StringUtils.isEmpty(inform.getProductId())) {
                continue;
            }
            Product product = productSet.Get(inform.getProductId());
            if (null == product || StringUtils.isEmpty(product.getFactoryId())) {
                continue;
            }
            set.add(product.getFactoryId());
        }
        for (int i = 0; i < vo.getList().size(); i++) {
            SchemeInfornVO inform = vo.getList().get(i);
            if (StringUtils.isEmpty(inform.getProductId())) {
                continue;
            }
            Product product = productSet.Get(inform.getProductId());
            for (String factoryId : set) {
                Map<String, Object> map = new HashMap<>();
                List<String> list1 = new ArrayList<>();
                map.put("userId", factoryId);
                if (factoryId.equals(product.getFactoryId())) {
                    list1.add(inform.getProductId());
                }
                map.put("list", list1);
                list.add(map);
            }
        }
        list.forEach((a) -> {
            {
                Messages messages = new Messages();
                messages.setTitle(BaseType.Message.SUBMISSION.getCode());
                messages.setStatus("2");
                messages.setTime(System.currentTimeMillis());
                messages.setUserId(user.getId());
                messages.setSource(user.getCompanyName());
                messages.setReceiveId(a.get("userId").toString());
                messages.setContent(user.getCompanyName() + "邀请您发送报价");
                messages.setData(a.get("list"));
                messagesSet.Add(messages);
            }
        });

        List<FactorySchemeSaleGoodDTO> infos = new ArrayList<>();
        Set<String> factoryIds = new HashSet<>();
        //获得经销商方案的经销商商品
        scheme.getSalesGoods().forEach((a) -> {
            FactorySchemeSaleGoodDTO dto = BsonUtil.toBean(a, FactorySchemeSaleGoodDTO.class);
            if (StringUtils.isNotEmpty(dto.getSaleGoods().getFactoryId())) {
                factoryIds.add(dto.getSaleGoods().getFactoryId());
            }
            infos.add(dto);
        });
        //如果经销商方案有某个供应商的商品 但是没有邀请他报过价 此时也应该提报到拥有这个商品的供应商
        factoryIds.forEach((a) -> {
            FactoryScheme factoryScheme = factorySchemeSet.Where("(schemeId=?)and(factoryId=?)", scheme.getId(), a).First();
            if (null == factoryScheme) {
                SchemeQuotationVO vo1 = new SchemeQuotationVO();
                vo1.setFactoryId(a);
                vo1.setSchemeId(scheme.getId());
                this.quotation(vo1, user);
            }
        });

        //获取需要给供应商方案添加经销商方案共有商品的供应商方案id去重
        List<FactoryScheme> factorySchemeIdsSet = factorySchemeSet.Where("schemeId=?", scheme.getId()).ToList();
        //遍历供应商方案
        factorySchemeIdsSet.forEach((a) -> {
            FactoryScheme factoryScheme = factorySchemeSet.Get(a.getId());
            if (BaseType.Status.NO.getCode().equals(factoryScheme.getScheme())) {
                //获得供应商方案的所有供应商的商品
                List<Document> documents;
                try {
                    documents = factoryScheme.getFactoryProductIds();
                } catch (Exception e) {
                    documents = new ArrayList<>();
                }
                if (null == documents || documents.size() == 0) {
                    documents = new ArrayList<>();
                }
                List<Document> list1 = new ArrayList<>(documents);
                List<Product> products = productSet.Where("factoryId=?", factoryScheme.getFactoryId()).ToList();
                //遍历供应商的所有商品
                products.forEach((b) -> {
                    //遍历经销商方案商品
                    infos.forEach((c) -> {
                        if (c.getSaleGoods().getProductId().equals(b.getId())) {
                            FactorySchemeProductDTO factorySchemeProductDTO = new FactorySchemeProductDTO();
                            factorySchemeProductDTO.setProduct(productSet.Get(c.getSaleGoods().getProductId()));
                            factorySchemeProductDTO.setProductIds(b.getId());
                            factorySchemeProductDTO.setFactoryNewMoneyTaxNo(0);
                            factorySchemeProductDTO.setFactoryNewMoneyTax(0);
                            factorySchemeProductDTO.setSalerNewMoneyTax(c.getSalerNewMoneyTax());
                            factorySchemeProductDTO.setSalerNewMoneyTaxNo(c.getSalerNewMoneyTaxNo());
                            list1.add(BsonUtil.toDocument(factorySchemeProductDTO));
                        }
                    });
                });
                factoryScheme.setFactoryProductIds(list1);
                factoryScheme.setScheme(BaseType.Status.YES.getCode());
                factoryScheme.setNumber(list1.size() + "");
                factoryScheme.updateById();
            }
        });
        scheme.setStatus(BaseType.SchemeStatus.COMPLETE.getCode());
        return scheme.updateById();
    }

    /**
     * 结束方案
     *
     * @param id 经销商方案id
     * @return
     */
    @Override
    public boolean over(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new OtherExcetion("请选择要结束的方案");
        }
        Scheme scheme = schemeSet.Get(id);
        if (null == scheme) {
            throw new OtherExcetion("不存在的方案");
        }
        if (BaseType.SchemeStatus.UNDERWAY.getCode().equals(scheme.getStatus())) {
            scheme.setStatus(BaseType.SchemeStatus.OVER.getCode());
        } else if (BaseType.SchemeStatus.OVER.getCode().equals(scheme.getStatus())) {
            if (scheme.getValidity() < System.currentTimeMillis()) {
                throw new OtherExcetion("已过期的方案不能重新启用");
            }
            scheme.setStatus(BaseType.SchemeStatus.UNDERWAY.getCode());
        }
        return scheme.updateById();
    }

    /**
     * 批量删除方案里面的商品
     *
     * @param vo
     * @return
     */
    @Override
    public boolean deleteBatch(SchemeDeleteBatchListVO vo) {
        if (StringUtils.isEmpty(vo.getSchemeId())) {
            throw new OtherExcetion("请选择方案");
        }
        if (vo.getList().size() == 0) {
            throw new OtherExcetion("请选择商品");
        }
        Scheme scheme = schemeSet.Get(vo.getSchemeId());
        if (null == scheme) {
            throw new OtherExcetion("不存在的方案");
        }
        if (!BaseType.SchemeStatus.UNDERWAY.getCode().equals(scheme.getStatus())) {
            throw new OtherExcetion("该方案已结束");
        }
        int[] count = {0};
        vo.getList().forEach((a) -> {
            boolean result = false;
            if (BaseType.UserType.SALER.getCode().equals(a.getType())) {
                List<Document> salesGoods = scheme.getSalesGoods();
                for (int i = 0; i < scheme.getSalesGoods().size(); i++) {
                    FactorySchemeSaleGoodDTO dto = BsonUtil.toBean(salesGoods.get(i), FactorySchemeSaleGoodDTO.class);
                    if (!StringUtils.isAnyEmpty(a.getProductId(), dto.getSaleGoods().getProductId()) && a.getProductId().equals(dto.getSaleGoods().getProductId())) {
                        salesGoods.remove(i);
                        result = true;
                    }
                }
                if (result) {
                    scheme.setSalesGoods(salesGoods);
                    if (scheme.updateById()) {
                        count[0]++;
                    }
                }
            } else if (BaseType.UserType.FACTORY.getCode().equals(a.getType())) {
                if (StringUtils.isNotEmpty(a.getFactorySchemeId())) {
                    FactoryScheme factoryScheme = factorySchemeSet.Get(a.getFactorySchemeId());
                    List<Document> factoryProductIds = factoryScheme.getFactoryProductIds();
                    for (int i = 0; i < factoryScheme.getFactoryProductIds().size(); i++) {
                        FactorySchemeProductDTO dto = BsonUtil.toBean(factoryProductIds.get(i), FactorySchemeProductDTO.class);
                        if (null != dto && !StringUtils.isAnyEmpty(a.getProductId(), dto.getProduct().getId()) && a.getProductId().equals(dto.getProduct().getId())) {
                            factoryProductIds.remove(i);
                            result = true;
                        }
                    }
                    if (result) {
                        factoryScheme.setFactoryProductIds(factoryProductIds);
                        if (factoryScheme.updateById()) {
                            count[0]++;
                        }
                    }
                }
            }
        });
        if (count[0] == vo.getList().size()) {
            return true;
        }
        return false;
    }

    /**
     * 生成ppt
     *
     * @param vo
     * @param user 用户对象
     * @return
     */
    @Override
    public String createPPT(PptVO vo, User user) {
        if (StringUtils.isAnyEmpty(vo.getEmail(),
                vo.getMainTitle(),
                vo.getPhone(),
                vo.getPosition(),
                vo.getQq(),
                vo.getSubhead(),
                vo.getUserName(),
                vo.getSchemeId())) {
            throw new OtherExcetion("请完善必填项");
        }
        List<ProductDTO> productDTOS = new ArrayList<>();
        FactorySchemeDTO dto = this.getDetails(vo.getSchemeId(), user, null, "0");
        dto.getProductList().forEach(a -> vo.getProductIds().forEach((b) -> {
            if (a.getProduct().getId().equals(b)) {
                if (StringUtils.isNotEmpty(a.getProduct().getBrandId())) {
                    Brand brand = brandSet.Get(a.getProduct().getBrandId());
                    if (null != brand && StringUtils.isNotEmpty(brand.getName())) {
                        a.getProduct().setBrandName(brand.getName());
                    } else {
                        a.getProduct().setBrandName("无");
                    }
                } else {
                    a.getProduct().setBrandName("无");
                }
                productDTOS.add(a);
            }
        }));
        vo.setProductDTOS(productDTOS);
        String fileName;
        try {
            fileName = PPTUtil.createPPT(vo);
        } catch (Exception e) {
            LOG.error("ppt创建失败");
            throw new OtherExcetion("ppt创建失败");
        }
        return fileName;
    }
}
