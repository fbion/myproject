package com.ctfo.upp.gatewayservice.fastpay.impl;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.gateway.fastpay.base.domain.FastPayDomain;
import com.ctfo.upp.gateway.fastpay.intf.FastPay;
import com.ctfo.upp.gateway.fastpay.intf.Filter;
import com.ctfo.upp.gateway.fastpay.intf.FilterChan;
import com.ctfo.upp.gatewayservice.fastpay.mobile.yibao.impl.FilterHandlerYB;
import com.ctfo.upp.gatewayservice.fastpay.pc.yibao.impl.PCFilterHandlerYB;

public class FilterChanHandler implements FilterChan {
	final private AtomicInteger filterIndex = new AtomicInteger(0);

	static private ThreadLocal<FilterChan> filterChanThreadPer = new ThreadLocal<FilterChan>() {

		@Override
		protected FilterChan initialValue() {
			// TODO Auto-generated method stub
			return new FilterChanHandler();
		}

	};
	static final private Log logger = LogFactory.getLog(FilterChanHandler.class);
	static private final List<Filter> filterList = new CopyOnWriteArrayList<Filter>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			add(new FilterHandlerYB());
			add(new PCFilterHandlerYB());
		}
	};

	private FilterChanHandler() {
	}

	static public FilterChan getInstance() {
		if (filterChanThreadPer.get() == null)
			filterChanThreadPer.set(new FilterChanHandler());
		return filterChanThreadPer.get();
	}

	@Override
	public FastPay doFilter(FastPayDomain fastPayDomain) throws UPPException {
		// TODO Auto-generated method stub
		FastPay fastPay = null;
		try {
			for (;;) {
				fastPay = filterList.get(filterIndex.getAndIncrement()).doFilter(fastPayDomain, this);
				if (null == fastPay && filterIndex.get() < filterList.size()) {
					continue;
				} else
					break;
			}
		} catch (Exception e) {
			logger.debug("根据请求过滤对应实现时失败，原因：", e);
			fastPay = null;
		} finally {
			filterChanThreadPer.remove();
		}
		return fastPay;
	}
}
