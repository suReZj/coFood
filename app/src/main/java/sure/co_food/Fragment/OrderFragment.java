package sure.co_food.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import sure.co_food.R;
import sure.co_food.adapter.order_adapter;
import sure.co_food.bean.order;
import sure.co_food.gson.GsonOrder;
import sure.co_food.gson.GsonOrderList;
import sure.co_food.retrofit.getOrderList;
import sure.co_food.util.RetrofitUtil;
import sure.co_food.util.UserUtil;

import static sure.co_food.Contants.localhost;
import static sure.co_food.Contants.order;
import static sure.co_food.Contants.orders;

/**
 * Created by dell88 on 2017/12/18 0018.
 */

public class OrderFragment extends Fragment {
    private View view;
    private Context context;
    private RecyclerView recyclerView;
    private order_adapter adapter;
    private LinearLayoutManager layoutManager;
    private Toolbar toolbar;
    private List<GsonOrder> list;
    private SwipeRefreshLayout order_refresh;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);
        context = getActivity();
        initView();
        return view;
    }

    private void initView() {
        toolbar = view.findViewById(R.id.order_toolbar);
        toolbar.setTitle(order);
        recyclerView = view.findViewById(R.id.order_recyclerView);
        order_refresh=view.findViewById(R.id.order_refresh);


        order_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Retrofit retrofit = RetrofitUtil.getRetrofit(localhost + orders);
                getOrderList getOrderList=retrofit.create(getOrderList.class);
                getOrderList.getList(UserUtil.gsonUsers.getUserphone())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<GsonOrderList>() {
                            private Disposable disposable;
                            @Override
                            public void onSubscribe(Disposable d) {
                                disposable = d;
                            }

                            @Override
                            public void onNext(GsonOrderList gsonOrderList) {
                                UserUtil.list=gsonOrderList;
                                list=UserUtil.list.getList();
                                adapter.notifyDataSetChanged();
                                order_refresh.setRefreshing(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                disposable.dispose();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });


        getData();
    }


    public void getData() {
        list=UserUtil.list.getList();
        adapter=new order_adapter(list);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }
}
