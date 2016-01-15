package com.sinoiov.upp.noaop.controller;

import java.io.File;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ctfo.file.bean.AttachmentBean;
import com.ctfo.upp.base.IFileUploadService;
import com.ctfo.upp.exception.UPPException;
import com.sinoiov.pay.utils.UPPConfigUtil;

@Controller
@Scope("prototype")
@SuppressWarnings("all")
@RequestMapping(value = "/fileUpload")
public class FileUpload {
	private static Log logger = LogFactory.getLog(FileUpload.class);

	@Resource
	private IFileUploadService fileUploadService;

	/**
	 * 上传文件接口
	 * <p>
	 * 描述：非加密接口，用于上传线下汇款凭证
	 */
	@RequestMapping(value = "/upload.action", method = RequestMethod.POST)
	@ResponseBody
	public String upload(@RequestParam(required = false, value = "file") final MultipartFile file) {
		String imageUrl = "";
		File f = null;
		try {
			logger.info("<<<<<文件名称:" + file.getOriginalFilename());
			CommonsMultipartFile cf = (CommonsMultipartFile) file;
			f = new File(cf.getName());
			cf.transferTo(f);
			AttachmentBean bean = new AttachmentBean();
			bean.setFile(f);
			bean.setFileName(file.getOriginalFilename());
			
			String imgName = fileUploadService.uploadFile(bean);

			if (StringUtils.isBlank(imgName))
				throw new UPPException("保存图片到mongo失败");
			String imagePath = UPPConfigUtil.getValue("IMAGE_URL");
			if (StringUtils.isBlank(imagePath))
				throw new UPPException("获取图片配置路径失败");

			imageUrl = imagePath + imgName;
			logger.info("=========银行转账上传汇款凭证:" + imageUrl);
		} catch (Exception e) {
			logger.error("银行转账上传汇款凭证异常!", e);
		} finally {
			try {
				if (f != null)
					f.delete();
			} catch (Exception e) {
				logger.error("删除临时文件失败！");
			}
		}
		return imageUrl;
	}

}
