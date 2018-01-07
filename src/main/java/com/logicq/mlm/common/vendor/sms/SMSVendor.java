package com.logicq.mlm.common.vendor.sms;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PropertiesLoaderUtils;



@PropertySource("classpath:SMSVendor.properties")
public class SMSVendor {
	
	   private static  SMSVendor instance=getInstance();
		
		@Value("smsvendor.url")
		public  String url;
		
		@Value("smsvendor.userid")
		public   String userid;
		
		@Value("smsvendor.password")
		public  String password;
		
		@Value("smsvendor.sid")
		public  String sid;
		

		@Value("smsvendor.smstype")
		public  String smstype;
		

		@Value("smsvendor.apikey")
		public  String apikey;
		

		
		@Value("email.admin")
		public String adminEmail;
		
		@Value("sms.mobilenumber")
		public String mobilenumber;
		
		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getUserid() {
			return userid;
		}

		public void setUserid(String userid) {
			this.userid = userid;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getSid() {
			return sid;
		}
		public void setSid(String sid) {
			this.sid = sid;
		}

		public String getSmstype() {
			return smstype;
		}

		public void setSmstype(String smstype) {
			this.smstype = smstype;
		}

		public String getApikey() {
			return apikey;
		}

		public void setApikey(String apikey) {
			this.apikey = apikey;
		}

		public String getAdminEmail() {
			return adminEmail;
		}

		public void setAdminEmail(String adminEmail) {
			this.adminEmail = adminEmail;
		}

		public String getMobilenumber() {
			return mobilenumber;
		}

		public void setMobilenumber(String mobilenumber) {
			this.mobilenumber = mobilenumber;
		}

		private SMSVendor(){}
		
		public static SMSVendor getInstance() {
			try {
				if (null == instance) {
					instance = new SMSVendor();
					Properties prop = PropertiesLoaderUtils
							.loadAllProperties("SMSVendor.properties");
					
					setPropertyValue(instance,prop);
				}
			} catch (Exception ex) {
	             ex.printStackTrace();
			}
			return instance;
		}
		
		private static void setPropertyValue(SMSVendor instance, Properties prop) throws Exception {
			Field[] fields = instance.getClass().getDeclaredFields();
			for (Field field : fields) {
				Annotation valueanotation = field.getAnnotation(Value.class);
				String propvalue=null;
				if(null!=valueanotation){
				propvalue = ((Value) valueanotation).value();
				}
				field.setAccessible(Boolean.TRUE);
				if (field.getType().isAssignableFrom(String.class)) {
					field.set(instance, (String) prop.get(propvalue));
				}
			}

		}

		@Override
		public String toString() {
			return "SMSVendor [url=" + url + ", userid=" + userid + ", password=" + password + ", sid=" + sid + ", smstyp="
					+ smstype + ", apikey=" + apikey + ", adminEmail=" + adminEmail + ", mobilenumber=" + mobilenumber + "]";
		}



}
