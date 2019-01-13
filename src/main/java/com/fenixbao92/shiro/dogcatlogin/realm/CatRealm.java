package com.fenixbao92.shiro.dogcatlogin.realm;

import com.fenixbao92.shiro.dogcatlogin.token.CatToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create by fenixbao92 on 2019/1/11.
 */
public class CatRealm extends AuthorizingRealm {

    @Override
    public Class getAuthenticationTokenClass() {
        return CatToken.class;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 从 token 中获取用户身份信息
        String username = (String) token.getPrincipal();
        // 通过 username 从数据库中查询

        // 如果查询不到则返回 null
        if(!username.equals("helloKitty")){//这里模拟查询不到
            return null;
        }

        //获取从数据库查询出来的用户密码
        String dbPassword = "123";//这里使用静态数据模拟

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("words","what a cute cat!welcome to our site:)");

        //返回认证信息由父类 AuthenticatingRealm 进行认证
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, dbPassword, getName());

        return simpleAuthenticationInfo;
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获取身份信息
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        String username = (String) principals.getPrimaryPrincipal();
        // 根据身份信息从数据库中查询权限数据
        // 这里使用静态数据模拟
        List<String> permissions = new ArrayList<String>();
        permissions.add("user:*");
        permissions.add("department:*");

        // 将权限信息封闭为AuthorizationInfo
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 模拟数据，添加 manager 角色
        simpleAuthorizationInfo.addRole("manager");

        for(String permission:permissions){
            simpleAuthorizationInfo.addStringPermission(permission);
        }

        return simpleAuthorizationInfo;
    }

}
