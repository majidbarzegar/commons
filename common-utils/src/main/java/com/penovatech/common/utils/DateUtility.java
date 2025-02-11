package com.penovatech.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtility {

    public static String nowToFormat(DateTimeFormatter formatter){
        return LocalDateTime.now().format(formatter);
    }

    public static String nowToTimestampFormat(){
        return nowToFormat(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    public static String nowToTimestampWithMillisFormat(){
        return nowToFormat(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    }

}
