package com.wqp.bos.service;

import com.wqp.bos.domain.Decidedzone;
import com.wqp.bos.page.PageBean;

public interface DecidedzoneService {

	public void add(Decidedzone decidedzone, String[] subareaId);

	public void pageQuery(PageBean pb);
}
