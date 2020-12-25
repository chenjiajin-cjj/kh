package io.hk.webApp.Tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public HashMap<String, Object> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        LOG.error("全局异常提取", e);
        HashMap<String, Object> resut = new HashMap<>();
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            resut.put("code", 404);
            resut.put("info", e.getMessage());
        } else if (e instanceof OtherExcetion) {
            OtherExcetion exception = (OtherExcetion) e;
            resut.put("status", exception.getStatus());
            resut.put("message", exception.getMessage());
        } else {
            resut.put("code", 500);
            resut.put("message", "服务器错误，请联系管理员");
        }
        return resut;
    }
}
