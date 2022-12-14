package controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import util.JWTUtils;
import util.ResponseUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 对于所有需要登录的Api，进行过滤
@WebFilter(filterName = "AuthorizeFilter", urlPatterns = "/auth2/*")
public class AuthorizeFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        System.out.println("认证过滤器");
        try {
//            JSONObject data = JsonUtils.getRequestPostJson((HttpServletRequest) servletRequest);
            String token = ((HttpServletRequest) servletRequest).getHeader("token");
            DecodedJWT decodedJWT = JWTUtils.decodeRsa(token);
            System.out.println(decodedJWT.getClaim("name").asString());
            System.out.println(decodedJWT.getClaim("code").asString());
            System.out.println("用户已登录，放行");
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            System.out.println("解析失败");
            System.out.println(e.getMessage());
            System.out.println("用户未登录，拒绝访问");
            ResponseUtils.responseJson(405, "用户未登录或没有权限访问该功能", (HttpServletResponse) servletResponse);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
