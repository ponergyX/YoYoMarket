package dreamspace.com.yoyomarket.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import dreamspace.com.yoyomarket.R;

/**
 * Created by Lx on 2016/2/4.
 */
public class CatalogItemAdapter extends RecyclerView.Adapter<CatalogItemAdapter.ViewHolder>{
    private Context mContext;
    private int selectPosition = 0;
    private CatalogOnClickListener catalogOnClickListener;
    private ArrayList<String> data;

    public CatalogItemAdapter(Context context){
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_catalog,parent,false);
        return new ViewHolder(view);
    }

    public void setData(ArrayList<String> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBindView(position);
    }

    @Override
    public int getItemCount() {
        return data == null? 0 : data.size();
    }

    public void setSelect(int position){
        selectPosition = position;
        notifyDataSetChanged();
    }

    public void setCatalogOnClickListener(CatalogOnClickListener catalogOnClickListener) {
        this.catalogOnClickListener = catalogOnClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.catalog_tv)
        TextView catalogTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void onBindView(final int postion){
            if(postion == selectPosition){
                itemView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                catalogTv.setTextColor(mContext.getResources().getColor(R.color.app_color));
            }else{
                itemView.setBackgroundColor(mContext.getResources().getColor(R.color.gray6));
                catalogTv.setTextColor(mContext.getResources().getColor(R.color.black));
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelect(postion);
                    if(catalogOnClickListener != null){
                        catalogOnClickListener.onClick(postion);
                    }
                    notifyDataSetChanged();
                }
            });

            catalogTv.setText(data.get(postion));
        }
    }

    public interface CatalogOnClickListener{
        void onClick(int catalogPosition);
    }
}
