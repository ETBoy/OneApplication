package com.example.lsx.oneapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private Button mButtonGetData;
    private EditText mEditTextUrl;
    private TextView mTextResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonGetData= (Button) findViewById(R.id.button_main_getData);
        mEditTextUrl= (EditText) findViewById(R.id.edit_main_url);
        mTextResult= (TextView) findViewById(R.id.text_main_result);
        mButtonGetData.setOnClickListener(this);
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button_main_getData:
                String url=mEditTextUrl.getText().toString();
                HttpUtils http = new HttpUtils();
                url="http://tapi.test.tuoguibao.com/basicapi.php?ac=1002&mobile=18562172800&password=123456";
                http.send(HttpRequest.HttpMethod.GET,
                      url,
                        new RequestCallBack<String>(){
                            @Override
                            public void onLoading(long total, long current, boolean isUploading) {
                                Log.d("MainActivity", "onLoading: ");
                                mTextResult.setText(current + "/" + total);
                            }

                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {

                                try {
                                    JSONTokener jsonParser = new JSONTokener(responseInfo.result);
                                    JSONObject  jsonObject=(JSONObject) jsonParser.nextValue();
                                    mTextResult.setText("运行结果:\n"+jsonObject.getString("info"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } finally {

                                }


                            }

                            @Override
                            public void onStart() {
                                Log.d("MainActivity", "onStart: ");
                            }

                            @Override
                            public void onFailure(HttpException error, String msg) {
                                Log.d("MainActivity", "onFailure:");
                            }
                        });
                break;

        }


    }
}
