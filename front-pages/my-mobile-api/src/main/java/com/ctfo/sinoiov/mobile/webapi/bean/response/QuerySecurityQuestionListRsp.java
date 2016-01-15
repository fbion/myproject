package com.ctfo.sinoiov.mobile.webapi.bean.response;

import java.util.List;

import com.ctfo.sinoiov.mobile.webapi.bean.vo.SecurityQuestionVo;

/**
 * 查询可以选择的密保问题列表返回
 * 
 * @author sunchuanfu
 */
public class QuerySecurityQuestionListRsp extends BaseBeanRsp {
	private static final long serialVersionUID = -4906559539685940683L;

	private List<SecurityQuestionVo> questions = null;

	public List<SecurityQuestionVo> getQuestions() {
		return questions;
	}

	public void setQuestions(List<SecurityQuestionVo> questions) {
		this.questions = questions;
	}

}
