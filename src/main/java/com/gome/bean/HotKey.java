package com.gome.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class HotKey {
	private String question;
	private String search;
	private String click;
	private String cat_1_1_id;
	private String cat_1_1_name;
	private String cat_1_2_id;
	private String cat_1_2_name;
	private String cat_1_3_id;
	private String cat_1_3_name;
	private String cat_1_3_search;
	private String cat_1_3_click;
	private String cat_2_1_id;
	private String cat_2_1_name;
	private String cat_2_2_id;
	private String cat_2_2_name;
	private String cat_2_3_id;
	private String cat_2_3_name;
	private String cat_2_3_search;
	private String cat_2_3_click;
	private String cat_3_1_id;
	private String cat_3_1_name;
	private String cat_3_2_id;
	private String cat_3_2_name;
	private String cat_3_3_id;
	private String cat_3_3_name;
	private String cat_3_3_search;
	private String cat_3_3_click;
	private String other_search;
	private String other_click;
	private String time;

	public HotKey() {
	}

	public HotKey(String line) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		String[] split = line.split(",");
		this.question = split[0];
		this.search = split[1];
		this.click = split[2];
		this.cat_1_1_id = split[3];
		this.cat_1_1_name = split[4];
		this.cat_1_2_id = split[5];
		this.cat_1_2_name = split[6];
		this.cat_1_3_id = split[7];
		this.cat_1_3_name = split[8];
		this.cat_1_3_search = split[9];
		this.cat_1_3_click = split[10];
		// this.cat_2_1_id = split[11];
		// this.cat_2_1_name = split[12];
		// this.cat_2_2_id = split[13];
		// this.cat_2_2_name = split[14];
		// this.cat_2_3_id = split[15];
		// this.cat_2_3_name = split[16];
		// this.cat_2_3_search = split[17];
		// this.cat_2_3_click = split[18];
		// this.cat_3_1_id = split[19];
		// this.cat_3_1_name = split[20];
		// this.cat_3_2_id = split[21];
		// this.cat_3_2_name = split[22];
		// this.cat_3_3_id = split[23];
		// this.cat_3_3_name = split[24];
		// this.cat_3_3_search = split[25];
		// this.cat_3_3_click = split[26];
		// this.other_search = split[27];
		// this.other_click = split[28];
		try {
			// if (split.length > 28) {
			// this.time = sdf1.format(sdf.parse(split[29]));
			// }
			if (split.length > 10) {
				this.time = sdf1.format(sdf.parse(split[11]));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getClick() {
		return click;
	}

	public void setClick(String click) {
		this.click = click;
	}

	public String getCat_1_1_id() {
		return cat_1_1_id;
	}

	public void setCat_1_1_id(String cat_1_1_id) {
		this.cat_1_1_id = cat_1_1_id;
	}

	public String getCat_1_1_name() {
		return cat_1_1_name;
	}

	public void setCat_1_1_name(String cat_1_1_name) {
		this.cat_1_1_name = cat_1_1_name;
	}

	public String getCat_1_2_id() {
		return cat_1_2_id;
	}

	public void setCat_1_2_id(String cat_1_2_id) {
		this.cat_1_2_id = cat_1_2_id;
	}

	public String getCat_1_2_name() {
		return cat_1_2_name;
	}

	public void setCat_1_2_name(String cat_1_2_name) {
		this.cat_1_2_name = cat_1_2_name;
	}

	public String getCat_1_3_id() {
		return cat_1_3_id;
	}

	public void setCat_1_3_id(String cat_1_3_id) {
		this.cat_1_3_id = cat_1_3_id;
	}

	public String getCat_1_3_name() {
		return cat_1_3_name;
	}

	public void setCat_1_3_name(String cat_1_3_name) {
		this.cat_1_3_name = cat_1_3_name;
	}

	public String getCat_1_3_search() {
		return cat_1_3_search;
	}

	public void setCat_1_3_search(String cat_1_3_search) {
		this.cat_1_3_search = cat_1_3_search;
	}

	public String getCat_1_3_click() {
		return cat_1_3_click;
	}

	public void setCat_1_3_click(String cat_1_3_click) {
		this.cat_1_3_click = cat_1_3_click;
	}

	public String getCat_2_1_id() {
		return cat_2_1_id;
	}

	public void setCat_2_1_id(String cat_2_1_id) {
		this.cat_2_1_id = cat_2_1_id;
	}

	public String getCat_2_1_name() {
		return cat_2_1_name;
	}

	public void setCat_2_1_name(String cat_2_1_name) {
		this.cat_2_1_name = cat_2_1_name;
	}

	public String getCat_2_2_id() {
		return cat_2_2_id;
	}

	public void setCat_2_2_id(String cat_2_2_id) {
		this.cat_2_2_id = cat_2_2_id;
	}

	public String getCat_2_2_name() {
		return cat_2_2_name;
	}

	public void setCat_2_2_name(String cat_2_2_name) {
		this.cat_2_2_name = cat_2_2_name;
	}

	public String getCat_2_3_id() {
		return cat_2_3_id;
	}

	public void setCat_2_3_id(String cat_2_3_id) {
		this.cat_2_3_id = cat_2_3_id;
	}

	public String getCat_2_3_name() {
		return cat_2_3_name;
	}

	public void setCat_2_3_name(String cat_2_3_name) {
		this.cat_2_3_name = cat_2_3_name;
	}

	public String getCat_2_3_search() {
		return cat_2_3_search;
	}

	public void setCat_2_3_search(String cat_2_3_search) {
		this.cat_2_3_search = cat_2_3_search;
	}

	public String getCat_2_3_click() {
		return cat_2_3_click;
	}

	public void setCat_2_3_click(String cat_2_3_click) {
		this.cat_2_3_click = cat_2_3_click;
	}

	public String getCat_3_1_id() {
		return cat_3_1_id;
	}

	public void setCat_3_1_id(String cat_3_1_id) {
		this.cat_3_1_id = cat_3_1_id;
	}

	public String getCat_3_1_name() {
		return cat_3_1_name;
	}

	public void setCat_3_1_name(String cat_3_1_name) {
		this.cat_3_1_name = cat_3_1_name;
	}

	public String getCat_3_2_id() {
		return cat_3_2_id;
	}

	public void setCat_3_2_id(String cat_3_2_id) {
		this.cat_3_2_id = cat_3_2_id;
	}

	public String getCat_3_2_name() {
		return cat_3_2_name;
	}

	public void setCat_3_2_name(String cat_3_2_name) {
		this.cat_3_2_name = cat_3_2_name;
	}

	public String getCat_3_3_id() {
		return cat_3_3_id;
	}

	public void setCat_3_3_id(String cat_3_3_id) {
		this.cat_3_3_id = cat_3_3_id;
	}

	public String getCat_3_3_name() {
		return cat_3_3_name;
	}

	public void setCat_3_3_name(String cat_3_3_name) {
		this.cat_3_3_name = cat_3_3_name;
	}

	public String getCat_3_3_search() {
		return cat_3_3_search;
	}

	public void setCat_3_3_search(String cat_3_3_search) {
		this.cat_3_3_search = cat_3_3_search;
	}

	public String getCat_3_3_click() {
		return cat_3_3_click;
	}

	public void setCat_3_3_click(String cat_3_3_click) {
		this.cat_3_3_click = cat_3_3_click;
	}

	public String getOther_search() {
		return other_search;
	}

	public void setOther_search(String other_search) {
		this.other_search = other_search;
	}

	public String getOther_click() {
		return other_click;
	}

	public void setOther_click(String other_click) {
		this.other_click = other_click;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
