package dreamspace.com.yoyomarket.api.entity;

import java.util.ArrayList;

import dreamspace.com.yoyomarket.api.entity.element.AddressInfo;
import dreamspace.com.yoyomarket.api.entity.element.AddressesListItem;

/**
 * Created by Lx on 2016/1/28.
 */
public class Addresses {
    private ArrayList<AddressesListItem> result;

    public ArrayList<AddressesListItem> getResult() {
        return result;
    }

    public void setResult(ArrayList<AddressesListItem> result) {
        this.result = result;
    }
}
