package com.ctfo.upp.service.login;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.ctfo.csm.annotations.AnnotationName;
import com.ctfo.csm.local.Rule;
import com.ctfo.csm.uaa.intf.support.Operator;
import com.ctfo.file.boss.IMongoService;
import com.ctfo.upp.log.ServiceLog;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.ISLogManageService;
import com.ctfo.upp.service.ServiceImpl;
import com.ctfo.util.JsonUtil;
import com.google.code.morphia.query.Query;

@AnnotationName(name = "服务日志管理")
@Service("sLogService")
public class SLogManageServiceImpl extends ServiceImpl implements ISLogManageService {
	private static Log logger=LogFactory.getLog(SLogManageServiceImpl.class);
	
	private IMongoService<ServiceLog> getMongoService() {
		return getRemoManager(IMongoService.class);
	}

	public PaginationResult<ServiceLog> queryListPage(
			DynamicSqlParameter requestParam, Operator op) {
		IMongoService<ServiceLog> mongoService = getMongoService();
		PaginationResult<ServiceLog> result = new PaginationResult<ServiceLog>();
		List<ServiceLog> callRecordsList = null;
		try {
			Query<ServiceLog> query = mongoService
					.getQuery(ServiceLog.class);
			this.convertParam(query, requestParam);
			callRecordsList = mongoService.query(
					ServiceLog.class, query);
			result.setData(callRecordsList);
			result.setStart(requestParam.getStartNum());
			Long resultCount = mongoService.getCount();
			result.setTotal(resultCount.intValue());
		} catch (Exception e) {
			logger.error(String.format("查询服务日志时出错,原因:%s", e.getMessage()));
		}
		return result;
	}

	/***
	 * 扩展FileService jar包中参数转换功能
	 * 
	 * @param query
	 * @param param
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void convertParam(Query<ServiceLog> query,DynamicSqlParameter param) {
		if (param.getEqual() != null) {
			Map map = param.getEqual();
			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				if ((key != null) && (map.get(key) != null))
					query = (Query) query.field(key).equal(map.get(key));
			}
		}
		if (param.getLike() != null) {
			Map map = param.getLike();
			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				if ((key != null) && (map.get(key) != null)) {
					String value = (String) map.get(key);
					value = StringUtils.removeEnd(value, "%");
					value = StringUtils.removeStart(value, "%");
					query = (Query) query.field(key).contains(
							JsonUtil.jsonCharFormat(value));
				}
			}
		}
		if (param.getInMap() != null) {
			Map map = param.getInMap();
			Iterator it = map.keySet().iterator();

			while (it.hasNext()) {
				String key = (String) it.next();
				if ((key != null) && (map.get(key) != null)) {
					Object value = map.get(key);
					if ((value instanceof Iterable)) {
						if (((Iterable) map.get(key)).iterator().hasNext())
							query = (Query) query.field(key).in(
									(Iterable) value);
					} else if ((value instanceof String[])) {
						List temList = new ArrayList();
						for (String val : ((String[]) (String[]) value)[0]
								.split(",")) {
							temList.add(val);
						}
						query = (Query) query.field(key).in(temList);
					}
				}
			}
		}
		if (param.getStartwith() != null) {
			Map map = param.getStartwith();
			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				if ((key != null) && (map.get(key) != null))
					query = (Query) query.field(key).greaterThanOrEq(
							map.get(key));
			}
		}
		if (param.getEndwith() != null) {
			Map map = param.getEndwith();
			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				if ((key != null) && (map.get(key) != null))
					query = (Query) query.field(key).lessThan(map.get(key));
			}
		}
		if (param.getOrder() != null) {
			if ((param.getSort() != null)
					&& (param.getSort().equalsIgnoreCase("desc"))
					&& (!StringUtils.startsWith(param.getOrder(), "-"))) {
				query.order("-" + param.getOrder());
			} else
				query.order(param.getOrder());
		}
		if (param.getRules() != null) {
			Iterator<Rule> it = param.getRules().iterator();
			while (it.hasNext()) {
				Rule e = it.next();
				if (e != null) {
					if (e.getOp() == null || e.getOp().trim().equals(""))
						continue;
					if (e.getField() == null || e.getField().trim().equals(""))
						continue;
					if (e.getValue() == null || e.getValue().trim().equals(""))
						continue;
					if (e.getOp().equalsIgnoreCase("greater"))
						query = (Query) query.field(e.getField()).greaterThan(
								Long.valueOf(e.getValue() == null ? "0" : e
										.getValue()));
					else if (e.getOp().equalsIgnoreCase("greaterorequal"))
						query = (Query) query.field(e.getField())
								.greaterThanOrEq(
										Long.valueOf(e.getValue() == null ? "0"
												: e.getValue()));
					else if (e.getOp().equalsIgnoreCase("less"))
						query = (Query) query.field(e.getField()).lessThan(
								Long.valueOf(e.getValue() == null ? "0" : e
										.getValue()));
					else if (e.getOp().equalsIgnoreCase("lessorequal"))
						query = (Query) query.field(e.getField()).lessThanOrEq(
								Long.valueOf(e.getValue() == null ? "0" : e
										.getValue()));
					else if (e.getOp().equalsIgnoreCase("equal"))
						query = (Query) query.field(e.getField()).equal(
								Long.valueOf(e.getValue() == null ? "0" : e
										.getValue()));
				}
			}
		}
		if ((param.getRows() != 1) && (param.getPage() != 0)) {
			query.offset((param.getPage() - 1) * param.getRows());
			query.limit(param.getRows());
		}
	}

	public boolean batchDeleteLog(String[] ids, Operator op) throws Exception {
		IMongoService<ServiceLog> mongoService = getMongoService();
		if (ids == null || ids.length == 0)
			throw new Exception("参数为空!");
		boolean result = true;
		try {
			for (String id : ids) {
				mongoService.delete(ServiceLog.class,
						new ObjectId(id));
			}
			result = true;
		} catch (Exception e) {
			result = false;
			throw e;
		}
		return result;
	}

	public PaginationResult<ServiceLog> queryLogById(
			ObjectId _id, Operator op) throws Exception {
		PaginationResult<ServiceLog> result = new PaginationResult<ServiceLog>();
		IMongoService<ServiceLog> mongoService = getMongoService();
		result.setDataObject(mongoService.get(ServiceLog.class,
				_id));
		return result;
	}



	private String formatSpecialDate(Date date) {
		DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		return sf.format(date);
	}

	private Date getCurDate() {
		return new Date();
	}

	@SuppressWarnings("all")
	private String dateOper(Integer day) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(getCurDate());
		gc.add(gc.DAY_OF_YEAR, day);
		return formatSpecialDate(gc.getTime());
	}

}
