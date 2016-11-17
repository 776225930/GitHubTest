package com.example.viewpager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewpager;
    private TextView tv_title;
    private LinearLayout ll_point_group;
    ArrayList<ImageView> imageViews;
    private int prePosition = 0;
    //是否已经滚栋
    private boolean isDragging = false;
    private final int[] imageIds = {
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
    };
    private final String[] imageDescription = {
            "犬夜叉",
            "火神",
            "绿箭",
            "清风大辉"
    };
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int item = viewpager.getCurrentItem() + 1;
            viewpager.setCurrentItem(item);
            mHandler.sendEmptyMessageDelayed(0, 2000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        tv_title = (TextView) findViewById(R.id.tv_title);
        ll_point_group = (LinearLayout) findViewById(R.id.ll_point_group);
        //1.在布局文件中定义viewpager
        // 2.实例化viewpager
        //3.准备数据
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(16, 16);
        imageViews = new ArrayList<>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            //添加到集合中
            imageViews.add(imageView);
            //添加点
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_selector);
            if (i == 0) {
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
                params.leftMargin = 16;
            }

            point.setLayoutParams(params);
            ll_point_group.addView(point);
        }
        //4.设置适配器
        MyAdapter adapter = new MyAdapter();
        viewpager.setAdapter(adapter);
        //设置监听viewpager页面的改变
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());
        //设置当前位置
        int item = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % imageViews.size();
        viewpager.setCurrentItem(item);
        tv_title.setText(imageDescription[prePosition]);
        //发消息,这个handler起引发作用
        mHandler.sendEmptyMessageDelayed(0, 2000);
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            System.out.println("---->onPageScrolled");
        }

        @Override
        public void onPageSelected(int position) {
            int realPosition = position % imageViews.size();

            //设置标题
            tv_title.setText(imageDescription[realPosition]);
            //把上一个高亮设置为灰色
            ll_point_group.getChildAt(prePosition).setEnabled(false);
            //把灰色设置为高亮
            ll_point_group.getChildAt(realPosition).setEnabled(true);
            prePosition = realPosition;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                Log.i("TAG", "---------SCROLL_STATE_DRAGGING");
                isDragging = true;
                mHandler.removeCallbacksAndMessages(null);
            } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                isDragging = false;
                Log.i("TAG", "---------SCROLL_STATE_IDLE");
            } else if (state == ViewPager.SCROLL_STATE_SETTLING && isDragging) {
                mHandler.removeCallbacksAndMessages(null);
                mHandler.sendEmptyMessageDelayed(0, 2000);
                Log.i("TAG", "---------SCROLL_STATE_SETTLING");
            }
        }
    }

    class MyAdapter extends PagerAdapter {
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int realPosition = position % imageViews.size();
            ImageView view = imageViews.get(realPosition);
            container.addView(view);//添加到ViewPager中
            Log.i("TAG", "instantiateItem=" + view);
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            Log.i("TAG", "手指按下");
                            mHandler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            Log.i("TAG", "手指移动");
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            Log.i("TAG", "事件取消");
                            break;
                        case MotionEvent.ACTION_UP:
                            Log.i("TAG", "手指离开");
                            mHandler.sendEmptyMessageDelayed(0, 2000);
                            break;
                    }
                    return false;
                }
            });
            view.setTag(position);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position= (int) v.getTag()%imageViews.size();
                    String text=imageDescription[position];
                }
            });
            return view;
        }

        @Override
        public int getCount() {
            //return imageViews.size();
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
