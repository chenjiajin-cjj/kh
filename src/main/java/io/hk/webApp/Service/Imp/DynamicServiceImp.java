package io.hk.webApp.Service.Imp;

import io.framecore.Frame.PageData;
import io.hk.webApp.DataAccess.*;
import io.hk.webApp.Domain.*;
import io.hk.webApp.Service.IDynamicService;
import io.hk.webApp.Tools.BaseType;
import io.hk.webApp.Tools.OtherExcetion;
import io.hk.webApp.Tools.TablePagePars;
import io.hk.webApp.dto.DynamicDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 动态
 */
@Service
public class DynamicServiceImp implements IDynamicService {

    @Autowired
    private DynamicSet dynamicSet;

    @Autowired
    private FriendsSet friendsSet;

    @Autowired
    private UserSet userSet;

    @Autowired
    private AuthSet authSet;

    @Autowired
    private FriendApplySet friendApplySet;

    /**
     * 供应商发布动态
     *
     * @param dynamic 动态对象
     * @return
     */
    @Override
    public boolean add(Dynamic dynamic) {
        dynamicSet.Add(dynamic);
        return true;
    }

    /**
     * 查询供应商自己的动态集合
     *
     * @param pagePars 分页参数对象
     * @param user     用户对象
     * @return
     */
    @Override
    public PageData<DynamicDTO> search(TablePagePars pagePars, User user) {
        List<Dynamic> list = dynamicSet.Where("factoryId=?", user.getId()).Limit(pagePars.PageSize, pagePars.PageIndex).OrderByDesc("_ctime").ToList();
        long count = dynamicSet.Where("factoryId=?", user.getId()).Count();
        List<DynamicDTO> dynamicDTOS = new ArrayList<>();
        list.forEach((a) -> {
            DynamicDTO dto = new DynamicDTO();
            dto.setDynamic(a);
            Date date = (Date) a.get("_ctime");
            dto.setTime(date.getTime());
            dto.setName(StringUtils.isEmpty(user.getName()) ? "无" : user.getName());
            dynamicDTOS.add(dto);
        });
        PageData<DynamicDTO> pageData = new PageData<>();
        pageData.total = count;
        pageData.rows = dynamicDTOS;
        return pageData;
    }

