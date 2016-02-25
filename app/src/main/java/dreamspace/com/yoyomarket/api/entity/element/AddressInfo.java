package dreamspace.com.yoyomarket.api.entity.element;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lx on 2016/1/28.
 */
public class AddressInfo implements Parcelable{
    private String name;
    private String phone_num;
    private String address;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(phone_num);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(sex);
    }

    public static Parcelable.Creator<AddressInfo> CREATOR = new Creator<AddressInfo>() {
        @Override
        public AddressInfo createFromParcel(Parcel source) {
            return new AddressInfo(source);
        }

        @Override
        public AddressInfo[] newArray(int size) {
            return new AddressInfo[size];
        }
    };

    public AddressInfo(){

    }

    public AddressInfo(Parcel in){
        phone_num = in.readString();
        name = in.readString();
        address = in.readString();
        sex = in.readString();
    }
}
