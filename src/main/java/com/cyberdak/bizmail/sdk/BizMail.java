package com.cyberdak.bizmail.sdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

/**
 * Hello world!
 *
 */
public class BizMail {
	public static final String TOKEN_URL = "https://exmail.qq.com/cgi-bin/token";
	public static final String SYNC_URL = "http://openapi.exmail.qq.com:12211/openapi/user/sync";
	public static final String GET_URL = "http://openapi.exmail.qq.com:12211/openapi/user/get";
	public static final String LIST_URL = "http://openapi.exmail.qq.com:12211/openapi/user/list";
	
	public static final String CLIENT_SECRET = "eeafff72806f49fc73bd67aee312a34f";
	public static final String CLIENT_ID = "nxjsjwl";
	
	public static final String ADD = "2";
	public static final String DEL = "1";
	public static final String MOD = "3";

	public static final String DISABLE_USER = "2";
	public static final String ENABLE_USER = "1";
	
	public static Token token = null;
	
	public static void main(String[] args) {
//		addUser("kwn@nxjsjwl.com", "kwn", "1", "2", "13619571941", "0001", "310282520a");
		disableUser("kwn@nxjsjwl.com");
	}
	
	private static String getToken(){
		if(token == null){
			token = createToken();
		}
		if(!token.isValid()){
			token = createToken();
		}
		return token.getAccess_token();
	}
	
	/**
	 * 通过OAuth获取access_token
	 * @return
	 */
	public static final Token createToken(){
		Token token = null;
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(TOKEN_URL);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("client_id", CLIENT_ID));
        nvps.add(new BasicNameValuePair("client_secret", CLIENT_SECRET));
        nvps.add(new BasicNameValuePair("grant_type", "client_credentials"));
        post.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		CloseableHttpResponse response;
		try {
			response = client.execute(post);
			String result = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
			System.out.println(result);
			token = JSON.parseObject(result, Token.class);
			token.setCreateDate(new Date());
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return token;
	}
	
	/**
	 * 添加用户
	 * 需要添加相应的参数
	 */
	public static final void addUser(String alias,String name,String gender,String position,String phone,String extId,String password){
		String access_token = getToken();
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(SYNC_URL);
		List<NameValuePair> npvs = new ArrayList<NameValuePair>();
		npvs.add(new BasicNameValuePair("access_token", access_token));
		npvs.add(new BasicNameValuePair("Action", ADD));
		npvs.add(new BasicNameValuePair("Alias", alias));
		npvs.add(new BasicNameValuePair("name", name));
		npvs.add(new BasicNameValuePair("gender", "1"));
		npvs.add(new BasicNameValuePair("position", position));
		npvs.add(new BasicNameValuePair("phone", phone));
		npvs.add(new BasicNameValuePair("extId", extId));
		npvs.add(new BasicNameValuePair("password", password));
		npvs.add(new BasicNameValuePair("openType", "1"));
		post.setEntity(new UrlEncodedFormEntity(npvs,Consts.UTF_8));
		try {
			CloseableHttpResponse response = client.execute(post);
			String result = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 禁用用户
	 * 需要添加相应的参数
	 */
	public static final void disableUser(String alias){
		String access_token = getToken();
		Map<String,String> params = new HashMap<String, String>();
		params.put("access_token", access_token);
		params.put("alias", alias);
		params.put("openType", DISABLE_USER);
		params.put("action", MOD);
		String result = HttpUtil.post(SYNC_URL, params, null);
		System.out.println(result);
	}
}
