package animalize.github.com.quantangshi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import animalize.github.com.quantangshi.Database.MyDatabaseHelper;
import animalize.github.com.quantangshi.UIPoem.OnePoemActivity;


public class MainActivity
        extends AppCompatActivity
        implements View.OnClickListener {
    private int mPoemCount = -1;

    private EditText idEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 防止从安装器打开出现问题
        if (!isTaskRoot()) {
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        mPoemCount = MyDatabaseHelper.getPoemCount();

        // toolbar
        Toolbar tb = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(tb);

        // 打开诗 ------------------------------
        Button bt = (Button) findViewById(R.id.main_viewpoem);
        bt.setOnClickListener(this);

        // 跳转指定ID ------------------------------
        idEdit = (EditText) findViewById(R.id.jump_edit);
        bt = (Button) findViewById(R.id.jump_button);
        bt.setOnClickListener(this);

        // 清空ID
        bt = (Button) findViewById(R.id.jump_clear);
        bt.setOnClickListener(this);

        // 标签检索 -------------------------
        bt = (Button) findViewById(R.id.main_opentag);
        bt.setOnClickListener(this);

        // 标签管理 -------------------------
        bt = (Button) findViewById(R.id.main_managetag);
        bt.setOnClickListener(this);

        // 阅读设置 ------------------------------
        bt = (Button) findViewById(R.id.main_option);
        bt.setOnClickListener(this);

        // 设置 ------------------------------
        bt = (Button) findViewById(R.id.main_setting);
        bt.setOnClickListener(this);

        // 使用技巧 ---------------------------
        bt = (Button) findViewById(R.id.main_tip);
        bt.setOnClickListener(this);

        // 关于 ------------------------------
        bt = (Button) findViewById(R.id.main_about);
        bt.setOnClickListener(this);

        // 启动后跳转
        if (savedInstanceState == null) {
            Context c = MyApplication.getContext();
            SharedPreferences sp = c.getSharedPreferences(
                    "global",
                    Context.MODE_PRIVATE);
            if (sp.getBoolean("jump", false)) {
                OnePoemActivity.actionStart(MainActivity.this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_viewpoem:
                OnePoemActivity.actionStart(MainActivity.this);
                break;

            case R.id.jump_button:
                String s = idEdit.getText().toString();
                if (s.equals("")) {
                    showToast("请输入编号");
                    return;
                }

                int id = Integer.parseInt(s);
                if (1 <= id && id <= mPoemCount) {
                    OnePoemActivity.actionStart(MainActivity.this, id);
                } else {
                    showToast("请确保: 1<=编号<=" + mPoemCount);
                }
                break;

            case R.id.jump_clear:
                idEdit.setText("");
                idEdit.clearFocus();
                break;

            case R.id.main_opentag:
                TagSearchActivity.actionStart(MainActivity.this);
                break;

            case R.id.main_managetag:
                TagManageActivity.actionStart(MainActivity.this);
                break;

            case R.id.main_option:
                OptionActivity.actionStart(MainActivity.this);
                break;

            case R.id.main_setting:
                BackupActivity.actionStart(MainActivity.this);
                break;

            case R.id.main_tip:
                TipActivity.actionStart(MainActivity.this);
                break;

            case R.id.main_about:
                AboutActivity.actionStart(MainActivity.this);
                break;
        }
    }

    private void showToast(String s) {
        Toast t = Toast.makeText(this,
                s,
                Toast.LENGTH_SHORT);

        // 字体
        ViewGroup group = (ViewGroup) t.getView();
        TextView messageTextView = (TextView) group.getChildAt(0);
        messageTextView.setTextSize(18);
        // 居中
        t.setGravity(Gravity.CENTER, 0, 0);
        // 显示
        t.show();
    }
}
