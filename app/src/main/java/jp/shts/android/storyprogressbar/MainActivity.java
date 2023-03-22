package jp.shts.android.storyprogressbar;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class MainActivity extends AppCompatActivity implements StoriesProgressView.StoriesListener {

    private static final int PROGRESS_COUNT = 6;

    private StoriesProgressView storiesProgressView;
    private ViewPager2 viewPager;

    private static final int START_POSITION = 0;

    private final long[] durations = new long[]{
            500L, 1000L, 1500L, 4000L, 5000L, 1000,
    };

    long pressTime = 0L;
    long limit = 500L;

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    pressTime = System.currentTimeMillis();
                    storiesProgressView.pause();
                    return false;
                case MotionEvent.ACTION_UP:
                    long now = System.currentTimeMillis();
                    storiesProgressView.resume();
                    return limit < now - pressTime;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        storiesProgressView = (StoriesProgressView) findViewById(R.id.stories);
        storiesProgressView.setStoriesCount(PROGRESS_COUNT);
        storiesProgressView.setStoryDuration(2000L);
        // or
        // storiesProgressView.setStoriesCountWithDurations(durations);
        storiesProgressView.setStoriesListener(this);
//        storiesProgressView.startStories();
        storiesProgressView.startStories(START_POSITION);
        initRecyclerView();

    }

    private void initRecyclerView() {
        viewPager = (ViewPager2) findViewById(R.id.recycler_view);

        ImageAdapter adapter = new ImageAdapter();
        final List<Integer> resources = new ArrayList<Integer>();
        resources.add(R.drawable.sample1);
        resources.add(R.drawable.sample2);
        resources.add(R.drawable.sample3);
        resources.add(R.drawable.sample4);
        resources.add(R.drawable.sample5);
        resources.add(R.drawable.sample6);
        adapter.setList(resources);
        viewPager.setAdapter(adapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                storiesProgressView.startStories(position);
            }
        });
    }

    @Override
    public void onNext(int current) {
        viewPager.setCurrentItem(current, true);
    }

    @Override
    public void onPrev(int current) {
        viewPager.setCurrentItem(current, true);
    }

    @Override
    public boolean onComplete(int current) {
        viewPager.setCurrentItem(current, true);
        storiesProgressView.startStories(current);
        return false;
    }

    @Override
    protected void onDestroy() {
        // Very important !
        storiesProgressView.destroy();
        super.onDestroy();
    }
}
