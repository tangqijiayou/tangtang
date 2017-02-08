package com.tangqijiayou.controller;

import com.tangqijiayou.common.ResultJsonMsg;
import com.tangqijiayou.model.Account;
import com.tangqijiayou.service.IAccountService;
import com.tangqijiayou.vo.AccountResultVo;
import com.tangqijiayou.vo.LoginParamVo;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 用户管理操作Controller
 */
@Controller
@RequestMapping("user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private @Resource
    IAccountService accountService;

    /**
     * @api {post} /user/login 用户登录
     * @apiName login
     * @apiGroup user
     * @apiVersion 0.0.1
     * @apiParam {String} phone 手机号
     * @apiParam {String} password 密码（明文）
     *
     *
     * @apiSuccessExample {JSON}
     * {
     *   "statusCode": "200",
     *   "message": "登录成功验证通过",
     *   "data": {
     *      "id": 1,
     *      "phone": "11111111111",
     *      "nickName": "haha",
     *      "realName": "hahah",
     *      "logoUrl": "http:xxxxx",
     *      "email": "11111111111@qq.com",
     *      "accountStatus": 1,
     *      "role": "user",
     *      "idCard": "3666666666666666666",
     *      "auth": 0,
     *      "del": 0
     *   }
     * }
     *
     * @apiErrorExample {JSON}
     * {
     *   "statusCode": 1001,
     *   "message": "用户名/密码出错",
     *   "data": {}
     * }
     *
     * @apiSuccess {Number} statusCode 接口返回状态（200为登录成功， 1001
     *             改状态码为登录失败，失效，踢出时，返回登录界面重新登录）
     * @apiSuccess {String} message 消息返回，可直接返回给用户展示（产品定义文字信息）
     *
     * @apiSuccess (data item) {Number} id
     * @apiSuccess (data item) {String} phone 用户手机账号
     * @apiSuccess (data item) {String} nickName 用户昵称
     * @apiSuccess (data item) {String} realName 用户真实姓名
     * @apiSuccess (data item) {String} logoUrl 用户头像地址
     * @apiSuccess (data item) {String} email 用户email
     * @apiSuccess (data item) {Number} accountStatus 1为正常 2为冻结
     * @apiSuccess (data item) {String} role 用户角色 user为普通用户 admin为管理员
     * @apiSuccess (data item) {String} idCard 用户身份证
     * @apiSuccess (data item) {Number} auth 1为未认证 2为已认证
     * @apiSuccess (data item) {Number} del 1为正常，2为该用户被删除
     *
     * @return
     */
    @RequestMapping("/login")
    public @ResponseBody
    ResultJsonMsg<Object> login(@Valid LoginParamVo loginParamVo, HttpSession session) {
        String phone = loginParamVo.getPhone();
        String password = loginParamVo.getPassword();
//		password = CryptoHelper.getSecuretPassword(password, CryptoHelper.key);

        UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
        token.setRememberMe(true);
        logger.info("为了验证登录用户而封装的token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
        // 获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        try {
            // 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            // 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            // 所以这一步在调用login(token)方法时,它会走到ShiroAuthorizingRealmService.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            // 现在这里只是实现了shiro的基本使用，exception可进一步根据需求实现和对用户进一步做登录登出的限制
            logger.debug("对用户[" + phone + "]进行登录验证..验证开始");
            currentUser.login(token);
            logger.debug("对用户[" + phone + "]进行登录验证..验证通过");

        } catch (UnknownAccountException uae) {
            logger.debug("对用户[" + phone + "]进行登录验证..验证未通过,未知账户");

        } catch (IncorrectCredentialsException ice) {
            logger.debug("对用户[" + phone + "]进行登录验证..验证未通过,错误的凭证");

        } catch (LockedAccountException lae) {
            logger.debug("对用户[" + phone + "]进行登录验证..验证未通过,账户已锁定");

        } catch (ExcessiveAttemptsException eae) {
            logger.debug("对用户[" + phone + "]进行登录验证..验证未通过,错误次数过多");

        } catch (AuthenticationException ae) {
            // 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.debug("对用户[" + phone + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();

        }
        // 验证是否登录成功
        if (currentUser.isAuthenticated()) {
            logger.info("用户[" + phone + "]登录认证通过");
            // 这里可以进行一些认证通过后的一些系统参数初始化操作，在登录成功时返回信息给前端
            Account account = accountService.findByPhone(phone);
            return new ResultJsonMsg<Object>(ResultJsonMsg.RESULT_CODE_SUCCESS, "登录认证通过", new AccountResultVo(account));
        } else {
            token.clear();
            logger.debug("对用户[" + phone + "]进行登录验证..验证未通过,token.clear()");
        }

        return new ResultJsonMsg<Object>(ResultJsonMsg.RESULT_CODE_NOTLOGIN, "用户名/密码出错");

    }


    /**
     * @api {post} /user/logout 用户退出登录
     * @apiName logout
     * @apiGroup user
     * @apiVersion 0.0.1
     *
     * @apiSuccessExample {JSON} Request-Example
     * {
     *   "statusCode": 200,
     *   "message": "昵称修改成功",
     *   "data": {}
     * }
     * @apiSuccess {Number} statusCode 昵称修改状态 （200成功，300则出错）
     * @apiSuccess {String} message 错误消息反馈
     *
     * @apiErrorExample JSON 出错时返回内容
     * {
     *   "statusCode": 300,
     *   "message": "昵称修改失败",
     *   "data": {}
     * }
     *
     * @return
     */
    @RequestMapping("/logout")
    @ResponseBody
    public ResultJsonMsg<Object> logout(HttpServletRequest request) {
        String phone = (String) SecurityUtils.getSubject().getPrincipal();
        SecurityUtils.getSubject().logout();
        logger.debug("phone " + phone + "该手机号已经退出登录");
        return new ResultJsonMsg<Object>(ResultJsonMsg.RESULT_CODE_NOTLOGIN, "成功退出当前账号", null);
    }
}