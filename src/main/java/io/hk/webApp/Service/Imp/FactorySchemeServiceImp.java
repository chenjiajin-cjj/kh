package io.hk.webApp.Service.Imp;

import io.framecore.Frame.PageData;
import io.hk.webApp.DataAccess.*;
import io.hk.webApp.Domain.*;
import io.hk.webApp.Service.IFactorySchemeService;
import io.hk.webApp.Tools.BaseType;
import io.hk.webApp.Tools.BsonUtil;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.TablePagePars;
import io.hk.webApp.dto.FactorySchemeDTO;
import io.hk.webApp.dto.FactorySchemeProductDTO;
import io.hk.webApp.dto.FactorySchemeSaleGoodDTO;
import io.hk.webApp.dto.ProductDTO;
import io.hk.webApp.vo.FactorySchemeSubmissionVO;
import io.hk.webApp.vo.FactorySchemeUpdateMoneyVO;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 供应商方案
 */
@Service
public class FactorySchemeServiceImp implements IFactorySchemeService {

    @Autowired
    private FactorySchemeSet factorySchemeSet;

    @Autowired
    private SchemeSet schemeSet;

    @Autowired
    private ProductSet productSet;

    @Autowired
    private MessagesSet messagesSet;

    @Autowired
    private SaleGoodsSet saleGoodsSet;

    /**
     * 展示方案列表
     *
     * @param userId   用户id
     * @param pagePars 分页参数对象
     * @return
     */
    @Override
    public PageData<FactoryScheme> search(String userId, TablePagePars pagePars) {
        PageData<FactoryScheme> pageData = factorySchemeSet.search(pagePars.Pars, pagePars.PageSize, pagePars.PageIndex, userId);
        pageData.rows.forEach((a) -> {
            Scheme scheme = schemeSet.Get(a.getSchemeId());
            if (null == scheme) {
                a.setStatus("10");
            }
        });
        return pageData;
    }

    /**
     * 删除方案
     *
     * @param id     方案id
     * @param userId 用户id
     * @return
     */
    @Override
    public boolean delete(String id, String userId) {
        if (StringUtils.isEmpty(id)) {
            throw new OtherExcetion("请选择要删除的方案");
        }
        FactoryScheme factoryScheme = factorySchemeSet.Get(id);
        if (null == factoryScheme) {
            throw new OtherExcetion("不存在的方案");
        }
        Scheme scheme = schemeSet.Get(factoryScheme.getSchemeId());
        if (null != scheme) {
            List<String> factoryIds = scheme.getFactoryIds();
            for (int i = 0; i < factoryIds.size(); i++) {
                if (userId.equals(factoryIds.get(i))) {
                    factoryIds.remove(i);
                }
            }
            scheme.setFactoryIds(factoryIds);
            scheme.updateById();
        }
        return factoryScheme.deleteById();
    }

