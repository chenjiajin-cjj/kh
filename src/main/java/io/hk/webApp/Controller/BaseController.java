package io.hk.webApp.Controller;

import com.qiniu.util.Auth;
import io.framecore.Frame.Result;
import io.framecore.Tool.PropertiesHelp;
import io.framecore.redis.CacheHelp;
import io.hk.webApp.Tools.AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/base/")
public class BaseController {

    @GetMapping("qiniuToken")
    public Result get7niuToken() {
        String cacheKey = "chat:img.7niu.token";
//        String token = CacheHelp.get(cacheKey);
//        if (token == null) {
            String accessKey = null;
            String secretKey = null;
            String bucket = null;
            try {
                accessKey = PropertiesHelp.getApplicationConf("img.7niu.accessKey");
                secretKey = PropertiesHelp.getApplicationConf("img.7niu.secretKey");
                bucket = PropertiesHelp.getApplicationConf("img.7niu.bucket");

            } catch (IOException e) {
                e.printStackTrace();
            }
            Auth auth = Auth.create(accessKey, secretKey);
            String token = auth.uploadToken(bucket, null, 2592000, null);
//            CacheHelp.set(cacheKey, token, 1592000);
//        }
        return Result.succeed(token);

    }
}
