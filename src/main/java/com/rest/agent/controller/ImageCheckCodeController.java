package com.rest.agent.controller;

import java.awt.image.BufferedImage;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rest.util.AuthCodeImgUitl;
import com.rest.util.AuthCodeImgUitl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rest.util.AuthCodeImgUitl;

/**
 * 生成验证码图片
 * 
 */

@Controller
public class ImageCheckCodeController {

	@RequestMapping("/kaptcha")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control",	"no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");
		Map<String, Object> resu = AuthCodeImgUitl.getRandcode();
		Session se = SecurityUtils.getSubject().getSession();
		se.setAttribute("KAPTCHA_SESSION_KEY", resu.get("code"));
		BufferedImage bi = (BufferedImage) resu.get("image");
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		try {
			out.flush();
		} finally {
			out.close();
		}
		return null;
	}
}
