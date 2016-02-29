package dreamspace.com.yoyomarket.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.api.entity.element.DeliverTime;

/**
 * Created by Lx on 2016/2/26.
 */
public class PickTimeItemAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<DeliverTime> data;

    public PickTimeItemAdapter(Context context,ArrayList<DeliverTime> times){
        mContext = context;
        data = times;
    }

    @Override
    public int getCount() {
        return data == null? 0 : data.size();
    }

    @Override
    public DeliverTime getItem(int position) {
        return data == null ? null : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_item_pick_time,parent,false);
            holder.timeTv = (TextView) convertView.findViewById(R.id.time_tv);
            holder.divider = (ImageView) convertView.findViewById(R.id.divider);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.timeTv.setText(data.get(position).getTime());
        if(position == getCount() || position == 0){
            holder.divider.setVisibility(View.INVISIBLE);
        }else{
            holder.divider.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    public class ViewHolder{
        public TextView timeTv;
        public ImageView divider;
    }
}
