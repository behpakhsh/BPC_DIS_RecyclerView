package bpc.dis.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import bpc.dis.recyclerutilities.BottomSpaceItemDecoration.BottomSpaceItemDecoration;
import bpc.dis.recyclerutilities.RecyclerItemClickListener.OnItemClickListener;
import bpc.dis.recyclerutilities.RecyclerItemClickListener.RecyclerItemClickListener;

public class DisRecyclerView extends FrameLayout {

    private RecyclerView recyclerView;
    private AppCompatImageButton btnGoUp;
    private DisBaseAdapter disBaseAdapter;

    public DisRecyclerView(@NonNull Context context) {
        super(context);
        init(null, 0, 0);
    }

    public DisRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    public DisRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        recyclerView = null;
        btnGoUp = null;
        disBaseAdapter = null;
    }

    public void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        View view = inflate(getContext(), R.layout.dis_recycler_view, this);
        recyclerView = view.findViewById(R.id.custom_recycler_view);
        btnGoUp = view.findViewById(R.id.btn_go_up);
        setupView(attrs);
    }

    private void setupView(AttributeSet attrs) {
        TypedArray styledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.DisRecyclerView);


        //handle GoUp Button

        boolean goUpEnable = styledAttributes.getBoolean(R.styleable.DisRecyclerView_disGoUpEnable, false);
        setGoUpEnable(goUpEnable);


        //handle dis_divider

        boolean dividerEnable = styledAttributes.getBoolean(R.styleable.DisRecyclerView_disDividerEnable, false);
        if (dividerEnable) {
            int dividerSrc = styledAttributes.getResourceId(R.styleable.DisRecyclerView_disDividerSrc, R.drawable.dis_divider);
            setDivider(dividerSrc);
        }


        //handle Src

        int goUpSrc = styledAttributes.getResourceId(R.styleable.DisRecyclerView_disGoUpSrc, R.drawable.ic_go_up);
        setGoUpImageResource(goUpSrc);


        // handle tableOrientation

        int tableOrientation = styledAttributes.getInteger(R.styleable.DisRecyclerView_disTableOrientation, DisTableOrientation.VERTICAL.getValue());
        switch (tableOrientation) {
            case 0:
                setTableOrientation(DisTableOrientation.HORIZONTAL);
                break;
            case 1:
                setTableOrientation(DisTableOrientation.VERTICAL);
                break;
            case 2:
                int numberOfColumns = styledAttributes.getInteger(R.styleable.DisRecyclerView_disNumberOfColumns, 1);
                setTableGrid(numberOfColumns);
                break;
        }


        // handle overScrollMode

        int overScrollMode = styledAttributes.getInteger(R.styleable.DisRecyclerView_disOverScrollMode, 0);
        setOverScrollMode(overScrollMode);


        // handle overScrollMode

        int layoutDirection = styledAttributes.getInteger(R.styleable.DisRecyclerView_disLayoutDirection, 0);
        setLayoutDirection(layoutDirection);


        //handle disBottomSpace

        float disBottomSpace = styledAttributes.getInteger(R.styleable.DisRecyclerView_disBottomSpace, 0);
        if (disBottomSpace != 0) {
            disBottomSpace = disBottomSpace / getResources().getDisplayMetrics().density;
            recyclerView.addItemDecoration(new BottomSpaceItemDecoration((int) disBottomSpace));
        }

        styledAttributes.recycle();
    }


    public void setOverScrollMode(int overScrollMode) {
        if (recyclerView == null) {
            return;
        }
        recyclerView.setOverScrollMode(overScrollMode);
    }

    public void setDivider(int dividerSrc) {
        addItemDecoration(new DisDividerItemDecoration(getContext(), dividerSrc));
    }

    public void setGoUpEnable(boolean goUpEnable) {
        btnGoUp.setVisibility(GONE);
        if (goUpEnable) {
            btnGoUp.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerView == null || recyclerView.getLayoutManager() == null) {
                        return;
                    }
                    recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, new RecyclerView.State(), 0);
                }
            });

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    if (DisRecyclerView.this.recyclerView == null || DisRecyclerView.this.recyclerView.getLayoutManager() == null) {
                        return;
                    }
                    if (((LinearLayoutManager) DisRecyclerView.this.recyclerView.getLayoutManager()).findFirstVisibleItemPosition() > 3) {
                        btnGoUp.setVisibility(VISIBLE);
                    } else {
                        btnGoUp.setVisibility(GONE);
                    }
                }
            });
        }
    }

    public void setGoUpVisibility(int goUpVisibility) {
        btnGoUp.setVisibility(goUpVisibility);
    }

    public void setTableOrientation(DisTableOrientation tableOrientation) {
        switch (tableOrientation) {
            case HORIZONTAL:
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                break;
            case VERTICAL:
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                break;
            case GRID:
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                break;
        }
    }

    public void setTableGrid(int numberOfColumns) {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
    }

    public void setGoUpImageResource(int goUpSrc) {
        btnGoUp.setImageResource(goUpSrc);
    }

    public void addOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        recyclerView.addOnScrollListener(onScrollListener);
    }

    public DisBaseAdapter getAdapter() {
        return disBaseAdapter;
    }

    public void setAdapter(DisBaseAdapter adapter) {
        if (adapter == null) {
            return;
        }
        recyclerView.setAdapter(adapter);
        disBaseAdapter = adapter;
    }

    public int getItemDecorationCount() {
        return recyclerView.getItemDecorationCount();
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        recyclerView.addItemDecoration(itemDecoration);
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return recyclerView.getLayoutManager();
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        if (!(layoutManager instanceof GridLayoutManager)) {
            return;
        }
        ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
    }

    public void setLayoutDirection(int layoutDirection) {
        recyclerView.setLayoutDirection(layoutDirection);
    }

    public DisBaseViewHolder findViewHolderForAdapterPosition(int index) {
        return (DisBaseViewHolder) recyclerView.findViewHolderForAdapterPosition(index);
    }

    public View getChild(int index) {
        return recyclerView.getChildAt(index);
    }

    public void addItemClickListener(OnItemClickListener onItemClickListener) {
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, onItemClickListener));
    }

}