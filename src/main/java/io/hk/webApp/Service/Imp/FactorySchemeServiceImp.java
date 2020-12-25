package io.hk.webApp.Service.Imp;

import com.alibaba.fastjson.JSONObject;
import io.framecore.Frame.PageData;
import io.hk.webApp.DataAccess.FactorySchemeSet;
import io.hk.webApp.DataAccess.MessagesSet;
import io.hk.webApp.DataAccess.ProductSet;
import io.hk.webApp.DataAccess.SchemeSet;
import io.hk.webApp.Domain.*;
import io.hk.webApp.Service.IFactorySchemeService;
import io.hk.webApp.Tools.BaseType;
import io.hk.webApp.Tools.BsonUtil;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.TablePagePars;
import io.hk.webApp.dto.FactorySchemeDTO;
import io.hk.webApp.dto.FactorySchemeProductDTO;
import io.hk.webApp.dto.ProductDTO;
import io.hk.webApp.vo.FactorySchemeSubmissionVO;
import io.hk.webApp.vo.FactorySchemeUpdateMoneyVO;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 展示方案列表
     *
     * @param userId
     * @param pagePars
     * @return
     */
    @Override
    public PageData<FactoryScheme> search(String userId, TablePagePars pagePars) {
        PageData<FactoryScheme> pageData = factorySchemeSet.search(pagePars.Pars, pagePars.PageSize, pagePars.PageIndex, userId);
        return pageData;
    }

    /**
     * 删除方案
     *
     * @param id
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
        if (null == scheme) {
            throw new OtherExcetion("不存在的方案");
        }
        List<String> factoryIds = scheme.getFactoryIds();
        for (int i = 0; i < factoryIds.size(); i++) {
            if (userId.equals(factoryIds.get(i))) {
                factoryIds.remove(i);
            }
        }
        scheme.setFactoryIds(factoryIds);
        return factoryScheme.deleteById() && scheme.updateById();
    }

    /**
     * 提报
     *
     * @param vo
     * @return
     */
    @Override
    public boolean submission(FactorySchemeSubmissionVO vo, User user) {
        if (StringUtils.isEmpty(user.getCompanyName())) {
            throw new OtherExcetion("请先完成基本信息方可操作");
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
            throw new OtherExcetion("经销商已删除方案");
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
        List<Document> finalList = list;
        vo.getProductIds().forEach((a) -> {
            Product product = productSet.Get(a.getProductIds());
            if (null != product) {
                a.setProduct(product);
                finalList.add(BsonUtil.toDocument(a));
            }
        });
        factoryScheme.setFactoryProductIds(finalList);
        factoryScheme.setStatus(BaseType.Status.YES.getCode());

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
            messages.setData(factoryScheme.getId());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", "0");
            jsonObject.put("msg", "供应商向经销商进行提报");
            messagesSet.Add(messages);
        }

        return factoryScheme.updateById();
    }

    /**
     * 查询方案详情
     *
     * @param factorySchemeId
     * @return
     */
    @Override
    public Object getDetails(String factorySchemeId) {
        if (StringUtils.isEmpty(factorySchemeId)) {
            throw new OtherExcetion("请选择方案");
        }
        FactoryScheme factoryScheme = factorySchemeSet.Get(factorySchemeId);
        if (null == factoryScheme) {
            throw new OtherExcetion("不存在的方案");
        }
        Scheme scheme = schemeSet.Get(factoryScheme.getSchemeId());
        if (null == scheme) {
            throw new OtherExcetion("经销商已删除方案");
        }
//        if (!BaseType.SchemeStatus.UNDERWAY.getCode().equals(scheme.getStatus())) {
//            throw new OtherExcetion("此方案已结束");
//        }
        FactorySchemeDTO factorySchemeDTO = new FactorySchemeDTO();
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
            productDTO.setSalerNewMoney(dto.getSalerNewMoney());
            productDTO.setFactoryNewMoney(dto.getFactoryNewMoney());
            productDTO.setProduct(product);
            productList.add(productDTO);
        });
        factorySchemeDTO.setProductList(productList);
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
        if (vo.getFactoryNewMoney() <= 0) {
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
                dto.setFactoryNewMoney(vo.getFactoryNewMoney());
                list.set(i, BsonUtil.toDocument(dto));
            }
        }

        factoryScheme.setFactoryProductIds(list);
        return factoryScheme.updateById();
    }
}
