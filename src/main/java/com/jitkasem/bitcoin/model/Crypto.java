package com.jitkasem.bitcoin.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Crypto {
	
	@JsonProperty("BTC")
	private double BTC;
	@JsonProperty("USD")
	private double USD;
	@JsonProperty("EUR")
	private double EUR;
	
	public Crypto() {
		// TODO Auto-generated constructor stub
	}

	public double getBTC() {
		return BTC;
	}

	public void setBTC(double bTC) {
		BTC = bTC;
	}

	public double getUSD() {
		return USD;
	}

	public void setUSD(double uSD) {
		USD = uSD;
	}

	public double getEUR() {
		return EUR;
	}

	public void setEUR(double eUR) {
		EUR = eUR;
	}

	@Override
	public String toString() {
		return "Crypto [BTC=" + BTC + ", USD=" + USD + ", EUR=" + EUR + "]";
	}
	
	
	
	

}
