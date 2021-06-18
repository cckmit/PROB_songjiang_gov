package com.enesource.jump.web.action;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

public class JsonUtils {

	public static void main(String[] args) {
		
		FileReader fileReader = new FileReader("D://test_copy.json");
		String jsons = fileReader.readString();
		
		
		JSONObject json = new JSONObject(jsons);
		
		JSONArray array = json.getJSONArray("features");
		
//		System.out.println(array);
		
		for (int i = 0; i < array.size(); i++) {
			JSONObject data = array.getJSONObject(i).getJSONObject("properties");
			JSONObject data1 = array.getJSONObject(i).getJSONObject("geometry");
			
			System.out.println(data.get("info") + "######" + data1.get("coordinates") );
			
		}
	}
}
