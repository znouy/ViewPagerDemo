package com.znouy.viewpagerdemo.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class PicturePagerAdapter extends PagerAdapter {
	private static final String tag = "PicturePagerAdapter";
	List<ImageView> list;

	public PicturePagerAdapter(List<ImageView> imageViews) {
		this.list = imageViews;

	}

	@Override
	public int getCount() {
		// 确定要显示的数据量
		return Integer.MAX_VALUE;// 无限轮播
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// 判断当前要显示的view是否等于标记object,true则显示
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		position = position % list.size();
		Log.d(tag, "instantiateItem==" + position);
		// 无限轮播-因为position的值从0~Integer.MAX_VALUE
		View view = list.get(position);
		// 添加view到VIewPager中
		container.addView(view);
		return view;

	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// 从容器中移除view
		Log.d(tag, "destroyItem==" + position);
		container.removeView((View) object);
	}

}
