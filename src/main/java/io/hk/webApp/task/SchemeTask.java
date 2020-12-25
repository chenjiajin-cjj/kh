package io.hk.webApp.task;

import io.hk.webApp.DataAccess.SchemeSet;
import io.hk.webApp.Domain.Scheme;
import io.hk.webApp.Tools.AccTokenAop;
import io.hk.webApp.Tools.BaseType;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Component
@RunWith(SpringRunner.class)
@SpringBootTest
public class SchemeTask {

    private final Logger LOG = LoggerFactory.getLogger(SchemeTask.class);
    @Autowired
    private SchemeSet schemeSet;

    /**
     * 超时自动收货  定时器
     *
     * @return
     */
    @Scheduled(initialDelay = 1000, fixedRate = 60000)
    public void aiOrder() {
        long begin = System.currentTimeMillis();
        List<Scheme> schemes = schemeSet.Where("status=?", BaseType.SchemeStatus.UNDERWAY.getCode()).ToList();
        LOG.info("开始过滤过期方案");
        final int[] count = {0};
        schemes.forEach((a) -> {
            long nowTime = System.currentTimeMillis();
            if (nowTime - a.getValidity() > 0) {
                a.setStatus(BaseType.SchemeStatus.PAST.getCode());
                count[0] += schemeSet.Update(a.getId(), a);
            }
        });
        if (0 == count[0]) {
            LOG.info("没有过期方案，耗时:" + (System.currentTimeMillis() - begin) + "ms");
        } else {
            LOG.info("已处理过期方案：" + count[0] + "个，耗时:" + (System.currentTimeMillis() - begin) + "ms");
        }
    }
}
