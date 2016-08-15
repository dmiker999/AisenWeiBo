package org.aisen.weibo.sina.ui.fragment.hot;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import org.aisen.android.support.bean.TabItem;
import org.aisen.android.ui.activity.basic.BaseActivity;
import org.aisen.android.ui.activity.container.FragmentArgs;
import org.aisen.android.ui.fragment.ATabsTabLayoutFragment;
import org.aisen.weibo.sina.R;
import org.aisen.weibo.sina.sinasdk.bean.WebHotTopicsBean;
import org.aisen.weibo.sina.support.utils.UMengUtil;
import org.aisen.weibo.sina.ui.activity.base.SinaCommonActivity;

import java.util.ArrayList;

/**
 * Created by wangdan on 16/8/15.
 */
public class TopicsHotTimelinePagerFragment extends ATabsTabLayoutFragment<TabItem> {

    public static void launch(Activity from, WebHotTopicsBean bean) {
        FragmentArgs args = new FragmentArgs();
        args.add("bean", bean);

        SinaCommonActivity.launch(from, TopicsHotTimelinePagerFragment.class, args);
    }

    private WebHotTopicsBean mBean;

    @Override
    public int inflateContentView() {
        return R.layout.ui_settings_tabs;
    }

    @Override
    protected void setupTabLayout(Bundle savedInstanceSate, TabLayout tabLayout) {
        super.setupTabLayout(savedInstanceSate, tabLayout);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("bean", mBean);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBean = savedInstanceState == null ? (WebHotTopicsBean) getArguments().getSerializable("bean")
                                           : (WebHotTopicsBean) savedInstanceState.getSerializable("bean");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        BaseActivity activity = (BaseActivity) getActivity();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(false);
        activity.getSupportActionBar().setTitle(String.format("#%s#", mBean.getCard_type_name()));
    }

    @Override
    protected ArrayList<TabItem> generateTabs() {
        ArrayList<TabItem> items = new ArrayList<>();

        items.add(new TabItem("0", "推荐"));
        items.add(new TabItem("1", "热门"));

        return items;
    }

    @Override
    protected Fragment newFragment(TabItem bean) {
        if ("0".equals(bean.getType())) {
            return TopicsHotTimelineFragment.newInstance(mBean, TopicsHotTimelineFragment.Type.recommend);
        }
        else if ("1".equals(bean.getType())) {
            return TopicsHotTimelineFragment.newInstance(mBean, TopicsHotTimelineFragment.Type.hot);
        }

        return null;
    }

    @Override
    public void onResume() {
        super.onResume();

        UMengUtil.onPageStart(getActivity(), "热门话题详情页");
    }

    @Override
    public void onPause() {
        super.onPause();

        UMengUtil.onPageEnd(getActivity(), "热门话题详情页");
    }

}
