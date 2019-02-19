package com.axp.controller;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
*
* @author hp
*/
public class Test {
public static void main(String args[]) {
int i= compare_date("2017-04-06 18:08:50", "2017-3-13 09:59:08");
int j= compare_date("2017-04-07 07:00:50", "2017-3-13 09:59:08");
}
public static int compare_date(String DATE1, String DATE2) {
DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
try {
	
Date dt1 = df.parse(DATE1);
Date dt2 = new Date();
if (dt1.getTime() < dt2.getTime()) {

return 1;
} else if (dt1.getTime() > dt2.getTime()) {

return -1;
} else {
return 0;
}
} catch (Exception exception) {
exception.printStackTrace();
}
return 0;
}
}