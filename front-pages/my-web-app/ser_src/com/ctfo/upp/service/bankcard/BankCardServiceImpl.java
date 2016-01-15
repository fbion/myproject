package com.ctfo.upp.service.bankcard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.base.intf.internal.BankCardManager;
import com.ctfo.upp.excelbeans.BankCardExcel;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.ServiceImpl;
import com.ctfo.upp.soa.ServiceFactory;
import com.ctfo.upp.util.DateUtil;

@Service("bankCardService")
public class BankCardServiceImpl extends ServiceImpl implements BankCardService {
	private static Log logger = LogFactory.getLog(BankCardServiceImpl.class);
	private BankCardManager manager = null;

	private BankCardManager getManager() {
		if (this.manager == null) {
			manager = (BankCardManager) ServiceFactory.getFactory().getService(BankCardManager.class);
		}
		return this.manager;
	}

	@Override
	public PaginationResult<?> queryBankCardByPage(DynamicSqlParameter requestParam) throws UPPException {
		PaginationResult<Object> result = new PaginationResult<Object>();
		try {
			DynamicSqlParameter totalRequest = new DynamicSqlParameter();
			int total = getManager().sqlTotalPage(totalRequest);
			List<Map<?, ?>> list = getManager().querySqlBankCardByPage(requestParam);
			result.setData((list != null) ? Arrays.asList(list.toArray(new Object[] {})) : null);
			result.setTotal(total);
		} catch (Exception e) {
			logger.error("分页查询银行卡信息异常", e);
			throw new UPPException("分页查询银行卡信息异常", e);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<BankCardExcel> exportExcel(DynamicSqlParameter requestParam) throws UPPException {
		List<BankCardExcel> excelList = new ArrayList<BankCardExcel>(); 
		List list = new ArrayList();
		list = getManager().querySqlBankCardByPage(requestParam);
		excelList = (List<BankCardExcel>) copy(list);
		return excelList;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<BankCardExcel> copy(List list){
		List excelList = new ArrayList();
		if(list.size()>=1){
			for(int i=0;i<list.size();i++){
				Map obj = new HashMap();
				BankCardExcel excel = new BankCardExcel();
				obj = (Map) list.get(i);
				excel.setAccCardType((String) obj.get("ACC_CARD_TYPE"));
				excel.setAccountId((String) obj.get("ACCOUNT_ID"));
				excel.setBranchBankName((String) obj.get("BRANCH_BANK_NAME"));
				String province = obj.get("BRANCH_BANK_PROVINCE")+"/"+obj.get("BRANCH_BANK_CITY");
				excel.setBranchBankProvince(province);
				Object createTime=obj.get("CREATE_TIME")==null||"null".equals(obj.get("CREATE_TIME"))?"":DateUtil.longToDate(Long.parseLong((String) obj.get("CREATE_TIME")),"yyyy-MM-dd HH:mm:ss");
				excel.setCreateTime(String.valueOf(createTime));
				excel.setIdcardNo((String) obj.get("IDCARD_NO"));
				excel.setIsMainCard((String) obj.get("IS_MAIN_CARD"));
				excel.setPartBankAccountNo((String) obj.get("PART_BANK_ACCOUNT_NO"));
				excel.setStoreName((String) obj.get("STORE_NAME"));
				excelList.add(excel);
			}
		}
		return excelList;
	}

}
