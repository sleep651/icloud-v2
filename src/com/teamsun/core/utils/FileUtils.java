/**
 * Copyright By 2008 ChinaPost_TeamSun Co. Ltd.  
 * All right reserved. 
 */
package com.teamsun.core.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * cppims2
 * 
 * @author GuoJF
 * @since Dec 30, 2008
 * @version 2.0 
 * @Component :Spring 注入 TODO:
 */
public class FileUtils {
	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
	/**
	 * Administrator_guojf Dec 30, 2008 12:17:37 AM
	 * 
	 * @param args
	 * TODO:
	 */

	public final static String IMAGE_FILE_EXT = "jpg;jpeg;png;gif;bmp;ico";

	public final static String ATTACHE_FILE_EXT = "doc;zip;rar;pdf";// 附件文件

	public final static String FORBID_FILE_EXT = "jsp;com;bat;cmd";// 禁止的文件

	public final static String EXE_FILE_EXT = "exe;com;bat;cmd";
	
	public final static String SQL_PROPPERTY_PATH_NAME = "script/sqlcodes.properties";
	
	public final static String APP_PROPPERTY_PATH_NAME = "config/application.properties";

	public static boolean isAttacheFile(String fileName) {
		return checkExtFile(ATTACHE_FILE_EXT, fileName);
	}

	public static boolean isForbidFile(String fileName) {
		return checkExtFile(FORBID_FILE_EXT, fileName);
	}

	public static boolean isImgageFile(String fileName) {
		return checkExtFile(IMAGE_FILE_EXT, fileName);
	}

	public static boolean isExeFile(String fileName) {
		return checkExtFile(EXE_FILE_EXT, fileName);
	}

	/**
	 * @author guojf ex:pathName="com/util/config/MailInfo.properties";//表示
	 *         配置文件在src下的....com.util.config/xxxxx.properties
	 */

	public static Properties getPropsByPathAndNameByRealPath(String pathName) {
		Properties props = new Properties();
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		try {
			props.load(new FileInputStream(path + pathName));
		} catch (FileNotFoundException e) {
			logger.error(e.toString());
		} catch (IOException e) {
			logger.error(e.toString());
		}
		return props;
	}

	public static Properties getPropsByPathAndName(String pathName) {
		ClassPathResource cr = new ClassPathResource(pathName);//会重新加载spring框架
		Properties props = new Properties();
		try {
			props.load(cr.getInputStream());
		} catch (IOException ex) {
			logger.error("资源文件不存在");
		}
		return props;
	}
	
	public static String getPropertiesContext(String key, String pathName) {
		String result = "";
		Properties props = getPropsByPathAndName(pathName);
		result = props.getProperty(key);
		return result;
	}

	public static String getSqlProperties(String key) {
		String result = "";
		Properties props = getPropsByPathAndName(SQL_PROPPERTY_PATH_NAME);
		result = props.getProperty(key);
		return result;
	}
	
	public static String getApplicationProperties(String key) {
		String result = "";
		Properties props = getPropsByPathAndName(APP_PROPPERTY_PATH_NAME);
		result = props.getProperty(key);
		return result;
	}
	
	public static boolean checkExtFile(String ext, String fileName) {
		if (ext == null)
			return false;
		String[] exts = ext.split(";");
		String file = fileName.toLowerCase();
		for (int i = 0; i < exts.length; i++)
			if (file.endsWith("." + exts[i]))
				return true;
		return false;
	}

	public static String getTempFile(String dir, String fileExt) {
		String tempFileName = CommUtil.getRandString(8) + fileExt;
		File file = new File(dir + "/" + tempFileName);
		if (file.exists())
			return getTempFile(dir, fileExt);
		else
			return tempFileName;
	}

	public static boolean saveFile(InputStream in, String fileName) {
		File outFile = new File(fileName);
		try {
			FileOutputStream out = new FileOutputStream(outFile);
			byte[] temp = new byte[11024];
			int length = -1;
			while ((length = in.read(temp)) > 0) {
				out.write(temp, 0, length);
			}
			out.flush();
			out.close();
			in.close();
		} catch (Exception e) {
			logger.error(e.toString());
			return false;
		}
		return true;
	}

	/**
	 * 获得控制台用户输入的信息
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getInputMessage() throws IOException {
		logger.debug("请输入您的命令∶");
		byte buffer[] = new byte[1024];
		int count = System.in.read(buffer);
		char[] ch = new char[count - 2];// 最后两位为结束符，删去不要
		for (int i = 0; i < count - 2; i++)
			ch[i] = (char) buffer[i];
		String str = new String(ch);
		return str;
	}

	/**
	 * 以文件流的方式复制文件
	 * 
	 * @param src
	 *            文件源目录
	 * @param dest
	 *            文件目的目录
	 * @throws IOException
	 */
	public void copyFile(String src, String dest) throws IOException {
		FileInputStream in = new FileInputStream(src);
		File file = new File(dest);
		if (!file.exists())
			file.createNewFile();
		FileOutputStream out = new FileOutputStream(file);
		int c;
		byte buffer[] = new byte[1024];
		while ((c = in.read(buffer)) != -1) {
			for (int i = 0; i < c; i++)
				out.write(buffer[i]);
		}
		in.close();
		out.close();
	}

