package dreamspace.com.yoyomarket.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.widget.RatingBar;

/**
 * Created by Lx on 2016/2/6.
 */
public class CommentItemAdapter extends RecyclerView.Adapter<CommentItemAdapter.ViewHolder> {

    private Context mContext;

    public CommentItemAdapter(Context context){
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_comment,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBindView(position);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.avatar_civ)
        CircleImageView avatarCiv;

        @Bind(R.id.phone_num_tv)
        TextView phoneTv;

        @Bind(R.id.comment_ratingbar)
        RatingBar ratingBar;

        @Bind(R.id.date_time)
        TextView dateTv;

        @Bind(R.id.comment_tv)
        TextView commentTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void onBindView(int position){

        }
    }
}
