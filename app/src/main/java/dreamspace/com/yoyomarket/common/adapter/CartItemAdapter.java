package dreamspace.com.yoyomarket.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.api.entity.element.GoodInfo;
import dreamspace.com.yoyomarket.common.event.GoodsListPickGoodsChangeEvent;
import dreamspace.com.yoyomarket.common.event.ShopingCartPickGoodsChangeEvent;
import dreamspace.com.yoyomarket.common.provider.BusProvider;

/**
 * Created by Lx on 2016/2/8.
 */
public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder>{

    private Context mContext;
    private HashMap<String,GoodInfo> pickGoods;
    private ArrayList<GoodInfo> dataList = new ArrayList<>();
    private OnCartCleanListener onCartCleanListener;
    private OnResizeRecyclerViewListener onResizeRecyclerViewListener;

    public CartItemAdapter(Context context){
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
        return dataList.size();
    }

    public void cleanShoppingCart(){
        dataList.clear();
        pickGoods.clear();
        BusProvider.getInstance().post(new ShopingCartPickGoodsChangeEvent(pickGoods));
        if(onCartCleanListener != null){
            onCartCleanListener.onCartClean();
        }
    }

    @Subscribe public void pickGoodsChange(GoodsListPickGoodsChangeEvent event){
        pickGoods = event.getPickGoods();
        dataList.clear();
        dataList.addAll(pickGoods.values());
        notifyDataSetChanged();
    }

    public void setOnCartCleanListener(OnCartCleanListener onCartCleanListener) {
        this.onCartCleanListener = onCartCleanListener;
    }

    public void setOnResizeRecyclerViewListener(OnResizeRecyclerViewListener onResizeRecyclerViewListener) {
        this.onResizeRecyclerViewListener = onResizeRecyclerViewListener;
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
            final GoodInfo goodInfo = dataList.get(position);

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
                        priceTv.setText("￥" + ((double)goodInfo.getPrice() * count)/100);
                        BusProvider.getInstance().post(new ShopingCartPickGoodsChangeEvent(pickGoods));
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
                        dataList.remove(goodInfo);
                        if (pickGoods.size() == 0 && onCartCleanListener != null) {
                            onCartCleanListener.onCartClean();
                        }
                        if(onResizeRecyclerViewListener != null){
                            onResizeRecyclerViewListener.onResizeRecyclerView();
                        }
                        notifyDataSetChanged();
                    }
                    BusProvider.getInstance().post(new ShopingCartPickGoodsChangeEvent(pickGoods));
                }
            });
        }
    }

    public void unRegisterBus(){
        BusProvider.getInstance().unregister(this);
    }

    public interface OnCartCleanListener{
        void onCartClean();
    }

    public interface OnResizeRecyclerViewListener{
        void onResizeRecyclerView();
    }
}
