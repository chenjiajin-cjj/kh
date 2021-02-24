package io.hk.webApp.Service;

import io.framecore.Frame.PageData;
import io.hk.webApp.Domain.FriendApply;
import io.hk.webApp.Domain.Friends;
import io.hk.webApp.Domain.User;
import io.hk.webApp.Tools.TablePagePars;
import io.hk.webApp.dto.FactoryFriendsDTO;
import io.hk.webApp.vo.FriendsQuotesVO;
import io.hk.webApp.vo.ProductShareVO;
import io.hk.webApp.vo.TransferVO;
import io.hk.webApp.vo.UpdatePriceVO;

import java.util.List;

public interface IFriendsService {

    /**
     * 查询客户审核列表
     * @param pagePars
     * @return
     */
    Object search(TablePagePars pagePars, String userId);

    /**
     * 审核
     * @return
     */
    boolean check(Friends friends,String status,User user);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    boolean batchDelete(String[] ids);

    /**
     * 查询审核通过的客户列表
     * @param pagePars
     * @param userId
     * @return
     */
    PageData<FactoryFriendsDTO> searchForPass(TablePagePars pagePars, String userId);

    /**
     * 添加经销商
     * @param friends
     * @return
     */
    boolean addFriend(Friends friends);

    /**
     * 添加黑名单
     * @param blackId
     * @param userId
     * @return
     */
    boolean addBlackList(String blackId, String userId);

    /**
     * 查询黑名单列表
     * @param id
     * @return
     */
    List<User> searchBlackList(String id);


    /**
     * 一方向另一方请求合作
     */
    boolean cooperation(FriendApply friendApply, User user,String type);

    /**
     * 设置供应商为核心
     * @param id
     * @return
     */
    boolean kernel(String id,String userId);

    /**
     * 发送报价
     * @param vo
     * @return
     */
    boolean share(ProductShareVO vo, User user);

    /**
     * 查询发送报价页的商品清单
     * @param salerId
     * @param user
     * @return
     */
    Object searchProducts(String salerId, User user,TablePagePars pagePars);

    /**
     * 修改已发送报价的商品的价格
     * @param vo
     * @param user
     * @return
     */
    boolean updatePrice(UpdatePriceVO vo, User user);

    /**
     * 批量转移子账号
     * @param vo
     * @param user
     * @return
     */
    boolean transfer(TransferVO vo, User user);

    /**
     * 索要报价
     * @param vo
     * @param user
     * @return
     */
    boolean quotes(FriendsQuotesVO vo, User user);

    /**
     * 查询客户审核的条数
     * @param user
     * @return
     */
    Object getAllCount(User user);

    /**
     * 经销商解除合作的同时删除供应商的所有商品
     * @param id
     * @param saler
     * @return
     */
    boolean salerDelete(String id, User saler);
}
