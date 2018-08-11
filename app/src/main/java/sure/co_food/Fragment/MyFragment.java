package sure.co_food.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.SuperTextView;

import org.greenrobot.eventbus.EventBus;

import sure.co_food.Activity.LoginActivity;
import sure.co_food.Activity.StarActivity;
import sure.co_food.R;
import sure.co_food.event.fnishEvent;
import sure.co_food.util.UserUtil;

import static sure.co_food.Contants.my;


/**
 * Created by dell88 on 2017/12/17 0017.
 */

public class MyFragment extends Fragment {
    private Context context;
    private View view;
    private Toolbar toolbar;
    private SuperTextView my_stars;
    private TextView user_name;
    private TextView tv_user_phone;
    private SuperTextView log_off;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);
        context = getActivity();
        initView();
        setListener();
        return view;
    }
    private void initView(){
        tv_user_phone=view.findViewById(R.id.tv_user_phone);
        my_stars=view.findViewById(R.id.my_stars);
        toolbar=view.findViewById(R.id.my_toolbar);
        toolbar.setTitle(my);
        toolbar.inflateMenu(R.menu.toolbar_my);
        user_name=view.findViewById(R.id.user_name);
        user_name.setText(UserUtil.gsonUsers.getUsername());
        tv_user_phone.setText(UserUtil.gsonUsers.getUserphone());
        log_off=view.findViewById(R.id.log_off);
    }
    public void setListener(){
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.setting){
                    Toast.makeText(context,"设置",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        my_stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, StarActivity.class);
                startActivity(intent);
            }
        });
        log_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new fnishEvent());
            }
        });
    }
}
