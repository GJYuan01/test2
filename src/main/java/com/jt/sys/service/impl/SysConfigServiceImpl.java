package com.jt.sys.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.common.exception.ServiceException;
import com.jt.common.vo.PageObject;
import com.jt.sys.dao.SysConfigDao;
import com.jt.sys.entity.SysConfig;
import com.jt.sys.service.SysConfigService;
@Service
public class SysConfigServiceImpl implements SysConfigService {
	@Autowired
	private SysConfigDao sysConfigDao;
	@Override
	public int updateObject(SysConfig entity) {
		if(entity==null)
		throw new ServiceException("对象不能为空");
		if(entity.getName()==null)
		throw new ServiceException("对象名不能为空");
		if(entity.getValue()==null)
		throw new ServiceException("对象值不能为空");
		int sysRows=sysConfigDao.updateObject(entity);
		return sysRows;
	}
	@Override
	public int saveObject(SysConfig entity) {
		if(entity==null)
		throw new ServiceException("对象不能为空");
		if(entity.getName()==null)
		throw new ServiceException("对象名不能为空");
		if(entity.getValue()==null)
		throw new ServiceException("对象值不能为空");
		int sysRows=sysConfigDao.insertObject(entity);
		return sysRows;
	}
	@Override
	public int deleteObjedcts(String ids) {
		if(StringUtils.isEmpty(ids))
		throw new ServiceException("请先选择");
		int rows=sysConfigDao.deleteObjects(ids.split(","));
		if(rows==0)
		throw new ServiceException("数据可能已经不存在");
		return rows;
	}
	@Override
	public PageObject<SysConfig> findPageObjects(
			Integer pageCurrent,String name) {
		//1.查询总记录数
		int rowCount=sysConfigDao.getRowCount(name);
		if(rowCount==0)
		throw new ServiceException("没有记录");
	    //2.查询当前页记录
		//2.1定义页面大小(每页最多现实多少条记录)
		int pageSize=3;
		//2.2计算当前页位置
		if(pageCurrent==null||pageCurrent<=0)
		throw new ServiceException("当前页码不合法");
		int startIndex=(pageCurrent-1)*pageSize;
		//2.3查询当前数据
		List<SysConfig> list=
		sysConfigDao.findPageObjects(startIndex,pageSize,name);
		//3.封装数据
		PageObject<SysConfig> pageObject=new PageObject<>();
		pageObject.setRecords(list);
		pageObject.setRowCount(rowCount);
		pageObject.setPageSize(pageSize);
		pageObject.setPageCurrent(pageCurrent);

		return pageObject;
	}
}
