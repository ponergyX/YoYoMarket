package dreamspace.com.yoyomarket.common.adapter;

import android.content.Context;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.api.entity.element.GoodInfo;
import dreamspace.com.yoyomarket.common.event.SearchCartPickGoodsChangeEvent;
import dreamspace.com.yoyomarket.common.event.SearchPickGoodsListChangeEvent;
import dreamspace.com.yoyomarket.common.provider.BusProvider;

/**
 * Created by Lx on 2016/2/25.
 */
public class SearchCartItemAdapter extends RecyclerView.Adapter<SearchCartItemAdapter.ViewHolder>{

    private Context mContext;
    private HashMap<String,GoodInfo> pickGoods = new HashMap<>();
    private ArrayList<GoodInfo> data = new ArrayList<>();
    private OnCartCleanListener onCartCleanListener;
    private OnResizeRecyclerViewListener onResizeRecyclerViewListener;
    private OnTotalMoneyChangeListener onTotalMoneyChangeListener;
    private int totalMoney = 0;

    public SearchCartItemAdapter(Context context){
        mContext = context;
        BusProvider.getInstance().register(this);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_shopping_cart,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBindView(position);
    }

    @Override
    public int getItemCount() {
        return data == null? 0 : data.size();
    }

    public void setData(HashMap<String,GoodInfo> pickGoods){
        this.pickGoods = pickGoods;
        data.clear();
        data.addAll(this.pickGoods.values());
        reComputeTotalMoney();
        notifyDataSetChanged();
    }

    public void cleanShoppingCart(){
        data.clear();
        pickGoods.clear();
        totalMoney = 0;
        BusProvider.getInstance().post(new SearchCartPickGoodsChangeEvent(pickGoods));

        if(onTotalMoneyChangeListener != null){
            onTotalMoneyChangeListener.onTotalMoneyChange(totalMoney);
        }

        if(onCartCleanListener != null){
            onCartCleanListener.onCartClean();
        }
    }

    @Subscribe
    public void pickGoodsChange(SearchPickGoodsListChangeEvent event){
        pickGoods = event.getPickGoods();
        data.clear();
        data.addAll(pickGoods.values());
        reComputeTotalMoney();
        notifyDataSetChanged();
    }

    private void reComputeTotalMoney(){
        totalMoney = 0;
        for(GoodInfo goodInfo:data){
            totalMoney += (goodInfo.getPrice() * goodInfo.getPickNum());
        }
        if(onTotalMoneyChangeListener != null){
            onTotalMoneyChangeListener.onTotalMoneyChange(totalMoney);
        }
    }

    public HashMap<String,GoodInfo> getPickGoods(){
        return pickGoods;
    }

    public void setOnCartCleanListener(OnCartCleanListener onCartCleanListener) {
        this.onCartCleanListener = onCartCleanListener;
    }

    public void setOnResizeRecyclerViewListener(OnResizeRecyclerViewListener onResizeRecyclerViewListener) {
        this.onResizeRecyclerViewListener = onResizeRecyclerViewListener;
    }

    public void unRegisterBus(){
        BusProvider.getInstance().unregister(this);
    }

    public void setOnTotalMoneyChangeListener(OnTotalMoneyChangeListener onTotalMoneyChangeListener) {
        this.onTotalMoneyChangeListener = onTotalMoneyChangeListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.good_name_tv)
        TextView goodNameTv;

        @Bind(R.id.price_tv)
        TextView priceTv;

        @Bind(R.id.add_iv)
        ImageView addIv;

        @Bind(R.id.reduce_iv)
        ImageView reduceIv;

        @Bind(R.id.num_tv)
        TextView numTv;

        private int count;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBindView(int position){
            final GoodInfo goodInfo = data.get(position);

            count = goodInfo.getPickNum();
            numTv.setText(count+"");

            goodNameTv.setText(goodInfo.getName());
            priceTv.setText("￥" + ((double)goodInfo.getPrice() * count)/100);
            addIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(++count <= 99){
                        numTv.setText(count + "");
                        goodInfo.setPickNum(count);
                        pickGoods.put(goodInfo.getGoods_id(), goodInfo);
                        priceTv.setText("￥" + ((double) goodInfo.getPrice() * count) / 100);
                        totalMoney += goodInfo.getPrice();
                        BusProvider.getInstance().post(new SearchCartPickGoodsChangeEvent(pickGoods));
                        if(onTotalMoneyChangeListener != null){
                            onTotalMoneyChangeListener.onTotalMoneyChange(totalMoney);
                        }
                    }else{
                        count--;
                    }
                }
            });

            reduceIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (--count > 0) {
                        numTv.setText(count + "");
                        goodInfo.setPickNum(count);
                        pickGoods.put(goodInfo.getGoods_id(), goodInfo);
                        priceTv.setText("￥" + ((double) goodInfo.getPrice() * count) / 100);
                    } else {
                        count = 0;
                        goodInfo.setPickNum(count);
                        pickGoods.remove(goodInfo.getGoods_id());
                        data.remove(goodInfo);
                        if (pickGoods.size() == 0 && onCartCleanListener != null) {
                            onCartCleanListener.onCartClean();
                        }
                        if(onResizeRecyclerViewListener != null){
                            onResizeRecyclerViewListener.onResizeRecyclerView();
                        }
                        notifyDataSetChanged();
                    }
                    totalMoney -= goodInfo.getPrice();
                    if(onTotalMoneyChangeListener != null){
                        onTotalMoneyChangeListener.onTotalMoneyChange(totalMoney);
                    }
                    BusProvider.getInstance().post(new SearchCartPickGoodsChangeEvent(pickGoods));
                }
            });
        }
    }

    public interface OnCartCleanListener{
        void onCartClean();
    }

    public interface OnResizeRecyclerViewListener{
        void onResizeRecyclerView();
    }

    public interface OnTotalMoneyChangeListener{
        void onTotalMoneyChange(int totalMoney);
    }
}
