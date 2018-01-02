package com.rest.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 验证码工具类
 * 
 * 
 */
public class AuthCodeImgUitl {

	private static Random random = new Random();
	// private String randString =
	// "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";//随机产生的字符串
	private static String randString = "23456789abcdefghjkmnprstuvwxy";// 随机产生的字符串
	private static int width = 80;// 图片宽
	private static int height = 32;// 图片高
	private static int lineSize = 40;// 干扰线数量
	private static int stringNum = 4;// 随机产生字符数量

	/**
	 * 获得字体
	 * @return
	 */
	private static Font getFont() {
		return new Font("Fixedsys", Font.CENTER_BASELINE, 18);
	}

	/**
	 * 获得颜色
	 * @param fc
	 * @param bc
	 * @return
	 */
	private static Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc - 16);
		int g = fc + random.nextInt(bc - fc - 14);
		int b = fc + random.nextInt(bc - fc - 18);
		return new Color(r, g, b);
	}

	/**
	 * 绘制字符串
	 * @param g
	 * @param randomString
	 * @param i
	 * @return
	 */
	private static String drowString(Graphics g, String randomString, int i) {
		g.setFont(getFont());
		g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));
		String rand = String.valueOf(getRandomString(random.nextInt(randString.length())));
		randomString += rand;
		g.translate(random.nextInt(3), random.nextInt(6));
		g.drawString(rand, 13 * i, 16);
		return randomString;
	}

	/**
	 * 绘制干扰线
	 * @param g
	 */
	private static void drowLine(Graphics g) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int xl = random.nextInt(13);
		int yl = random.nextInt(15);
		g.drawLine(x, y, x + xl, y + yl);
	}

	/**
	 * 获取随机的字符
	 * @param num
	 * @return
	 */
	private static String getRandomString(int num) {
		return String.valueOf(randString.charAt(num));
	}

	/**
	 * 生成随机图片 返回 code:验证码 ,image:验证码图片
	 * @return
	 */
	public static Map<String, Object> getRandcode() {
		// BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));
		g.setColor(getRandColor(110, 133));
		// 绘制干扰线
		for (int i = 0; i <= lineSize; i++) {
			drowLine(g);
		}
		// 绘制随机字符
		String code = "";
		for (int i = 1; i <= stringNum; i++) {
			code = drowString(g, code, i);
		}
		g.dispose();
		Map<String, Object> codeMap = new HashMap<String, Object>();
		codeMap.put("code", code);
		codeMap.put("image", image);
		return codeMap;

	}
	public static void main(String[] args) {
		System.out.println(AuthCodeImgUitl.getRandcode().get("code"));
	}
}
