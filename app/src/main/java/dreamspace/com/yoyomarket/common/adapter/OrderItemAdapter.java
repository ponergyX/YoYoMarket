package dreamspace.com.yoyomarket.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import dreamspace.com.yoyomarket.R;

/**
 * Created by Lx on 2016/2/2.
 */
public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder>{

    private Context mContext;

    public OrderItemAdapter(Context context){
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_order,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.time_tv)
        TextView timeTv;

        @Bind(R.id.order_image_iv1)
        ImageView goodIv1;

        @Bind(R.id.order_image_iv2)
        ImageView goodIv2;

        @Bind(R.id.order_btn)
        Button orderBtn;

        @Bind(R.id.in_send_ll)
        LinearLayout inSendLl;

        @Bind(R.id.get_time_tv)
        TextView getTimeTv;

        @Bind(R.id.state_tv)
        TextView stateTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }
}
