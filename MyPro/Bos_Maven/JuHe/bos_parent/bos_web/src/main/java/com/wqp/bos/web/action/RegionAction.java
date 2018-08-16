package com.wqp.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.wqp.bos.domain.Region;
import com.wqp.bos.utils.PinYin4jUtils;
import com.wqp.bos.web.action.base.BaseAction;

@Controller
public class RegionAction extends BaseAction<Region>{
	
	private File myFile;
	
	public String upload() throws IOException {
		String flag = "1";
		try{
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(myFile));
			HSSFSheet sheetAt = workbook.getSheetAt(0);
			List<Region> list = new ArrayList<>();
			for (Row row : sheetAt) {
				int rowNum = row.getRowNum();
				if(rowNum == 0){
					continue;
				}
				String id = row.getCell(0).getStringCellValue();
				String province = row.getCell(1).getStringCellValue();
				String city = row.getCell(2).getStringCellValue();
				String district = row.getCell(3).getStringCellValue();
				String postcode = row.getCell(4).getStringCellValue();
				Region region = new Region(id, province, city, district, postcode, null, null, null);
				
				city  = city.substring(0, city.length() - 1);
				String[] stringToPinyin = PinYin4jUtils.stringToPinyin(city);
				String citycode = StringUtils.join(stringToPinyin, "");
				
				province  = province.substring(0, province.length() - 1);
				district  = district.substring(0, district.length() - 1);
				String info = province + city + district;
				String[] headByString = PinYin4jUtils.getHeadByString(info);
				String shortcode = StringUtils.join(headByString, "");
				
				region.setCitycode(citycode);
				region.setShortcode(shortcode);
				list.add(region);
			}
			regionService.saveBatch(list);
		}catch(Exception e){
			flag = "0";
			e.printStackTrace();
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}
	
	public String queryPage() throws IOException{
		regionService.pageQuery(pb);
		writePageBean2Json(pb,new String[]{"subareas","currentPage","detachedCriteria","pageSize"});
		return NONE;
	}
	
	public String listajax() throws IOException{
		List<Region> list = regionService.findAll();
		writeListJson(list, new String[]{"subareas","currentPage","detachedCriteria","pageSize"});
		return NONE;
	}
	
	public File getMyFile() {
		return myFile;
	}
	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}
	
}
