package test;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

public class TestJson {

	public static void main(String[] args) {
		JSONObject.parseObject(JSONObject.toJSONString("111"),
				new TypeReference<ArrayList<?>>() {
				});
	}
}
