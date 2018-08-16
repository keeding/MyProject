package com.wqp.bos.web.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.wqp.bos.base.BaseAction;
import com.wqp.bos.domain.Region;
import com.wqp.bos.domain.Subarea;
import com.wqp.bos.service.SubareaService;
import com.wqp.bos.utils.FileUtils;

@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea> {

	public String pageQuery() throws IOException {
		DetachedCriteria detachedCriteria2 = pb.getDetachedCriteria();
		String addressKey = model.getAddresskey();
		Region region = model.getRegion();
		if (StringUtils.isNotBlank(addressKey)) {
			detachedCriteria2.add(Restrictions.like("addresskey", "%" + addressKey + "%"));
		}
		if (region != null) {
			detachedCriteria2.createAlias("region", "r");
			String province = region.getProvince();
			String city = region.getCity();
			String district = region.getDistrict();
			if (StringUtils.isNotBlank(province)) {
				detachedCriteria2.add(Restrictions.like("r.province", "%" + province + "%"));
			}
			if (StringUtils.isNotBlank(city)) {
				detachedCriteria2.add(Restrictions.like("r.city", "%" + city + "%"));
			}
			if (StringUtils.isNotBlank(district)) {
				detachedCriteria2.add(Restrictions.like("r.district", "%" + district + "%"));
			}
		}
		subareaService.pageQuery(pb);
		String[] excludes = new String[] { "detachedCriteria", "currentPage", "pageSize", "decidedzone", "subareas" };
		this.writePageBean2Json(pb, excludes);
		return NONE;
	}

	public String exportXls() throws IOException {
		String flag = "1";
		List<Subarea> list = subareaService.findAll();
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("分区编号");
		headRow.createCell(1).setCellValue("区域编号");
		headRow.createCell(2).setCellValue("地址关键字");
		headRow.createCell(3).setCellValue("省市区");
		for (Subarea sub : list) {
			HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
			row.createCell(0).setCellValue(sub.getId());
			row.createCell(1).setCellValue(sub.getRegion().getId());
			row.createCell(2).setCellValue(sub.getAddresskey());
			Region region = sub.getRegion();
			row.createCell(3).setCellValue(region.getProvince() + region.getCity() + region.getDistrict());
		}
		String filename = "分区数据.xls";
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		filename = FileUtils.encodeDownloadFilename2(filename, agent);
		// 一个流两个头
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
		String contentType = ServletActionContext.getServletContext().getMimeType(filename);
		ServletActionContext.getResponse().setContentType(contentType);
		ServletActionContext.getResponse().setHeader("content-disposition", "attchment;filename=" + filename);
		workbook.write(out);
		return NONE;
	}

	public String save() {
		subareaService.save(model);
		return "list";
	}
	
	public String listAjax() throws IOException{
		List<Subarea> list = subareaService.findListNotAssociation();
		writeListJson(list, new String[]{"region","decidedzone"});
		return NONE;
	}

}
