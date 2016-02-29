package dreamspace.com.yoyomarket.api.entity.element;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Lx on 2016/1/28.
 */
public class GoodInfo implements Serializable{
    private String goods_id;
    private String image;
    private String name;
    private int sales_number;
    private int price;

    //非传递来的数据，用来标记购物车中选定了多少件此商品
    private int pickNum;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSales_number() {
        return sales_number;
    }

    public void setSales_number(int sales_number) {
        this.sales_number = sales_number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof GoodInfo){
            GoodInfo goodInfo = (GoodInfo) o;
            if(goodInfo.getGoods_id().equals(getGoods_id())){
                return true;
            }
        }
        return false;
    }

    public int getPickNum() {
        return pickNum;
    }

    public void setPickNum(int pickNum) {
        this.pickNum = pickNum;
    }
}
