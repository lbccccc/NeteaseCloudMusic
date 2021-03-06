package com.imooc.lib_common_ui.delegate;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imooc.lib_common_ui.R;
import com.imooc.lib_common_ui.R2;
import com.imooc.lib_common_ui.navigator.CommonNavigatorCreater;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public abstract class NeteaseTabDelegate extends NeteaseDelegate {

	@BindView(R2.id.delegate_magic_indicator_tab)
	MagicIndicator mTabMagicIndicator;
	@BindView(R2.id.delegate_view_pager_tab)
	ViewPager mTabViewPager;
	@BindView(R2.id.delegate_tab_title)
	TextView mTvLeftTitle;
	@BindView(R2.id.delegate_tab_more)
	ImageView mIvMore;
	@BindView(R2.id.ll_delegate_title)
	LinearLayout mLlTitle;
	@BindView(R2.id.delegate_tab_search)
	ImageView mIvSearch;

	protected List<NeteaseDelegate> mDelegateList = new ArrayList<>();
	private MultiFragmentPagerAdapter mAdapter;
	private CommonNavigator commonNavigator;

	@Override
	public Object setLayout() {
		return R.layout.delegate_tab;
	}

	@Override
	public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View view) throws Exception {

		mTvLeftTitle.setText(setLeftTitle());

		mAdapter = new MultiFragmentPagerAdapter(getChildFragmentManager());
		if(setDelegateList() != null){
			mAdapter.init(setDelegateList());
		}else{
			throw new NullPointerException("DelegateList can not be null");
		}
		mTabViewPager.setAdapter(mAdapter);
		mTabViewPager.setOffscreenPageLimit(setTitleDataList().length);
		//mTabMagicIndicator.setBackgroundColor(Color.WHITE);
		commonNavigator = CommonNavigatorCreater.setDefaultNavigator(getContext(), setTitleDataList(), mTabViewPager);
		commonNavigator.setAdjustMode(setAdjustMode());
		mTabMagicIndicator.setNavigator(commonNavigator);
		setDividerDrawable(commonNavigator,0);
		ViewPagerHelper.bind(mTabMagicIndicator, mTabViewPager);

		setShowMoreView(View.GONE);
		setMoreViewOnClickListener(null);
		setToolBarVisiable(View.VISIBLE);
		setShowSearchView(true);

	}

	public ImageView getIvMore() {
		return mIvMore;
	}

	public MagicIndicator getTabMagicIndicator() {
		return mTabMagicIndicator;
	}

	public void setDividerDrawable(CommonNavigator navigator, final int dp) {
		if(dp != 0){
			LinearLayout titleContainer = navigator.getTitleContainer();
			titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
			//设置TAB的间距
			titleContainer.setDividerDrawable(new ColorDrawable() {
				@Override
				public int getIntrinsicWidth() {
					return UIUtil.dip2px(getContext(), dp);
				}
			});
		}
	}

	//  true:自适应模式，适用于数目固定的、少量的title
	public boolean setAdjustMode(){
		return true;
	}
	public ViewPager getTabViewPager() {
		return mTabViewPager;
	}

	public abstract CharSequence[] setTitleDataList();

	public abstract CharSequence setLeftTitle();

	public abstract List<NeteaseDelegate> setDelegateList();

	public void setShowMoreView(int vivisiable){
		mIvMore.setVisibility(vivisiable);
	}
	public void setShowSearchView(boolean vivisiable){
		mIvSearch.setVisibility(vivisiable ? View.VISIBLE:View.INVISIBLE);
	}
	public void setMoreViewOnClickListener(View.OnClickListener listener){
		mIvMore.setOnClickListener(listener);
	}
	public void setToolBarVisiable(int visiable){
		mLlTitle.setVisibility(visiable);
	}

	@OnClick(R2.id.delegate_tab_back)
	void onClickBack(){
		getSupportDelegate().pop();
	}

}
