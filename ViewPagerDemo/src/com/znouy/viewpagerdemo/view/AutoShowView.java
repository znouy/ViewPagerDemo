package com.znouy.viewpagerdemo.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znouy.viewpagerdemo.R;
import com.znouy.viewpagerdemo.adapter.PicturePagerAdapter;

public class AutoShowView extends FrameLayout {

	private View view;
	private ViewPager vp_lunbo;
	private TextView tv_desc;
	private LinearLayout ll_points;
	private List<ImageView> datas;
	private PicturePagerAdapter adapter;
	private Handler handler;

	final AutoScrollTask autoScrollTask = new AutoScrollTask();
	protected int selectIndex;

	public AutoShowView(Context context) {

		this(context, null);
	}

	public AutoShowView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public AutoShowView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context);
		initData();
		initListener();
	}

	private void initView(Context context) {
		view = LayoutInflater.from(context).inflate(R.layout.view_autoshow,
				this);

		vp_lunbo = (ViewPager) view.findViewById(R.id.vp_lunbo);
		tv_desc = (TextView) view.findViewById(R.id.tv_desc);
		ll_points = (LinearLayout) view.findViewById(R.id.ll_points);

		datas = new ArrayList<ImageView>();
		adapter = new PicturePagerAdapter(datas);
		if (handler == null) {
			handler = new Handler();
		}
	}

	private void initData() {
		// 初始化图片数据
		initImageView();
		// 初始化点数据
		initPoints();

		vp_lunbo.setAdapter(adapter);
		// 设置ViewPager当前选中的位置,会调用instantiateItem，最终会选中item0
		// 原理：一个数减去其余数后肯定能被除数整除
		vp_lunbo.setCurrentItem(10000 - 10000 % datas.size());

		autoScrollTask.start();
	}

	private void initImageView() {
		for (int i = R.drawable.welcome_1; i < R.drawable.welcome_1 + 4; i++) {
			// 创建ImageView容器
			ImageView imageView = new ImageView(getContext());

			// 设置图片
			imageView.setImageResource(i);
			imageView.setScaleType(ScaleType.FIT_XY);// 图片填充容器
			datas.add(imageView);// 添加到集合中
		}
	}

	private void initPoints() {
		// 因为每次页面改变都会调用该方法，所以进入该方法时先清空集合。
		ll_points.removeAllViews();

		for (int i = 0; i < datas.size(); i++) {
			View view = new View(getContext());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					10, 10);
			if (i == selectIndex) {
				tv_desc.setText("图片" + i + "的描述");
				view.setBackgroundResource(R.drawable.point_red);
			} else {
				view.setBackgroundResource(R.drawable.point_white);
			}
			params.leftMargin = 10;
			view.setLayoutParams(params);// 设置布局参数
			ll_points.addView(view);// 添加到点的容器中
		}
	}

	private void initListener() {
		// viewpager注册页面改变监听器
		vp_lunbo.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// 页面选中调用
				selectIndex = position % datas.size();
				initPoints();// 红点跟着页面改变而移动
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) { // 页面滑动就调用
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				// 页面状态改变调用

			}
		});

		// viewpager 注册触摸事件监听器，实现自动轮播
		vp_lunbo.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:// 按下停止轮播
					autoScrollTask.stop();
					break;
				case MotionEvent.ACTION_MOVE:

					break;
				case MotionEvent.ACTION_UP:// 松开，取消 开始轮播
				case MotionEvent.ACTION_CANCEL:
					autoScrollTask.start();
					break;

				default:
					break;
				}
				return false;// 不会影响其他view的事件分发
			}
		});
	}

	/** 实现轮播图自动轮播 */

	class AutoScrollTask implements Runnable {

		@Override
		public void run() {
			int currentItem = vp_lunbo.getCurrentItem();
			currentItem++;
			vp_lunbo.setCurrentItem(currentItem);
			start();
		}

		/**
		 * 开始轮播
		 */
		public void start() {
			handler.postDelayed(this, 2000);
		}

		/**
		 * 停止轮播
		 */
		public void stop() {
			handler.removeCallbacks(this);
		}
	}

}
