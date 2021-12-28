package com.example.api4_2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.UriPermission;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button btnReact,btnVue,btnAngularJS,btnLaravel, btnVaadin, btnSpring;
    TextView txtFrameworkName,txtAuthor,txtOfficial, txtDescription;
    ImageView logo;
    FloatingActionButton btnRefresh;
    View lyCurrency;
    ProgressBar loadingIndicator;
    private String frameworkName = "React";
    JSONObject dataFramework;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inisialisasiView();
        getDataFramework();
    }

    private void inisialisasiView() {
        txtFrameworkName = findViewById(R.id.txtFrameworkName);
        txtFrameworkName = findViewById(R.id.txtFrameworkName);
        lyCurrency = findViewById(R.id.lyCurrency);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtOfficial = findViewById(R.id.txtOfficial);
        txtDescription = findViewById(R.id.txtDescription);
        loadingIndicator = findViewById(R.id.loadingIndicator);
        logo = findViewById(R.id.logo);

        btnReact = findViewById(R.id.btnReact);
        btnReact.setOnClickListener(view -> showDataFramework("React"));

        btnVue = findViewById(R.id.btnVue);
        btnVue.setOnClickListener(view -> showDataFramework("Vue"));

        btnAngularJS = findViewById(R.id.btnAngularJS);
        btnAngularJS.setOnClickListener(view -> showDataFramework("AngularJS"));

        btnLaravel = findViewById(R.id.btnLaravel);
        btnLaravel.setOnClickListener(view -> showDataFramework("Laravel"));

        btnVaadin = findViewById(R.id.btnVaadin);
        btnVaadin.setOnClickListener(view -> showDataFramework("Vaadin"));

        btnSpring = findViewById(R.id.btnSpring);
        btnSpring.setOnClickListener(view -> showDataFramework("Spring Framework"));

        btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(view -> getDataFramework());
    }

    private void getDataFramework() {
        loadingIndicator.setVisibility(View.VISIBLE);
        String baseURL = "https://ewinsutriandi.github.io/mockapi/web_framework.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, baseURL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("MAIN",response.toString());
                        dataFramework = response;
                        showDataFramework(frameworkName);
                        loadingIndicator.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingIndicator.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(),"Gagal mengambil data",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    private void showDataFramework(String frameworkName) {
        this.frameworkName = frameworkName;
        // tampilkan nama framework terpilih
        txtFrameworkName.setText(frameworkName);
        try { // try catch untuk antisipasi error saat parsing JSON
            // tampilkan data framework
            JSONObject data = dataFramework.getJSONObject(frameworkName);
            txtAuthor.setText(data.getString("original_author"));
            String link = data.getString("official_website");
            txtOfficial.setLinksClickable(true);
            txtOfficial.setMovementMethod(LinkMovementMethod.getInstance());
            txtOfficial.setText(Html.fromHtml(link));
            txtDescription.setText(data.getString("description"));

            String imgUrl = data.getString("logo");
            Glide.with(this)
                    .load(imgUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .fitCenter()
                    .into(logo);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}