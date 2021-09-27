package com.gpg.dto;

import java.util.List;

public class DaschboardFactureEtat {
	private String name;
	private List<ChartFactureDto> series;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ChartFactureDto> getSeries() {
		return series;
	}
	public void setSeries(List<ChartFactureDto> series) {
		this.series = series;
	}
	public DaschboardFactureEtat(String name, List<ChartFactureDto> series) {
		super();
		this.name = name;
		this.series = series;
	}
	

}
