package com.hr.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public final class SendCodeUtil {
	
	private static String chs=
		"345678abcdefhjkmnpqrstuvwxyABCDEFGHJL";
	
	public static byte[] createPng(String code) throws IOException{
		//1. 利用BufferedImage 创建 img 对象
		BufferedImage img=new BufferedImage
			(100, 40, BufferedImage.TYPE_3BYTE_BGR);
		img.setRGB(50, 20, 0xffffff);
		Graphics2D g=img.createGraphics();
		
		Random random = new Random();
		//生成随机颜色：
		Color c=new Color(random.nextInt(0xffffff));
		//填充图像的背景
		g.setColor(c); 
		g.fillRect(0, 0, 100, 40);
		
		//绘制500个随机点
		for(int i=0; i<500; i++){
			int x=random.nextInt(100);
			int y=random.nextInt(40); 
			int rgb=random.nextInt(0xffffff); 
			img.setRGB(x, y, rgb);
		}
		//设置平滑抗锯齿绘制
		g.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING, 
			RenderingHints.VALUE_ANTIALIAS_ON);
		//设置字体大小
		g.setFont(new Font(Font.SANS_SERIF, 
				Font.PLAIN, 30));
		g.setColor(new Color(
				random.nextInt(0xffffff)));
		g.drawString(code, 10, 30);
		
		//随机绘制10条线段
		for(int i=0; i<10; i++){
			int x1=random.nextInt(100);
			int y1=random.nextInt(40);
			int x2=random.nextInt(100);
			int y2=random.nextInt(40);
			g.drawLine(x1, y1, x2, y2);
		}
		
		//2. 利用ImageIO将 img 编码为png
		ByteArrayOutputStream out=
				new ByteArrayOutputStream();
		ImageIO.write(img, "png", out);
		out.close();
		byte[] bytes=out.toByteArray();
		return bytes;
	}

	public static String genCode(int len){
		char[] code=new char[len];
		Random random = new Random();
		for(int i=0; i<code.length; i++){
			code[i]=chs.charAt(
					random.nextInt(chs.length()));
		}
		return new String(code); 
	}
}
