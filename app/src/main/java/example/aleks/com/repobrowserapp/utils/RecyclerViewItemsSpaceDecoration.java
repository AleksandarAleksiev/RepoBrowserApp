package example.aleks.com.repobrowserapp.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by aleks on 07/05/2018.
 */

public class RecyclerViewItemsSpaceDecoration extends RecyclerView.ItemDecoration {

    private final int left;
    private final int top;
    private final int right;
    private final int bottom;

    public RecyclerViewItemsSpaceDecoration(int left, int top, int right, int bottom) {

        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.left = this.left;
        outRect.top = this.top;
        outRect.right = this.right;
        outRect.bottom = this.bottom;
    }
}
