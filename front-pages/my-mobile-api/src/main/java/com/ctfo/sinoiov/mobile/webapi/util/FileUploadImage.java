package com.ctfo.sinoiov.mobile.webapi.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctfo.sinoiov.mobile.webapi.exception.ClientException;

/**
 * 图片上传功能
 * 
 * @author dxs
 */
public class FileUploadImage {
	protected static final Log logger = LogFactory.getLog(FileUploadImage.class);

	public static String upload(String url, Map<String, String> mMap, File[] files) throws ClientException {
		try {
			String BOUNDARY = UUID.randomUUID().toString(); // 分隔符
			String PREFIX = "--", LINEND = "\r\n";
			String MULTIPART_FROM_DATA = "multipart/form-data";

			// 设置HTTP头
			URL requestUrl = new URL(url);
			HttpURLConnection hc = (HttpURLConnection) requestUrl.openConnection();
			hc.setRequestMethod("POST");
			hc.setDoOutput(true);
			hc.setDoInput(true);
			hc.setUseCaches(false);
			hc.setRequestProperty("Connection", "Keep-alive");
			hc.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);

			StringBuffer sb = new StringBuffer();
			// 拼接参数
			for (Map.Entry<String, String> entry : mMap.entrySet()) {
				sb.append(PREFIX)
				  .append(BOUNDARY)
				  .append(LINEND)
				  .append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND + LINEND)
				  .append(entry.getValue())
				  .append(LINEND);
			}
			OutputStream output = hc.getOutputStream();
			byte[] data = sb.toString().getBytes();
			output.write(data);

			// // 拼接文件
			// for(Map.Entry<String, File> entry : fileMap.entrySet()) {
			// StringBuilder sbFile = new StringBuilder();
			// String path = entry.getValue().getAbsolutePath();
			// //文件名进行转码，防止中文乱码
			// String filename =
			// URLEncoder.encode(path.substring(path.lastIndexOf("\\")+1),"UTF-8");
			// sbFile = sbFile.append(PREFIX);
			// sbFile = sbFile.append(BOUNDARY);
			// sbFile = sbFile.append(LINEND);
			// sbFile =
			// sbFile.append("Content-Disposition: form-data; name=\""+entry.getKey()+"\"; filename=\""+filename+"\""+LINEND);
			// sbFile = sbFile.append("Content-Type: text/plain"+LINEND+LINEND);
			// //类型根据上传文件做修改
			// output.write(sbFile.toString().getBytes());
			// output.write(FileToBytes(entry.getValue()));
			// output.write(LINEND.getBytes());
			// }

			for (File uploadFile : files) {// 得到文件类型数据的总长度
				StringBuilder sbFile = new StringBuilder();
				String path = uploadFile.getPath();
				// 文件名进行转码，防止中文乱码
				URLEncoder.encode(path.substring(path.lastIndexOf("\\") + 1), "UTF-8");
				sbFile = sbFile.append(PREFIX);
				sbFile = sbFile.append(BOUNDARY);
				sbFile = sbFile.append(LINEND);
				sbFile = sbFile.append("Content-Disposition: form-data; name=\"" + "file" + "\"; filename=\""
						+ getFileName(uploadFile.getName()) + "\"" + LINEND);
				sbFile = sbFile.append("Content-Type: text/plain" + LINEND + LINEND); // 类型根据上传文件做修改
				output.write(sbFile.toString().getBytes());
				output.write(FileToBytes(uploadFile));
				output.write(LINEND.getBytes());
			}

			// 结束标志
			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
			output.write(end_data);
			output.flush();

			// 得到响应码
			int code = hc.getResponseCode();
			StringBuffer stringBuffer = new StringBuffer();
			if (code == HttpURLConnection.HTTP_OK) {
				try {
					String strCurrentLine;
					BufferedReader reader = new BufferedReader(new InputStreamReader(hc.getInputStream()));
					while ((strCurrentLine = reader.readLine()) != null) {
						stringBuffer.append(strCurrentLine).append("\n");
					}
					// {"head":{"callTime":"1401932438050","result":"0","sequenceNum":"1401932559326"},"sign":"caf20643ef32aec144bb1cbc83323d16"}
					// SchedulingRsp mSchedulingRsp = JSON.parseObject(
					// mCommonResponseBean.getBody(),
					// SchedulingRsp.class);

					reader.close();
				} catch (IOException e) {
					logger.error(e);
					throw new ClientException("上传文件读取返回值错误", e);
				}
			} else {
				logger.info("返回码不正常，返回码为：" + code);
				throw new ClientException("上传文件错误，返回码不正常，返回码为：" + code);
			}
			String mResult = stringBuffer.toString();
			return mResult;
		} catch (Exception e) {
			logger.error("上传文件错误！", e);
			throw new ClientException("上传文件错误！", e);
		}
	}

	public static String getFileName(String fileame) {
		if (!StringUtils.isEmpty(fileame) && !fileame.contains(".")) {
			fileame += ".jpg";
		}
		return fileame;
	}

	/**
     * 将文件转换成byte数组
	 */
	public static byte[] FileToBytes(File f) {
		if (f == null) {
			return null;
		}
		try {
			FileInputStream stream = new FileInputStream(f);
			ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
			byte[] b = new byte[1024];
			int n;
			while ((n = stream.read(b)) != -1) {
				out.write(b, 0, n);
			}

			stream.close();
			out.close();
			return out.toByteArray();
		} catch (IOException e) {
		}
		return null;
	}

	/**
	 * 文件复制功能
	 *
	 * @param oldFileStr
	 *            旧文件路径
	 * @param newFileStr
	 *            新文件路径
	 * @return 返回新文件对象
	 */
	public static File FileCopy(String oldFileStr, String newFileStr) {
		if (oldFileStr == null) {
			return null;
		}
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			File newFile = new File(newFileStr);
			fos = new FileOutputStream(newFile);
			fis = new FileInputStream(oldFileStr);
			byte[] b = new byte[1024];
			int n = 0;
			while ((n = fis.read(b)) != -1) {
				fos.write(b, 0, n);
			}

			fis.close();
			fos.close();
			return newFile;
		} catch (IOException e) {
			logger.info("文件copy失败", e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("关闭输入流失败", e);
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					logger.error("关闭输出流失败", e);
				}
			}

		}
		return null;
	}

}
