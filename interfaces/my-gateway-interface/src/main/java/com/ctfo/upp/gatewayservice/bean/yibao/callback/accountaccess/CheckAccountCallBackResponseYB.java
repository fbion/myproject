package com.ctfo.upp.gatewayservice.bean.yibao.callback.accountaccess;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.ctfo.gateway.bean.yibao.callback.CheckAccountCallBackBeanDetailYB;
import com.ctfo.gateway.bean.yibao.callback.CheckAccountCallBackBeanResponseYB;
import com.google.code.morphia.annotations.Id;

public class CheckAccountCallBackResponseYB implements Serializable{
	@Id
	private String id;
	private String command;
	private String timestamp;
	private String syncDate;
	private String detail;
	private String hmac;
	public CheckAccountCallBackBeanResponseYB toBeanResponse() throws Exception{
		CheckAccountCallBackBeanResponseYB response = new CheckAccountCallBackBeanResponseYB();
		response.setDate(syncDate);
		
		if(!detail.equals("SUCCESS")){
			List<CheckAccountCallBackBeanDetailYB> detailList = new ArrayList<CheckAccountCallBackBeanDetailYB>();
			String[] rows = detail.split("\\|");
			for(int i = 0 ; i < rows.length ; i++){
				String row = rows[i];
				String[] row_sub = row.split("&");
				CheckAccountCallBackBeanDetailYB d = new CheckAccountCallBackBeanDetailYB();
				d.setAccountId(row_sub[0]);
				String[] row_sub_sub = row_sub[1].split(";");
				for(int j = 0 ; j < row_sub_sub.length ; j++){
					String[] row_sub_sub_sub = row_sub_sub[j].split(":");
					PropertyDescriptor pd = new PropertyDescriptor(row_sub_sub_sub[0], CheckAccountCallBackBeanDetailYB.class);  
		            Method setMethod = pd.getWriteMethod();
		            setMethod.invoke(d, row_sub_sub_sub[1]);
				}
				detailList.add(d);
			}
			response.setDetails(detailList);
		}
		
		return response;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSyncDate() {
		return syncDate;
	}
	public void setSyncDate(String syncDate) {
		this.syncDate = syncDate;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
