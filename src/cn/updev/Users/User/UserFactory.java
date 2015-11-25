package cn.updev.Users.User;

import cn.updev.Users.Static.IUser;
import cn.updev.Users.Static.UserRule;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by blf2 on 15-9-28.
 */
public class UserFactory {

    private String userName;
    private String nickName;
    private String eMail;
    private String passWord;
    private String url;
    private UserRule rule;

    public UserFactory(String userName, String nickName, String eMail, String passWord,String url,UserRule rule) {
        this.seteMail(eMail);//this.eMail = eMail;
        this.setNickName(nickName);
        this.setUserName(userName);
        this.setPassWord(passWord);
        this.setRule(rule);
        this.setUrl(url);
    }

    private void seteMail(String eMail) {

        //判断合法E-Mail
        Pattern pattern = Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
        Matcher matcher = pattern.matcher(eMail);

        if(matcher.matches()){
            this.eMail = eMail;
        }else{
            this.eMail = null;
        }

    }

    private void setNickName(String nickName) {

        //限制长度
        if(nickName.length() > 10){
            nickName = nickName.substring(0,10);
        }
        this.nickName = nickName;
    }

    private void setUserName(String userName) {

        //限制长度
        if(userName.length() > 10){
            userName = userName.substring(0,10);
        }
        this.userName = userName;
    }

    private void setPassWord(String passWord) {

        //密码MD5加密 把注册邮箱作为盐
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((passWord + this.eMail).getBytes());
            passWord = new String(md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        this.passWord = passWord;
    }

    private void setUrl(String url){
        //限制长度
        if(url.length() > 50){
            url = url.substring(0,50);
            this.url = url;
        }
    }

    private void setRule(UserRule rule){
        if(rule.isAdmin() || rule.isUser()){
            this.rule = rule;
        }else{
            this.rule = null;
        }
    }

    public IUser getUser(){

        if(this.eMail == null || this.rule == null){
            return null;
        }
        IUser user = new User(this.userName,this.nickName,this.eMail,this.passWord,this.url,this.rule).saveUser();
        return user;
    }
}
