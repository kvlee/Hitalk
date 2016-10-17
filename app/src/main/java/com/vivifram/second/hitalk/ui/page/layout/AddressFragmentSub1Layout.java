package com.vivifram.second.hitalk.ui.page.layout;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jiang.android.lib.adapter.expand.StickyRecyclerHeadersDecoration;
import com.vivifram.second.hitalk.R;
import com.vivifram.second.hitalk.bean.address.SchoolMate;
import com.vivifram.second.hitalk.ui.recycleview.address.DividerDecoration;
import com.vivifram.second.hitalk.ui.recycleview.address.SchoolMatesAdapter;
import com.vivifram.second.hitalk.ui.view.CommonItem;
import com.vivifram.second.hitalk.ui.view.SRecyclerView;
import com.zuowei.utils.pinyin.CharacterParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zuowei on 16-10-11.
 */

public class AddressFragmentSub1Layout extends BaseFragmentLayout {

    public AddressFragmentSub1Layout(View root) {
        super(root);
    }

    private CommonItem conditionSearchCi;
    private CommonItem nearByCi;

    private SRecyclerView recyclerView;
    private SchoolMatesAdapter schoolMatesAdapter;
    private CharacterParser characterParser;
    @Override
    public void onViewCreate(View root) {
        super.onViewCreate(root);
        init();
    }

    private void init() {
        characterParser = CharacterParser.getInstance();

        conditionSearchCi = (CommonItem) findViewById(R.id.conditionSearch);
        fillCommonItem(conditionSearchCi,R.drawable.condif,mRes.getString(R.string.conditionSearch));
        conditionSearchCi.showDivider(true);
        nearByCi = (CommonItem) findViewById(R.id.nearbyp);
        fillCommonItem(nearByCi,R.drawable.nearby,mRes.getString(R.string.nearbyp));
        nearByCi.showDivider(false);


        recyclerView = (SRecyclerView) findViewById(R.id.schoolmateList);
        recyclerView.setHasFixedSize(true);

        int orientation = LinearLayoutManager.VERTICAL;
        final LinearLayoutManager layoutManager = new LinearLayoutManager(mCtx, orientation, false);
        recyclerView.setLayoutManager(layoutManager);

        schoolMatesAdapter = new SchoolMatesAdapter();

        initData(schoolMatesAdapter);

        recyclerView.setAdapter(schoolMatesAdapter);
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(schoolMatesAdapter);
        recyclerView.addItemDecoration(headersDecor);
        recyclerView.addItemDecoration(new DividerDecoration(mCtx));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                animateCommonItems(dy);
            }
        });
    }

    private void animateCommonItems(int dy) {
        float offset = recyclerView.scrollVerticalOffset();
        View headerView = getHeaderView();
        int maxOffset = 2 * conditionSearchCi.getHeight();
        if (offset >= 0 && offset <= maxOffset){
            if (headerView == null) {
                conditionSearchCi.setTranslationY(-maxOffset);
                nearByCi.setTranslationY(-maxOffset);
            }else {
                conditionSearchCi.setTranslationY(-offset);
                nearByCi.setTranslationY(-offset);
            }
        }
    }

    private View getHeaderView(){
        if (recyclerView.getChildCount() > 0){
            View child = recyclerView.getChildAt(0);
            int position = recyclerView.getChildAdapterPosition(child);
            if (schoolMatesAdapter.isRecyclerViewHeader(position)){
                return child;
            }
            return null;
        }
        return null;
    }

    private void initData(SchoolMatesAdapter schoolMatesAdapter) {
        schoolMatesAdapter.addAll(makeFake());
    }

    private String mates[] = new String[]{"檀为为","檀为为","吴非凡",
            "吴非凡","吴非凡","吴非凡","吴非凡","邓超",
            "邓超","邓超","邓超","邓超","你瞧","杨树伟"};

    private List<SchoolMate> makeFake() {
        ArrayList<SchoolMate> list = new ArrayList<>();
        for (String mate : mates) {
            SchoolMate schoolMate = new SchoolMate();
            schoolMate.setNickName(mate);
            schoolMate.setsInfo("清华大学");
            String pinyin = characterParser.getSelling(schoolMate.getNickName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            if (sortString.matches("[A-Z]")) {
                schoolMate.setSortLetters(sortString.toUpperCase());
            } else {
                schoolMate.setSortLetters("#");
            }

            list.add(schoolMate);
        }


        return list;
    }

    private void fillCommonItem(CommonItem commonItem, int resId, String title){
        commonItem.setBackgroundColor(mRes.getColor(R.color.colorWhite));
        commonItem.setType(CommonItem.Type.SummaryImgSummaryTxt_DetailImg);
        Drawable drawable = mRes.getDrawable(resId);
        commonItem.setSummaryImg(drawable,54,54);
        commonItem.setSummaryText(title);
        commonItem.setDetailImg(R.drawable.detail_arrow);
    }
}