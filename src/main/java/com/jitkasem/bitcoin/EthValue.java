package com.jitkasem.bitcoin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EthValue {

    private Double ethPri;
    private String timeRecord;

    public EthValue() {
    }

    public Double getEthPri() {
        return this.ethPri;
    }

    public String getTimeRecord() {
        return this.timeRecord;
    }

    public void setEthPri(Double ethPri) {
        this.ethPri = ethPri;
    }

    public void setTimeRecord(String timeRecord) {
        this.timeRecord = timeRecord;
    }

    @Override
    public String toString() {
        return "EthValue{" +
                "ethPri=" + ethPri +
                ", timeRecord='" + timeRecord + '\'' +
                '}';
    }
}
