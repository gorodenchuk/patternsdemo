package pageObject.mmjtraine.businessobject.productitembuilder;

import java.util.HashMap;

public class PortionProductItem {

    //required parameters
    private String productType;
    private String name;
    private String straine;

    //optional parameters
    private  String portion_1_8;
    private  String portion_1_6;
    private  String portion_1_4;
    private  String portion_1_2;
    private  String portion_1;
    private  String portion;


    public String getProductType() {
        return productType;
    }

    public String getname() {
        return name;
    }

    public String getStraine() {
        return straine;
    }

    public String getPortion_1_8() {
        return portion_1_8;
    }

    public String getPortion_1_6() {
        return portion_1_6;
    }

    public String getPortion_1_4() {
        return portion_1_4;
    }

    public String getPortion_1() {
        return portion_1;
    }

    public String getPortion() {
        return portion;
    }



    private PortionProductItem(PortionProductItemBuilder builder) {
        this.productType=builder.productType;
        this.name=builder.name;
        this.straine=builder.straine;
        this.portion_1_8=builder.portion_1_8;
        this.portion_1_6=builder.portion_1_6;
        this.portion_1_4=builder.portion_1_4;
        this.portion_1_2=builder.portion_1_2;
        this.portion_1=builder.portion_1;
        this.portion=builder.portion;
    }

    //Builder Class
    public static class PortionProductItemBuilder{

        // required parameters
        private String productType;
        private String name;
        private String straine;

        // optional parameters
        private String portion_1_8;
        private String portion_1_6;
        private String portion_1_4;
        private String portion_1_2;
        private String portion_1;
        private String portion;

        public PortionProductItemBuilder(String productType, String name, String straine){
            this.productType=productType;
            this.name=name;
            this.straine=straine;
        }

        public PortionProductItemBuilder setEnabledPortion_1_8(String portion) {
            this.portion_1_8 = portion;
            return this;
        }

        public PortionProductItemBuilder setEnabledPortion_1_6(String portion) {
            this.portion_1_6 = portion;
            return this;
        }

        public PortionProductItemBuilder setEnabledPortion_1_4(String portion) {
            this.portion_1_4 = portion;
            return this;
        }

        public PortionProductItemBuilder setEnabledPortion_1_2(String portion) {
            this.portion_1_2 = portion;
            return this;
        }

        public PortionProductItemBuilder setEnabledPortion_1(String portion) {
            this.portion_1 = portion;
            return this;
        }

        public PortionProductItemBuilder setEnabledPortion(String portion) {
            this.portion= portion;
            return this;
        }

        public PortionProductItem build(){
            return new PortionProductItem(this);
        }
    }

}
