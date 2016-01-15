package com.ctfo.upp.service;

import com.ctfo.csm.uaa.intf.support.Operator;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;


//@Service("service")
public class ServiceImpl  extends AbstractService implements IService {

	public PaginationResult<?> add(Object model) throws UPPException {
		PaginationResult<Object> result=null;
		try {
			System.out.println("add");
			result=new PaginationResult<Object>();
			result.setMessage("成功!");
		} catch (Exception e) {
			throw new UPPException("UC_E0002:调用通用add方法异常",e);//UC_0001 调用通用add方法异常
		}


		return result;
	}


	public PaginationResult<?> update(Object model) throws UPPException {
		// TODO Auto-generated method stub
		PaginationResult<Object> result=null;
		try {
			System.out.println("update");
			result=new PaginationResult<Object>();
			result.setMessage("成功！");
		} catch (Exception e) {
			throw new UPPException("UC_E0003:调用通用update方法异常",e);//UC_0002 调用通用update方法异常
		}
		return result;
	}


	public PaginationResult<?> delete(String id) throws UPPException {
		// TODO Auto-generated method stub
		PaginationResult<Object> result=null;
		try {
			System.out.println("delete");
			result=new PaginationResult<Object>();
			result.setMessage("成功！");
		} catch (Exception e) {
			throw new UPPException("UC_E0004:调用通用delete方法异常",e);//UC_0002 调用通用delete方法异常
		}
		return result;
	}


	public PaginationResult<?> queryObjectById(Object model) throws UPPException {
		// TODO Auto-generated method stub
		PaginationResult<Object> result=null;
		try {
			
			
			System.out.println("queryObject");
			
			
			result=new PaginationResult<Object>();
			//result.setDataObject(dataObject);
			
			result.setMessage("成功！");
			
			
		} catch (Exception e) {
			throw new UPPException("UC_E0005:调用通用queryObject方法异常",e);//UC_0004 调用通用delete方法异常
		}
		return result;
	}


	public PaginationResult<?> queryList(DynamicSqlParameter requestParam)
			throws UPPException {
		// TODO Auto-generated method stub
		return null;
	}

	public PaginationResult<?> queryListPage(DynamicSqlParameter requestParam)
			throws UPPException {
		// TODO Auto-generated method stub
		PaginationResult<Object> result=null;
		try {
			System.out.println("queryListPage");
			result=new PaginationResult<Object>();
			result.setMessage("成功！");
		} catch (Exception e) {
			throw new UPPException("UC_E0001:调用通用queryListPage方法异常",e);//UC_0001 调用通用queryListPage方法异常
		}
		return result;

	}


	@Override
	public boolean checkExist(Object model, String fieldName, Operator op) {
		// TODO Auto-generated method stub
		return false;
	}


}