    /**
     * 提报
     *
     * @param vo
     * @param user 用户对象
     * @return
     */
    @Override
    public boolean submission(FactorySchemeSubmissionVO vo, User user) {
        if (StringUtils.isEmpty(user.getCompanyName())) {
            throw new OtherExcetion(-10, "请先完成基本信息方可操作");
        }
        if (vo.getProductIds().size() == 0) {
            throw new OtherExcetion("至少提报一件商品");
        }
        if (StringUtils.isEmpty(vo.getFactorySchemeId())) {
            throw new OtherExcetion("请选择要提报的方案");
        }
        FactoryScheme factoryScheme = factorySchemeSet.Get(vo.getFactorySchemeId());
        if (null == factoryScheme) {
            throw new OtherExcetion("不存在的方案");
        }
        if (!user.getId().equals(factoryScheme.getFactoryId())) {
            throw new OtherExcetion("只能提报自己的方案");
        }
        Scheme scheme = schemeSet.Get(factoryScheme.getSchemeId());
        if (null == scheme) {
            throw new OtherExcetion(-100, "经销商已删除方案");
        }
        if (!BaseType.SchemeStatus.UNDERWAY.getCode().equals(scheme.getStatus())) {
            throw new OtherExcetion("此方案已结束");
        }
        List<Document> list = null;
        try {
            list = factoryScheme.getFactoryProductIds();
        } catch (Exception e) {
            list = new ArrayList<>();
        }
        if (null == list || list.size() == 0) {
            list = new ArrayList<>();
        }
        List<Document> saleSchemeGoods = new ArrayList<>(scheme.getSalesGoods());
        long nowSize = saleSchemeGoods.size();
        //如果供应商添加的商品已存在于经销商方案里，则删除经销商方案的对应商品
        for (int i = 0; i < saleSchemeGoods.size(); i++) {
            FactorySchemeSaleGoodDTO dto = BsonUtil.toBean(saleSchemeGoods.get(i), FactorySchemeSaleGoodDTO.class);
            SaleGoods saleGoods = saleGoodsSet.Get(dto.getSaleGoodId());
            if (null == saleGoods) {
                continue;
            }
            for (int j = 0; j < vo.getProductIds().size(); j++) {
                if (vo.getProductIds().get(j).getProductIds().equals(saleGoods.getProductId())) {
                    scheme.getSalesGoods().remove(saleSchemeGoods.get(i));
                    break;
                }
            }
        }
        if (scheme.getSalesGoods().size() != nowSize) {
            scheme.updateById();
        }
        //供应商不应重复添加商品到供应商方案
        List<Document> finalList = list;
        List<FactorySchemeProductDTO> productIds = new ArrayList<>(vo.getProductIds());
        for (int i = 0; i < list.size(); i++) {
            FactorySchemeProductDTO dto = BsonUtil.toBean(list.get(i), FactorySchemeProductDTO.class);
            for (int j = 0; j < productIds.size(); j++) {
                if (dto.getProductIds().equals(productIds.get(j).getProductIds())) {
                    vo.getProductIds().remove(productIds.get(j));
                }
            }
        }
        List<String> imgs = new ArrayList<>();
        vo.getProductIds().forEach((a) -> {
            Product product = productSet.Get(a.getProductIds());
            if (null != product) {
                a.setProduct(product);
                if (StringUtils.isNotEmpty(product.getImage1())) {
                    imgs.add(product.getImage1());
                }
                finalList.add(BsonUtil.toDocument(a));
            }
        });
        factoryScheme.setFactoryProductIds(finalList);
        factoryScheme.setStatus(BaseType.Status.YES.getCode());
        factoryScheme.setNumber(finalList.size() + "");
        {
            //完成提报后发送消息提醒经销商
            Messages messages = new Messages();
            messages.setTitle(BaseType.Message.SUBMISSION.getCode());
            messages.setStatus("2");
            messages.setTime(System.currentTimeMillis());
            messages.setUserId(user.getId());
            messages.setSource(user.getCompanyName());
            messages.setReceiveId(scheme.getSalerId());
            messages.setContent(user.getCompanyName() + "：提报了方案：" + scheme.getName());
            messages.setImg(imgs);
            messages.setData(factoryScheme.getId());
            messagesSet.Add(messages);
        }
        return factoryScheme.updateById();
    }

