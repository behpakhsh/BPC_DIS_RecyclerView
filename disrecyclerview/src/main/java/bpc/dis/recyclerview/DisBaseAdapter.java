package bpc.dis.recyclerview;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public abstract class DisBaseAdapter<T> extends RecyclerView.Adapter<DisBaseViewHolder<T>> {

    private int mViewCount = 0;
    private List<T> mData;
    private Context mContext;

    protected DisBaseAdapter(Context context, List<T> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public DisBaseViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return onCreateBaseViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull DisBaseViewHolder holder, int position) {
        if (position < 0) {
            return;
        }
        if (position > 0 && position < mViewCount - 1 && position - 1 < mData.size()) {
            holder.setData(mData.get(position - 1));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void add(T object) {
        if (object != null) {
            mData.add(object);
            int position = mViewCount - 1;
            mViewCount++;
            notifyItemInserted(position);
        }
    }

    public void insert(T object, int itemPosition) {
        int maxPosition = mViewCount - 1;
        if (mData != null && itemPosition < maxPosition && object != null) {
            mData.add(itemPosition, object);
            mViewCount++;
            notifyItemInserted(itemPosition);
        }
    }

    private void addAll(List<T> data) {
        if (data == null) {
            return;
        }
        int size = data.size();
        if (size > 0) {
            mData.addAll(data);
            int positionStart = mViewCount;
            mViewCount += size;
            notifyItemRangeInserted(positionStart, size);
        }
    }

    public void addAll(T[] objects) {
        addAll(Arrays.asList(objects));
    }

    public void replace(T object, int itemPosition) {
        if (mData != null && object != null) {
            if (itemPosition < mData.size()) {
                mData.set(itemPosition, object);
                mViewCount++;
                notifyItemChanged(itemPosition);
            }
        }
    }

    public void remove(T object) {
        if (object != null && !mData.contains(object)) {
            return;
        }
        int dataPosition = mData.indexOf(object);
        remove(dataPosition);
    }

    private void remove(int itemPosition) {
        if (itemPosition >= mData.size()) {
            throw new IllegalArgumentException("itemPosition is greater than data size");
        } else {
            mData.remove(itemPosition);
            notifyItemRemoved(itemPosition);
            mViewCount--;
        }
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    protected List<T> getData() {
        return mData;
    }

    protected T getData(int position) {
        return mData.get(position);
    }

    protected Context getContext() {
        return mContext;
    }

    protected abstract DisBaseViewHolder<T> onCreateBaseViewHolder(ViewGroup parent, int viewType);

}