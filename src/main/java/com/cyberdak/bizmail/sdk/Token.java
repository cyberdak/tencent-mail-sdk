/**
 * 
 */
package com.cyberdak.bizmail.sdk;

import java.util.Date;

/**
 * @author 亢伟楠
 * @description 
 *	时间:2015年9月24日上午11:20:35
 */
public class Token {
		private String access_token;
		private Date createDate;
		private Long expires_in;
		private String token_type;
		public String getAccess_token() {
			return access_token;
		}
		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}
		public Date getCreateDate() {
			return createDate;
		}
		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}
		public String getToken_type() {
			return token_type;
		}
		public void setToken_type(String token_type) {
			this.token_type = token_type;
		}

		public Long getExpires_in() {
			return expires_in;
		}
		public void setExpires_in(Long expires_in) {
			this.expires_in = expires_in;
		}
		public boolean isValid(){
			Date now = new Date();
			Long nowTimeStamp = now.getTime();
			Long createTimeStmap = createDate.getTime();
			return nowTimeStamp - createTimeStmap < expires_in;
		}
		
}
