package com.tang.newcloud.service.ucenter.controller.api;

import com.google.gson.Gson;
import com.tang.newcloud.common.base.result.ResultCodeEnum;
import com.tang.newcloud.common.base.util.ExceptionUtils;
import com.tang.newcloud.common.base.util.HttpClientUtils;
import com.tang.newcloud.common.base.util.JwtInfo;
import com.tang.newcloud.common.base.util.JwtUtils;
import com.tang.newcloud.service.base.exception.NewCloudException;
import com.tang.newcloud.service.ucenter.entity.UcenterMember;
import com.tang.newcloud.service.ucenter.service.UcenterMemberService;
import com.tang.newcloud.service.ucenter.util.UcenterProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller//注意这里没有配置 @RestController
@RequestMapping("/api/ucenter/wx")
@Slf4j
public class ApiWxController {

    @Autowired
    private UcenterProperties ucenterProperties;

    @Autowired
    private UcenterMemberService memberService;

    @GetMapping("login")
    public String genQrConnect(HttpSession session){

        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //处理回调url
        String redirecturi = "";
        try {
            redirecturi = URLEncoder.encode(ucenterProperties.getRedirectUri(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new NewCloudException(ResultCodeEnum.URL_ENCODE_ERROR);
        }

        //处理state：生成随机数，存入session
        String state = UUID.randomUUID().toString();
        log.info("生成 state = " + state);
        session.setAttribute("wx_open_state", state);

        String qrcodeUrl = String.format(
                baseUrl,
                ucenterProperties.getAppId(),
                redirecturi,
                state
        );

        return "redirect:" + qrcodeUrl;
    }

    /**
     * 回调方法
     * 得到access_token
     * 使用token得到用户数据
     * 插入用户数据到本地
     * @param code
     * @param state
     * @param session
     * @return
     */
    @GetMapping("/callback")
    public String callback(String code, String state,HttpSession session){

        //回调被拉起，并获得code和state参数
        log.info("callback被调用");
        log.info("code = #{}",code);
        log.info("state = #{} " ,state);

        if(StringUtils.isEmpty(code)||StringUtils.isEmpty(state)){
            log.error("非法回调请求");
            throw new NewCloudException(ResultCodeEnum.ILLEGAL_CALLBACK_REQUEST_ERROR);
        }
        String sessionState = (String)session.getAttribute("wx_open_state");
        if(!state.equals(sessionState)){
            log.error("非法回调请求");
            throw new NewCloudException(ResultCodeEnum.ILLEGAL_CALLBACK_REQUEST_ERROR);
        }

        //携带授权临时票据code，和appid以及appsecret请求access_token
        String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
        Map<String, String> accessTokenParam = new HashMap();
        accessTokenParam.put("appid", ucenterProperties.getAppId());
        accessTokenParam.put("secret", ucenterProperties.getAppSecret());
        accessTokenParam.put("code", code);
        accessTokenParam.put("grant_type", "authorization_code");
        HttpClientUtils client = new HttpClientUtils(accessTokenUrl, accessTokenParam);

        String result = "";
        try {
            //发送请求
            client.get();
            //拿到响应access_token
            result = client.getContent();
            log.info("result =#{} " , result);
        } catch (Exception e) {
            log.error("获取access_token失败");
            throw new NewCloudException(ResultCodeEnum.FETCH_ACCESSTOKEN_FAILD);
        }
//        解析json数据,转换成map集合
        Gson tool = new Gson();
        HashMap<String, Object> resultMap = tool.fromJson(result, HashMap.class);

        //判断微信获取access_token失败的响应
        if(resultMap.get("errcode")!=null){
            log.error("获取access_token失败：#{}",resultMap.toString());
            throw new NewCloudException(ResultCodeEnum.FETCH_ACCESSTOKEN_FAILD);
        }
        //判断微信获取access_token成功
        String accessToken = (String)resultMap.get("access_token");
        //每个人微信独有
        String openid = (String)resultMap.get("openid");

        log.info("accessToken = " + accessToken);
        log.info("openid = " + openid);

        //根据access_token获取微信用户的基本信息
        //拿到个人信息过后在本地注册
        // TODO
        //1.检查用户是否注册过
        UcenterMember ucenterMember=memberService.getByOpenid(openid);
        //未注册
        if(ucenterMember==null){
            String dataUrl ="https://api.weixin.qq.com/sns/userinfo";
            Map<String, String> queryDataMap = new HashMap<>();
            queryDataMap.put("access_token",accessToken);
            queryDataMap.put("openid",openid);

            HttpClientUtils clientTwo = new HttpClientUtils(dataUrl, queryDataMap);
            String data="";
            try {
                clientTwo.get();
                data=clientTwo.getContent();
            } catch (Exception e) {
                log.error("获取个人信息失败");
                throw new NewCloudException(ResultCodeEnum.FETCH_USERINFO_ERROR);
            }
            HashMap<String,Object> resultUserInfoMap = tool.fromJson(data, HashMap.class);

            String nickname = (String)resultUserInfoMap.get("nickname");
            String headimgurl = (String)resultUserInfoMap.get("headimgurl");
            Double sex = (Double)resultUserInfoMap.get("sex");

            //得到member对象
            UcenterMember member = new UcenterMember();
            member.setOpenid(openid);
            member.setNickname(nickname);
            member.setAvatar(headimgurl);
            member.setSex(sex.intValue());
            memberService.saveWechatMember(member);
        }
        JwtInfo jwtInfo = new JwtInfo();
        jwtInfo.setId(ucenterMember.getId());
        jwtInfo.setNickname(ucenterMember.getNickname());
        jwtInfo.setAvatar(ucenterMember.getAvatar());
        String jwtToken = JwtUtils.getJwtToken(jwtInfo, 1800);

        //携带token跳转
        return "redirect:http://localhost:3000?token=" + jwtToken;
    }
}