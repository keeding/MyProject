package com.wqp.bos.crm;

import java.util.List;

import cn.wqp.crm.domain.Customer;

public interface CustomerService {
	// 未关联定区客户
	public List<Customer> findnoassociationCustomers();

	// 查询已经关联指定定区的客户
	public List<Customer> findhasassociationCustomers(String decidedZoneId);

	// 将未关联定区客户关联到定区上
	public void assignCustomersToDecidedZone(Integer[] customerIds, String decidedZoneId);

	public Customer findCustomerByTelephone(String telephone);

	public String findDecidedzoneIdByPickaddress(String pickaddress);
}
