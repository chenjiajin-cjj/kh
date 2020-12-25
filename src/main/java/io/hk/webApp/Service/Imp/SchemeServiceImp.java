package io.hk.webApp.Service.Imp;

import com.alibaba.fastjson.JSONObject;
import io.framecore.Frame.PageData;
import io.hk.webApp.DataAccess.*;
import io.hk.webApp.Domain.*;
import io.hk.webApp.Service.IFactorySchemeService;
import io.hk.webApp.Service.ISchemeService;
import io.hk.webApp.Tools.*;
import io.hk.webApp.dto.*;
import io.hk.webApp.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


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

    private final Logger LOG = LoggerFactory.getLogger(SchemeServiceImp.class);

    /**
     * 新建方案
     *
     * @param scheme
     * @return
     */
    @Override
    public boolean add(Scheme scheme) {
        if (StringUtils.isAnyEmpty(scheme.getName(), scheme.getSynopsis())) {
            throw new OtherExcetion("请完善必填项");
        }
        if (null == scheme.getValidity()) {
            throw new OtherExcetion("请输入正确的有效期");
        }
        scheme.setStatus(BaseType.SchemeStatus.UNDERWAY.getCode());
        schemeSet.Add(scheme);
        return true;
    }

    /**
     * 查询经销商自己的方案列表
     *
     * @param salerId
     * @param pagePars
     * @return
     */
    @Override
    public PageData<Scheme> search(String salerId, TablePagePars pagePars) {
        PageData<Scheme> pageData = schemeSet.search(pagePars.PageSize, pagePars.PageIndex, pagePars.Pars, salerId);
        return pageData;
    }

    /**
     * 添加商品到方案
     *
     * @param vo
     * @param userId
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
        // TODO 问题集锦提到 放到方案中的商品不会因为供应商的更改而更改
        for (String saleGoodId : vo.getSaleGoodIds()) {
            if (StringUtils.isNotEmpty(saleGoodId)) {
                SaleGoods saleGoods = saleGoodsSet.Get(saleGoodId);
                if (null != saleGoods && userId.equals(saleGoods.getSalerId())) {
                    Product product = productSet.Get(saleGoods.getProductId());
                    saleGoods.setProduct(BsonUtil.toDocument(product));
                    FactorySchemeSaleGoodDTO dto = new FactorySchemeSaleGoodDTO();
                    dto.setSaleGoods(saleGoods);
                    dto.setSaleGoodId(saleGoods.getId());
                    list.add(BsonUtil.toDocument(dto));
                }
            }
        }
        scheme.setSalesGoods(list);
        return schemeSet.Update(scheme.getId(), scheme) > 0;
    }

    /**
     * 重命名方案
     *
     * @param scheme
     * @param id
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
        return scheme1.updateById();
    }

    /**
     * 删除方案
     *
     * @param id
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
     * @param userId
     * @param pagePars
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
            if (null != scheme.getFactoryIds() && scheme.getFactoryIds().size() > 0) {
                scheme.getFactoryIds().forEach(b -> dto.setFlag(a.getFriendId().equals(b)));
            }
            list.add(dto);
        });
        return list;
    }

    /**
     * 邀请厂商报价
     *
     * @param vo
     * @return
     */
    @Override
    public boolean quotation(SchemeQuotationVO vo, User user) {
        FactoryScheme factoryScheme = factorySchemeSet.Where("(factoryId=?)and(schemeId=?)and(salerId=?)", vo.getFactoryId(), vo.getSchemeId(), user.getId()).First();
        if (null != factoryScheme) {
            throw new OtherExcetion("请勿重复分享此方案");
        }
        if (StringUtils.isEmpty(user.getCompanyName())) {
            throw new OtherExcetion("完善个人资料后方可操作");
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
        if (null == scheme.getSalesGoods() || scheme.getSalesGoods().size() == 0) {
            throw new OtherExcetion("请添加至少一件商品才能邀请报价");
        }
        factoryScheme.setFactoryId(vo.getFactoryId());
        factoryScheme.setSalerId(user.getId());
        factoryScheme.setSchemeId(scheme.getId());
        factoryScheme.setSalerName(user.getCompanyName());
        factoryScheme.setValidity(scheme.getValidity());
        factoryScheme.setUpdateTime(System.currentTimeMillis());
        factoryScheme.setStatus(BaseType.Status.NO.getCode());
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
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", "0");
            jsonObject.put("msg", "邀请报价");
            messagesSet.Add(messages);
        }
        return scheme.updateById();
    }

    /**
     * 查询方案详情
     *
     * @param schemeId
     * @return
     */
    @Override
    public Object getDetails(String schemeId) {
        if (StringUtils.isEmpty(schemeId)) {
            throw new OtherExcetion("请选择方案");
        }
        Scheme scheme = schemeSet.Get(schemeId);
        if (null == scheme) {
            throw new OtherExcetion("不存在的方案");
        }
//        if (!BaseType.SchemeStatus.UNDERWAY.getCode().equals(scheme.getStatus())) {
//            throw new OtherExcetion("此方案已结束");
//        }
        List<FactoryScheme> factorySchemes = factorySchemeSet.Where("schemeId=?", schemeId).ToList();
        FactorySchemeDTO factorySchemeDTO = new FactorySchemeDTO();
//        factorySchemeDTO.setFactorySchemeId(schemeId);
        factorySchemeDTO.setCreateTime(scheme.getValidity());
        factorySchemeDTO.setDetails(scheme.getSynopsis());
        factorySchemeDTO.setName(scheme.getName());
        List<ProductDTO> productList = new ArrayList<>();
        scheme.getSalesGoods().forEach((a) -> {
            FactorySchemeSaleGoodDTO dto = BsonUtil.toBean(a, FactorySchemeSaleGoodDTO.class);
            ProductDTO productDTO = new ProductDTO();
            Product product = productSet.Get(dto.getSaleGoods().getProductId());
            productDTO.setProduct(product);
            productDTO.setSalerNewMoney(dto.getSalerNewMoney());
            productList.add(productDTO);
            factorySchemeDTO.setNumbers(factorySchemeDTO.getNumbers() + 1);
        });
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
                productDTO.setFactoryNewMoney(dto.getFactoryNewMoney());
                productDTO.setSalerNewMoney(dto.getSalerNewMoney());
                productDTO.setProduct(product);
                productDTO.setFactorySchemeId(a.getId());
                productList.add(productDTO);
            });
        });
        factorySchemeDTO.setProductList(productList);
        return factorySchemeDTO;
    }

    /**
     * 查询供应商分享过来的方案列表
     *
     * @param id
     * @param pagePars
     * @return
     */
    @Override
    public Object searchFactorySchemes(String id, TablePagePars pagePars) {
        List<FactoryScheme> factorySchemes = factorySchemeSet.Where("(salerId=?)and(status=?)", id, BaseType.Status.YES.getCode()).Limit(pagePars.PageSize, pagePars.PageIndex).OrderByDesc("_uptime").ToList();
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
        return list;
    }

    /**
     * 查询供应商提报的方案详情
     *
     * @param factorySchemeId
     * @return
     */
    @Override
    public Object getFactorySchemeDetails(String factorySchemeId) {
        return iFactorySchemeService.getDetails(factorySchemeId);
    }

    /**
     * 删除商品提报管理的方案
     *
     * @param factorySchemeId
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
        if (vo.getSalerNewMoney() <= 0) {
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
                    dto.setSalerNewMoney(vo.getSalerNewMoney());
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
                    dto.setSalerNewMoney(vo.getSalerNewMoney());
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
        if (vo.getProductIds().length == 0) {
            throw new OtherExcetion("请选择商品");
        }
        Scheme scheme = schemeSet.Get(vo.getId());
        if (null == scheme) {
            throw new OtherExcetion("不存在的方案");
        }
        if (!BaseType.SchemeStatus.UNDERWAY.getCode().equals(scheme.getStatus())) {
            throw new OtherExcetion("该方案已结束");
        }
        List<Map<String,Object>> list = new ArrayList<>();
        Set<String> set = new HashSet<>();
        for (int i = 0; i < vo.getProductIds().length; i++) {
            Product product = productSet.Get(vo.getProductIds()[i]);
            if (null == product || StringUtils.isEmpty(product.getFactoryId())) {
                continue;
            }
            set.add(product.getFactoryId());
        }
        for (int i = 0; i < vo.getProductIds().length; i++) {
            Product product = productSet.Get(vo.getProductIds()[i]);
            for (String factoryId : set) {
                Map<String,Object> map = new HashMap<>();
                List<String> list1 = new ArrayList<>();
                map.put("userId",factoryId);
                if(factoryId.equals(product.getFactoryId())){
                    list1.add(vo.getProductIds()[i]);
                }
                map.put("list",list1);
                list.add(map);
            }
        }
        list.forEach((a)->{
            {
                Messages messages = new Messages();
                messages.setTitle(BaseType.Message.SUBMISSION.getCode());
                messages.setStatus("2");
                messages.setTime(System.currentTimeMillis());
                messages.setUserId(user.getId());
                messages.setSource(user.getCompanyName());
                messages.setReceiveId(a.get("userId").toString());
                messages.setContent(user.getCompanyName() + "邀请您提报商品");
                messages.setData(a.get("list"));
                messagesSet.Add(messages);
            }
        });
        scheme.setStatus(BaseType.SchemeStatus.COMPLETE.getCode());
        return scheme.updateById();
    }

}
