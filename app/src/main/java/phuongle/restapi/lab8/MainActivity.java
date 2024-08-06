package phuongle.restapi.lab8;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import nvdhai2003.restapi.lab7.R;

public class MainActivity extends AppCompatActivity {
    private Button bntSelect, btnInsert, btnDelete, btnUpdate;
    private TextView tvKQ;
    private EditText txt1, txt2, txt3;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bntSelect = findViewById(R.id.btn_select);
        btnInsert = findViewById(R.id.btn_insert);
        btnDelete = findViewById(R.id.btn_delete);
        btnUpdate = findViewById(R.id.btn_update);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        tvKQ = findViewById(R.id.tv_kq);
        bntSelect.setOnClickListener(v -> {
            selectVolley();
        });

        btnInsert.setOnClickListener(v -> {
            insertVolley();
        });

        btnDelete.setOnClickListener(v -> {
            deleteVolley();
        });

        btnUpdate.setOnClickListener(v -> {
            updateVolley();
        });
    }

    private void updateVolley() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://hungnq28.000webhostapp.com/su2024/update.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tvKQ.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvKQ.setText(error.getMessage());
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> mydata = new HashMap<>();
                mydata.put("MaSP", txt1.getText().toString());
                mydata.put("TenSP", txt2.getText().toString());
                mydata.put("MoTa", txt3.getText().toString());
                return mydata;
            }
        };
        queue.add(request);
    }

    private void deleteVolley() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://hungnq28.000webhostapp.com/su2024/delete.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tvKQ.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvKQ.setText(error.getMessage());
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> mydata = new HashMap<>();
                mydata.put("MaSP", txt1.getText().toString());
                return mydata;
            }
        };
        queue.add(request);
    }

    private void insertVolley() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://hungnq28.000webhostapp.com/su2024/insert.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tvKQ.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvKQ.setText(error.getMessage());
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> mydata = new HashMap<>();
                mydata.put("MaSP", txt1.getText().toString());
                mydata.put("TenSP", txt2.getText().toString());
                mydata.put("MoTa", txt3.getText().toString());
                return mydata;
            }
        };
        queue.add(request);

    }

    String strKQ = "";

    private void selectVolley() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://hungnq28.000webhostapp.com/su2024/select.php";
        JsonObjectRequest request = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //lay ve array:  "products": [
                    JSONArray jsonArray = response.getJSONArray("products");
                    //for array
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject p = jsonArray.getJSONObject(i);//lay ve doi tuong i
                        String MaSP = p.getString("MaSP");
                        String TenSP = p.getString("TenSP");
                        String MoTa = p.getString("MoTa");
                        strKQ += "MaSP: " + MaSP + "; TenSP:" + TenSP + "; MoTa: " + MoTa + "\n";
                    }
                    tvKQ.setText(strKQ);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvKQ.setText(error.getMessage());
            }
        });
        //4. thuc thi request
        queue.add(request);
    }
}