
package com.api.payloads;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class AMXGetHolidayMasterV2 {

    @SerializedName("exchange")
    @Expose
    private String exchange;
    @SerializedName("year")
    @Expose
    private String year;

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
