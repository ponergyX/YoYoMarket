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
import dreamspace.com.yoyomarket.common.event.SearchCartPickGoodsChangeEvent;
import dreamspace.com.yoyomarket.common.event.SearchPickGoodsListChangeEvent;
import dreamspace.com.yoyomarket.common.provider.BusProvider;
import dreamspace.com.yoyomarket.common.untils.CommonUntil;

/**
 * Created by Lx on 2016/2/25.
 */
public class SearchGoodItemAdapter extends RecyclerView.Adapter<SearchGoodItemAdapter.ViewHolder>{

    private Context mContext;
    private HashMap<String,GoodInfo> pickGoods;
    private ArrayList<GoodInfo> data;

    public SearchGoodItemAdapter(Context context){
        mContext = context;
        BusProvider.getInstance().register(this);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_good,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBindView(position);
    }

    @Override
    public int getItemCount() {
        return data == null?0 : data.size();
    }

    public void setPickGoods(HashMap<String, GoodInfo> pickGoods) {
        this.pickGoods = pickGoods;
    }

    public void setData(ArrayList<GoodInfo> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Subscribe
    public void pickGoodsChange(SearchCartPickGoodsChangeEvent event){
        pickGoods = event.getPickGoods();
        notifyDataSetChanged();
    }

    public void unRegisterBus(){
        BusProvider.getInstance().unregister(this);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.good_image_iv)
        ImageView goodImageIv;

        @Bind(R.id.good_name_tv)
        TextView goodNameTv;

        @Bind(R.id.sale_count_tv)
        TextView saleCountTv;

        @Bind(R.id.price_tv)
        TextView priceTv;

        @Bind(R.id.reduce_iv)
        ImageView reduceIv;

        @Bind(R.id.buy_count_tv)
        TextView buyCountTv;

        @Bind(R.id.add_iv)
        ImageView addIv;

        private int count;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void onBindView(int position){
            count = 0;
            final GoodInfo info;
            GoodInfo goodInfo = data.get(position);

            //用来解决good_id相同但不是同一java对象的问题
            if (pickGoods.containsKey(goodInfo.getGoods_id())){
                info = pickGoods.get(goodInfo.getGoods_id());
                count = info.getPickNum();
            }else{
                info = goodInfo;
                count = 0;
            }

            buyCountTv.setText(count + "");
            CommonUntil.showImageInIv(mContext, info.getImage(), goodImageIv);
            goodNameTv.setText(info.getName());
            saleCountTv.setText("月售" + info.getSales_number());
            priceTv.setText("￥" + ((double) info.getPrice()) / 100 + "元/份");

            if(count != 0){
                buyCountTv.setVisibility(View.VISIBLE);
                reduceIv.setVisibility(View.VISIBLE);
            }else{
                buyCountTv.setVisibility(View.INVISIBLE);
                reduceIv.setVisibility(View.INVISIBLE);
            }

            addIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (++count == 1) {
                        reduceIv.setVisibility(View.VISIBLE);
                        buyCountTv.setVisibility(View.VISIBLE);
                    }

                    if (count <= 99) {
                        buyCountTv.setText(count + "");
                        info.setPickNum(count);
                        pickGoods.put(info.getGoods_id(), info);

                        int[] locations = new int[2];
                        addIv.getLocationInWindow(locations);

                        BusProvider.getInstance().post(new SearchPickGoodsListChangeEvent(pickGoods));
                        notifyDataSetChanged();
                    }
                }
            });

            reduceIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (--count == 0) {
                        reduceIv.setVisibility(View.INVISIBLE);
                        buyCountTv.setVisibility(View.INVISIBLE);
                        info.setPickNum(count);
                        pickGoods.remove(info.getGoods_id());
                    } else {
                        buyCountTv.setText(count + "");
                        info.setPickNum(count);
                        pickGoods.put(info.getGoods_id(), info);
                    }

                    BusProvider.getInstance().post(new SearchPickGoodsListChangeEvent(pickGoods));
                    notifyDataSetChanged();
                }
            });
        }
    }
}