	public static void delAll(File f) throws IOException {
		if (!f.exists())// 文件夹不存在不存在
			throw new IOException("指定目录不存在:" + f.getName());
		boolean rslt = true;// 保存中间结果
		if (!(rslt = f.delete())) {// 先尝试直接删除
			// 若文件夹非空。枚举、递归删除里面内容
			File subs[] = f.listFiles();
			for (int i = 0; i <= subs.length - 1; i++) {
				if (subs[i].isDirectory())
					delAll(subs[i]);// 递归删除子文件夹内容
				rslt = subs[i].delete();// 删除子文件夹本身
			}
			rslt = f.delete();// 删除此文件夹本身
		}
		if (!rslt)
			throw new IOException("无法删除:" + f.getName());
		return;
	}

	public static void delFile(String filePathName) throws IOException{
		java.io.File file = new java.io.File(filePathName);
		delAll(file);
	}
	
	/**
	 * 文件输出示例 写文件
	 */
	public void PrintStreamDemo() {
		try {
			FileOutputStream out = new FileOutputStream("D:/test.txt");
			PrintStream p = new PrintStream(out);
			for (int i = 0; i < 10; i++)
				p.println("This is " + i + " line");
		} catch (FileNotFoundException e) {
			logger.error(e.toString());
		}
	}

	/*
	 * 利用StringBuffer写文件
	 */
	public void StringBufferDemo() throws IOException {
		File file = new File("/root/sms.log");
		if (!file.exists())
			file.createNewFile();
		FileOutputStream out = new FileOutputStream(file, true);
		for (int i = 0; i < 10000; i++) {
			StringBuffer sb = new StringBuffer();
			sb.append("这是第" + i + "行:前面介绍的各种方法都不关用,为什么总是奇怪的问题 ");
			out.write(sb.toString().getBytes("utf-8"));
		}
		out.close();
	}

	/**
	 * 文件重命名
	 * 
	 * @param path
	 *            文件目录
	 * @param oldname
	 *            原来的文件名
	 * @param newname
	 *            新文件名
	 */
	public void renameFile(String path, String oldname, String newname) {
		if (!oldname.equals(newname)) {// 新的文件名和以前文件名不同时,才有必要进行重命名
			File oldfile = new File(path + "/" + oldname);
			File newfile = new File(path + "/" + newname);
			if (newfile.exists())// 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
				logger.debug(newname + "已经存在！");
			else {
				oldfile.renameTo(newfile);
			}
		}
	}

	/**
	 * 转移文件目录
	 * 
	 * @param filename
	 *            文件名
	 * @param oldpath
	 *            旧目录
	 * @param newpath
	 *            新目录
	 * @param cover
	 *            若新目录下存在和转移文件具有相同文件名的文件时，是否覆盖新目录下文件，cover=true将会覆盖原文件， 否则不操作
	 */
	public void changeDirectory(String filename, String oldpath, String newpath, boolean cover) {
		if (!oldpath.equals(newpath)) {
			File oldfile = new File(oldpath + "/" + filename);
			File newfile = new File(newpath + "/" + filename);
			if (newfile.exists()) {// 若在待转移目录下，已经存在待转移文件
				if (cover)// 覆盖
					oldfile.renameTo(newfile);
				else
					logger.debug("在新目录下已经存在：" + filename);
			} else {
				oldfile.renameTo(newfile);
			}
		}
	}

	/**
	 * 读文件
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public String FileInputStreamDemo(String path) throws IOException {
		File file = new File(path);
		if (!file.exists() || file.isDirectory())
			throw new FileNotFoundException();
		FileInputStream fis = new FileInputStream(file);
		byte[] buf = new byte[1024];
		StringBuffer sb = new StringBuffer();
		while ((fis.read(buf)) != -1) {
			sb.append(new String(buf));
			buf = new byte[1024];// 重新生成，避免和上次读取的数据重复
		}
		return sb.toString();
	}

	/**
	 * 读文件
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public String BufferedReaderDemo(String path) throws IOException {
		File file = new File(path);
		if (!file.exists() || file.isDirectory())
			throw new FileNotFoundException();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String temp = null;
		StringBuffer sb = new StringBuffer();
		temp = br.readLine();
		while (temp != null) {
			sb.append(temp + " ");
			temp = br.readLine();
		}
		return sb.toString();
	}

	/**
	 * DOM4J从目录中读取xml文件
	 * 
	 * @param path
	 *            文件目录
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public Document readXml(String path) throws DocumentException, IOException {
		File file = new File(path);
		BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
		SAXReader saxreader = new SAXReader();
		Document document = (Document) saxreader.read(bufferedreader);
		bufferedreader.close();
		return document;
	}

	/**
	 * 创建文件夹
	 * 
	 * @param path
	 *            目录
	 */
	public void createDir(String path) {
		File dir = new File(path);
		if (!dir.exists())
			dir.mkdir();
	}

