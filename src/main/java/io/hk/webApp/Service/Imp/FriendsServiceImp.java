package io.hk.webApp.Service.Imp;

import com.alibaba.fastjson.JSONObject;
import io.framecore.Frame.PageData;
import io.hk.webApp.DataAccess.*;
import io.hk.webApp.Domain.*;
import io.hk.webApp.Service.IFriendsService;
import io.hk.webApp.Tools.BaseType;
import io.hk.webApp.Tools.BsonUtil;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.TablePagePars;
import io.hk.webApp.dto.FactoryFriendsDTO;
import io.hk.webApp.dto.FriendApplyDTO;
import io.hk.webApp.dto.ProductSalerDTO;
import io.hk.webApp.dto.ShareProduceDTO;
import io.hk.webApp.vo.ProductShareVO;
import io.hk.webApp.vo.UpdatePriceVO;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FriendsServiceImp implements IFriendsService {

    @Autowired
    private FriendsSet friendsSet;

    @Autowired
    private BlacklistSet blacklistSet;

    @Autowired
    private UserSet userSet;

    @Autowired
    private SchemeSet schemeSet;

    @Autowired
    private FriendApplySet friendApplySet;

    @Autowired
    private BrandSet brandSet;

    @Autowired
    private ProductSet productSet;

    @Autowired
    private ShareProducesSet shareProducesSet;

    @Autowired
    private MessagesSet messagesSet;

    @Autowired
    private SaleGoodsSet saleGoodsSet;

    /**
     * 查询客户审核列表
     *
     * @param pagePars
     * @return
     */
    @Override
    public Object search(TablePagePars pagePars, String userId) {
        PageData<FriendApply> friendApplyPageData = friendApplySet.searchNo(pagePars.Pars, pagePars.PageSize, pagePars.PageIndex, pagePars.Order, userId, BaseType.Consent.PASS.getCode());
        List<FriendApplyDTO> list = new ArrayList<>();
        friendApplyPageData.rows.forEach((a) -> {
            List<Brand> brandList = brandSet.Where("userId=?", a.getFriendId()).ToList();
            a.setBrand(brandList);
            FriendApplyDTO friendApplyDTO = new FriendApplyDTO();
            friendApplyDTO.setFriendApply(a);
            User user = userSet.Get(a.getUserId());
            if (null != user) {
                if (StringUtils.isEmpty(user.getCompanyName())) {
                    friendApplyDTO.setCompanyName("无");
                } else {
                    friendApplyDTO.setCompanyName(user.getCompanyName());
                }
                if (StringUtils.isEmpty(user.getName())) {
                    friendApplyDTO.setName("无");
                } else {
                    friendApplyDTO.setName(user.getName());
                }
                if (StringUtils.isEmpty(user.getPhone())) {
                    friendApplyDTO.setPhone("无");
                } else {
                    friendApplyDTO.setPhone(user.getPhone());
                }
                if (StringUtils.isAnyEmpty(user.getAddr(), user.getProvince())) {
                    friendApplyDTO.setAddr("无");
                } else {
                    friendApplyDTO.setAddr(user.getProvince() + user.getAddr());
                }
                if (StringUtils.isEmpty(user.getImg())) {
                    friendApplyDTO.setImg("无");
                } else {
                    friendApplyDTO.setImg(user.getImg());
                }
            }
            list.add(friendApplyDTO);
        });
        Map<String, Object> map = new HashMap<>();
        map.put("rows", list);
        map.put("total", friendApplyPageData.total);
        return map;
    }

    /**
     * 审核
     *
     * @return
     */
    @Override
    public boolean check(Friends info, String status, User user) {
        FriendApply friendApply = friendApplySet.Get(info.getId());
        if (null == friendApply || !BaseType.Consent.BASE.getCode().equals(friendApply.getStatus())) {
            throw new OtherExcetion("不可操作");
        }
        //通过就给双方好友关系表加数据
        if (BaseType.Consent.PASS.getCode().equals(status)) {
            Friends friends = new Friends();
            friends.setUserId(friendApply.getUserId());
            friends.setFriendId(friendApply.getFriendId());
            friends.setKernel("2");
            friendsSet.Add(friends);
            friends = new Friends();
            friends.setUserId(friendApply.getFriendId());
            friends.setFriendId(friendApply.getUserId());
            friends.setKernel("2");
            friends.setRemark(StringUtils.isEmpty(info.getRemark()) ? "" : info.getRemark());
            friends.setGroup(StringUtils.isEmpty(info.getGroup()) ? BaseType.FriendGroup.BASE.getCode() : info.getGroup());
            friendsSet.Add(friends);
        }
        friendApply.setStatus(status);
        return friendApply.updateById();
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @Override
    public boolean batchDelete(String[] ids) {
        for (int i = 0; i < ids.length; i++) {
            String id = ids[i];
            //首先删除关系表
            Friends friends = friendsSet.Get(id);
            if (null == friends) {
                continue;
            }
            String userId = friends.getUserId();
            String friendId = friends.getFriendId();
            friendsSet.Delete(friends.getId());
            friends = friendsSet.Where("(userId=?)and(friendId=?)", friendId, userId).First();
            if (null == friends) {
                continue;
            }
            friendsSet.Delete(friends.getId());
            //然后删除申请表
            FriendApply friendApply = friendApplySet.Where("(userId=?)and(friendId=?)and(status=?)", friendId, userId, BaseType.Consent.PASS.getCode()).First();
            if (null == friendApply) {
                friendApply = friendApplySet.Where("(userId=?)and(friendId=?)and(status=?)", userId, friendId, BaseType.Consent.PASS.getCode()).First();
                if (null != friendApply) {
                    friendApplySet.Delete(friendApply.getId());
                }
            } else {
                friendApplySet.Delete(friendApply.getId());
            }
        }
        return true;
    }

    /**
     * 查询审核通过的客户列表
     *
     * @param pagePars
     * @return
     */
    @Override
    public PageData<FactoryFriendsDTO> searchForPass(TablePagePars pagePars, String userId) {
        PageData<Friends> pageData = friendsSet.search(pagePars.Pars, pagePars.PageSize, pagePars.PageIndex, pagePars.Order, userId);
        List<FactoryFriendsDTO> list = new ArrayList<>();
        pageData.rows.forEach((a) -> {
            FactoryFriendsDTO dto = new FactoryFriendsDTO();
            dto.setFriends(a);
            dto.setSchemeNumber(schemeSet.Where("salerId=?", a.getFriendId()).Count() + "");
            dto.setType(a.getGroup());
            dto.setList(brandSet.Where("(userId=?)and(status=?)", a.getFriendId(), BaseType.Consent.PASS.getCode()).ToList());
            User user = userSet.Get(a.getUserId());
            if (null != user) {
                if (StringUtils.isEmpty(user.getCompanyName())) {
                    dto.setCompanyName("无");
                } else {
                    dto.setCompanyName(user.getCompanyName());
                }
                if (StringUtils.isEmpty(user.getName())) {
                    dto.setName("无");
                } else {
                    dto.setName(user.getName());
                }
                if (StringUtils.isEmpty(user.getPhone())) {
                    dto.setTel("无");
                } else {
                    dto.setTel(user.getPhone());
                }
                if (StringUtils.isAnyEmpty(user.getAddr(), user.getProvince())) {
                    dto.setAddr("无");
                } else {
                    dto.setAddr(user.getProvince() + user.getAddr());
                }
                if (StringUtils.isEmpty(user.getImg())) {
                    dto.setImg("无");
                } else {
                    dto.setImg(user.getImg());
                }
            }
            list.add(dto);
        });
        PageData<FactoryFriendsDTO> dtoPageData = new PageData<>();
        dtoPageData.rows = list;
        dtoPageData.total = pageData.total;
        return dtoPageData;
    }

    /**
     * 添加经销商
     *
     * @param friends
     * @return
     */
    @Override
    public boolean addFriend(Friends friends) {
//        if(StringUtils.isAnyEmpty(friends.getCompanyName(), friends.getUserPhone(), friends.getApply())){
//            throw new OtherExcetion("请完善经销商信息");
//        }
//        friends.setStatus(BaseType.Consent.BASE.getCode());
//        friendsSet.Add(friends);
        return true;
    }

    /**
     * 添加黑名单
     *
     * @param blackId
     * @param userId
     * @return
     */
    @Override
    public boolean addBlackList(String blackId, String userId) {
        if (StringUtils.isEmpty(blackId)) {
            throw new OtherExcetion("请选择要拉入黑名单的经销商");
        }
        Blacklist blacklist = new Blacklist();
        blacklist.setUserId(userId);
        blacklist.setBlackId(blackId);
        blacklistSet.Add(blacklist);
        return true;
    }

    /**
     * 查询黑名单列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<User> searchBlackList(String userId) {

        List<Blacklist> blacklist = blacklistSet.Where("userId=?", userId).ToList();
        List<User> users = new ArrayList<>();
        blacklist.forEach((a) -> {
            if (StringUtils.isNotEmpty(a.getBlackId())) {
                User user = userSet.Get(a.getBlackId());
                user.setLoginKey(null);
                user.setPassword(null);
                user.setLastloginTime(null);
                users.add(user);
            }
        });
        return users;
    }

    /**
     * 一方向另一方请求合作
     *
     * @param friendApply
     * @return
     */
    @Override
    public boolean cooperation(FriendApply friendApply, User user, String type) {
        if (StringUtils.isEmpty(friendApply.getApply())) {
            throw new OtherExcetion("请输入申请理由");
        }
        if (StringUtils.isEmpty(user.getCompanyName())) {
            throw new OtherExcetion("完善用户信息后方可操作");
        }
        User user1 = userSet.Get(friendApply.getFriendId());
        if (null == user1) {
            throw new OtherExcetion("不存在的用户");
        }
        if (type.equals(user1.getType())) {
            throw new OtherExcetion("不能添加同角色");
        }
        {
            FriendApply info1 = friendApplySet.Where("(userId=?)and(friendId=?)and(status!=?)", friendApply.getFriendId(), user.getId(), BaseType.Consent.REFUSE.getCode()).OrderByDesc("_ctime").First();
            if (null != info1) {
                throw new OtherExcetion("对方已经先向您发起合作了，去厂商审核列表看看吧！");
            }
        }
        {
            FriendApply info2 = friendApplySet.Where("(userId=?)and(friendId=?)and(status!=?)", user.getId(), friendApply.getFriendId(), BaseType.Consent.REFUSE.getCode()).OrderByDesc("_ctime").First();
            if (null != info2) {
                throw new OtherExcetion("已是合作或在请求中，请勿重复操作");
            }
        }
        friendApply.setUserId(user.getId());
        friendApply.setStatus(BaseType.Consent.BASE.getCode());
        Object id = friendApplySet.Add(friendApply);
        {
            //完成提报后发送消息提醒经销商
            Messages messages = new Messages();
            messages.setTitle(BaseType.Message.SUBMISSION.getCode());
            messages.setStatus("2");
            messages.setTime(System.currentTimeMillis());
            messages.setUserId(user.getId());
            messages.setSource(user.getCompanyName());
            messages.setReceiveId(friendApply.getFriendId());
            messages.setContent(user.getCompanyName() + "：向你发起了好友申请");
            messages.setData(id.toString());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", "0");
            jsonObject.put("msg", "发起好友请求");
            messagesSet.Add(messages);
        }
        return true;
    }

    /**
     * 设置供应商为核心
     *
     * @param id
     * @return
     */
    @Override
    public boolean kernel(String id, String userId) {
        if (StringUtils.isEmpty(id)) {
            throw new OtherExcetion("请选择要设为核心的厂商");
        }
        Friends friends = friendsSet.Get(id);
        if (null == friends || !userId.equals(friends.getUserId())) {
            throw new OtherExcetion("不可操作");
        }
        if ("2".equals(friends.getKernel())) {
            friends.setKernel("1");
        } else {
            friends.setKernel("2");
        }
        return friendsSet.Update(friends.getId(), friends) > 0;
    }

    /**
     * 发送报价
     *
     * @param vo
     * @return
     */
    @Override
    public boolean share(ProductShareVO vo, User user) {
        if (StringUtils.isEmpty(user.getCompanyName())) {
            throw new OtherExcetion("完善个人资料方可操作");
        }
        if (vo.getList().size() == 0) {
            throw new OtherExcetion("请选择要分享的商品");
        }
        ShareProduces shareProduces = new ShareProduces();
        shareProduces.setFactoryId(user.getId());
        shareProduces.setSalerId(vo.getSalerId());
        shareProduces.setStatus("1");
        shareProduces.setFactoryName(user.getCompanyName());
        List<Document> list = new ArrayList<>();
        vo.getList().forEach((a) -> {
            Product product = productSet.Get(a.getProductId());
            if (null == product) {
                throw new OtherExcetion("有不存在的商品");
            }
            ShareProduceDTO dto = new ShareProduceDTO();
            dto.setProductId(a.getProductId());
            dto.setStatus("2");
            dto.setNewMoney(a.getNewMoney());
            dto.setImgs(product.getImage1());
            list.add(BsonUtil.toDocument(dto));
        });
        shareProduces.setProducts(list);
        Object id = shareProducesSet.Add(shareProduces);
        {
            //完成提报后发送消息提醒经销商
            Messages messages = new Messages();
            messages.setTitle(BaseType.Message.SUBMISSION.getCode());
            messages.setStatus("2");
            messages.setTime(System.currentTimeMillis());
            messages.setUserId(user.getId());
            messages.setSource(user.getCompanyName());
            messages.setReceiveId(vo.getSalerId());
            messages.setContent(user.getCompanyName() + "：向你发送了报价");
            messages.setData(id.toString());
            messagesSet.Add(messages);
        }
        return true;
    }

    /**
     * 展示发送报价页的商品清单
     *
     * @param salerId
     * @param user
     * @return
     */
    @Override
    public Object searchProducts(String salerId, User user, TablePagePars pagePars) {
        List<Product> products = productSet.Where("factoryId=?", user.getId()).ToList();
        List<SaleGoods> saleGoods = saleGoodsSet.Where("salerId=?", salerId).ToList();
        List<ShareProduces> shareProduces = shareProducesSet.Where("salerId=?",salerId).OrderByDesc("_ctime").ToList();
        List<ProductSalerDTO> list = new ArrayList<>();
        products.forEach((a) -> {
            ProductSalerDTO dto = new ProductSalerDTO();
            dto.setProduct(a);
            //1是已接受 2是未接受 3是对方还没有这个商品 4是已拒绝  ShareProductStatus
            if (saleGoods.size() > 0) {
                saleGoods.forEach((b) -> {
//                    if (a.getId().equals(b.getProductId())) {
//                        dto.setStatus("2");
//                        dto.setPrice(b.getPrice());
//                    } else {
//                        dto.setStatus("1");
//                    }
                    shareProduces.forEach((c)->{

                    });

                });
            } else {
                dto.setStatus("3");
            }
            list.add(dto);
        });
        PageData<ProductSalerDTO> productSalerDTOPageData = new PageData<>();
        productSalerDTOPageData.total = list.size();
        productSalerDTOPageData.rows = list.subList(pagePars.PageSize, pagePars.PageIndex += pagePars.PageSize);
        return productSalerDTOPageData;
    }

    /**
     * 修改已发送报价的商品的价格
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public boolean updatePrice(UpdatePriceVO vo, User user) {
        if (StringUtils.isAnyEmpty(vo.getSalerId(), vo.getProductId())) {
            throw new OtherExcetion("请完善必填项");
        }
        if (vo.getNewMoney() < 0) {
            throw new OtherExcetion("价格有误");
        }
        SaleGoods saleGoods = saleGoodsSet.Where("(productId=?)and(salerId=?)", vo.getProductId(), vo.getSalerId()).First();
        Product product = productSet.Get(saleGoods.getProductId());
        if (null == product || !user.getId().equals(product.getFactoryId())) {
            throw new OtherExcetion("非法操作");
        }
        saleGoods.setPrice(vo.getNewMoney());
        return saleGoods.updateById();
    }
}
