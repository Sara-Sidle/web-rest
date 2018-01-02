package com.rest.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 加载 配置文件中的 系统配置，路径配置等
 * @author 武庆超
 *
 */
@Component
public class DataUtil {
	
	@Value("#{prop['qt_url_path']}")
	private String qtUrlPath;

	public String getQtUrlPath() {
		return qtUrlPath;
	}

	public void setQtUrlPath(String qtUrlPath) {
		this.qtUrlPath = qtUrlPath;
	}
	

}