    /**
     * 查询方案详情
     *
     * @param factorySchemeId 供应商方案id
     * @param pagePars        分页参数对象
     * @return
     */
    @Override
    public Object getDetails(String factorySchemeId, TablePagePars pagePars) {
        if (StringUtils.isEmpty(factorySchemeId)) {
            throw new OtherExcetion("请选择方案");
        }
        FactoryScheme factoryScheme = factorySchemeSet.Get(factorySchemeId);
        if (null == factoryScheme) {
            throw new OtherExcetion("不存在的方案");
        }
        Scheme scheme = schemeSet.Get(factoryScheme.getSchemeId());
        if (null == scheme) {
            throw new OtherExcetion(-100, "经销商已删除方案");
        }
//        if (!BaseType.SchemeStatus.UNDERWAY.getCode().equals(scheme.getStatus())) {
//            throw new OtherExcetion("此方案已结束");
//        }
        FactorySchemeDTO factorySchemeDTO = new FactorySchemeDTO();
        factorySchemeDTO.setSchemeId(scheme.getId());
        factorySchemeDTO.setFactorySchemeId(factoryScheme.getId());
        factorySchemeDTO.setCreateTime(scheme.getValidity());
        factorySchemeDTO.setDetails(scheme.getSynopsis());
        factorySchemeDTO.setName(scheme.getName());
        if (null == factoryScheme.getFactoryProductIds() || factoryScheme.getFactoryProductIds().size() == 0) {
            factoryScheme.setFactoryProductIds(new ArrayList<>());
        }
        factorySchemeDTO.setNumbers(factoryScheme.getFactoryProductIds().size());
        List<ProductDTO> productList = new ArrayList<>();
        factoryScheme.getFactoryProductIds().forEach((a) -> {
            FactorySchemeProductDTO dto = BsonUtil.toBean(a, FactorySchemeProductDTO.class);
            Product product = dto.getProduct(); //拿实体
//            Product product = productSet.Get(dto.getProductIds());  //动态获取
            ProductDTO productDTO = new ProductDTO();
            productDTO.setFactorySchemeId(factorySchemeId);
            productDTO.setSalerNewMoneyTax(dto.getSalerNewMoneyTax());
            productDTO.setSalerNewMoneyTaxNo(dto.getSalerNewMoneyTaxNo());
            productDTO.setFactoryNewMoneyTax(dto.getFactoryNewMoneyTax());
            productDTO.setFactoryNewMoneyTaxNo(dto.getFactoryNewMoneyTaxNo());
            productDTO.setProduct(product);
            productDTO.setType(BaseType.UserType.FACTORY.getCode());
            productList.add(productDTO);
        });
        if (productList.size() < pagePars.PageIndex) {
            pagePars.PageIndex = productList.size();
        }
        factorySchemeDTO.setTotal(productList.size());
        factorySchemeDTO.setProductList(productList.subList(pagePars.PageSize, pagePars.PageIndex += pagePars.PageSize));
        return factorySchemeDTO;
    }

    /**
     * 修改商品价格
     *
     * @param vo
     * @return
     */
    @Override
    public boolean updateMoney(FactorySchemeUpdateMoneyVO vo) {
        if (StringUtils.isAnyEmpty(vo.getFactorySchemeId(), vo.getProductIds())) {
            throw new OtherExcetion("请完善必填项");
        }
        if (vo.getFactoryNewMoneyTax() <= 0 || vo.getFactoryNewMoneyTaxNo() <= 0) {
            throw new OtherExcetion("请输入正确的价格");
        }
        FactoryScheme factoryScheme = factorySchemeSet.Get(vo.getFactorySchemeId());
        if (null == factoryScheme) {
            throw new OtherExcetion("不存在的方案");
        }
        Scheme scheme = schemeSet.Get(factoryScheme.getSchemeId());
        if (null == scheme) {
            throw new OtherExcetion("不存在的方案");
        }
        if (!BaseType.SchemeStatus.UNDERWAY.getCode().equals(scheme.getStatus())) {
            throw new OtherExcetion("此方案已结束,不能修改");
        }
        List<Document> list = factoryScheme.getFactoryProductIds();
        if (null == list || list.size() == 0) {
            throw new OtherExcetion("没有可供修改的商品");
        }
        for (int i = 0; i < list.size(); i++) {
            FactorySchemeProductDTO dto = BsonUtil.toBean(list.get(i), FactorySchemeProductDTO.class);
            if (dto.getProductIds().equals(vo.getProductIds())) {
                dto.setFactoryNewMoneyTax(vo.getFactoryNewMoneyTax());
                dto.setFactoryNewMoneyTaxNo(vo.getFactoryNewMoneyTaxNo());
                list.set(i, BsonUtil.toDocument(dto));
            }
        }
        factoryScheme.setFactoryProductIds(list);
        return factoryScheme.updateById();
    }
}
