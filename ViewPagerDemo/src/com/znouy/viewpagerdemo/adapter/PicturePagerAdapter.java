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
		// ȷ��Ҫ��ʾ��������
		return Integer.MAX_VALUE;// �����ֲ�
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// �жϵ�ǰҪ��ʾ��view�Ƿ���ڱ��object,true����ʾ
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		position = position % list.size();
		Log.d(tag, "instantiateItem==" + position);
		// �����ֲ�-��Ϊposition��ֵ��0~Integer.MAX_VALUE
		View view = list.get(position);
		// ���view��VIewPager��
		container.addView(view);
		return view;

	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// ���������Ƴ�view
		Log.d(tag, "destroyItem==" + position);
		container.removeView((View) object);
	}

}
