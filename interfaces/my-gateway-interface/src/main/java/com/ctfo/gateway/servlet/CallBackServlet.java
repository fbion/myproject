package com.ctfo.gateway.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.ctfo.gateway.bean.yibao.callback.CallBackBeanYB;
import com.ctfo.gateway.bean.yibao.callback.CheckAccountCallBackBeanResponseYB;
import com.ctfo.gateway.bean.yibao.callback.RechargeCallBackBeanResponseYB;
import com.ctfo.gateway.bean.yibao.callback.UnlockAccountCallBackBeanResponseYB;
import com.ctfo.gateway.bean.yibao.callback.WithdrawCallBackBeanResponseYB;

public abstract class CallBackServlet extends HttpServlet {

	private static final long serialVersionUID = 6372975263091409728L;
	
	//1：结算回调（CheckAccountCallBackBeanResponseYB），2：充值回调（RechargeCallBackBeanResponseYB），3：解冻回调（UnlockAccountCallBackBeanResponseYB），4：提现回调（WithdrawCallBackBeanResponseYB）
	protected abstract boolean executeTask(Object object, String type);

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) {
		// 获取请求参数
		@SuppressWarnings("unchecked")
		Map<String, String[]> paramMap = request.getParameterMap();
		// 接收成功，返回response给调用端
		try {
			ServletOutputStream outstream = response.getOutputStream();
			outstream.write("SUCCESS".getBytes());
			outstream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String json = paramMap.get("param")[0];

		CallBackBeanYB bean = (CallBackBeanYB) JSONObject.toBean(
				JSONObject.fromObject(json), CallBackBeanYB.class);
		String taskId = bean.getTaskId();
		String callbackURL = bean.getCallBackUrl();

		Map<String, Class> map = new HashMap<String, Class>();
		if (bean.getType().equals("1")) {
			map.put("object", CheckAccountCallBackBeanResponseYB.class);
		}
		if (bean.getType().equals("2")) {
			map.put("object", RechargeCallBackBeanResponseYB.class);
		}
		if (bean.getType().equals("3")) {
			map.put("object", UnlockAccountCallBackBeanResponseYB.class);
		}
		if (bean.getType().equals("4")) {
			map.put("object", WithdrawCallBackBeanResponseYB.class);
		}
		bean = (CallBackBeanYB) JSONObject.toBean(JSONObject.fromObject(json),
				CallBackBeanYB.class, map);

		// 执行具体任务
		boolean result = false;
		try {
			result = this.executeTask(bean.getCallBackBean(), bean.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 返回执行结果给调用端
		this.callback(result, taskId, callbackURL);
	}

	private void callback(boolean message, String taskId, String callbackURL) {
		URL url = null;
		HttpURLConnection conn = null;
		try {
			url = new URL(callbackURL);
			// 以post方式请求
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			String param = "taskId=" + taskId + "&status="
					+ (message == true ? "1" : "0");
			conn.getOutputStream().write(param.getBytes());
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
			// 获取响应代码
			StringBuffer result = new StringBuffer("");
			InputStreamReader isr = new InputStreamReader(conn.getInputStream());
			BufferedReader in = new BufferedReader(isr);
			String inputLine;
			while (null != (inputLine = in.readLine())) {
				result.append(inputLine);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
