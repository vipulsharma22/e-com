package com.nitsoft.ecommerce.auth;

import com.nitsoft.ecommerce.auth.service.Authenticated;
import com.nitsoft.ecommerce.database.model.User;
import com.nitsoft.ecommerce.database.model.UserToken;
import com.nitsoft.ecommerce.enums.PermissionEnum;
import com.nitsoft.ecommerce.service.auth.AuthService;
import com.nitsoft.util.Constant;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

@Aspect
@Component
public class AuthenticationInterceptor extends WebContentInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Autowired
    private AuthService authService;

    @Pointcut("@annotation(com.nitsoft.ecommerce.auth.service.Authenticated)")
    public void authentication() {
    }

    @Around("authentication()")
    public Object process(ProceedingJoinPoint jointPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) jointPoint.getSignature();
        Method method = methodSignature.getMethod();
        if (null != method) {
            Authenticated authAnnotation = jointPoint.getTarget().getClass().getMethod(method.getName(), method.getParameterTypes()).getAnnotation(Authenticated.class);
            HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            if (null != httpRequest) {
                String token = fetchAuthToken(httpRequest);
                String userType = getUserType(httpRequest);
                if (token != null) {
                    List<PermissionEnum> requiredPermissionEnumList = fetchRequiredPermissionEnumList(authAnnotation);
                    UserToken userToken = authService.getByToken(token);
                    if (userToken == null || userToken.getExpirationDate().after(new Date())) {
                        throw new AuthenticationException();
                    }
                    User user = authService.getUserByUserIdAndStatus(userToken.getUserId());
                    setAuthUmsDTOParameterInMethodSignatureIfPresent(user, jointPoint);
                    return jointPoint.proceed();

                }
            }
        }

        throw new AuthenticationException();
    }

    private List<PermissionEnum> fetchRequiredPermissionEnumList(Authenticated authAnnotation) {
        PermissionEnum[] requiredPermissionEnums = authAnnotation.permissions();
        List<PermissionEnum> requiredPermissionEnumList = new ArrayList<>(Arrays.asList(requiredPermissionEnums));
        requiredPermissionEnumList.add(PermissionEnum.ALL);
        return requiredPermissionEnumList;
    }

    private String fetchAuthToken(HttpServletRequest httpRequest) {
        String token = null;

        if (StringUtils.isBlank(token)) {
            token = httpRequest.getHeader("token");
        }
        if (StringUtils.isBlank(token)) {
            token = httpRequest.getParameter("token");
        }
        return token;
    }

    private String getUserType(HttpServletRequest httpRequest) {
        String userType = httpRequest.getHeader("X-USER-TYPE");
        if (StringUtils.isNotEmpty((userType))) {
            return userType;
        }
        userType = httpRequest.getParameter("X-USER-TYPE");
        if (StringUtils.isNotEmpty((userType))) {
            return userType;
        }
        return userType;
    }


    private void setAuthUmsDTOParameterInMethodSignatureIfPresent(User user, ProceedingJoinPoint jointPoint) {
        for (Object parameter : jointPoint.getArgs()) {
            if (parameter instanceof AuthUser) {
                AuthUser authUmsDTO = (AuthUser) parameter;
                authUmsDTO.setEmail(user.getEmail());
                authUmsDTO.setActive(Constant.USER_STATUS.ACTIVE.getStatus() == user.getStatus());
                authUmsDTO.setFirstName(user.getFirstName());
                authUmsDTO.setPhone(user.getPhone());
                LOG.info("validatedUserDTO {}", user);
                break;
            }
        }
    }
}
