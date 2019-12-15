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

public class DisRecyclerView extends FrameLayout {

    private RecyclerView recyclerView;
    private AppCompatImageButton btnGoUp;
    private DisBaseAdapter disBaseAdapter;

    public DisRecyclerView(@NonNull Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public DisRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public DisRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }


    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        View view = inflate(context, R.layout.dis_recycler_view, this);
        recyclerView = view.findViewById(R.id.custom_recycler_view);
        btnGoUp = view.findViewById(R.id.btn_go_up);
        setupView(context, attrs);
    }

    private void setupView(Context context, AttributeSet attrs) {
        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.DisRecyclerView);


        //handle GoUp Button

        boolean goUpEnable = styledAttributes.getBoolean(R.styleable.DisRecyclerView_goUpEnable, false);
        setGoUpEnable(goUpEnable);


        //handle dis_divider

        boolean dividerEnable = styledAttributes.getBoolean(R.styleable.DisRecyclerView_dividerEnable, false);
        if (dividerEnable) {
            int dividerSrc = styledAttributes.getResourceId(R.styleable.DisRecyclerView_dividerSrc, -1);
            setDivider(context, dividerSrc);
        }


        //handle Src

        int goUpSrc = styledAttributes.getResourceId(R.styleable.DisRecyclerView_goUpSrc, R.drawable.ic_go_up);
        setGoUpImageResource(goUpSrc);


        // handle tableOrientation

        int tableOrientation = styledAttributes.getInteger(R.styleable.DisRecyclerView_tableOrientation, DisTableOrientation.VERTICAL.getValue());
        switch (tableOrientation) {
            case 0:
                setTableOrientation(context, DisTableOrientation.HORIZONTAL);
                break;
            case 1:
                setTableOrientation(context, DisTableOrientation.VERTICAL);
                break;
            case 2:
                int numberOfColumns = styledAttributes.getInteger(R.styleable.DisRecyclerView_numberOfColumns, 1);
                setTableGrid(context, numberOfColumns);
                break;
        }


        // handle overScrollMode

        int overScrollMode = styledAttributes.getInteger(R.styleable.DisRecyclerView_disOverScrollMode, 0);
        setOverScrollMode(overScrollMode);

        styledAttributes.recycle();
    }


    public void setOverScrollMode(int overScrollMode) {
        try {
            recyclerView.setOverScrollMode(overScrollMode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDivider(Context context, int dividerSrc) {
        if (dividerSrc == -1) {
            addItemDecoration(new DisDividerItemDecoration(context));
        } else {
            addItemDecoration(new DisDividerItemDecoration(context, dividerSrc));
        }
    }

    private void setGoUpEnable(boolean goUpEnable) {
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

    private void setTableOrientation(Context context, DisTableOrientation tableOrientation) {
        switch (tableOrientation) {
            case HORIZONTAL:
                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                break;
            case VERTICAL:
                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                break;
            case GRID:
                recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                break;
        }
    }

    private void setTableGrid(Context context, int numberOfColumns) {
        recyclerView.setLayoutManager(new GridLayoutManager(context, numberOfColumns));
    }

    private void setGoUpImageResource(int goUpSrc) {
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

}

