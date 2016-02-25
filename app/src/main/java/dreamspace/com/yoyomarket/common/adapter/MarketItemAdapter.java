package dreamspace.com.yoyomarket.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.api.entity.element.MarketListItem;
import dreamspace.com.yoyomarket.common.untils.CommonUntil;
import dreamspace.com.yoyomarket.widget.RatingBar;

/**
 * Created by Lx on 2016/1/31.
 */
public class MarketItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private OnMarketItemClickListener onMarketItemClickListener;
    private ArrayList<MarketListItem> data;

    public MarketItemAdapter(Context context){
        mContext = context;
        data = new ArrayList<>();
    }

    public void setData(ArrayList<MarketListItem> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(ArrayList<MarketListItem> data){
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0)? 0 : 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 1){
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.view_item_market,parent,false);
            return new ViewHolderItem(itemView);
        }else{
            View bannerView = LayoutInflater.from(mContext).inflate(R.layout.view_item_banner,parent,false);
            return new ViewHolderBanner(bannerView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position != 0){
            ((ViewHolderItem)holder).onBindView(position);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 1 : data.size() + 1;
    }

    public void setOnMarketItemClickListener(OnMarketItemClickListener onMarketItemClickListener) {
        this.onMarketItemClickListener = onMarketItemClickListener;
    }

    public class ViewHolderItem extends RecyclerView.ViewHolder{
        @Bind(R.id.market_iv)
        ImageView marketIv;

        @Bind(R.id.market_name_tv)
        TextView marketNameTv;

        @Bind(R.id.market_ratingbar)
        RatingBar ratingBar;

        @Bind(R.id.month_sale_tv)
        TextView monthSaleTv;

        @Bind(R.id.start_price_tv)
        TextView startPriceTv;

        @Bind(R.id.take_price_tv)
        TextView takePriceTv;

        @Bind(R.id.take_time_tv)
        TextView takeTimeTv;

        public ViewHolderItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void onBindView(final int position){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMarketItemClickListener != null) {
                        onMarketItemClickListener.onClick(data.get(position - 1).getSup_id(),data.get(position - 1).getName());
                    }
                }
            });

            CommonUntil.showImageInIv(mContext, data.get(position - 1).getImage(), marketIv);
            marketNameTv.setText(data.get(position - 1).getName());
            monthSaleTv.setText("月售" + data.get(position - 1).getSales_number() + "单");
            ratingBar.setmClickable(false);
            ratingBar.setStar(data.get(position - 1).getScore());
            startPriceTv.setText(data.get(position - 1).getSend_price() + "元起送");
            takePriceTv.setText(data.get(position - 1).getDeliver_fee() + "元配送费");
        }
    }

    public class ViewHolderBanner extends RecyclerView.ViewHolder{
        @Bind(R.id.banner)
        ConvenientBanner banner;

        public ViewHolderBanner(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void onBindView(int position){

        }
    }

    public interface OnMarketItemClickListener{
        void onClick(String supId,String supName);
    }
}
