package com.wqp.notes.po;

import java.util.Date;

public class Notes {
    private Integer id;

    private String mark;

    private String headline;

    private String content;

    private Date inittime;

    private Date updatetime;

    private Integer category;

    private Integer power;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark == null ? null : mark.trim();
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline == null ? null : headline.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getInittime() {
        return inittime;
    }

    public void setInittime(Date inittime) {
        this.inittime = inittime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

	@Override
	public String toString() {
		return "Notes [id=" + id + ", mark=" + mark + ", headline=" + headline + ", content=" + content + ", inittime="
				+ inittime + ", updatetime=" + updatetime + ", category=" + category + ", power=" + power + "]";
	}
}