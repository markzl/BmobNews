package com.aqtc.bmobnews.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aqtc.bmobnews.R;
import com.aqtc.bmobnews.adapter.base.EasyRecyclerViewAdapter;
import com.aqtc.bmobnews.adapter.base.EasyRecyclerViewHolder;
import com.aqtc.bmobnews.bean.gank.base.BaseGankData;
import com.aqtc.bmobnews.data.gank.GankType;
import com.aqtc.bmobnews.data.gank.GankTypeDict;
import com.aqtc.bmobnews.util.GlideUtils;
import com.aqtc.bmobnews.util.ResourcesUtils;
import com.aqtc.bmobnews.widget.RatioImageView;

import java.util.List;

/**
 * author: markzl
 * time: 2016/10/15 21:31
 * email: 1015653112@qq.com
 */

public class DailyDetailAdapter extends EasyRecyclerViewAdapter {

    private Context context;

    private int cardItemPadding;
    private int cardCategoryPaddingToBottom;
    private int cardItemDivider;
    private int viaTextSize;

    private String viaModel;
    private String viaModelKey;
    private ColorStateList viaColorStateList;

    private static final int dividerColor = 0xffCCCCCC;

    private OnCardItemClickListener cardItemClickListener;

    public DailyDetailAdapter(Context context) {
        this.context = context;
        Resources res = this.context.getResources();
        this.initCardItemStyle(res);
        this.initViaTextStyle(res);

    }

    private void initCardItemStyle(Resources res) {
        this.cardItemPadding = res.getDimensionPixelOffset(R.dimen.card_item_content_padding);
        this.cardCategoryPaddingToBottom = res.getDimensionPixelOffset(
                R.dimen.card_category_padding_top_bottom);
        this.cardItemDivider = res.getDimensionPixelOffset(R.dimen.card_item_divider);
    }

    private void initViaTextStyle(Resources res) {

        int viaTextColor = ResourcesUtils.getColor(this.context, R.color.common_item_via);
        this.viaTextSize = res.getDimensionPixelSize(R.dimen.item_via_tv);
        this.viaModel = "via.%s";
        this.viaModelKey = "via.";
        this.viaColorStateList = ResourcesUtils.createColorStateList(
                viaTextColor, viaTextColor, viaTextColor, viaTextColor);
    }

    @Override
    public int[] getItemLayouts() {
        return new int[]{R.layout.item_gank_daily_detail};
    }

    @Override
    public int getRecyclerViewItemType(int position) {
        return 0;
    }

    @Override
    public void onBindRecyclerViewHolder(EasyRecyclerViewHolder viewHolder, int position) {
        List<BaseGankData> categoryData = this.getItem(position);
        if (categoryData == null) return;
        LinearLayout detail_ll = viewHolder.findViewById(R.id.ll_daily_detail_item);
        detail_ll.removeAllViews();
        for (int i = 0; i < categoryData.size(); i++) {
            final BaseGankData baseGankData = categoryData.get(i);
            if (i == 0) {
                TextView categoryTV = this.createCardCategory(baseGankData.type);
                detail_ll.addView(categoryTV);
                detail_ll.addView(this.createDivider());
            }
            if (GankTypeDict.urlType2TypeDict.get(baseGankData.type) == GankType.welfare) {
                RatioImageView welfareIV = this.createRatioImageView();
                GlideUtils.display(welfareIV, baseGankData.url);
                welfareIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (DailyDetailAdapter.this.cardItemClickListener != null) {
                            DailyDetailAdapter.this.cardItemClickListener
                                    .onWelfareOnClick(baseGankData.url, baseGankData.desc, v);
                        }
                    }
                });
                detail_ll.addView(welfareIV);
            } else {
                TextView itemText = this.createCardItemText(baseGankData);
                detail_ll.addView(itemText);
            }
        }

    }

    /**
     * 创建目录TextView
     *
     * @param urlType
     * @return
     */
    private TextView createCardCategory(String urlType) {
        TextView categoryTV = new TextView(this.context);
        categoryTV.setPadding(this.cardItemPadding, this.cardItemPadding,
                this.cardItemPadding, this.cardItemPadding);
        categoryTV.setText(urlType);
        categoryTV.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        categoryTV.setTextSize(20);
        categoryTV.setTextColor(GankTypeDict.urlType2ColorDict.get(urlType));
        categoryTV.setBackgroundResource(R.drawable.shape_card_item_categoty_background);
        return categoryTV;
    }

    /**
     * 创建ImageView
     *
     * @return
     */
    private RatioImageView createRatioImageView() {
        return (RatioImageView) LayoutInflater
                .from(this.context)
                .inflate(R.layout.view_card_video_image, null);
    }

    /**
     * 创建分隔线
     *
     * @return
     */
    private View createDivider() {
        View divider = new View(this.context);
        divider.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , this.cardItemDivider));
        divider.setBackgroundColor(dividerColor);
        return divider;
    }

    /**
     * 创建目录下的分支
     *
     * @param baseGankData
     * @return
     */
    private TextView createCardItemText(BaseGankData baseGankData) {
        TextView itemText = (TextView) LayoutInflater.from(context)
                .inflate(R.layout.view_card_item_text, null);
        itemText.setPadding(this.cardItemPadding, this.cardItemPadding,
                this.cardItemPadding, this.cardItemPadding);
        String content = baseGankData.desc.trim() + "    " +
                String.format(this.viaModel, baseGankData.who);
        SpannableStringBuilder ssb = new SpannableStringBuilder(content);
        ssb.setSpan(new TextAppearanceSpan("serif", Typeface.ITALIC, this.viaTextSize,
                        this.viaColorStateList, this.viaColorStateList), content.indexOf(this.viaModelKey),
                content.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        itemText.setText(ssb);
        itemText.setTag(R.id.tag_card_item_url, baseGankData.url);
        itemText.setTag(R.id.tag_card_item_desc, baseGankData.desc.trim());
        itemText.setTag(R.id.tag_card_item_type, baseGankData.type);
        itemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DailyDetailAdapter.this.cardItemClickListener != null) {
                    DailyDetailAdapter.this.cardItemClickListener.onCardItemOnClick(
                            (String) v.getTag(R.id.tag_card_item_type),
                            (String) v.getTag(R.id.tag_card_item_desc),
                            (String) v.getTag(R.id.tag_card_item_url));
                }
            }
        });
        return itemText;
    }

    /**
     * 设置目录text点击事件
     *
     * @param cardItemClickListener
     */
    public void setOnCardItemClickListener(OnCardItemClickListener cardItemClickListener) {

        this.cardItemClickListener = cardItemClickListener;
    }

    public interface OnCardItemClickListener {

        void onCardItemOnClick(String urlType, String title, String url);

        void onWelfareOnClick(String url, String title, View v);
    }
}
