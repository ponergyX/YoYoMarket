package dreamspace.com.yoyomarket.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.api.entity.element.OrderItem;
import dreamspace.com.yoyomarket.common.untils.CommonUntil;

/**
 * Created by Lx on 2016/2/2.
 */
public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<OrderItem> data = new ArrayList<>();
    private OnCancelOrderAfterMarketReceiveClickListener onCancelOrderAfterMarketReceiveClickListener;
    private OnCancelOrderBeforeMarketReceiveClickListener onCancelOrderBeforeMarketReceiveClickListener;
    private OnGoPayClickListener onGoPayClickListener;
    private OnGoCommentClickListener onGoCommentClickListener;
    private OnItemClickListener onItemClickListener;

    public OrderItemAdapter(Context context){
        mContext = context;
    }

    public void setData(ArrayList<OrderItem> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(ArrayList<OrderItem> data){
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_order,parent,false);
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

    public void setOnCancelOrderAfterMarketReceiveClickListener(OnCancelOrderAfterMarketReceiveClickListener onCancelOrderAfterMarketReceiveClickListener) {
        this.onCancelOrderAfterMarketReceiveClickListener = onCancelOrderAfterMarketReceiveClickListener;
    }

    public void setOnCancelOrderBeforeMarketReceiveClickListener(OnCancelOrderBeforeMarketReceiveClickListener onCancelOrderBeforeMarketReceiveClickListener) {
        this.onCancelOrderBeforeMarketReceiveClickListener = onCancelOrderBeforeMarketReceiveClickListener;
    }

    public void setOnGoPayClickListener(OnGoPayClickListener onGoPayClickListener) {
        this.onGoPayClickListener = onGoPayClickListener;
    }

    public void setOnGoCommentClickListener(OnGoCommentClickListener onGoCommentClickListener) {
        this.onGoCommentClickListener = onGoCommentClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.time_tv)
        TextView timeTv;

        @Bind(R.id.order_image_iv)
        ImageView goodIv;

        @Bind(R.id.operate_btn)
        Button operateBtn;

        @Bind(R.id.state_tv)
        TextView stateTv;

        @Bind(R.id.item_bg)
        RelativeLayout itemRl;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void onBindView(int position){
            final OrderItem orderItem = data.get(position);

            CommonUntil.showImageInIv(mContext, orderItem.getImage(), goodIv);
            timeTv.setText(orderItem.getOrder_time());
            itemRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(orderItem.getOrder_id());
                    }
                }
            });

            switch (orderItem.getStatus()){
                //退款中
                case -1:
                    stateTv.setText(mContext.getString(R.string.in_refund));
                    operateBtn.setVisibility(View.INVISIBLE);
                    break;

                //已取消
                case 0:
                    stateTv.setText(mContext.getString(R.string.order_cancel));
                    operateBtn.setVisibility(View.INVISIBLE);
                    break;

                //待付款
                case 1:
                    stateTv.setText(mContext.getString(R.string.order_wait_pay));
                    operateBtn.setVisibility(View.VISIBLE);
                    operateBtn.setText(mContext.getString(R.string.go_pay_order));
                    operateBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(onGoPayClickListener != null){
                                onGoPayClickListener.onGoPayClick(orderItem.getOrder_id());
                            }
                        }
                    });
                    break;

                //已付款，待接单
                case 2:
                    stateTv.setText(mContext.getString(R.string.order_wait_receive));
                    operateBtn.setVisibility(View.VISIBLE);
                    operateBtn.setText(mContext.getString(R.string.cancel_order));
                    operateBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(onCancelOrderBeforeMarketReceiveClickListener != null){
                                onCancelOrderBeforeMarketReceiveClickListener.onCancelClick(orderItem.getOrder_id());
                            }
                        }
                    });
                    break;

                //已接单，配送中
                case 3:
                    stateTv.setText(mContext.getString(R.string.order_receive));
                    operateBtn.setVisibility(View.VISIBLE);
                    operateBtn.setText(mContext.getString(R.string.cancel_order));
                    operateBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(onCancelOrderAfterMarketReceiveClickListener != null){
                                onCancelOrderAfterMarketReceiveClickListener.onCancelClick(orderItem.getOrder_id());
                            }
                        }
                    });
                    break;

                //已收货，待评价
                case 4:
                    stateTv.setText(mContext.getString(R.string.order_wait_comment));
                    operateBtn.setVisibility(View.VISIBLE);
                    operateBtn.setText(mContext.getString(R.string.go_comment));
                    operateBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(onGoCommentClickListener != null){
                                onGoCommentClickListener.onGoCommentClick(orderItem.getOrder_id());
                            }
                        }
                    });
                    break;

                //已评价，订单完成
                case 5:
                    stateTv.setText(mContext.getString(R.string.order_finish));
                    operateBtn.setVisibility(View.INVISIBLE);
                    break;

                default:
                    break;
            }
        }
    }

    public interface OnCancelOrderBeforeMarketReceiveClickListener{
        void onCancelClick(String orderId);
    }

    public interface OnCancelOrderAfterMarketReceiveClickListener{
        void onCancelClick(String orderId);
    }

    public interface OnGoPayClickListener{
        void onGoPayClick(String orderId);
    }

    public interface OnGoCommentClickListener{
        void onGoCommentClick(String orderId);
    }

    public interface OnItemClickListener{
        void onItemClick(String orderId);
    }
}
