package com.gdd.ylz.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    /**
     * 如果str为null，返回“”,否则返回str
     * @param str
     * @return
     */
    public static String isNull(String str) {
        if (str == null) {
            return "";
        }
        return str.trim();
    }

    public static String isNull(Object o) {
        if (o == null) {
            return "";
        }
        String str="";
        if(o instanceof String){
            str=(String)o;
        }else{
            str=o.toString();
        }
        return str;
    }

    /**
     * 获取小写写的guid
     * @return
     */
    public static String getGUID(){
      return  UUID.randomUUID().toString().replace("-","");
    }
    /**
     * 获取大写的guid
     * @return
     */
    public static String getGUIDtoUpperCase(){
        return  UUID.randomUUID().toString().replace("-","").toUpperCase();
    }
    /**
     * 检验手机号
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone){
        phone = isNull(phone);
        Pattern regex = Pattern
//                .compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
                .compile("^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$");
        Matcher matcher = regex.matcher(phone);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

    /**
     * 检查是否全中文，返回true 表示是 反之为否
     * @param realname
     * @return
     */
    public static boolean isChinese(String realname){
        realname = isNull(realname);
        Pattern regex = Pattern.compile("[\\u4e00-\\u9fa5]{2,25}");
        Matcher matcher = regex.matcher(realname);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

    /**
     * 检查email是否是邮箱格式，返回true表示是，反之为否
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        email = isNull(email);
        Pattern regex = Pattern
                .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher matcher = regex.matcher(email);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

    /**
     * 检查身份证的格式，返回true表示是，反之为否
     * @param cardId
     * @return
     */
    public static boolean isCard(String cardId) {
        cardId = isNull(cardId);
        //身份证正则表达式(15位)
        Pattern isIDCard1=Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
        //身份证正则表达式(18位)
        Pattern isIDCard2=Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
        Matcher matcher1= isIDCard1.matcher(cardId);
        Matcher matcher2= isIDCard2.matcher(cardId);
        boolean isMatched = matcher1.matches()||matcher2.matches();
        return isMatched;
    }

    /**
     * 判断字符串是否为整数
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        if (isEmpty(str)) {
            return false;
        }
        Pattern regex = Pattern.compile("\\d*");
        Matcher matcher = regex.matcher(str);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

    /**
     * 判断字符串是否为数字
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if (isEmpty(str)) {
            return false;
        }
        Pattern regex = Pattern.compile("(-)?\\d*(.\\d*)?");
        Matcher matcher = regex.matcher(str);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

    /**
     * 判断字符串是否为纯字母
     * @param str
     * @return
     */
    public static boolean isEnglish(String str) {
        if (isEmpty(str)) {
            return false;
        }
        Pattern regex = Pattern.compile("[a-zA-Z]{1,}");
        Matcher matcher = regex.matcher(str);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * 首字母大写
     * @param s
     * @return
     */
    public static String firstCharUpperCase(String s) {
        StringBuffer sb = new StringBuffer(s.substring(0, 1).toUpperCase());
        sb.append(s.substring(1, s.length()));
        return sb.toString();
    }

    public static String hideChar(String str,int len){
        if(str==null) return null;
        char[] chars=str.toCharArray();
        for(int i=1;i<chars.length-1;i++){
            if(i<len){
                chars[i]='*';
            }
        }
        str=new String(chars);
        return str;
    }

    public static String hideFirstChar(String str,int len){
        if(str==null) return null;
        char[] chars=str.toCharArray();
        if(str.length()<=len){
            for(int i=0;i<1;i++){
                chars[i]='*';
            }
        }else{
            for(int i=0;i<len;i++){
                chars[i]='*';
            }
        }
        str=new String(chars);
        return str;
    }

    public static String hideLastChar(String str,int len){
        if(str==null) return null;
        char[] chars=str.toCharArray();
        if(str.length()<=len){
            for(int i=0;i<chars.length;i++){
                chars[i]='*';
            }
        }else{
            for(int i=chars.length-1;i>chars.length-len-1;i--){
                chars[i]='*';
            }
        }
        str=new String(chars);
        return str;
    }

    public static String hideNumber(String str){
        if(str==null) return null;
        char[] chars=str.toCharArray();
        if(str.length()<=7){
            for(int i=0;i<chars.length;i++){
                chars[i]='*';
            }
        }else{
            for(int i=3;i<chars.length-4;i++){
                chars[i]='*';
            }
        }
        str=new String(chars);
        return str;
    }

    /**
     *
     * @return
     */
    public static String format(String str,int len){
        if(str==null) return "-";
        if(str.length()<=len){
            int pushlen=len-str.length();
            StringBuffer sb=new StringBuffer();
            for(int i=0;i<pushlen;i++){
                sb.append("0");
            }
            sb.append(str);
            str=sb.toString();
        }else{
            String newStr=str.substring(0, len);
            str=newStr;
        }
        return str;
    }

    public static String contact(Object[] args){
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<args.length;i++){
            sb.append(args[i]);
            if(i<args.length-1){
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /**
     * 是否包含在以“，”隔开字符串内
     * @param s
     * @param type
     * @return
     */
    public static boolean isInSplit(String s,String type){
        if(isNull(s).equals("")){
            return false;
        }
        List<String> list= Arrays.asList(s.split(","));
        if(list.contains(type)){
            return true;
        }
        return false;
    }

    public static boolean isBlank(String str){
        return StringUtils.isNull(str).equals("");
    }

    public synchronized static String generateTradeNO(long userid,String type){
        String s;
        s = type + userid + getFullTimeStr();
        return s;
    }

    public static String getFullTimeStr(){
        Calendar.getInstance().getTime();
        String s=DateUtils.getDate(Calendar.getInstance().getTime(),DateUtils.DATETIME_FORMAT);
        return s;
    }

    public static String array2Str(Object[] arr){
        StringBuffer s=new StringBuffer();
        for(int i=0;i<arr.length;i++){
            s.append(arr[i]);
            if(i<arr.length-1){
                s.append(",");
            }
        }
        return s.toString();
    }

    public static String array2Str(int[] arr){
        StringBuffer s=new StringBuffer();
        for(int i=0;i<arr.length;i++){
            s.append(arr[i]);
            if(i<arr.length-1){
                s.append(",");
            }
        }
        return s.toString();
    }


    /**
     * 指定起始位置字符串隐藏
     * @param str
     * @param index1
     * @param index2
     * @return
     */
    public static String hideStr(String str, int index1, int index2) {
        if (str == null)
            return null;
        String str1 = str.substring(index1, index2);
        String str2 = str.substring(index2);
        String str3 = "";
        if (index1 > 0) {
            str1 = str.substring(0, index1);
            str2 = str.substring(index1, index2);
            str3 = str.substring(index2);
        }
        char[] chars = str2.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = '*';
        }
        str2 = new String(chars);
        String str4 = str1 + str2 + str3;
        return str4;
    }

    // 四舍五入保留两位小数点
    public static String SetNumberFractionDigits(String str) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        return nf.format(Float.valueOf(str));
    }
    public static String getMonday(String the_rq){
        int n=getXC_days(the_rq);
        //System.out.println("n="+n);
        n=n*-1;
        return Q_N_Day(n,the_rq);
    }



    //获取输入日期的星期天日期

    public static String getSunday(String the_rq){
        int n=getXC_days(the_rq);
        //System.out.println("n="+n);
        n=(6-(n*-1))*-1;
        return Q_N_Day(n,the_rq);
    }

    // 获得输入日期与本周一相差的天数
    public static int getXC_days(String rq){
        SimpleDateFormat formatYMD=new SimpleDateFormat("yyyy-MM-dd");//formatYMD表示的是yyyy-MM-dd格式
        SimpleDateFormat formatD=new SimpleDateFormat("E");//"E"表示"day in week"
        Date d=null;
        String weekDay="";
        int xcrq=0;
        try{
            d=formatYMD.parse(rq);//将String 转换为符合格式的日期
            weekDay=formatD.format(d);
            if(weekDay.equals("星期一")){
                xcrq=0;
            }else{
                if(weekDay.equals("星期二")){
                    xcrq=-1;
                }else{
                    if(weekDay.equals("星期三")){
                        xcrq=-2;
                    }else{
                        if(weekDay.equals("星期四")){
                            xcrq=-3;
                        }else{
                            if(weekDay.equals("星期五")){
                                xcrq=-4;
                            }else{
                                if(weekDay.equals("星期六")){
                                    xcrq=-5;
                                }else{
                                    if(weekDay.equals("星期日")){
                                        xcrq=-6;
                                    }

                                }

                            }

                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return xcrq;
    }

    public static String Q_N_Day(int N,String d1){//
        String []d2=d1.split("-");
        int year=Integer.parseInt(d2[0]);
        int month=Integer.parseInt(d2[1]);
        int day=Integer.parseInt(d2[2]);
        if(day-N<=0){
            if(month==1){
                year=year-1;
                month=12;
                day = day + 31-N;
            }else{
                month=month-1;
                if (month == 2) {
                    if (year % 4 == 0) {
                        day = day + 29-N;
                    } else {
                        day = day + 28-N;
                    }
                }else{
                    if(month==4||month==6||month==9||month==11){
                        day=day+30-N;
                    }else{
                        day=day+31-N;
                    }
                }
            }
        }else{
            ///////////////////////////////////////////////////////////////////////////////////
            if(month==12){
                if((day-N)>31){
                    year=year+1;
                    month=1;
                    day=(day-N)-31;
                }else{
                    day=day-N;
                }
            }else{
                if (month == 2) {
                    if (year % 4 == 0) {
                        if((day-N)>29){
                            month++;
                            day=(day-N)-29;
                        }else{
                            day=day-N;
                        }
                    } else {
                        if((day-N)>28){
                            month++;
                            day=(day-N)-28;
                        }else{
                            day=day-N;
                        }
                    }
                }else{
                    if(month==4||month==6||month==9||month==11){
                        if((day-N)>30){
                            month++;
                            day=(day-N)-30;
                        }else{
                            day=day-N;
                        }
                    }else{
                        if((day-N)>31){
                            month++;
                            day=(day-N)-31;
                        }else{
                            day=day-N;
                        }
                    }
                }
            }



            //day=day-N;
        }
        String str=String.valueOf(year);
        if(month<10){
            str=str+"-0"+String.valueOf(month);
        }else{
            str=str+"-"+String.valueOf(month);
        }
        if(day<10){
            str=str+"-0"+String.valueOf(day);
        }else{
            str=str+"-"+String.valueOf(day);
        }

        return str;
    }

    /*public static void main(String[] args) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
        String mondayString=StringUtils.getMonday(df.format(new Date()))+ " 00:00:00";
        String sumdayString=StringUtils.getSunday(df.format(new Date()))+ " 23:59:59";
        String monday=DateUtils.getTime(mondayString)+"";
        String sumday=DateUtils.getTime(sumdayString)+"";
        //System.out.println(monday);
        //System.out.println(sumday);

    }*/

    public static String fillTemplet(String templet, String phstr, String[] paras){
        StringBuffer templetSB = new StringBuffer(templet);
        int i = 0;
        while(templetSB.indexOf(phstr) >= 0 && i < paras.length){
            templetSB.replace(templetSB.indexOf(phstr), templetSB.indexOf(phstr)+phstr.length(), paras[i]);
            i++;
        }
        return templetSB.toString();
    }
    //V1.6.6.1 RDPROJECT-226 liukun 2013-09-26 start
    /*public static String fillTemplet(String template){
        //V1.6.6.1 RDPROJECT-331 liukun 2013-10-12 start
        //模板中的'是非法字符，会导致无法提交，所以页面上用`代替
        template = template.replace('`', '\'');
        //V1.6.6.1 RDPROJECT-331 liukun 2013-10-12 end

        Map<String,Object> data=Global.getTransfer();
        try {
            return FreemarkerUtil.renderTemplate(template, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }*/
    //V1.6.6.1 RDPROJECT-226 liukun 2013-09-26 end

    //V1.6.5.3 RDPROJECT-142 liukun 2013-09-11 start
    public static int[] strarr2intarr(String[] strarr){
        int[] result = new int[strarr.length];
        for(int i=0;i<strarr.length;i++)
        {
            result[i] = Integer.parseInt(strarr[i]);
        }
        return result;
    }

    /**
     * 大写字母转成“_”+小写
     * @param str
     * @return
     */
    public static String toUnderline(String str){
        char[] charArr=str.toCharArray();
        StringBuffer sb=new StringBuffer();
        sb.append(charArr[0]);
        for(int i=1;i<charArr.length;i++){
            if(charArr[i]>='A'&&charArr[i]<='Z'){
                sb.append('_').append(charArr[i]);
            }else{
                sb.append(charArr[i]);
            }
        }
        return sb.toString().toLowerCase();
    }


    /**
     * 根据身份证计算性别
     * @param cardId
     * @return
     */
    public static int getSexByCardid(String cardId) {
        /*String sexNum = "";
        if (cardId.length() == 15) {
            sexNum = cardId.substring(13, 14);
        } else {
            sexNum = cardId.substring(16, 17);
        }

        if (Integer.parseInt(sexNum) % 2 == 1) {
            return 1;
        } else {
            return 0;
        }*/
        int sexNum = 0;
        if (cardId.length() == 15) {
            sexNum = cardId.charAt(13);
        } else {
            sexNum = cardId.charAt(16);
        }
        if (sexNum % 2 == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 根据身份证计算生日
     * @param cardId
     * @return
     */
    public static String getBirthdayByCardid(String cardId) {
        String birth = null;
        if (cardId.length() == 15) {
            birth = cardId.substring(6, 12);
        } else {
            birth = cardId.substring(6, 14);
        }
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
        String birthday = null;
        try {
            birthday = sf2.format(sf1.parse(birth));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return birthday;
    }

    public static String getNullStr(Object obj) {
        if(obj==null) {
            return "";
        }
        return obj.toString();
    }

    public static String getFileSuffixName(String fileName){
        String suffix = null;
        if (fileName != null) {
            int last = fileName.lastIndexOf('.');
            suffix = fileName.substring(last);
        }
        return suffix;
    }
    /**
     * 格式化数字
     * @param num
     * @return
     */
    public static String getFormatNumber(String num){
        int number=Integer.parseInt(num);
        if(Integer.parseInt(num) >= 100000) {
            BigDecimal accountB = new BigDecimal(number);
            return accountB.divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString()+"万元";
        } else {
            return number+"元";
        }
    }

    /**
     * 获取拼音首字母
     * @param str
     * @return
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static String getPinYinFirstLetter(String str) {
        char[] charArray = str.toCharArray();
        StringBuilder pinyin = new StringBuilder();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 设置大小写格式
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        // 设置声调格式
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < charArray.length; i++) {
            // 匹配中文,非中文转换会转换成null
            if (Character.toString(charArray[i]).matches("[\\u4E00-\\u9FA5]+")) {
                String[] hanyuPinyinStringArray = new String[0];
                try {
                    hanyuPinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(charArray[i], defaultFormat);
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }

                if (hanyuPinyinStringArray != null) {
                    pinyin.append(hanyuPinyinStringArray[0].charAt(0));
                }
            }else{
                pinyin.append(charArray[i]);
            }
        }
        return pinyin.toString();
    }

    /**
     * 必须是6-10位字母、数字、下划线（这里字母、数字、下划线是指任意组合，没有必须三类均包含）
     * 不能以数字开头
     * @param name
     * @return
     */
    public static boolean isUserName(String name){
//        String regExp = "^[^0-9][\\w_]{5,9}$";
        String regExp = "^[a-zA-Z0-9_-]{4,16}$";
        if(name.matches(regExp)) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 必须是6-20位的字母、数字、下划线（这里字母、数字、下划线是指任意组合，没有必须三类均包含）
     * @param password
     * @return
     */
    public static boolean isPassword(String password){
        String regExp = "^[\\w_]{6,20}$";
        if(password.matches(regExp)) {
            return true;
        }
        return false;
    }



}