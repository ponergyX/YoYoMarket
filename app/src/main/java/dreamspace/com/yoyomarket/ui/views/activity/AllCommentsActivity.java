package dreamspace.com.yoyomarket.ui.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kennyc.view.MultiStateView;

import javax.inject.Inject;

import butterknife.Bind;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.common.VerticalSpaceItemDecoration;
import dreamspace.com.yoyomarket.common.adapter.CommentItemAdapter;
import dreamspace.com.yoyomarket.common.base.BaseActivity;
import dreamspace.com.yoyomarket.ui.presenter.activity.AllCommentsActivityPresenter;
import dreamspace.com.yoyomarket.ui.view.activity.AllCommentsView;

/**
 * Created by Lx on 2016/2/6.
 */
public class AllCommentsActivity extends BaseActivity implements AllCommentsView{

    @Bind(R.id.comments_msview)
    MultiStateView multiStateView;

    @Bind(R.id.swrlayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.comments_recyclerview)
    RecyclerView recyclerView;

    @Inject
    AllCommentsActivityPresenter allCommentsActivityPresenter;

    CommentItemAdapter adapter;

    public static Intent getCallingIntent(Context context){
        return new Intent(context,AllCommentsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();
        initPresenter();
    }

    private void initInjector(){
        getApiComponent().inject(this);
    }

    private void initPresenter(){
        allCommentsActivityPresenter.attchView(this);
    }

    @Override
    protected void initViewsAndEvents() {
        setTitle(getString(R.string.all_comments));
        swipeRefreshLayout.setColorSchemeResources(R.color.app_color);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.x2));
        adapter = new CommentItemAdapter(this);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_all_comments;
    }
}
