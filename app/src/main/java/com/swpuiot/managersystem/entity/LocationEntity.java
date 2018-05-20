package com.swpuiot.managersystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * Created by DELL on 2018/5/18.
 */
public class LocationEntity {


    /**
     * status : 1
     * info : OK
     * infocode : 10000
     * regeocode : {"formatted_address":"四川省成都市新都区新都镇蜀龙大道北段535号","addressComponent":{"country":"中国","province":"四川省","city":"成都市","citycode":"028","district":"新都区","adcode":"510114","township":"新都镇","towncode":"510114100000","neighborhood":{"name":[],"type":[]},"building":{"name":[],"type":[]},"streetNumber":{"street":"蜀龙大道北段","number":"535号","location":"104.181014,30.8322339","direction":"西","distance":"11.0996"},"businessAreas":[{"location":"104.16399792820516,30.819455649816884","name":"新都","id":"510114"}]}}
     */

    private String status;
    private String info;
    private String infocode;
    /**
     * formatted_address : 四川省成都市新都区新都镇蜀龙大道北段535号
     * addressComponent : {"country":"中国","province":"四川省","city":"成都市","citycode":"028","district":"新都区","adcode":"510114","township":"新都镇","towncode":"510114100000","neighborhood":{"name":[],"type":[]},"building":{"name":[],"type":[]},"streetNumber":{"street":"蜀龙大道北段","number":"535号","location":"104.181014,30.8322339","direction":"西","distance":"11.0996"},"businessAreas":[{"location":"104.16399792820516,30.819455649816884","name":"新都","id":"510114"}]}
     */

    private RegeocodeBean regeocode;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public RegeocodeBean getRegeocode() {
        return regeocode;
    }

    public void setRegeocode(RegeocodeBean regeocode) {
        this.regeocode = regeocode;
    }

    public static class RegeocodeBean {
        private String formatted_address;
        /**
         * country : 中国
         * province : 四川省
         * city : 成都市
         * citycode : 028
         * district : 新都区
         * adcode : 510114
         * township : 新都镇
         * towncode : 510114100000
         * neighborhood : {"name":[],"type":[]}
         * building : {"name":[],"type":[]}
         * streetNumber : {"street":"蜀龙大道北段","number":"535号","location":"104.181014,30.8322339","direction":"西","distance":"11.0996"}
         * businessAreas : [{"location":"104.16399792820516,30.819455649816884","name":"新都","id":"510114"}]
         */

        private AddressComponentBean addressComponent;

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public AddressComponentBean getAddressComponent() {
            return addressComponent;
        }

        public void setAddressComponent(AddressComponentBean addressComponent) {
            this.addressComponent = addressComponent;
        }

        public static class AddressComponentBean {
            private String country;
            private String province;
            private String city;
            private String citycode;
            private String district;
            private String adcode;
            private String township;
            private String towncode;
            @JsonIgnore
            private NeighborhoodBean neighborhood;
            @JsonIgnore
            private BuildingBean building;
            /**
             * street : 蜀龙大道北段
             * number : 535号
             * location : 104.181014,30.8322339
             * direction : 西
             * distance : 11.0996
             */
            @JsonIgnore
            private StreetNumberBean streetNumber;
            /**
             * location : 104.16399792820516,30.819455649816884
             * name : 新都
             * id : 510114
             */
            @JsonIgnore
            private List<BusinessAreasBean> businessAreas;

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCitycode() {
                return citycode;
            }

            public void setCitycode(String citycode) {
                this.citycode = citycode;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getAdcode() {
                return adcode;
            }

            public void setAdcode(String adcode) {
                this.adcode = adcode;
            }

            public String getTownship() {
                return township;
            }

            public void setTownship(String township) {
                this.township = township;
            }

            public String getTowncode() {
                return towncode;
            }

            public void setTowncode(String towncode) {
                this.towncode = towncode;
            }

            public NeighborhoodBean getNeighborhood() {
                return neighborhood;
            }

            public void setNeighborhood(NeighborhoodBean neighborhood) {
                this.neighborhood = neighborhood;
            }

            public BuildingBean getBuilding() {
                return building;
            }

            public void setBuilding(BuildingBean building) {
                this.building = building;
            }

            public StreetNumberBean getStreetNumber() {
                return streetNumber;
            }

            public void setStreetNumber(StreetNumberBean streetNumber) {
                this.streetNumber = streetNumber;
            }

            public List<BusinessAreasBean> getBusinessAreas() {
                return businessAreas;
            }

            public void setBusinessAreas(List<BusinessAreasBean> businessAreas) {
                this.businessAreas = businessAreas;
            }

            public static class NeighborhoodBean {
                private List<?> name;
                private List<?> type;

                public List<?> getName() {
                    return name;
                }

                public void setName(List<?> name) {
                    this.name = name;
                }

                public List<?> getType() {
                    return type;
                }

                public void setType(List<?> type) {
                    this.type = type;
                }
            }

            public static class BuildingBean {
                private List<?> name;
                private List<?> type;

                public List<?> getName() {
                    return name;
                }

                public void setName(List<?> name) {
                    this.name = name;
                }

                public List<?> getType() {
                    return type;
                }

                public void setType(List<?> type) {
                    this.type = type;
                }
            }

            public static class StreetNumberBean {
                private String street;
                private String number;
                private String location;
                private String direction;
                private String distance;

                public String getStreet() {
                    return street;
                }

                public void setStreet(String street) {
                    this.street = street;
                }

                public String getNumber() {
                    return number;
                }

                public void setNumber(String number) {
                    this.number = number;
                }

                public String getLocation() {
                    return location;
                }

                public void setLocation(String location) {
                    this.location = location;
                }

                public String getDirection() {
                    return direction;
                }

                public void setDirection(String direction) {
                    this.direction = direction;
                }

                public String getDistance() {
                    return distance;
                }

                public void setDistance(String distance) {
                    this.distance = distance;
                }
            }

            public static class BusinessAreasBean {
                private String location;
                private String name;
                private String id;

                public String getLocation() {
                    return location;
                }

                public void setLocation(String location) {
                    this.location = location;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }
            }
        }
    }
}
