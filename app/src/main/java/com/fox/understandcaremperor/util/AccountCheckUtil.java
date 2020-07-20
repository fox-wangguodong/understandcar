package com.fox.understandcaremperor.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountCheckUtil {

    /**
     * 判断是否为手机号码
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }


    /**
     * 判断帐号是否为Email账号
	 * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.length() < 5) {// 如果帐号小于5位，则肯定不可能为邮箱帐号eg: x@x.x
            return false;
        }
        if (!email.contains("@")) {// 判断是否含有@符号
            return false;// 没有@则肯定不是邮箱
        }
        String[] sAcc = email.split("@");
        if (sAcc.length != 2) {// # 数组长度不为2则包含2个以上的@符号，不为邮箱帐号
            return false;
        }
        if (sAcc[0].length() <= 0) {// #@前段为邮箱用户名，自定义的话至少长度为1，其他暂不验证
            return false;
        }
        if (sAcc[1].length() < 3 || !sAcc[1].contains(".")) {// @后面为域名，位数小于3位则不为有效的域名信息 .如果后端不包含.则肯定不是邮箱的域名信息
            return false;
        } else {
            if (sAcc[1].substring(sAcc[1].length() - 1).equals(".")) {// # 最后一位不能为.结束
                return false;
            }
            String[] sDomain = sAcc[1].split("\\.");// #将域名拆分 tm-sp.com 或者 .com.cn.xxx
            for (String s : sDomain) {
                if (s.length() <= 0) {
                    return false;
                }
            }
        }
        return true;
    }


    //用户账号格式判断
    public static boolean isUserAccount(String userAccount) {
        String str = "^[a-zA-Z_]{1}\\w{5,15}$";//tiansankun 描述：注册用户名格式校验修改
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(userAccount);
        return m.matches();
    }


}
