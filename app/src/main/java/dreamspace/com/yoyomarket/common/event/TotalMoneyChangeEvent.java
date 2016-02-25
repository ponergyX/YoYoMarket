package dreamspace.com.yoyomarket.common.event;

/**
 * Created by Lx on 2016/2/23.
 */
public class TotalMoneyChangeEvent {
    private String totalMoney;

    public TotalMoneyChangeEvent(String totalMoney){
        this.totalMoney = totalMoney;
    }

    public String getTotalMoney() {
        return totalMoney;
    }
}
