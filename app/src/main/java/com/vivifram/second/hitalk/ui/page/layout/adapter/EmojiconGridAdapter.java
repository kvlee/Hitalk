package com.vivifram.second.hitalk.ui.page.layout.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vivifram.second.hitalk.R;
import com.vivifram.second.hitalk.bean.Emojicon;
import com.zuowei.utils.common.SmileUtils;

public class EmojiconGridAdapter extends ArrayAdapter<Emojicon>{

    private Emojicon.Type emojiconType;


    public EmojiconGridAdapter(Context context, int textViewResourceId, List<Emojicon> objects, Emojicon.Type emojiconType) {
        super(context, textViewResourceId, objects);
        this.emojiconType = emojiconType;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            if(emojiconType == Emojicon.Type.BIG_EXPRESSION){
                convertView = View.inflate(getContext(), R.layout.row_big_expression_layout, null);
            }else{
                convertView = View.inflate(getContext(), R.layout.row_expression_layout, null);
            }
        }
        
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_expression);
        TextView textView = (TextView) convertView.findViewById(R.id.tv_name);
        Emojicon emojicon = getItem(position);
        if(textView != null && emojicon.getName() != null){
            textView.setText(emojicon.getName());
        }
        if(SmileUtils.DELETE_KEY.equals(emojicon.getEmojiText())){
            imageView.setImageResource(R.drawable.delete_expression);
        }else{
            if(emojicon.getIcon() != 0){
                imageView.setImageResource(emojicon.getIcon());
            }else if(emojicon.getIconPath() != null){
                Glide.with(getContext()).load(emojicon.getIconPath()).placeholder(R.drawable.default_expression).into(imageView);
            }
        }
        
        
        return convertView;
    }
    
}