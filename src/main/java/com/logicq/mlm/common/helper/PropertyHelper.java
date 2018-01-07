package com.logicq.mlm.common.helper;

import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicq.mlm.model.profile.NetWorkDetails;
import com.logicq.mlm.model.profile.NetworkInfo;

public class PropertyHelper {
	 static ObjectMapper mapper = new ObjectMapper();
	 
	public static Properties loadProperty(){
		Properties prop=new Properties();
		prop.put("filepath", "");
		return prop;
	}
	
 
  
  public static NetWorkDetails convertJsonToNetworkInfo(NetworkInfo networkinfo) throws Exception{
	 return  mapper.readValue(new String( networkinfo.getNetworkjson()),NetWorkDetails.class );
  }

  public static String convertNetworkInfoToJson(NetWorkDetails networkDetails) throws Exception{
		 return  mapper.writeValueAsString(networkDetails);
	  }
  
	 
	public static Properties loadUploadProperty(){
		Properties prop=new Properties();
		prop.put("file.uploadsize", 1000000);
		prop.put("file.filepath", "assets/images/uploadImage/");
		//prop.put("fileDirectory", "F:/Project/server/apache-tomcat-7.0.67-windows-x64/apache-tomcat-7.0.67/webapps/mlmlogicq/");
		//prop.put("url", "http://127.0.0.1:8090/mlmlogicq/");
		prop.put("fileDirectory", "/home/logicqso/webapps/mlmlogicq/");
		prop.put("url", "http://getpay.co.in/");
		return prop;
	}
	

  
}
