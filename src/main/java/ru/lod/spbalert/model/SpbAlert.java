package ru.lod.spbalert.model;

import java.util.Date;

/**
 */
public class SpbAlert extends GpsPoint {

    private Date date;
    private String type;
    private Double addressEac;
    private Double buildingEac;
    private String district;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAddressEac() {
        return addressEac;
    }

    public void setAddressEac(Double addressEac) {
        this.addressEac = addressEac;
    }

    public Double getBuildingEac() {
        return buildingEac;
    }

    public void setBuildingEac(Double buildingEac) {
        this.buildingEac = buildingEac;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public String toString() {
        return "SpbAlert{" +
            "date=" + date +
            ", type='" + type + '\'' +
            ", addressEac=" + addressEac +
            ", buildingEac=" + buildingEac +
            ", district='" + district + '\'' +
            '}';
    }
}
