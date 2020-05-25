package com.weison.sbj.util;

import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class ImgUrlUtils {

	/**
	 * 获取文件oss路径(带http)
	 * @param url
	 * @return
	 */
	public static String getHttpUrl(String url, String host) {
		String fileUrl = "";
		if (StringUtils.isNotBlank(url)) {
			if (url.startsWith("http://") || url.startsWith("https://")) {
				fileUrl = url;
			} else {
				if(url.startsWith("/")){
					fileUrl = host + url;
				} else {
					fileUrl = host + File.separator + url;
				}
			}
		}
		return fileUrl;
	}
	/**
	 * 函数功能说明:取相对路径
	 * @return String    
	 */
	public static String getSubImgUrl(String url, String key){
		if(StringUtils.isNotBlank(url)){
			if(url.startsWith("http://")){
				url = url.substring(url.lastIndexOf(key));
				return url;
			} else {
				return url;
			}
		}
		return "";
	}

	public static void main(String[] args) {
		String result = convertNetUrl2Base64("http://thirdwx.qlogo.cn/mmopen/vi_32/ajNVdqHZLLBu3DBmbF2PmCQ95y46r9q2WMBIyYWS7XPiaVWUBicslGfxKGyqUqoOoZ9G54yjCD4icwiaDJeK5H9Yibg/132");
		System.out.println(result);
	}
	/**
	 * 根据远程url图片地址，转换成base64
	 * @param url
	 * @return
	 */
	public static String convertNetUrl2Base64(String url) {
		InputStream backInputSteam = null;
		BufferedImage backImg = null;
		byte[] backImgBytes = getImageFromNetByUrl(url);
		if(backImgBytes != null){
			try {
				backInputSteam = new ByteArrayInputStream(backImgBytes);
				backImg = ImageIO.read(backInputSteam);
				ByteArrayOutputStream bs = new ByteArrayOutputStream();
				ImageIO.write(backImg, "png", bs);
				Base64 base64 = new Base64();
				String base64Img = new String(base64.encode(bs.toByteArray()));
				return base64Img;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return url;
	}
	/**
	 * 根据地址获得数据的字节流
	 * @param strUrl 网络连接地址
	 * @return
	 */
	public static byte[] getImageFromNetByUrl(String strUrl){
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			//conn.setRequestProperty("referer", "xxx");
			InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
			byte[] btImg = readInputStream(inStream);//得到图片的二进制数据
			return btImg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 从输入流中获取数据
	 * @param inStream 输入流
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while( (len=inStream.read(buffer)) != -1 ){
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}
}
