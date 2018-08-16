package com.wqp.bos.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wqp.bos.dao.DecidedzoneDao;
import com.wqp.bos.dao.WorkbillDao;
import com.wqp.bos.dao.impl.NoticeDao;
import com.wqp.bos.domain.Decidedzone;
import com.wqp.bos.domain.Noticebill;
import com.wqp.bos.domain.Staff;
import com.wqp.bos.domain.Workbill;
import com.wqp.bos.service.NoticebillService;

import cn.wqp.crm.CustomerService;

@Service
@Transactional
public class NoticebillServiceImpl implements NoticebillService{
	@Autowired
	private DecidedzoneDao decidedzoneDao;
	@Autowired
	private CustomerService proxy;
	@Autowired
	private NoticeDao noticeDao;
	@Autowired
	private WorkbillDao workbillDao;
	
	public void add(Noticebill model){
		noticeDao.save(model);
		String pickaddress = model.getPickaddress();
		String decidedzoneId = proxy.findDecidedzoneIdByPickaddress(pickaddress);
		if(decidedzoneId != null){
			Decidedzone decidedzone = decidedzoneDao.findById(decidedzoneId);
			Staff staff = decidedzone.getStaff();
			model.setStaff(staff);//业务通知单关联匹配到的取派员
			model.setOrdertype("自动");//分单类型

			
			Workbill workbill = new Workbill();
			workbill.setAttachbilltimes(0);//追单次数
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//工单创建的时间
			workbill.setNoticebill(model);//工单关联业务通知单
			workbill.setPickstate("未取件");//取件状态
			workbill.setRemark(model.getRemark());//备注
			workbill.setStaff(staff);//工单关联取派员
			workbill.setType("新单");
			workbillDao.save(workbill);//保存工单
		}else{
			//没有查询到定区id，转为人工分单
			model.setOrdertype("人工");
		}
	}
}
