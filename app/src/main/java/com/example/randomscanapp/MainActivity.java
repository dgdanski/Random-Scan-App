package com.example.randomscanapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.randomscanapp.data.ItemInfo;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements InfoRecyclerAdapter.ItemClickListener {

    private LiveDataTimerViewModel mLiveDataTimerViewModel;
    private ArrayList<ItemInfo> itemInfos = new ArrayList<>();
    private InfoRecyclerAdapter adapter;
    private final String SAVED_INSTANCE_JSON = "SAVED_INSTANCE_JSON";
    private final String SAVED_INSTANCE_TOOLBAR_COLOR_RES_ID = "SAVED_INSTANCE_TOOLBAR_COLOR_RES_ID";
    private int toolbarColorResId;
    private final Observer<Long> elapsedTimeObserver = aLong -> insertSingleInfoItem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            itemInfos = UtilsKt.fromJson(savedInstanceState.getString(SAVED_INSTANCE_JSON));
            changeToolbarColor(savedInstanceState.getInt(SAVED_INSTANCE_TOOLBAR_COLOR_RES_ID));
        }

        setContentView(R.layout.activity_main);

        mLiveDataTimerViewModel = new ViewModelProvider(this, new MyViewModelFactory()).get(LiveDataTimerViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new InfoRecyclerAdapter(this, itemInfos);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
    }

    private void changeToolbarColor(int colorResId) {
        if (colorResId == 0) {
            return;
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(colorResId)));
        toolbarColorResId = colorResId;

        if (colorResId == R.color.white) {
            setToolbarTitleColor(Color.BLUE, actionBar);
        } else {
            setToolbarTitleColor(Color.WHITE, actionBar);
        }
    }

    private void setToolbarTitleColor(int color, ActionBar actionBar) {
        Spannable text = new SpannableString(actionBar.getTitle());
        text.setSpan(new ForegroundColorSpan(color), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        actionBar.setTitle(text);
    }

    @Override
    protected void onResume() {
        super.onResume();
        subscribe();
    }

    private void subscribe() {
        mLiveDataTimerViewModel.getElapsedTime().observe(this, elapsedTimeObserver);
    }

    private void insertSingleInfoItem() {
        ItemInfo itemInfo = ItemInfo.Companion.factory();
        int insertIndex = adapter.getItemCount();
        itemInfos.add(insertIndex, itemInfo);
        itemInfos = sort(itemInfos);
        adapter.notifyItemInserted(insertIndex);
        adapter.notifyDataSetChanged();
        UtilsKt.writeData(this, itemInfos);
    }

    private ArrayList<ItemInfo> sort(ArrayList<ItemInfo> arrayList) {
        Collections.sort(arrayList, (itemInfo1, itemInfo2) -> itemInfo1.getBarcode().getValue().compareToIgnoreCase(itemInfo2.getBarcode().getValue()));
        return arrayList;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLiveDataTimerViewModel.getElapsedTime().removeObserver(elapsedTimeObserver);
    }

    @Override
    public void onItemClick(@org.jetbrains.annotations.Nullable Integer colorResId) {
        changeToolbarColor(colorResId);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(SAVED_INSTANCE_JSON, UtilsKt.asJson(itemInfos));
        outState.putInt(SAVED_INSTANCE_TOOLBAR_COLOR_RES_ID, toolbarColorResId);
        super.onSaveInstanceState(outState);
    }
}
