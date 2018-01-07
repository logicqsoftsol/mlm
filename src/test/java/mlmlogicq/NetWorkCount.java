package mlmlogicq;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

	public class NetWorkCount {

	final static String URL_HOST="http://sms.sudhanshulenka.com";

	    public static String SMSSender(String user,String apikey,String sender,String service,String mobile,String message)
	    {
	        String strTemp = "";
	        try {
	            // Construct The Post Data
	            String data = "/sendSMS?username="+user;
	            data += "&" + "apikey="+apikey;
	            data += "&" + "smstype="+service;
	            data += "&" + "sendername="+sender;
	            data += "&" + "numbers="+mobile;
	            data += "&" + "message=" + URLEncoder.encode(message, "UTF-8");


	                        URL url = new URL(URL_HOST+data);
	                        HttpURLConnection con = (HttpURLConnection)url.openConnection();
                             System.out.println(con);
                             BufferedReader br =
                            			new BufferedReader(
                            				new InputStreamReader(con.getInputStream()));
                             String input;
                                       System.out.println(br);
                      	   while ((input = br.readLine()) != null){
                      	      System.out.println(input);
                      	   }
                      	   br.close();
	                } catch (Exception ex) {
	                        ex.printStackTrace();
	                }


	        return  strTemp;
	    }

	    public static void main(String[] args) {
	       
	       // String response = SMSSender("logicq"+"%20"+"softsol", "ce1ab1a0-0093-4063-8033-58c1f44b0cc2", "HOMPLS", "TRANS", "917057014118", "this"+"%20"+"issample"+"%20"+"message");
	       // System.out.println(response);
	    }
	}

	
	
	
	   
