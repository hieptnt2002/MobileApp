package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileapp.Adapter.DanhmucAdapter;
import com.example.mobileapp.Adapter.MenuAdapter;
import com.example.mobileapp.Banner.Banner;
import com.example.mobileapp.Banner.BannerAdapter;
import com.example.mobileapp.Model.Danhmuc;
import com.example.mobileapp.Model.Menu;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    BannerAdapter bannerAdapter;
    RecyclerView recyclerView,recyclerViewCategory;
    NavigationView navigationView;
    ListView listViewMenu;
    List<Banner> arrBanner;
    Timer timer;
    TextView txtCart;
    // menu
    MenuAdapter menuAdapter;
    ArrayList<Menu> menuArrayList;
    ArrayList<Danhmuc> danhmucArrayList;
    DanhmucAdapter danhmucAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        actionBar();
        String t = txtCart.getText().toString();
        if(t.isEmpty()){
            return;
        }else txtCart.setBackground(this.getResources().getDrawable(R.drawable.num_cart));
        arrBanner = new ArrayList<>();
        arrBanner.add(new Banner(R.drawable.banner1));
        arrBanner.add(new Banner(R.drawable.banner2));
        arrBanner.add(new Banner(R.drawable.banner3));
        bannerAdapter = new BannerAdapter(this,arrBanner);
        viewPager.setAdapter(bannerAdapter);
        circleIndicator.setViewPager(viewPager);
        bannerAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSlideImages();
        dataListViewMenu();
        // danhmuc recyc
        dataRecycleViewDM();
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                new docJson().execute("http://hiepvakhanh21-001-site1.htempurl.com/show_danhmucc.php");
//            }
//        });
        loadCategory("http://hiepvakhanh21-001-site1.htempurl.com/show_danhmucc.php");
    }
    //add date recycleview danh muc
    public void dataRecycleViewDM(){
        recyclerViewCategory.setHasFixedSize(true);
        LinearLayoutManager linearLayout =new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategory.setLayoutManager(linearLayout);
    }
        // add data listview
    private void dataListViewMenu(){
        menuArrayList = new ArrayList<>();
        menuArrayList.add(new Menu("Điện thoại",R.drawable.baseline_phone_iphone_24));
        menuArrayList.add(new Menu("Laptop",R.drawable.baseline_computer_24));
        menuArrayList.add(new Menu("Phụ kiện",R.drawable.phukien));
        menuArrayList.add(new Menu("Tin tức",R.drawable.tintuc));
        menuArrayList.add(new Menu("Liên hệ",R.drawable.lienhe));
        menuArrayList.add(new Menu("Giới thiệu",R.drawable.gioithieu));

        menuAdapter = new MenuAdapter(menuArrayList,MainActivity.this);
        listViewMenu.setAdapter(menuAdapter);
    }
    private void autoSlideImages() {
        if(arrBanner == null || arrBanner.isEmpty() || viewPager == null){
            return;
        }
        if(timer == null){
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currenItem = viewPager.getCurrentItem();
                        int totalItem = arrBanner.size() - 1;
                        if(currenItem < totalItem){
                            currenItem ++;
                            viewPager.setCurrentItem(currenItem);
                        }
                        else
                            viewPager.setCurrentItem(0);
                    }
                });
            }
        },2000,3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer != null ){
            timer.cancel();
        }
    }

    public void actionBar(){
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    public void anhxa(){
        drawerLayout = findViewById(R.id.drawerLayout_main);
        toolbar = findViewById(R.id.toolbar_main);
        viewPager = findViewById(R.id.viewPager);
        circleIndicator = findViewById(R.id.circleIndicator);
//        recyclerView = findViewById(R.id.recyclerView_main);
        navigationView = findViewById(R.id.navigationView_main);
        listViewMenu = findViewById(R.id.list_menu);
        txtCart = findViewById(R.id.sl_cart);
        recyclerViewCategory = findViewById(R.id.recyclerView_category);
    }
    //doc noi dung url

    public void loadCategory(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                danhmucArrayList = new ArrayList<>();
                try{

                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject arr = jsonArray.getJSONObject(i);
                        danhmucArrayList.add(new Danhmuc(arr.getInt("id"),arr.getString("tensp"),arr.optString("anh")));
                    }
                    danhmucAdapter = new DanhmucAdapter(danhmucArrayList, MainActivity.this);
                    recyclerViewCategory.setAdapter(danhmucAdapter);
                }catch (JSONException e){
                    e.printStackTrace();
                }            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }
}