    /**
     * 查询所有动态合作或者未合作
     *
     * @param type     0查全部 1查合作 其余查不合作
     * @param pagePars 分页参数对象
     * @param salerId  经销商id
     * @return
     */
    @Override
    public PageData<DynamicDTO> search(TablePagePars pagePars, String type, String salerId) {
        PageData<DynamicDTO> pageData = new PageData<>();
        List<Dynamic> list;
        List<DynamicDTO> dynamicDTOS = new ArrayList<>();
        long[] count = new long[1];
        //查全部
        if (null == type || "0".equals(type)) {
            list = dynamicSet.Limit(pagePars.PageSize, pagePars.PageIndex).OrderByDesc("_ctime").ToList();
            count[0] = dynamicSet.Count();
            list.forEach((a) -> {
                DynamicDTO dynamicDTO = new DynamicDTO();
                dynamicDTO.setDynamic(a);
                FriendApply info2 = friendApplySet.Where("(userId=?)and(friendId=?)and(status!=?)", salerId, a.getFactoryId(), BaseType.Consent.BASE.getCode()).First();
                if (null != info2) {
                    dynamicDTO.setCooperation(info2.getStatus());
                } else {
                    info2 = friendApplySet.Where("(userId=?)and(friendId=?)and(status!=?)", salerId, a.getFactoryId(), BaseType.Consent.PASS.getCode()).First();
                    if (null != info2) {
                        dynamicDTO.setCooperation(info2.getStatus());
                    } else {
                        dynamicDTO.setCooperation("3");
                    }
                }
                User user = userSet.Get(a.getFactoryId());
                if (null == user || StringUtils.isEmpty(user.getImg())) {
                    dynamicDTO.setUserImg("无");
                } else {
                    dynamicDTO.setUserImg(user.getImg());
                }
                if (null == user || StringUtils.isEmpty(user.getName())) {
                    dynamicDTO.setName("无");
                } else {
                    dynamicDTO.setName(user.getName());
                }
                Date date = (Date) a.get("_ctime");
                dynamicDTO.setTime(date.getTime());
                dynamicDTOS.add(dynamicDTO);
            });
        }
        //查合作
        else if ("1".equals(type)) {
            List<Friends> friends = friendsSet.Where("userId=?", salerId).Limit(pagePars.PageSize, pagePars.PageIndex).ToList();
            List<Dynamic> finalList1 = new ArrayList<>();
            friends.forEach((a) -> {
                finalList1.addAll(dynamicSet.Where("factoryId=?", a.getFriendId()).Limit(pagePars.PageSize, pagePars.PageIndex).ToList());
                count[0] += dynamicSet.Where("factoryId=?", a.getId()).Count();
            });
            if (finalList1.size() < pagePars.PageIndex) {
                pagePars.PageIndex = finalList1.size();
            }
            list = finalList1.subList(pagePars.PageSize, pagePars.PageIndex += pagePars.PageSize);
            list.forEach((a) -> {
                DynamicDTO dynamicDTO = new DynamicDTO();
                dynamicDTO.setDynamic(a);
                dynamicDTO.setCooperation("1");
                User user = userSet.Get(a.getFactoryId());
                if (null == user || StringUtils.isEmpty(user.getImg())) {
                    dynamicDTO.setUserImg("无");
                } else {
                    dynamicDTO.setUserImg(user.getImg());
                }
                if (null == user || StringUtils.isEmpty(user.getName())) {
                    dynamicDTO.setName("无");
                } else {
                    dynamicDTO.setName(user.getName());
                }
                Date date = (Date) a.get("_ctime");
                dynamicDTO.setTime(date.getTime());
                dynamicDTOS.add(dynamicDTO);
            });
        }
        //查未合作
        else {
            List<Friends> friends = friendsSet.Where("userId=?", salerId).Limit(pagePars.PageSize, pagePars.PageIndex).ToList();
            List<User> users = userSet.Where("type=?", BaseType.UserType.FACTORY.getCode()).Limit(pagePars.PageSize, pagePars.PageIndex).ToList();
            List<User> notFriends = new ArrayList<>(users);
            users.forEach((a) -> friends.forEach((b) -> {
                if (a.getId().equals(b.getFriendId())) {
                    notFriends.remove(a);
                }
            }));
            List<Dynamic> finalList = new ArrayList<>();
            notFriends.forEach((a) -> {
                finalList.addAll(dynamicSet.Where("factoryId=?", a.getId()).Limit(pagePars.PageSize, pagePars.PageIndex).ToList());
                count[0] += dynamicSet.Where("factoryId=?", a.getId()).Count();
            });
            if (finalList.size() < pagePars.PageIndex) {
                pagePars.PageIndex = finalList.size();
            }
            list = finalList.subList(pagePars.PageSize, pagePars.PageIndex += pagePars.PageSize);
            list.forEach((a) -> {
                DynamicDTO dynamicDTO = new DynamicDTO();
                dynamicDTO.setDynamic(a);
                dynamicDTO.setCooperation("3");
                User user = userSet.Get(a.getFactoryId());
                if (null == user || StringUtils.isEmpty(user.getImg())) {
                    dynamicDTO.setUserImg("无");
                } else {
                    dynamicDTO.setUserImg(user.getImg());
                }
                if (null == user || StringUtils.isEmpty(user.getName())) {
                    dynamicDTO.setName("无");
                } else {
                    dynamicDTO.setName(user.getName());
                }
                Date date = (Date) a.get("_ctime");
                dynamicDTO.setTime(date.getTime());
                dynamicDTOS.add(dynamicDTO);
            });
        }
        pageData.rows = dynamicDTOS;
        pageData.total = count[0];
        return pageData;
    }

    /**
     * 获取厂商信息
     *
     * @param factoryId 厂商id
     * @return
     */
    @Override
    public Object getUserMsg(String factoryId) {
        if (StringUtils.isEmpty(factoryId)) {
            throw new OtherExcetion("操作错误");
        }
        User user = new User().getById(factoryId);
        if (null != user) {
            user.setLoginKey(null);
            user.setPassword(null);
        }
        Auth auth = authSet.Where("userId=?", user.getId()).First();
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("auth", auth);
        return map;
    }
}