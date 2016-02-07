package dreamspace.com.yoyomarket.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import dreamspace.com.yoyomarket.R;

/**
 * Created by Lx on 2016/2/4.
 */
public class GoodItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int CATALOG_TITLE_TYPE = 1;
    private static final int GOOD_TYPE = 2;

    private Context mContext;
    private OnGoodAddClickListener onGoodAddClickListener;
    private OnGoodReduceClickListener onGoodReduceClickListener;
    private HashMap<Integer,Integer> goodMap = new HashMap<>();

    public GoodItemAdapter(Context context){
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(position % 6 == 0){
            return CATALOG_TITLE_TYPE;
        }
        return GOOD_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == CATALOG_TITLE_TYPE){
            View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_catalog_title,parent,false);
            return new ViewHolderTitle(view);
        }else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_good,parent,false);
            return new ViewHolderGood(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == CATALOG_TITLE_TYPE){
            ((ViewHolderTitle)holder).onBindView(position);
        }else{
            ((ViewHolderGood)holder).onBindView(position);
        }
    }

    @Override
    public int getItemCount() {
        return 35;
    }

    public void setOnGoodAddClickListener(OnGoodAddClickListener onGoodAddClickListener) {
        this.onGoodAddClickListener = onGoodAddClickListener;
    }

    public void setOnGoodReduceClickListener(OnGoodReduceClickListener onGoodReduceClickListener) {
        this.onGoodReduceClickListener = onGoodReduceClickListener;
    }

    public class ViewHolderTitle extends RecyclerView.ViewHolder{

        @Bind(R.id.catalog_title_tv)
        TextView catalogTitleTv;

        public ViewHolderTitle(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void onBindView(int position){

        }
    }

    public class ViewHolderGood extends  RecyclerView.ViewHolder{

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

        public ViewHolderGood(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void onBindView(final int position){
            if (goodMap.containsKey(position)){
                count = goodMap.get(position);
            }else{
                count = 0;
            }

            buyCountTv.setText(count + "");
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
                    if(++count == 1){
                        reduceIv.setVisibility(View.VISIBLE);
                        buyCountTv.setVisibility(View.VISIBLE);
                    }

                    if(count <= 9){
                        buyCountTv.setText(count+"");
                    }else{
                        count--;
                    }

                    goodMap.put(position,count);

                    if(onGoodAddClickListener != null){
                        onGoodAddClickListener.onAddClick();
                    }
                }
            });

            reduceIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(--count == 0){
                        reduceIv.setVisibility(View.INVISIBLE);
                        buyCountTv.setVisibility(View.INVISIBLE);
                    }

                    if(count < 0){
                        count = 0;
                    }else{
                        buyCountTv.setText(count + "");
                    }

                    goodMap.put(position,count);

                    if(onGoodReduceClickListener != null){
                        onGoodReduceClickListener.onReduceClick();
                    }
                }
            });
        }
    }

    public interface OnGoodAddClickListener{
        void onAddClick();
    }

    public interface OnGoodReduceClickListener{
        void onReduceClick();
    }
}
