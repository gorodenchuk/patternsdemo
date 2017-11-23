package pageObject.mmjtraine.businessobject.productitembuilder;

import java.util.HashMap;

public class QuantityProductItem {

    //required parameters
    private String productType;
    private String name;
    private String straine;

    //optional parameters
    private  int quantity;

    public String getProductType() {
        return productType;
    }

    public String getname() {
        return name;
    }

    public String getStraine() {
        return straine;
    }

    public int getQuantity() {
        return quantity;
    }


    private QuantityProductItem(QuantityProductItemBuilder builder) {
        this.productType=builder.productType;
        this.name=builder.name;
        this.straine=builder.straine;
        this.quantity=builder.quantity;
    }

    //Builder Class
    public static class QuantityProductItemBuilder{

        // required parameters
        private String productType;
        private String name;
        private String straine;

        // optional parameters
        private int quantity;
        HashMap<String, String> portionMap = new HashMap<>();


        public QuantityProductItemBuilder(String productType, String name, String straine){
            this.productType=productType;
            this.name=name;
            this.straine=straine;
        }

        public QuantityProductItemBuilder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public QuantityProductItem build(){
            return new QuantityProductItem(this);
        }
    }

}
