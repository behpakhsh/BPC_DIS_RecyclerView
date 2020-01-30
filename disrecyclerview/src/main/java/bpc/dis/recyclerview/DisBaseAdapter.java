package bpc.dis.recyclerview;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public abstract class DisBaseAdapter<T> extends RecyclerView.Adapter<DisBaseViewHolder<T>> {

    private int viewCount = 0;
    private List<T> data;
    private Context context;

    protected DisBaseAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data;
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
        if (position > 0 && position < viewCount - 1 && position - 1 < data.size()) {
            holder.setData(data.get(position - 1));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        context = null;
    }

    public void add(T object) {
        if (object != null) {
            data.add(object);
            int position = viewCount - 1;
            viewCount++;
            notifyItemInserted(position);
        }
    }

    public void insert(T object, int itemPosition) {
        int maxPosition = viewCount - 1;
        if (data != null && itemPosition < maxPosition && object != null) {
            data.add(itemPosition, object);
            viewCount++;
            notifyItemInserted(itemPosition);
        }
    }

    private void addAll(List<T> data) {
        if (data == null) {
            return;
        }
        int size = data.size();
        if (size > 0) {
            this.data.addAll(data);
            int positionStart = viewCount;
            viewCount += size;
            notifyItemRangeInserted(positionStart, size);
        }
    }

    public void addAll(T[] objects) {
        addAll(Arrays.asList(objects));
    }

    public void replace(T object, int itemPosition) {
        if (data != null && object != null) {
            if (itemPosition < data.size()) {
                data.set(itemPosition, object);
                viewCount++;
                notifyItemChanged(itemPosition);
            }
        }
    }

    public void remove(T object) {
        if (object != null && !data.contains(object)) {
            return;
        }
        int dataPosition = data.indexOf(object);
        remove(dataPosition);
    }

    private void remove(int itemPosition) {
        if (itemPosition >= data.size()) {
            throw new IllegalArgumentException("itemPosition is greater than data size");
        } else {
            data.remove(itemPosition);
            notifyItemRemoved(itemPosition);
            viewCount--;
        }
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    protected List<T> getData() {
        return data;
    }

    protected T getData(int position) {
        return data.get(position);
    }

    protected Context getContext() {
        return context;
    }

    protected abstract DisBaseViewHolder<T> onCreateBaseViewHolder(ViewGroup parent, int viewType);

    public int getCount() {
        return viewCount;
    }

}