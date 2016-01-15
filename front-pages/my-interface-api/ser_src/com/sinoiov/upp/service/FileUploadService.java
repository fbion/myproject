package com.sinoiov.upp.service;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.file.bean.AttachmentBean;
import com.ctfo.file.bean.ImageSizeBean;
import com.ctfo.file.boss.IFileService;
import com.ctfo.upp.base.IFileUploadService;
import com.ctfo.upp.business.util.UPPBusinessException;
import com.ctfo.upp.utils.SpringBUtils;
@Service("fileUploadService")
public class FileUploadService extends AbstractService implements IFileUploadService{
	
	static final private Log logger = LogFactory.getLog(FileUploadService.class);
	
	private static ImageSizeBean imageSizeBean = new ImageSizeBean();
	static {
		imageSizeBean.setBigWidth(400);
		imageSizeBean.setBigHeight(200);
		imageSizeBean.setMaxWater(true);
		imageSizeBean.setMinWidth(110);
		imageSizeBean.setMinHeight(55);
	}
	
	/***
	 * 上传凭证信息
	 */
	@Override
	public String uploadFile(AttachmentBean attachmentBean) throws UPPBusinessException {
		String imgUrl = "";
		try {
			
			IFileService fileService = (IFileService) SpringBUtils.getBean("fileService");
			
			imgUrl = fileService.addFile(attachmentBean, imageSizeBean);
			
		} catch (Exception e) {
			logger.error("上传凭证信息异常。",e);
			throw new UPPBusinessException("上传凭证信息异常", e);
		}
		return imgUrl;
	}

}
