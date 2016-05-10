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
		// ��ʼ��ͼƬ����
		initImageView();
		// ��ʼ��������
		initPoints();

		vp_lunbo.setAdapter(adapter);
		// ����ViewPager��ǰѡ�е�λ��,�����instantiateItem�����ջ�ѡ��item0
		// ԭ��һ������ȥ��������϶��ܱ���������
		vp_lunbo.setCurrentItem(10000 - 10000 % datas.size());

		autoScrollTask.start();
	}

	private void initImageView() {
		for (int i = R.drawable.welcome_1; i < R.drawable.welcome_1 + 4; i++) {
			// ����ImageView����
			ImageView imageView = new ImageView(getContext());

			// ����ͼƬ
			imageView.setImageResource(i);
			imageView.setScaleType(ScaleType.FIT_XY);// ͼƬ�������
			datas.add(imageView);// ��ӵ�������
		}
	}

	private void initPoints() {
		// ��Ϊÿ��ҳ��ı䶼����ø÷��������Խ���÷���ʱ����ռ��ϡ�
		ll_points.removeAllViews();

		for (int i = 0; i < datas.size(); i++) {
			View view = new View(getContext());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					10, 10);
			if (i == selectIndex) {
				tv_desc.setText("ͼƬ" + i + "������");
				view.setBackgroundResource(R.drawable.point_red);
			} else {
				view.setBackgroundResource(R.drawable.point_white);
			}
			params.leftMargin = 10;
			view.setLayoutParams(params);// ���ò��ֲ���
			ll_points.addView(view);// ��ӵ����������
		}
	}

	private void initListener() {
		// viewpagerע��ҳ��ı������
		vp_lunbo.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// ҳ��ѡ�е���
				selectIndex = position % datas.size();
				initPoints();// ������ҳ��ı���ƶ�
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) { // ҳ�滬���͵���
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				// ҳ��״̬�ı����

			}
		});

		// viewpager ע�ᴥ���¼���������ʵ���Զ��ֲ�
		vp_lunbo.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:// ����ֹͣ�ֲ�
					autoScrollTask.stop();
					break;
				case MotionEvent.ACTION_MOVE:

					break;
				case MotionEvent.ACTION_UP:// �ɿ���ȡ�� ��ʼ�ֲ�
				case MotionEvent.ACTION_CANCEL:
					autoScrollTask.start();
					break;

				default:
					break;
				}
				return false;// ����Ӱ������view���¼��ַ�
			}
		});
	}

	/** ʵ���ֲ�ͼ�Զ��ֲ� */

	class AutoScrollTask implements Runnable {

		@Override
		public void run() {
			int currentItem = vp_lunbo.getCurrentItem();
			currentItem++;
			vp_lunbo.setCurrentItem(currentItem);
			start();
		}

		/**
		 * ��ʼ�ֲ�
		 */
		public void start() {
			handler.postDelayed(this, 2000);
		}

		/**
		 * ֹͣ�ֲ�
		 */
		public void stop() {
			handler.removeCallbacks(this);
		}
	}

}
