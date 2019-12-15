package bpc.dis.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DisDividerItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable divider;

    public DisDividerItemDecoration(Context context) {
        divider = context.getResources().getDrawable(R.drawable.dis_divider);
    }

    public DisDividerItemDecoration(Context context, int resId) {
        divider = context.getResources().getDrawable(resId);
    }

    @Override
    public void onDraw(@NonNull Canvas canvas, RecyclerView parent, @NonNull RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + divider.getIntrinsicHeight();
            divider.setBounds(left, top, right, bottom);
            divider.draw(canvas);
        }
    }

}