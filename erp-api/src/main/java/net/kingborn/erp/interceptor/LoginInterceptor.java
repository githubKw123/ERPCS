package net.kingborn.erp.interceptor;

import com.alibaba.fastjson.JSONArray;
import io.jsonwebtoken.Claims;
import lombok.extern.java.Log;
import net.kingborn.core.exception.Assert;
import net.kingborn.core.exception.BizException;
import net.kingborn.core.util.JsonKit;
import net.kingborn.core.util.StrKit;
import net.kingborn.erp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 */
@Component
@Log
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        if(StrKit.isBlank(authorization)) {
            throw new BizException("未登录", 99998);
        }

        if (!authorization.startsWith("Bearer ")) {
            throw new BizException("未登陆！", 99998);
        }

        String token = authorization.substring(7);

        Claims claims = jwtUtil.parseJwt(token);
        if (claims == null) {
            throw new BizException("令牌不正确", 99999);
        }

        CurrentUser currentUser = new CurrentUser();
        currentUser.setId(Long.parseLong(claims.getId()));
        currentUser.setUsername(claims.getSubject());

        String roles = claims.get("roles", String.class);
        currentUser.setRoles(JSONArray.parseArray(roles));
        request.setAttribute("currentUser", currentUser);

        log.info("currentUser:" + JsonKit.toJson(currentUser));

        return true;
    }
}
