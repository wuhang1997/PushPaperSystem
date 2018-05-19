package cn.zjut.wangjie.pushpaper.util;


import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


public class DownloadFileUtil {
	public static void main(String[] args) throws Exception {
		 // download("http://proceedings.mlr.press/v70/jaderberg17a/jaderberg17a.pdf");
		  
		}
		public static void download(String urlString,String path )throws Exception{
		  URL url = new URL(urlString);
		  URLConnection con = url.openConnection();
		  InputStream is = con.getInputStream();

		  
		  byte[] bs = new byte[1024];
		  int len;
		  //"D:\\resource\\paper"
		  String file=path+"\\"+getFileNameFromUrl(urlString);
		  OutputStream os = new FileOutputStream(file);
		  while((len = is.read(bs))!=-1){
		   os.write(bs, 0, len);
		  }
		  os.close();
		  is.close();
		 
		}
		public static String getFileNameFromUrl(String url){
			 String[] str=url.split("/");
			 return str[str.length-1];
		}
	}
