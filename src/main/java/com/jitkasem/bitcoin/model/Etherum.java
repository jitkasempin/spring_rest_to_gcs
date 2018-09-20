package com.jitkasem.bitcoin.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Etherum {
	@JsonProperty("ETH")
	private Crypto crypto;

	public Etherum() {
		// TODO Auto-generated constructor stub
	}

	public Crypto getCrypto() {
		return crypto;
	}

	public void setCrypto(Crypto crypto) {
		this.crypto = crypto;
	}

	@Override
	public String toString() {
		return "Etherum [crypto=" + crypto + "]";
	}
	
	

}
