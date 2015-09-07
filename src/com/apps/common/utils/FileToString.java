package com.apps.common.utils;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;


public class FileToString {

	 /**
	   * 读取源文件内容
	   * @param filename String 文件路径
	   * @throws IOException
	   * @return byte[] 文件内容
	   */
	 	public static byte[] readFile2(String filename) throws IOException {
		    File file =new File(filename);
		    if(filename==null || filename.equals(""))
		    {
		      throw new NullPointerException("无效的文件路径");
		    }
		    long len = file.length();
		    byte[] bytes = new byte[(int)len];
	
		    BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(file));
		    int r = bufferedInputStream.read( bytes );
		    if (r != len)
		      throw new IOException("读取文件不正确");
		    bufferedInputStream.close();
		    return bytes;
		}	       
	     public static void main (String args[]){  
	         //String  str = readFile("C:/Users/Administrator/Desktop/test1.jpg");  
	         //System.out.print(str);
	    	 
	    	 try {
				byte[] b = readFile2("C:/Users/Administrator/Desktop/qq1.jpg");
				String str = (new sun.misc.BASE64Encoder()).encode( b ); 
				System.out.print(str);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	    	 
	     }

}
