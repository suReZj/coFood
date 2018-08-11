package sure.co_food.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by dell88 on 2017/12/17 0017.
 */

public class BaseActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
        setListener();
    }
    protected void initView(Bundle savedInstanceState){

    }
    protected void setListener(){

    }

}
