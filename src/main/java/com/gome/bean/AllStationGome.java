package com.gome.bean;

public class AllStationGome {
	private String skuid;
	private String productid;
	private String skudisplayname;
	private String brandname;
	private String gomeprice;
	private String ipv1week;
	private String sales1week;
	private String bj;
	private String sh;
	private String gz;
	private String sz;
	private String cd;
	private String url;
	private String order;

	public AllStationGome() {
	}

	public AllStationGome(String line) {
		String[] split = line.split("#");
		this.skuid = split[0];
		this.productid = split[1];
		this.skudisplayname = split[2];
		this.brandname = split[3];
		this.gomeprice = split[4];
		this.ipv1week = split[5];
		this.sales1week = split[6];
		this.bj = split[7];
		this.sh = split[8];
		this.gz = split[9];
		this.sz = split[10];
		this.cd = split[11];
		this.url = "http://item.gome.com.cn/" + productid + "-" + skuid + ".html";
		// product_id-sku_id
	}

	public String getSkuid() {
		return skuid;
	}

	public void setSkuid(String skuid) {
		this.skuid = skuid;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getSkudisplayname() {
		return skudisplayname;
	}

	public void setSkudisplayname(String skudisplayname) {
		this.skudisplayname = skudisplayname;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getGomeprice() {
		return gomeprice;
	}

	public void setGomeprice(String gomeprice) {
		this.gomeprice = gomeprice;
	}

	public String getIpv1week() {
		return ipv1week;
	}

	public void setIpv1week(String ipv1week) {
		this.ipv1week = ipv1week;
	}

	public String getSales1week() {
		return sales1week;
	}

	public void setSales1week(String sales1week) {
		this.sales1week = sales1week;
	}

	public String getBj() {
		return bj;
	}

	public void setBj(String bj) {
		this.bj = bj;
	}

	public String getSh() {
		return sh;
	}

	public void setSh(String sh) {
		this.sh = sh;
	}

	public String getGz() {
		return gz;
	}

	public void setGz(String gz) {
		this.gz = gz;
	}

	public String getSz() {
		return sz;
	}

	public void setSz(String sz) {
		this.sz = sz;
	}

	public String getCd() {
		return cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
