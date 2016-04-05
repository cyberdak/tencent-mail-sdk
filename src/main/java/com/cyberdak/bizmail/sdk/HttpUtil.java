/**
 * 
 */
package com.cyberdak.bizmail.sdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @author 亢伟楠
 * @description 
 *	时间:2015年9月24日下午3:29:00
 */
public class HttpUtil {
		public static final String get(String url,List<Header> header){
			String result = null;
			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet get = new HttpGet(url);
			CloseableHttpResponse response = null;
			try {
				response = client.execute(get);
				result = EntityUtils.toString(response.getEntity(),Consts.UTF_8);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}
		
		public static final String post(String url,Map<String,String> params,List<Header> headers){
			String result = null;
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost post = new HttpPost(url);
			List<NameValuePair> nvps = tranToList(params);
			post.setEntity(new UrlEncodedFormEntity(nvps,Consts.UTF_8));
			CloseableHttpResponse response;
			try {
				response = client.execute(post);
				result = EntityUtils.toString(response.getEntity(),Consts.UTF_8);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}
		
		private static List<NameValuePair> tranToList(Map<String,String> params){
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			for(Entry<String,String> entry : params.entrySet()){
				list.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
			}
			return list;
		}
		
		private static void setHeader(){
			
		}
}
