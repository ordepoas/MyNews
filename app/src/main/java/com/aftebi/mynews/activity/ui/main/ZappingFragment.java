package com.aftebi.mynews.activity.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.aftebi.mynews.R;
import com.aftebi.mynews.activity.TechNewsActivity;
import com.aftebi.mynews.activity.TechNewsDetailActivity;
import com.aftebi.mynews.adapter.AdapterTechNews;
import com.aftebi.mynews.adapter.AdapterZapping;
import com.aftebi.mynews.databinding.FragmentZappingBinding;
import com.aftebi.mynews.model.TechNews;
import com.aftebi.mynews.model.Zapping;
import com.aftebi.mynews.service.AppExecutors;
import com.aftebi.mynews.service.RecyclerItemClickListener;
import com.aftebi.mynews.service.TechNewsParser;
import com.aftebi.mynews.service.ZappingParser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class ZappingFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private FragmentZappingBinding binding;
    private ZappingParser zappingParser;
    private List<Zapping> zappingList;
    private AdapterZapping adapterZapping;

    public static ZappingFragment newInstance(int index) {
        ZappingFragment fragment = new ZappingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentZappingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //---- config init
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(R.layout.loading_layout);

        AlertDialog dialog = builder.create();

        try {
            URL url = new URL("https://www.zerozero.pt/rss/zapping.php/");
            zappingParser = new ZappingParser(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        dialog.show();

        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {

                //techNews = parseXML();
                zappingParser.parseXML();
                zappingList = zappingParser.getZappingList();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadZapping(zappingList);
                        dialog.dismiss();
                    }
                });
            }
        });

        return root;
    }

    public void loadZapping(List<Zapping> z){

        //Config Recycler view
        //---- Config Adapter
        adapterZapping = new AdapterZapping(z, getContext());
        //---- Comfig Recycler View
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.zappingRecyclerView.setLayoutManager(layoutManager);
        binding.zappingRecyclerView.setHasFixedSize(true);
        binding.zappingRecyclerView.setAdapter(adapterZapping);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }}