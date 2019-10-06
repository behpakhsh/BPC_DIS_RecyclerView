package bpc.dis.recyclerview;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public abstract class DisBaseViewHolder<T> extends RecyclerView.ViewHolder {

    private T mData;

    private View itemView;

    public DisBaseViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        onInitializeView();

    }

    protected View getItemView() {
        return itemView;
    }

    private void onInitializeView() {

    }

    public T getData() {
        return mData;
    }

    void setData(final T data) {
        if (data == null) {
            return;
        }
        mData = data;
    }

}