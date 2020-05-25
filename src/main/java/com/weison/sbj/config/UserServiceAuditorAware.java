package com.weison.sbj.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

public class UserServiceAuditorAware implements AuditorAware<Integer> {

    /**
     * 通过 Security 取
     * 通过 Request 取
     *
     * @return
     */
    @Override
    public Optional<Integer> getCurrentAuditor() {
        //    第一种方式：如果我们集成了spring的Security，我们直接通过如下方法即可获得当前请求的用户ID.
        //    Authentication authentication =
        //SecurityContextHolder.getContext().getAuthentication();
        //    if (authentication == null || !authentication.isAuthenticated()) {
        //       return null;
        //    }
        //    return ((LoginUserInfo) authentication.getPrincipal()).getUser().getId();
        //第二种方式通过request里面取或者session里面取
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Integer userId = (Integer) servletRequestAttributes.getRequest().getSession().getAttribute("userId");
        return Optional.ofNullable(userId);
    }

}
