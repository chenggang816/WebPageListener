package com.webpagelistener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.commontools.BareBonesBrowserLaunch;
import com.commontools.Time;

public class Main {
	public Main() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		String fileName = "html.txt";
		String url = "http://hqbzb.whu.edu.cn/tzgg.htm";
		int sleepTime = 10 * 60 * 1000 ; 
//		int sleepTime = 5000;
		
		System.out.println("已经开始监听网页：" + url);
		System.out.println();
		while(true){
			try {
				detectWebPage(fileName, url);
				sleepTime = 10 * 60 * 1000;
			} catch (IOException e) {
				System.out.println(e.getMessage());
				sleepTime = 5 * 60 * 1000;
			}
			String nowString = Time.getNowTimeString();
			System.out.println("当前时间：" + nowString);
			System.out.println("下次侦听：" + sleepTime / 60000 + "分钟后\n");
			System.out.println();
			Thread.sleep(sleepTime);
			
		}
		
	}
	private static void detectWebPage(String fileName,String url) throws IOException {
		String lastHtml = getFileContent(fileName,"UTF8");
		String newHtml = getHtmlByUrl(url);
		if(!lastHtml.equals(newHtml) && newHtml.contains("停水")){
			BareBonesBrowserLaunch.openURL(url);
			writeToFile(newHtml,fileName);
			System.err.println("网页有变化!");
		}else {
			System.out.println("网页无变化！");
		}
		
	}
	private static String getFileContent(String fileName, String encoding) throws IOException{
		String content = "";
		BufferedReader reader = null;
		try {
//			reader = new BufferedReader(new FileReader(fileName));
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),encoding));
			String s;
			while ((s = reader.readLine()) != null) {
				content += s;
			}
		} finally{
			if(reader != null){
				reader.close();
			}
		}
		return content;
	}
	private static String getHtmlByUrl(String urlstr)throws IOException{
		URL url = null;
		InputStream in = null;
		BufferedReader reader = null;		
		String html = "";
		try {
			url = new URL(urlstr);
			in = url.openStream();
			InputStream dis = new DataInputStream(in);
			InputStreamReader isr = new InputStreamReader(dis,"UTF8");
//			System.err.println(isr.getEncoding());
			reader = new BufferedReader(isr);
			reader.read();
			String line;
			while ((line = reader.readLine()) != null) {
				//System.out.println(line);
				html += line;
			}
		} catch (MalformedURLException e) {
			// TODO: handle exception
			System.out.println("Bad URL!");
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("IO Error " + e.getMessage());
			throw e;
		}
		finally{
			if(in != null){
				in.close();
			}
			if(reader != null){
				reader.close();
			}
			
		}
		return html;
	}
	private static void writeToFile(String content,String fileName) throws IOException {
		BufferedWriter writer = null;
		try {
//			writer = new BufferedWriter(new FileWriter("html.txt"));
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF8"));
			writer.write(content);
		} finally{
			if(writer != null){
				writer.close();
			}
		}
		
	}
}
