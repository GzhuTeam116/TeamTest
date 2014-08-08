package models;

import java.util.regex.Pattern;

/**
 * Created by jance on 2014/8/6.
 */
public class JudgeLan {
    public  Boolean JudgeLan(String userIp){
        String ALLOWABLE_IP_REGEX ="(127[.]0[.]0[.]1)|" + "(localhost)|"+
                "(10[.]\\d{1,3}[.]\\d{1,3}[.]\\d{1,3})|" +
                "(172[.]((1[6-9])|(2\\d)|(3[01]))[.]\\d{1,3}[.]\\d{1,3})|" +
                "(192[.]168[.]\\d{1,3}[.]\\d{1,3})";
        Boolean result = false;
        result= Pattern.matches(ALLOWABLE_IP_REGEX, userIp);
        return result;
    }
}
