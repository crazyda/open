package com.axp.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
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
			/*String urlNameString = "http://localhost:8080/aixiaopingRes/" + "fileHandle/upload?dirName="
					+ divName + "&currentUserId=" + currentUserId+"&uploadFile="+file;*/
			String urlNameString = "http://27.54.226.210:8080/aixiaopingRes/" + "fileHandle/upload?dirName="
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
              
            System.out.println("status code: "+conn.getResponseCode());  
              
            conn.disconnect();  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}