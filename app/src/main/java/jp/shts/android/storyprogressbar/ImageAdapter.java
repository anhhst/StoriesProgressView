package jp.shts.android.storyprogressbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class ImageAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    public ImageAdapter() {
        super(R.layout.item_image);
    }

    public ImageAdapter(int layoutResId, @Nullable List<Integer> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Integer i) {
        baseViewHolder.setImageResource(R.id.image_view, i);
    }
}