	/**
	 * 创建新文件
	 * 
	 * @param path
	 *            目录
	 * @param filename
	 *            文件名
	 * @throws IOException
	 */
	public void createFile(String path, String filename) throws IOException {
		File file = new File(path + "/" + filename);
		if (!file.exists())
			file.createNewFile();
	}

	/**
	 * 删除文件
	 * 
	 * @param path
	 *            目录
	 * @param filename
	 *            文件名
	 */
	public void delFile(String path, String filename) {
		File file = new File(path + "/" + filename);
		if (file.exists() && file.isFile())
			file.delete();
	}

	/** */
	/**
	 * 递归删除文件夹
	 * 
	 * @param path
	 *            要利用File类的delete()方法删除目录时，必须保证该目录下没有文件或者子目录，否则删除失败，
	 *            因此在实际应用中，我们要删除目录，必须利用递归删除该目录下的所有子目录和文件，然后再删除该目录。
	 */
	public void delDir(String path) {
		File dir = new File(path);
		if (dir.exists()) {
			File[] tmp = dir.listFiles();
			for (int i = 0; i < tmp.length; i++) {
				if (tmp[i].isDirectory()) {
					delDir(path + "/" + tmp[i].getName());
				} else {
					tmp[i].delete();
				}
			}
			dir.delete();
		}
	}

	/**
	 * the java.io.File.renameTo(File,String newname) actually move the file to
	 * newname's path and rename it which is inconvenient.<br>
	 * this method just rename the file and keep where it is,ignore the move<br>
	 * and return the new File's reference
	 * <p>
	 * eg. file = renameFile(file,"NewName.xxx");
	 * 
	 * @param file
	 *            File
	 * @param name
	 *            String the newName
	 * @return File the new File's reference
	 */
	public static File renameFile(String file, String name) {
		return renameFile(new File(file), name);
	}

	public static File renameFile(File file, String name) {
		File newname;
		if (file == null || !file.exists()) {
			logger.debug("File not found!");
			return null;
		}
		if (file.getParent() == null) {
			newname = new File(name);
			file.renameTo(newname);
		} else {
			newname = new File(file.getParentFile(), name);
			file.renameTo(newname);
		}
		logger.debug("rename is done: " + file + " -> " + newname);
		return newname;
	}

	/**
	 * use java.io.File.renameTo(File,String newname) to move file
	 * <p>
	 * parameters must be a file and a directory<br>
	 * return a reference point to the new file
	 * 
	 * @param scr
	 *            String
	 * @param dir
	 *            String
	 * @return File a reference point to the new file
	 */
	public static File moveFile(String scr, String dir) {
		return moveFile(new File(scr), new File(dir));
	}

	public static File moveFile(File scr, File dir) {
		if (scr == null || dir == null) {
			logger.debug("a null reference!");
			return null;
		}
		if (!scr.exists() || !dir.exists() || scr.isDirectory() || dir.isFile()) {
			logger.debug("not file or directory or not exist!");
			return null;
		}
		File f = new File(dir, scr.getName());
		if (f.exists()) {
			logger.debug("target file has existed!");
		}
		scr.renameTo(f);
		logger.debug("move file done: " + scr + " -> " + f);
		return f;
	}

	/**
	 * turn file to String
	 * <p>
	 * maybe you can use it to access file randomly through the string<br>
	 * but it maybe fault when trun a big file to string
	 * 
	 * @param file
	 *            String
	 * @return String
	 */
	public static String fileToString(String file) {
		// String lineStr = "";
		String string = "";
		int i;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(file));
			while ((i = in.read()) != -1) {
				string += (char) i;
			}
			string = string.trim();
			return string;
		} catch (FileNotFoundException ex) {
			logger.error("File Not Found!");
		} catch (IOException ex) {
			logger.error("IO exception!");
		} finally {
			try {
				in.close();
			} catch (IOException ex1) {
				logger.error("IOException when closing!");
			}
		}
		return null;
	}

	/**
	 * 文件与String互转 write the string to file<br>
	 * if fail return false else return true
	 * 
	 * @param src
	 *            String
	 * @param file
	 *            String
	 * @return boolean
	 */
	public static boolean stringToFile(String src, String file) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(file));
			out.write(src);
			return true;
		} catch (Exception ex) {
			logger.error("IO exception!");
		} finally {
			try {
				out.close();
			} catch (IOException ex) {
				logger.error("IOException when closing!");
			}
		}
		return false;
	}

}
