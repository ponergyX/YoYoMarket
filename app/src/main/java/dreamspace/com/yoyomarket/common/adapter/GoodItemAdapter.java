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
import java.util.Iterator;

import butterknife.Bind;
import butterknife.ButterKnife;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.api.entity.element.GoodInfo;
import dreamspace.com.yoyomarket.common.event.GoodsListPickGoodsChangeEvent;
import dreamspace.com.yoyomarket.common.event.ShopingCartPickGoodsChangeEvent;
import dreamspace.com.yoyomarket.common.provider.BusProvider;
import dreamspace.com.yoyomarket.common.untils.CommonUntil;

/**
 * Created by Lx on 2016/2/4.
 */
public class GoodItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int CATALOG_TITLE_TYPE = 1;
    private static final int GOOD_TYPE = 2;

    private Context mContext;
    private OnGoodAddClickListener onGoodAddClickListener;
    private OnMoneyChangeListener onMoneyChangeListener;
    private HashMap<String,ArrayList<GoodInfo>> data;
    private HashMap<String,GoodInfo> pickGoodMap = new HashMap<>();
    private int totalGoods = 0;
    private ArrayList catalogTitlePosition = new ArrayList();
    private ArrayList<Object> dataList = new ArrayList();
    private ArrayList<GoodInfo> rankList = new ArrayList<>();
    private int totalMoney = 0;

    public GoodItemAdapter(Context context){
        mContext = context;
        BusProvider.getInstance().register(this);
    }

    public void setData(HashMap<String,ArrayList<GoodInfo>> data){
        this.data = data;
        getCatalogTitlePosition();
        notifyDataSetChanged();
    }

    public void setPickGoods(HashMap<String,GoodInfo> pickGoods){
        pickGoodMap = pickGoods;
        reComputeTotalPrice();
        BusProvider.getInstance().post(new GoodsListPickGoodsChangeEvent(pickGoods));
        notifyDataSetChanged();
    }

    private void getCatalogTitlePosition(){
        rankList.addAll(data.get(mContext.getString(R.string.sale_count_rank)));
        Iterator<String> iterator = data.keySet().iterator();
        catalogTitlePosition.add(totalGoods);
        totalGoods += (data.get(mContext.getString(R.string.sale_count_rank)).size() + 1);
        dataList.add(mContext.getString(R.string.sale_count_rank));
        dataList.addAll(data.get(mContext.getString(R.string.sale_count_rank)));
        while (iterator.hasNext()){
            String key = iterator.next();
            if(!key.equals(mContext.getString(R.string.sale_count_rank))){
                catalogTitlePosition.add(totalGoods);
                totalGoods += (data.get(key).size() + 1);
                dataList.add(key);
                dataList.addAll(data.get(key));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(catalogTitlePosition.contains(position)){
            return CATALOG_TITLE_TYPE;
        }
        return GOOD_TYPE;
    }

    public int getCatalogTitlePosition(int position){
        if(position <= catalogTitlePosition.size()){
            return (int) catalogTitlePosition.get(position);
        }
        return 0;
    }

    public ArrayList getCatalogTitlePositionList(){
        return catalogTitlePosition;
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
        return totalGoods;
    }

    public HashMap<String,GoodInfo> getPickGoods(){
        return pickGoodMap;
    }

    public void setOnGoodAddClickListener(OnGoodAddClickListener onGoodAddClickListener) {
        this.onGoodAddClickListener = onGoodAddClickListener;
    }

    public void setOnMoneyChangeListener(OnMoneyChangeListener onMoneyChangeListener) {
        this.onMoneyChangeListener = onMoneyChangeListener;
    }

    private int checkInRankList(GoodInfo goodInfo){
        for(GoodInfo rankGoodInfo:rankList) {
            if (rankGoodInfo.equals(goodInfo)) {
                return rankList.indexOf(rankGoodInfo);
            }
        }

        return -1;//不在销量排行中
    }

    public class ViewHolderTitle extends RecyclerView.ViewHolder{

        @Bind(R.id.catalog_title_tv)
        TextView catalogTitleTv;

        public ViewHolderTitle(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void onBindView(int position){
            catalogTitleTv.setText((String)dataList.get(position));
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
            ButterKnife.bind(this, itemView);
        }

        public void onBindView(final int position){
            final GoodInfo info;
            GoodInfo goodInfo = (GoodInfo) dataList.get(position);

            //用来解决good_id相同但不是同一java对象的问题
            if (pickGoodMap.containsKey(goodInfo.getGoods_id())){
                info = pickGoodMap.get(goodInfo.getGoods_id());
                count = info.getPickNum();
            }else{
                info = goodInfo;
                count = 0;
            }

            buyCountTv.setText(count + "");
            CommonUntil.showImageInIv(mContext,info.getImage(),goodImageIv);
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
                        pickGoodMap.put(info.getGoods_id(), info);
                        totalMoney += info.getPrice();

                        int[] locations = new int[2];
                        addIv.getLocationInWindow(locations);
                        if (onGoodAddClickListener != null) {
                            onGoodAddClickListener.onAddClick(locations);
                        }

                        if (onMoneyChangeListener != null) {
                            onMoneyChangeListener.onMoneyChange(totalMoney);
                        }

                        BusProvider.getInstance().post(new GoodsListPickGoodsChangeEvent(pickGoodMap));
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
                        pickGoodMap.remove(info.getGoods_id());
                    } else {
                        buyCountTv.setText(count + "");
                        info.setPickNum(count);
                        pickGoodMap.put(info.getGoods_id(), info);
                    }
                    totalMoney -= info.getPrice();

                    if (onMoneyChangeListener != null) {
                        onMoneyChangeListener.onMoneyChange(totalMoney);
                    }

                    BusProvider.getInstance().post(new GoodsListPickGoodsChangeEvent(pickGoodMap));
                    notifyDataSetChanged();
                }
            });
        }
    }

    public interface OnGoodAddClickListener{
        void onAddClick(int[] locations);
    }

    public interface OnMoneyChangeListener{
        void onMoneyChange(int money);
    }

    @Subscribe public void changePickGoods(ShopingCartPickGoodsChangeEvent event){
        pickGoodMap = event.getPickGoods();
        reComputeTotalPrice();
        notifyDataSetChanged();
    }

    private void reComputeTotalPrice(){
        Iterator<GoodInfo> iterator = pickGoodMap.values().iterator();
        totalMoney = 0;
        while (iterator.hasNext()){
            GoodInfo goodInfo = iterator.next();
            totalMoney += goodInfo.getPrice() * goodInfo.getPickNum();
        }
        if(onMoneyChangeListener != null){
            onMoneyChangeListener.onMoneyChange(totalMoney);
        }
    }

    public void unRegisterBus(){
        BusProvider.getInstance().unregister(this);
    }
}
