package com.aftebi.mynews.activity.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aftebi.mynews.R;
import com.aftebi.mynews.activity.SportNewsActivity;
import com.aftebi.mynews.activity.SportNewsDetailActivity;
import com.aftebi.mynews.adapter.AdapterSportNews;
import com.aftebi.mynews.databinding.FragmentSportBinding;
import com.aftebi.mynews.model.SportNews;
import com.aftebi.mynews.service.AppExecutors;
import com.aftebi.mynews.service.RecyclerItemClickListener;
import com.aftebi.mynews.service.SportNewsParser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.lang.Runnable;


/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private FragmentSportBinding binding;
    private SportNewsParser sportNewsParser;
    private List<SportNews> sportNewsList;
    private AdapterSportNews adapterSportNews;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
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

        binding = FragmentSportBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(R.layout.loading_layout);

        AlertDialog dialog = builder.create();



        try {
            //https://www.rtp.pt/noticias/rss/desporto/
            //https://desporto.sapo.pt/rss/
            //https://wwww.noticiasaominuto.com/rss/desporto
            URL url = new URL("https://wwww.noticiasaominuto.com/rss/desporto/");
            sportNewsParser = new SportNewsParser(url);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        dialog.show();

        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {

                sportNewsParser.parseXML();
                sportNewsList = sportNewsParser.getSportNewsList();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadSportNews(sportNewsList);
                        dialog.dismiss();
                    }
                });

            }
        });

        return root;
    }

    public void loadSportNews(List<SportNews> sn){

        //Config Recycler view
        //---- Config Adapter
        adapterSportNews = new AdapterSportNews(sn, getContext());
        //---- Comfig Recycler View
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.sportsNewsRecyclerViewNew.setLayoutManager(layoutManager);
        binding.sportsNewsRecyclerViewNew.setHasFixedSize(true);
        binding.sportsNewsRecyclerViewNew.setAdapter(adapterSportNews);

        binding.sportsNewsRecyclerViewNew.addOnItemTouchListener(new RecyclerItemClickListener(
                getContext(),
                binding.sportsNewsRecyclerViewNew,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        SportNews sportNews = sn.get(position);
                        Intent intent = new Intent(getContext(), SportNewsDetailActivity.class);
                        intent.putExtra("sportNewsItem", sportNews);
                        startActivity(intent);

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }
        ));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}