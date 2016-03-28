package com.chn.halo.ui.activity;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chn.halo.R;
import com.chn.halo.core.BaseButterKnifeActivity;
import com.chn.halo.custom.DividerGridItemDecoration;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 张伟光
 * @ClassName: RecyclerActivity
 * @date 2016-3-25
 * @Description: RecyclerActivity
 */
public class RecyclerActivity extends BaseButterKnifeActivity {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_recycler;
    }

    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private HomeAdapter mAdapter;


    @Override
    protected void initializeAfterOnCreate() {
        super.initializeAfterOnCreate();
        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
//                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());
    }

    protected void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    getThis()).inflate(R.layout.item_recycler, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            //渐进式加载
            ImageRequest request = ImageRequestBuilder
                    .newBuilderWithSource(Uri.parse("https://i.ytimg.com/vi/tntOCGkgt98/maxresdefault.jpg"))
                    .setProgressiveRenderingEnabled(true)
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setOldController(holder.tv.getController())
                    .build();

            holder.tv.setController(controller);
            //holder.tv.setImageURI(Uri.parse("https://i.ytimg.com/vi/tntOCGkgt98/maxresdefault.jpg"));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            SimpleDraweeView tv;

            public MyViewHolder(View view) {
                super(view);
                tv = (SimpleDraweeView) view.findViewById(R.id.id_num);
            }
        }
    }
}