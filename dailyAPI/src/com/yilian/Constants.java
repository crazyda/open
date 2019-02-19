package com.yilian;

/**
 * @author
 */
/**
 * @author
 */
public class Constants {
  //----商户信息：商户根据对接的实际情况对下面数据进行修改； 以下数据在测试通过后，部署到生产环境，需要替换为生产的数据----
  //商户编号，由易联产生，邮件发送给商户				
  //public static final String MERCHANT_ID = "502053000185";//502053000121 //302020000058   //内部测试商户号，商户需要替换该参数
   public static final String MERCHANT_ID ="502050002695";  //正式商户号
//  public static final String MERCHANT_ID = "002020000008";     //互联网金融行业的商户号
  //商户接收订单通知接口地址（异步通知）；  测试
   public static final String MERCHANT_NOTIFY_URL = "http://jifen.aixiaoping.cn:8080/jupinhuiAPI/invoke/order/yiLianNotify";
  //商户接收订单通知接口地址(同步通知),H5版本对接需要该参数
 // public static final String MERCHANT_RETURN_URL = "http://127.0.0.1:8080/ReturnH5.do";
  //商户RSA私钥，商户自己产生（可采用易联提供RSA工具产生）  测试
 	//public static final String MERCHANT_RSA_PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAJgzUFz1eK/C/TAC0nI3LIm987w26WLpNN1aqH0lQdVWAtk/58IlZaeq0N3T91JBMi3ACryBoPsvLI78ngQkkHvyhxKyQFOEdCEO/ZgjrFUZYxymEb26PGhzE8EaTqZ+9mzVycGJYZPB8z3aUC0JxNIOXyJON1u6Ds3g53kaLFX3AgMBAAECgYEAgCcEzaDq46NdKGXX6M/IMpq8dYgO73fJSXCiWe1bB1SKxX5nmDOA6rKLd5yYrKu0oo0G/T5w288Wx7axHm/jLw3DOHW04rudX/8FncEcyzC7s6aW6ZUuFDlCQ+8qX84mBGmYA5EqF2Q6/8FTy+AbUnAjV4vqNGWQ8h30kHJR7FECQQDJxKFJEZjDXKANEr9sPiHdrPCflTMwNgdjOPnDawvlDVUvHA8WzMQNDWdBt0OahfIJ3es6yMBqFpIuVLD2LhYtAkEAwRwBmhxKk4QQGNF3cEM/wuJht8/7HQiEFOZX4Xqa7GyOJa3Zcs45M04EHUVkOaXotsV8qtHDbIW2JkfuYct3MwJAAmE8WcVIXP2Jsb3H3jn5Ykj46Zjz6pyoh6YTZBeuIzx+Bbk6AFXX9iNzVVYZCQiNa0pfzOizRE0R2lQLZkvy3QJBAKzp4flX2eNLPoqqjXNllCNySCOqROWTaYm5U/mfqjeHYKUWjX5CcG+jLQX0y7DfAYkfPulKGN4EwLx4blj9MocCQQC7VsVolGzoZSwQEXzHvPUopGQFeGWT7EdV/OwBQBS1LB3bEPnsUZByFVLj23/IGm8vJNakH2utiO/7Fx/FBgDi";
 	//正式PKCS8格式的商户私钥
    //public static final String MERCHANT_RSA_PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKB9YRlJ8en5cSdF1RiJlpWeTDkxN968Nl1L9WVvzLh2Rm69BiGlw4VlKvnkKfS2bFuMEdSAb+qoyyuavLMV797hAJiIbRVFJuAk2cJiq6mTcpL5zuUOUOnxhIzNvogdq1dKKHS8LVTy/5gtipoFjUCheHRH4XajgvGKxvTnnH+rAgMBAAECgYAKcb5k7+v/Zw1XGfNRQnr7R678NdjF9QewsKXKsSjohEH3KPQCMpBz2O6gDpowY8LPfmEWrDkcU1nNf6dyHCZdB3B3mFilB2NPUOIf0nNthRoYGFOnp+bzHU6wpL/zWmcscX2uI5lzRnxv+KjXHsklzwPzzvqZN/HinCLP5ROqGQJBANEKAzWBrJ3FjTc83goY/EzuMI1j9g19WuLvM6fPGBedQWp7CMN5LwVPXe3PQqkq5mwZ4cgRf0oN5pjy5A93Nn8CQQDEi0Jh+Jdo/tfeVymUGelTvtXiI4G6JHgsL8qgSZYtZhbLUnLMIYS1lW37vI4QjvlhgEGIIZYULxUG/ce+D9jVAkB7zkFOTGoO23Ui1RLwyhGMArqQ0zZkkOzmYSOn7LjpK4MV7J6pHznhHDuWYF2r0bO9LKsTkoFgO9FqntjB7CpDAkBDOBWWCD1QbYhKxKi1vD7uo1/nHZmqnfpzskyskT5JfJbjJumYIwaOS/L3m79CroNf80t2UXZQpOGeKIF94VGZAkAYbORmy8dlG8kdD2TbOryp6Zluhdx2uouFNBNUgtJWLun9/M7Ketcy3MskMU8ZnY6PhKUcx5YAtN9390GeBcVP";
  public static final String MERCHANT_RSA_PRIVATE_KEY="MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANMozp4vrvgNp5Xl"
	    +"WGa+34Sp2O5GpuYIpynnoySaHPit9EyKWhkpdGUvQ95wjsElN4yJvBUD8LyuCxfG"
	    +"Q/ScHOmPoSfYqrEq2TKzTSpkx0f1CH2s8mKO2cDOQFIcnI+qgJVMNyaL/E4H4iRO"
	    +"O+XFziR8EV3FDDRtuX2+y6VxOoyBAgMBAAECgYEApQe7k/4IbW2ekJRSZtq+rlxg"
	    +"YrltL1OL8kBLTQv1oJWz3S40BH5Vrc8m5+5oY6PGqlvrVzFhMNWFbetSqRZpsOVU"
	    +"9AGRLcKANf/HY+FaWxfkqASsMQhE7Q8gwsA3RyM8WGXr7dIOvOO67wMIN+/pCmmR"
	    +"OidFWZbOCn8M5RqEjAECQQDqM172CgjYlSsISt50dYBeqghkBT4rqT868pFh+lI5"
	    +"CFKNMN6PPbKNarkGTAJcAd0aBAiBXp3aPjj+Qqj8rTSRAkEA5tBmRWxNI3li+O58"
	    +"b6N3KuRoAN+ZUclW/eSawkz04Qdd3UBHozJeDM9pNkSPdyXaLDHmcfZlPA65dgb5"
	    +"5jMQ8QJBAOff0KccrEFy/tYI+lKne18+TWxp3HHx8Y51VweAhSO+X602s0NyvHNT"
	    +"NLlNTBC4L6ZwU9NyUCsh69+hNBpnimECQDKfpq63UDvllcWPWQ+LRMnNitoWMKR1"
	    +"inTpPOA8zMDGQtoSDSRIGcSpgSP76ZNyY+WplCeOqSmA+UBfy1OoA6ECQQCvfTJG"
	    +"iZ4LoCgKzmJH2XM4baZ0dUwVSs0TsxITceL3Wu2nRFO1JCHdJcehLqwgig3ET1Oy"
	    +"wFh+fcRKuy5gnnRN";
  //---互联网金融测试商户RSA私钥（从证书文件导出）
//  public static final String MERCHANT_RSA_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKpZJ9Rbd9LKg5jM4byOjLfGV2kFqctWDyAQNy+b0rFWOq8D+okjvLRGaRzUjuX28B9a03OmEwL7CW6WloCxr/g9t9WP5aGg1DKEb6biw/bsEDzG5681P39bv/ZlWTjfbg1KjDBaRqqjXK5l7XBAxxWFE7PaH6DuP+5kPR+IKiRbAgMBAAECgYAfDloAkRxrRZhwRwnwglyNNI/DCdFGzM29Hrew6kujIQFZ3vPSBL3mb9/B7c6PhlGIpdpe/ywAIxw5GSMfG0XlQ6umgPSsxF6TaRCXkBE1B1QYn5L4jVgHkszTRMCXkTybtaxEqEh6nhA6Krj4Y5ki1wpDpwHToTUYwz3RHuxdgQJBAN8hkxIhQ0ERALsrOWRZoishT9Ci5BxUtCYwKKw4Und1w3ywvxT28kDO2tp8aZ9/JVcHcRW04I+MmS0ZEPzGYNECQQDDcRpeVL6DLC/+fWhsUK6PixSmfH+roZURpJXlRPmQlxQwluoaQ2b/KUouujycnsphXIIpWHCZenfrJrS1yB1rAkBgU/lPOWb0fyempil3xi55mj7/3mLGTFcdqWrVttb7Va7YdOF5Zob9LZBUBKQAxH5VTRQn/9d2gYdbbdfkmKwRAkEAljVaP7/AAE64wE4gMIc98kLBZ0duVDnGuR2WuvPtHuyObt2+JNtC0L8qLYmjRfhgsL2JqD85oyvV+Jvx7XhU6wJBALIT5T+T3HdFRXlRAH12X74VXOnfHZ79sU/NNDBBtRN2AKfNo/I9g9xV7hZiVGTWEuDK8NImWYBU33PejWCZdS8="; //互联网金融



