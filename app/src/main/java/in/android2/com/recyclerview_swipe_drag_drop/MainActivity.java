package in.android2.com.recyclerview_swipe_drag_drop;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<String> mDataList;
    private RecyclerViewAdapter mAdapter;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initRecView();
    }
    //method for initializing toolbar
    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Main Activity");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });
    }
    //method for initializing recycler view
    private void initRecView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDataList = new ArrayList<String>();
        addDummmyData(mDataList);
        mAdapter = new RecyclerViewAdapter(this, mDataList);
        mRecyclerView.setAdapter(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mIthCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    // method to add dummy data to recycler view
    private void addDummmyData(ArrayList<String> mDataList) {
        for (int i = 0; i < 100; i++) {
            mDataList.add("Item " + i);
        }
    }

    // Callback for ItemTouchHelper
    ItemTouchHelper.Callback mIthCallback = new ItemTouchHelper.Callback() {
        // method for getting the direction of movements
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN; // for drag and drop
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END; // for swipe left and right
            return makeMovementFlags(dragFlags, swipeFlags);
        }
        // method for drag and drop
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            Collections.swap(mDataList, viewHolder.getAdapterPosition(), target.getAdapterPosition());
            mAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }
        // method for swiping left and right
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            mDataList.remove(viewHolder.getAdapterPosition());
            mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        }
    };
}
