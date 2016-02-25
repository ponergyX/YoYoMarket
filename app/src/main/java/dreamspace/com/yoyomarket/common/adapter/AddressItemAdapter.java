package dreamspace.com.yoyomarket.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.api.entity.element.AddressInfo;
import dreamspace.com.yoyomarket.api.entity.element.AddressesListItem;
import dreamspace.com.yoyomarket.common.untils.CommonUntil;

/**
 * Created by Lx on 2016/2/3.
 */
public class AddressItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private OnAddressClickListener onAddressClickListener;
    private OnAddressLongClickListener onAddressLongClickListener;
    private OnAddAddressClickListener onAddAddressClickListener;
    private ArrayList<AddressesListItem> data;

    public AddressItemAdapter(Context context){
        mContext = context;
        data = new ArrayList<>();
    }

    public void setData(ArrayList<AddressesListItem> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(ArrayList<AddressesListItem> data){
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == getItemCount() - 1){
            return 2;
        }
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 1){
            View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_get_good,parent,false);
            return new ViewHolder1(view);
        }else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_add_get_good_address,parent,false);
            return new ViewHolder2(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position == getItemCount() - 1){
            ((ViewHolder2)holder).onBindView(position);
        }else{
            ((ViewHolder1)holder).onBindView(position);
        }
    }

    @Override
    public int getItemCount() {
        return data == null? 1 : 1 + data.size();
    }

    public void setOnAddressClickListener(OnAddressClickListener onAddressClickListener) {
        this.onAddressClickListener = onAddressClickListener;
    }

    public void setOnAddAddressClickListener(OnAddAddressClickListener onAddAddressClickListener) {
        this.onAddAddressClickListener = onAddAddressClickListener;
    }

    public void setOnAddressLongClickListener(OnAddressLongClickListener onAddressLongClickListener) {
        this.onAddressLongClickListener = onAddressLongClickListener;
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder{
        @Bind(R.id.get_good_address_item_rl)
        RelativeLayout itemRl;

        @Bind(R.id.avatar_civ)
        CircleImageView avatarCiv;

        @Bind(R.id.location_tv)
        TextView locationTv;

        @Bind(R.id.phone_num_tv)
        TextView phoneTv;

        @Bind(R.id.sex_tv)
        TextView sexTv;

        @Bind(R.id.name_tv)
        TextView nameTv;

        public ViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void onBindView(final int position){
            itemRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onAddressClickListener != null){
                        AddressInfo addressInfo= new AddressInfo();
                        addressInfo.setPhone_num(data.get(position).getPhone_num());
                        addressInfo.setSex(data.get(position).getSex());
                        addressInfo.setAddress(data.get(position).getAddress());
                        addressInfo.setName(data.get(position).getName());
                        onAddressClickListener.onClick(data.get(position).getAddress_id(),addressInfo);
                    }
                }
            });

            itemRl.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(onAddressLongClickListener != null){
                        onAddressLongClickListener.onLongClick(data.get(position).getAddress_id());
                        return true;
                    }
                    return false;
                }
            });

            locationTv.setText(data.get(position).getAddress());
            sexTv.setText(data.get(position).getSex());
            phoneTv.setText(data.get(position).getPhone_num());
            nameTv.setText(data.get(position).getName());
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder{

        @Bind(R.id.add_address_ll)
        LinearLayout addAddressLl;

        public ViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void onBindView(int position){
            addAddressLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onAddAddressClickListener != null){
                        onAddAddressClickListener.onClick();
                    }
                }
            });
        }
    }

    public interface OnAddressClickListener{
        void onClick(String addressId,AddressInfo addressInfo);
    }

    public interface OnAddressLongClickListener{
        void onLongClick(String addressId);
    }

    public interface OnAddAddressClickListener{
        void onClick();
    }

}