  //----易联信息： 以下信息区分为测试环境和生产环境，商户根据自己对接情况进行数据选择----
  //易联服务器地址_测试环境
  //public static final String PAYECO_URL = "https://testmobile.payeco.com";
  //易联服务器地址_生产环境
  public static final String PAYECO_URL = "https://mobile.payeco.com";

  //订单RSA公钥（易联提供）_测试环境
  //  public static final String PAYECO_RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCRxin1FRmBtwYfwK6XKVVXP0FIcF4HZptHgHu+UuON3Jh6WPXc9fNLdsw5Hcmz3F5mYWYq1/WSRxislOl0U59cEPaef86PqBUW9SWxwdmYKB1MlAn5O9M1vgczBl/YqHvuRzfkIaPqSRew11bJWTjnpkcD0H+22kCGqxtYKmv7kwIDAQAB";
  //订单RSA公钥（易联提供）_生产环境
  public static final String PAYECO_RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCoymAVb04bvtIrJxczCT/DYYltVlRjBXEBFDYQpjCgSorM/4vnvVXGRb7cIaWpI5SYR6YKrWjvKTJTzD5merQM8hlbKDucxm0DwEj4JbAJvkmDRTUs/MZuYjBrw8wP7Lnr6D6uThqybENRsaJO4G8tv0WMQZ9WLUOknNv0xOzqFQIDAQAB";
  
  
  //易联下单  01 正式 00 测试
  public static final String Science="01";
  
}
