package bpc.dis.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DisRecyclerView extends FrameLayout {

    private RecyclerView mRecyclerView;
    private AppCompatImageButton btnGoUp;
    private DisBaseAdapter mAdapter;

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DisRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, 0, 0);
    }

    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        View view = inflate(context, R.layout.dis_recycler_view, this);
        mRecyclerView = view.findViewById(R.id.custom_recycler_view);
        btnGoUp = view.findViewById(R.id.btn_go_up);
        setupView(context, attrs);
    }


    private void setupView(Context context, AttributeSet attrs) {
        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.DisRecyclerView);


        //handle GoUp Button

        boolean goUpEnable = styledAttributes.getBoolean(R.styleable.DisRecyclerView_goUpEnable, false);
        setGoUpEnable(goUpEnable);


        //handle divider

        boolean dividerEnable = styledAttributes.getBoolean(R.styleable.DisRecyclerView_dividerEnable, false);
        if (dividerEnable) {
            int dividerSrc = styledAttributes.getResourceId(R.styleable.DisRecyclerView_dividerSrc, -1);
            setDivider(context, dividerSrc);
        }


        //handle Src

        int goUpSrc = styledAttributes.getResourceId(R.styleable.DisRecyclerView_goUpSrc, R.drawable.ic_go_up);
        setGoUpImageResource(goUpSrc);


        //handle Size

        float goUpWidth = styledAttributes.getDimension(R.styleable.DisRecyclerView_goUpWidth, getResources().getDimension(R.dimen.goUpWidth));
        float goUpHeight = styledAttributes.getDimension(R.styleable.DisRecyclerView_goUpHeight, getResources().getDimension(R.dimen.goUpHeight));
//        setGoUpWidthAndHeight(goUpWidth, goUpHeight);


        //handle Margin

        float goUpMargin = styledAttributes.getDimension(R.styleable.DisRecyclerView_goUpMargin, 0);
        float goUpMarginTop = styledAttributes.getDimension(R.styleable.DisRecyclerView_goUpMarginTop, 0);
        float goUpMarginBottom = styledAttributes.getDimension(R.styleable.DisRecyclerView_goUpMarginBottom, 0);
        float goUpMarginEnd = styledAttributes.getDimension(R.styleable.DisRecyclerView_goUpMarginEnd, 0);
        float goUpMarginStart = styledAttributes.getDimension(R.styleable.DisRecyclerView_goUpMarginStart, 0);
//        setGoUpMargin((int) goUpMargin, (int) goUpMarginTop, (int) goUpMarginBottom, (int) goUpMarginEnd, (int) goUpMarginStart);


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

        styledAttributes.recycle();
    }

    private void setDivider(Context context, int dividerSrc) {
        if (dividerSrc == -1) {
            mRecyclerView.addItemDecoration(new DisDividerItemDecoration(context));
        } else {
            mRecyclerView.addItemDecoration(new DisDividerItemDecoration(context, dividerSrc));
        }
    }

    private void setGoUpEnable(boolean goUpEnable) {
        btnGoUp.setVisibility(GONE);
        if (goUpEnable) {
            btnGoUp.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mRecyclerView == null || mRecyclerView.getLayoutManager() == null) {
                        return;
                    }
                    mRecyclerView.getLayoutManager().smoothScrollToPosition(mRecyclerView, new RecyclerView.State(), 0);
                }
            });

            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    if (mRecyclerView == null || mRecyclerView.getLayoutManager() == null) {
                        return;
                    }
                    if (((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition() > 3) {
                        btnGoUp.setVisibility(VISIBLE);
                    } else {
                        btnGoUp.setVisibility(GONE);
                    }
                }
            });
        }
    }

    /**
     * @description can use this method for  orientation ---> vertical, horizontal and grid  for 2 numberOfColumns
     */
    private void setTableOrientation(Context context, DisTableOrientation tableOrientation) {
        switch (tableOrientation) {
            case HORIZONTAL:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                break;
            case VERTICAL:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                break;
            case GRID:
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                break;
        }
    }

    /**
     * @param context context
     * @description can use this method for  orientation ---> grid  for  any numberOfColumns
     */
    private void setTableGrid(Context context, int numberOfColumns) {
        mRecyclerView.setLayoutManager(new GridLayoutManager(context, numberOfColumns));
    }

    private int dpToPx(float dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    private void setGoUpMargin(int goUpMargin, int goUpMarginTop, int goUpMarginBottom, int goUpMarginEnd, int goUpMarginStart) {
        if (goUpMargin != 0) {
            goUpMarginTop = goUpMargin;
            goUpMarginBottom = goUpMargin;
            goUpMarginEnd = goUpMargin;
            goUpMarginStart = goUpMargin;
        }
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(goUpMarginEnd, goUpMarginTop, goUpMarginStart, goUpMarginBottom);
        btnGoUp.setLayoutParams(layoutParams);
    }

    private void setGoUpMargin(int margin) {
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(margin, margin, margin, margin);
        btnGoUp.setLayoutParams(layoutParams);
    }

    private void setGoUpWidthAndHeight(float goUpWidth, float goUpHeight) {
        goUpWidth = dpToPx(goUpWidth);
        goUpHeight = dpToPx(goUpHeight);

        btnGoUp.getLayoutParams().width = (int) goUpWidth;
        btnGoUp.getLayoutParams().height = (int) goUpHeight;
    }

    private void setGoUpImageResource(int goUpSrc) {
        btnGoUp.setImageResource(goUpSrc);
    }

    public DisBaseAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(DisBaseAdapter adapter) {
        if (adapter == null) {
            return;
        }
        mRecyclerView.setAdapter(adapter);
        mAdapter = adapter;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
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

