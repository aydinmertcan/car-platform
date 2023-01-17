package com.car.platform.dto.response;

public class CarResponseDto {
    /***
     * {"id":1,"carBrand":"Suzuki","carModel":"XL-7","productionYear":2008,"carVin":"WBASP2C59DC351448"}
     */
    private int id;
    private String carBrand;
    private String carModel;
    private int productionYear;
    private String carVin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public String getCarVin() {
        return carVin;
    }

    public void setCarVin(String carVin) {
        this.carVin = carVin;
    }
}
