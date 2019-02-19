package com.axp.util;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class UploadUtil{
	public static String uploadFile(String resPath, File file, String divName,String currentUserId){
		String result = "";
		try {
			
//			String urlNameString = "http://jhp.aixiaoping.cn:8080/jupinhuiRes/" + "fileHandle/upload?dirName="
//					+ divName + "&currentUserId=" + currentUserId+"&uploadFile="+file;
			String urlNameString = "localhost:8080/daily_ref/" + "fileHandle/upload?dirName="
					+ divName + "&currentUserId=" + currentUserId+"&uploadFile="+file;
			URL url = new URL(urlNameString);

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            conn.setRequestMethod("POST");  
            conn.setUseCaches(false);  
            conn.setInstanceFollowRedirects(true);  
            conn.setRequestProperty("Content-Type","multipart/form-data;");   
              
            conn.connect();  
              
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());  
              
              
            conn.disconnect();  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 图片设置圆角
	 * @param srcImage
	 * @param radius
	 * @param border
	 * @param padding
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage setRadius(BufferedImage srcImage, int radius, int border, int padding) throws IOException{
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();
        int canvasWidth = width + padding * 2;
        int canvasHeight = height + padding * 2;
        
        BufferedImage image = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gs = image.createGraphics();
        gs.setComposite(AlphaComposite.Src);
        gs.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        gs.setColor(Color.WHITE);
        gs.fill(new RoundRectangle2D.Float(0, 0, canvasWidth, canvasHeight, radius, radius));
        gs.setComposite(AlphaComposite.SrcAtop);
        gs.drawImage(setClip(srcImage, radius), padding, padding, null);
        if(border !=0){
            gs.setColor(Color.GRAY);
            gs.setStroke(new BasicStroke(border));
            gs.drawRoundRect(padding, padding, canvasWidth - 2 * padding, canvasHeight - 2 * padding, radius, radius);    
        }
        gs.dispose();
        return image;
    }
	
	/**
	 * 图片切圆角
	 * @param srcImage
	 * @param radius
	 * @return
	 */
	 public static BufferedImage setClip(BufferedImage srcImage, int radius){
	        int width = srcImage.getWidth();
	        int height = srcImage.getHeight();
	        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D gs = image.createGraphics();

	        gs.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        gs.setClip(new RoundRectangle2D.Double(0, 0, width, height, radius, radius));
	        gs.drawImage(srcImage, 0, 0, null);
	        gs.dispose();
	        return image;
	    }
	
	/**
	 * 图片设置圆角
	 * @param srcImage
	 * @return
	 * @throws IOException
	 */
	 public static BufferedImage setRadius(BufferedImage srcImage) throws IOException{
	        int radius = (srcImage.getWidth() + srcImage.getHeight()) / 2; //设置图片的圆弧
	        return setRadius(srcImage, radius, 0, 0);
	    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}