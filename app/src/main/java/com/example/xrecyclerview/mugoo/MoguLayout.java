package com.example.xrecyclerview.mugoo;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.example.xrecyclerview.R;
import com.example.xrecyclerview.view.AutoHorizontalScrollView;


/**
 * Created by jiangjieqiang on 16/1/18.
 */
public class MoguLayout extends LinearLayout{
    private String tag = this.getClass().getName();
    private View topView;
    private View horizontalScrollView;
    private AutoHorizontalScrollView menu;
    private ViewPager viewPager;
    private View layTab;
//    private ListView listView;

    private OverScroller scroller;
    private VelocityTracker mVelocityTracker;
    private int mTouchSlop;
    private int mMaximumVelocity, mMinimumVelocity;

    private int distanceFromViewPagerToX;
    private float mLastY;

    private Event onScrollListener;
    /**
     * 主要是用在用户手指离开MyScrollView，MyScrollView还在继续滑动，我们用来保存Y的距离，然后做比较
     */
    private int lastScrollY;
    /**
     * 设置滚动接口
     * @param onScrollListener
     */
    public void setOnScrollListener(Event onScrollListener){
        this.onScrollListener = onScrollListener;
    }

    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            int scrollY = scroller.getCurrY();

            //此时的距离和记录下的距离不相等，在隔5毫秒给handler发送消息
            if(lastScrollY != scrollY){
                lastScrollY = scrollY;
                handler.sendMessageDelayed(handler.obtainMessage(), 5);
            }
            if(onScrollListener != null){
                onScrollListener.show(scrollY);
            }

        };

    };

    private boolean mDragging;
    private boolean isInControl = false;
    private boolean isTopHidden = false;
    public MoguLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);

        scroller = new OverScroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        topView = findViewById(R.id.id_top_banner);
        horizontalScrollView = findViewById(R.id.id_horizontalview);
        viewPager = (ViewPager)findViewById(R.id.id_viewpager);
        menu = (AutoHorizontalScrollView)findViewById(R.id.id_horizontalmenu);
        layTab = findViewById(R.id.layTab);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams params = viewPager.getLayoutParams();
        params.height = getMeasuredHeight() - menu.getMeasuredHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        distanceFromViewPagerToX = topView.getMeasuredHeight()+horizontalScrollView.getMeasuredHeight();
        Log.i(tag,"distanceFromViewPagerToX:"+distanceFromViewPagerToX);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(onScrollListener != null){
            onScrollListener.show(lastScrollY = this.getScrollY());
        }
        initVelocityTrackerIfNotExists();
        mVelocityTracker.addMovement(event);
        int action = event.getAction();
        float y = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished())
                    scroller.abortAnimation();
                mLastY = y;
                return true;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;
                //判断是滑动还是点击
                if (!mDragging && Math.abs(dy) > mTouchSlop) {
                    mDragging = true;
                }

                if (mDragging) {
                    scrollBy(0, (int) -dy);

                    // 如果topView隐藏，且上滑动时，则改变当前事件为ACTION_DOWN
                    if (getScrollY() == distanceFromViewPagerToX && dy < 0) {


                        event.setAction(MotionEvent.ACTION_DOWN);
                        dispatchTouchEvent(event);
                        isInControl = false;
                    }
                }

                mLastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
                mDragging = false;
                recycleVelocityTracker();
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_UP:
                mDragging = false;
                //初始化
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int velocityY = (int) mVelocityTracker.getYVelocity();
                if (Math.abs(velocityY) > mMinimumVelocity) {
                    fling(-velocityY);
                }
                recycleVelocityTracker();
                break;
        }

        return super.onTouchEvent(event);
    }

    /**
     * 当滑动速度比较大的时候，实现快速滑动
     * @param velocityY
     */
    public void fling(int velocityY) {
        //
        scroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, distanceFromViewPagerToX);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > distanceFromViewPagerToX) {
            y = distanceFromViewPagerToX;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }

        isTopHidden = getScrollY() == distanceFromViewPagerToX;

    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.getCurrY());
            invalidate();
        }
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(tag,"--------事件分发："+scroller.getCurrY());
        int action = ev.getAction();
        float y = ev.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;
                getCurrentListView();

//                Log.i(tag,"dispath getFirstVisiblePosition:"+listView.getFirstVisiblePosition());
//                View view = listView.getChildAt(listView.getFirstVisiblePosition());
//                Log.i(tag,"view.getTop():"+view.getTop());
//                if (!isInControl && view != null && view.getTop() == 0 && isTopHidden && dy > 0) {
                Log.i(tag,"dy:"+dy+"  isTopHidden:"+isTopHidden);
                if (!isInControl&&isTopHidden && dy > 0&&scroller.getCurrY()!=distanceFromViewPagerToX){
                    Log.i(tag,"--------事件分发11111111");
                    isInControl = true;
                    ev.setAction(MotionEvent.ACTION_CANCEL);
                    MotionEvent ev2 = MotionEvent.obtain(ev);
                    dispatchTouchEvent(ev);
                    ev2.setAction(MotionEvent.ACTION_DOWN);
                    return dispatchTouchEvent(ev2);
                }else {
                    Log.i(tag,"--------事件分发000000000");
                }
                break;

        }
        return super.dispatchTouchEvent(ev);
    }

    private void getCurrentListView() {
        int currentItem = viewPager.getCurrentItem();
        PagerAdapter a = viewPager.getAdapter();
        FragmentPagerAdapter fadapter = (FragmentPagerAdapter) a;
        Fragment item = (Fragment) fadapter.instantiateItem(viewPager,
                currentItem);
//        listView = (ListView) (item.getView().findViewById(R.id.id_listview));
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(tag,"#######事件分发："+scroller.getCurrY());
        final int action = ev.getAction();
        float y = ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;
//                getCurrentListView();
//                Log.i(tag,"-0-------onInter y:"+y);

                if (Math.abs(dy) > mTouchSlop) {
                    //滑动
                    mDragging = true;
//                    Log.i(tag,"------scaleY:"+scaleY);
//                    Log.i(tag,"onInter getFirstVisiblePosition:"+listView.getFirstVisiblePosition());
//                    View view = listView.getChildAt(listView.getFirstVisiblePosition());

                    // 拦截条件：topView没有隐藏
                    // 或listView在顶部 && topView隐藏 && 下拉
//                    if (!isTopHidden || (view != null && view.getTop() == 0 && isTopHidden && dy > 0)) {
                    Log.i(tag,"dy:"+dy+"  isTopHidden:"+isTopHidden);
                    if(!isTopHidden||(isTopHidden && dy > 0&&scroller.getCurrY()<=distanceFromViewPagerToX)){
                        Log.i(tag,"#######事件分发111111111");
                        initVelocityTrackerIfNotExists();
                        mLastY = y;
                        mVelocityTracker.addMovement(ev);
                        return true;
                    }else {
                        Log.i(tag,"#######事件分发00000000");
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mDragging = false;
                recycleVelocityTracker();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }


    /**
     * 滚动的回调接口
     */
    public interface Event{

        public void show(int scrollY);
    }

}
