
package com.api.payloads;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Position {

    @SerializedName("contract")
    @Expose
    private String contract;
    @SerializedName("exchange")
    @Expose
    private String exchange;
    @SerializedName("optionType")
    @Expose
    private String optionType;
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("strikePrice")
    @Expose
    private Double strikePrice;
    @SerializedName("tradeType")
    @Expose
    private String tradeType;

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(Double strikePrice) {
        this.strikePrice = strikePrice;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

}
