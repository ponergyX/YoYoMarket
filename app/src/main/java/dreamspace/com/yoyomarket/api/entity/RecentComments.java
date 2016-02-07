package dreamspace.com.yoyomarket.api.entity;

import java.util.ArrayList;

import dreamspace.com.yoyomarket.api.entity.element.CommentItem;

/**
 * Created by Lx on 2016/1/30.
 */
public class RecentComments {
    private ArrayList<CommentItem> result;

    public ArrayList<CommentItem> getResult() {
        return result;
    }

    public void setResult(ArrayList<CommentItem> result) {
        this.result = result;
    }
}
