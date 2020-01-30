package bpc.dis.recyclerview;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public abstract class DisBaseViewHolder<T> extends RecyclerView.ViewHolder {

    private T data;

    public DisBaseViewHolder(View itemView) {
        super(itemView);
        onInitializeView();
    }

    private void onInitializeView() {

    }

    public T getData() {
        return data;
    }

    void setData(final T data) {
        if (data == null) {
            return;
        }
        this.data = data;
    }

}