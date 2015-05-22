package trains.thoughtworks.com.trains;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import trains.thoughtworks.com.trains.entity.Method;


public class MainActivity extends Activity implements View.OnClickListener{

    private EditText mStartEdit;
    private EditText mEndEdit;
    private Button mQueryButton;

    private String mStartNode;
    private String mEndNode;

    private Method mMethod ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        mMethod = new Method(this);
    }

    private void initViews() {
        mStartEdit = (EditText) findViewById(R.id.start_edit);
        mEndEdit = (EditText) findViewById(R.id.end_edit);
        mQueryButton = (Button) findViewById(R.id.query_button);

        mQueryButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        mMethod.getResultSet().clear();
        mMethod.setRouteDistance(0);

        mStartNode = mStartEdit.getText().toString().trim();
        mEndNode = mEndEdit.getText().toString().trim();
        if (mStartNode == null || "".equals(mStartNode) ||
                mEndNode == null || "".equals(mEndNode)) {
            Toast.makeText(MainActivity.this,"请输入起点或重点",Toast.LENGTH_SHORT).show();
        } else {
            mMethod.getAllRoutes(mStartNode, mEndNode);
            Hashtable<String,Long> resultTables = mMethod.getResultSet();

            Iterator iterator = resultTables.keySet().iterator();
            while (iterator != null && iterator.hasNext()) {
                String key = (String) iterator.next();
                Log.e("TAG",resultTables.get(key)+"-"+key);
            }
        }
    }

    private class SortByKey implements Comparator {
        @Override
        public int compare(Object o, Object o1) {
            if ((Long)o > (Long)o1) {
                return 0;
            }
            return 1;
        }
    }
}
