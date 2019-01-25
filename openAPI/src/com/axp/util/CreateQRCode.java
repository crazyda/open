package com.axp.util;
  
import java.awt.Color;  
import java.awt.Graphics2D;  
import java.awt.image.BufferedImage;  
import java.io.File;  
  

import javax.imageio.ImageIO;  
  

import com.swetake.util.Qrcode;  
  
public class CreateQRCode {  
   public static void main(String[] args) throws Exception{  
       Qrcode qrcode = new Qrcode();  
       qrcode.setQrcodeErrorCorrect('M');//纠错等级（分为L、M、H三个等级）  
       qrcode.setQrcodeEncodeMode('B');//N代表数字，A代表a-Z，B代表其它字符  
       qrcode.setQrcodeVersion(7);//版本  
       //生成二维码中要存储的信息  
       String qrData = "https://a.toutiaonanren.com/api/d/CNmtyO";  
       //设置一下二维码的像素  
       int width = 150;  
       int height = 150;  
       BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
       //绘图  
       Graphics2D gs = bufferedImage.createGraphics();  
       gs.setBackground(Color.WHITE);  
       gs.setColor(Color.BLACK);  
       gs.clearRect(0, 0, 300, 300);//清除下画板内容  
         
       //设置下偏移量,如果不加偏移量，有时会导致出错。  
       int pixoff = 2;  
         
       byte[] d = qrData.getBytes("gb2312");  
       if(d.length > 0 && d.length <120){  
           boolean[][] s = qrcode.calQrcode(d);  
           for(int i=0;i<s.length;i++){  
               for(int j=0;j<s.length;j++){  
                   if(s[j][i]){  
                       gs.fillRect(j*3+pixoff, i*3+pixoff, 3, 3);  
                   }  
               }  
           }  
       }  
       gs.dispose();  
       bufferedImage.flush();  
       ImageIO.write(bufferedImage, "png", new File("D:/img/qrcode.png"));  
   }  
   
   public  static String getQR(String url ,String path ,String filepath){
	   
	   try{
	   Qrcode qrcode = new Qrcode();  
       qrcode.setQrcodeErrorCorrect('M');//纠错等级（分为L、M、H三个等级）  
       qrcode.setQrcodeEncodeMode('B');//N代表数字，A代表a-Z，B代表其它字符  
       qrcode.setQrcodeVersion(7);//版本  
       //生成二维码中要存储的信息  
       String qrData = url;  
       //设置一下二维码的像素  
       int width = 150;  
       int height = 150;  
       BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
       //绘图  
       Graphics2D gs = bufferedImage.createGraphics();  
       gs.setBackground(Color.WHITE);  
       gs.setColor(Color.BLACK);  
       gs.clearRect(0, 0, 300, 300);//清除下画板内容  
         
       //设置下偏移量,如果不加偏移量，有时会导致出错。  
       int pixoff = 2;  
         
       byte[] d = qrData.getBytes("gb2312");  
       if(d.length > 0 && d.length <120){  
           boolean[][] s = qrcode.calQrcode(d);  
           for(int i=0;i<s.length;i++){  
               for(int j=0;j<s.length;j++){  
                   if(s[j][i]){  
                       gs.fillRect(j*3+pixoff, i*3+pixoff, 3, 3);  
                   }  
               }  
           }  
       }  
       gs.dispose();  
       bufferedImage.flush();  
       ImageIO.write(bufferedImage, "png", new File(filepath));  
	   
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   return filepath;
   }
   
}  