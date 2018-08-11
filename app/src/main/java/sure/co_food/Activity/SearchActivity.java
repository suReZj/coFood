package sure.co_food.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import sure.co_food.R;
import sure.co_food.adapter.search_adapter;
import sure.co_food.bean.goods;
import sure.co_food.bean.search;
import sure.co_food.bean.shop;
import sure.co_food.gson.GsonGood;
import sure.co_food.gson.GsonSearch;
import sure.co_food.retrofit.searchGood;
import sure.co_food.util.RetrofitUtil;

import static sure.co_food.Contants.good;
import static sure.co_food.Contants.localhost;

public class SearchActivity extends BaseActivity {
    private Toolbar toolbar;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private List<shop> list = new ArrayList<>();
    private search_adapter adapter;
    private LinearLayoutManager layoutManager;
    private TextView search_activity_textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        toolbar = findViewById(R.id.search_activity_toolbar);
        recyclerView = findViewById(R.id.search_activity_recyclerView);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        searchView = findViewById(R.id.search_activity_searchView);
        searchView.onActionViewExpanded();
        searchView.setQueryHint("搜索商家、商品名称");
        SearchView.SearchAutoComplete textView = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        textView.setTextColor(Color.WHITE);
        textView.setHintTextColor(getResources().getColor(R.color.Gray));

        search_activity_textView=findViewById(R.id.search_activity_textView);
    }

    @Override
    protected void setListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                Retrofit retrofit = RetrofitUtil.getRetrofit(localhost + good);
                searchGood searchGood = retrofit.create(searchGood.class);
                searchGood.searchGood(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<GsonSearch>() {
                            private Disposable disposable;

                            @Override
                            public void onSubscribe(Disposable d) {
                                disposable = d;
                            }

                            @Override
                            public void onNext(GsonSearch searchGood) {
                                Log.e("search", searchGood.toString());
                                List<search> list1 = new ArrayList<>();
                                if (searchGood.getList().size() > 0) {
                                    String shopphone = searchGood.getList().get(0).getShopphone();
                                    List<GsonGood> schGoods = new ArrayList<>();
                                    if (searchGood.getList().get(0).getName().contains(query)) {
                                        schGoods.add(searchGood.getList().get(0));
                                    }
                                    search search = new search();
                                    search.setShopphone(shopphone);
                                    for (int i = 1; i < searchGood.getList().size(); i++) {
                                        if (!searchGood.getList().get(i).getShopphone().equals(shopphone)) {
                                            list1.add(new search(shopphone, schGoods));
                                            shopphone = searchGood.getList().get(i).getShopphone();
                                            schGoods = new ArrayList<>();
                                            if (searchGood.getList().get(i).getName().contains(query)) {
                                                schGoods.add(searchGood.getList().get(i));
                                            }
                                        } else {
                                            if (searchGood.getList().get(i).getName().contains(query)) {
                                                schGoods.add(searchGood.getList().get(i));
                                            }
                                        }
                                    }
                                    list1.add(new search(shopphone, schGoods));
                                    System.out.println(list1.get(0).getList().size());

                                    layoutManager = new LinearLayoutManager(SearchActivity.this);
                                    adapter = new search_adapter(list1);
                                    recyclerView.setLayoutManager(layoutManager);
                                    recyclerView.setAdapter(adapter);
                                    search_activity_textView.setVisibility(View.GONE);
                                }else {
                                    search_activity_textView.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("search", e.toString());
                                disposable.dispose();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                if (newText.length() > 0) {
                    Retrofit retrofit = RetrofitUtil.getRetrofit(localhost + good);
                    searchGood searchGood = retrofit.create(searchGood.class);
                    searchGood.searchGood(newText)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<GsonSearch>() {
                                private Disposable disposable;

                                @Override
                                public void onSubscribe(Disposable d) {
                                    disposable = d;
                                }

                                @Override
                                public void onNext(GsonSearch searchGood) {
                                    Log.e("search", searchGood.toString());
                                    List<search> list1 = new ArrayList<>();
                                    if (searchGood.getList().size() > 0) {
                                        String shopphone = searchGood.getList().get(0).getShopphone();
                                        List<GsonGood> schGoods = new ArrayList<>();
                                        if (searchGood.getList().get(0).getName().contains(newText)) {
                                            schGoods.add(searchGood.getList().get(0));
                                        }
                                        search search = new search();
                                        search.setShopphone(shopphone);
                                        for (int i = 1; i < searchGood.getList().size(); i++) {
                                            if (!searchGood.getList().get(i).getShopphone().equals(shopphone)) {
                                                list1.add(new search(shopphone, schGoods));
                                                shopphone = searchGood.getList().get(i).getShopphone();
                                                schGoods = new ArrayList<>();
                                                if (searchGood.getList().get(i).getName().contains(newText)) {
                                                    schGoods.add(searchGood.getList().get(i));
                                                }
                                            } else {
                                                if (searchGood.getList().get(i).getName().contains(newText)) {
                                                    schGoods.add(searchGood.getList().get(i));
                                                }
                                            }
                                        }
                                        list1.add(new search(shopphone, schGoods));
                                        System.out.println(list1.get(0).getList().size());

                                        layoutManager = new LinearLayoutManager(SearchActivity.this);
                                        adapter = new search_adapter(list1);
                                        recyclerView.setLayoutManager(layoutManager);
                                        recyclerView.setAdapter(adapter);
                                        search_activity_textView.setVisibility(View.GONE);
                                    }else {
                                        search_activity_textView.setVisibility(View.VISIBLE);
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e("search", e.toString());
                                    disposable.dispose();
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                } else {
                    layoutManager = new LinearLayoutManager(SearchActivity.this);
                    adapter = new search_adapter(new ArrayList<search>());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    search_activity_textView.setVisibility(View.GONE);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
