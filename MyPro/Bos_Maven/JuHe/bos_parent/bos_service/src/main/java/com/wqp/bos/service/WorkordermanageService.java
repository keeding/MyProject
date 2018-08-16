package com.wqp.bos.service;

import java.util.List;

import com.wqp.bos.domain.Workordermanage;

public interface WorkordermanageService {

	void save(Workordermanage model);

	List<Workordermanage> findListNotStart();

	void start(String id);

	Workordermanage findById(String businessKey);

	void checkWorkordermanage(String taskId, Integer check, String processInstanceId);

}
