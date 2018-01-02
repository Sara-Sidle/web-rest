package com.rest.util;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;

/**
 * 密码加密处理
 * 
 * @author
 * 
 */
public class PasswordHelper {

	private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	
	/**
	 * 使用MD5加密
	 */
	private String algorithmName = "MD5";
	/**
	 * //加密迭代次数
	 */
	private final int hashIterations = 2;
	
	
	/**
	 * 对密码进行加密
	 * 
	 * @param passWord
	 *            密码明文
	 * @return 加密后的密码密文
	 */
	public String encryptPassword(String passWord) {
		DefaultHashService hashService = new DefaultHashService(); // 默认算法SHA-512
//		hashService.setHashAlgorithmName("MD5");//SHA-512
//		hashService.setPrivateSalt(new SimpleByteSource("123")); // 私盐，默认无
//		hashService.setGeneratePublicSalt(false);// 是否生成公盐，默认false
//		hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());// 用于生成公盐。默认就这个
//		hashService.setHashIterations(1); // 生成Hash值的迭代次数

		HashRequest request = new HashRequest.Builder()
		.setAlgorithmName(algorithmName)//使用MD5
		.setSource(ByteSource.Util.bytes(passWord))//密码明文
		.setSalt(ByteSource.Util.bytes("wukunpeng"))//加盐
		.setIterations(hashIterations)//加密迭代次数
		.build();
		
		String hex = hashService.computeHash(request).toHex();
		return hex;
	}

	public static void main(String[] args) {
		PasswordHelper pHelper = new PasswordHelper();
		System.out.println(pHelper.encryptPassword("12345678"));
	}
}