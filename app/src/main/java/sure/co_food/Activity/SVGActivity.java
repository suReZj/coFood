package sure.co_food.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jaredrummler.android.widget.AnimatedSvgView;

import sure.co_food.R;
import sure.co_food.bean.ModelSVG;

public class SVGActivity extends BaseActivity {
    private RelativeLayout mActivitySvg;
    private AnimatedSvgView mSvgView;
    private Handler checkhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            mAppName.setVisibility(View.VISIBLE);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_svg);
        mActivitySvg=findViewById(R.id.activity_svg);
        mSvgView=findViewById(R.id.animated_svg_view);
        setSvg(ModelSVG.values()[4]);
        mSvgView.start();
        CheckUpdate();
    }

    @Override
    protected void setListener() {
    }

    private void setSvg(ModelSVG modelSvg) {
        mSvgView.setGlyphStrings(modelSvg.glyphs);
        mSvgView.setFillColors(modelSvg.colors);
        mSvgView.setViewportSize(modelSvg.width, modelSvg.height);
        mSvgView.setTraceResidueColor(0x32000000);
        mSvgView.setTraceColors(modelSvg.colors);
        mSvgView.rebuildGlyphData();
        mSvgView.start();
    }

    /**
     * 检查是否有新版本，如果有就升级
     */
    private void CheckUpdate() {
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(500);
                    Message msg = checkhandler.obtainMessage();
                    checkhandler.sendMessage(msg);
                    Thread.sleep(2000);
                    toMain();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void toMain() {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
