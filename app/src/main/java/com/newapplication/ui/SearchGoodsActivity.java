package com.newapplication.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.newapplication.R;
import com.newapplication.bean.Goods_List;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class SearchGoodsActivity extends BaseActivity{

    EditText Search;
    ImageButton SearchButton;

    List<Goods_List> goodsList=new ArrayList<>();

    PullToRefreshListView mPull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach);
        initView();
    }

    private void initView() {
        Search = (EditText) findViewById(R.id.serach_goods);
        SearchButton = (ImageButton) findViewById(R.id.btn_search_goods);
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    static final int STATE_REFRESH=0;
    static final int STATE_MORE=1;
    private int limit = 10;		// 每页的数据是10条
    private int curPage = 0;

    private void queryGoods(final int page,final int actionType){
        String search = Search.getText().toString();
        BmobQuery<Goods_List> query1 = new BmobQuery<Goods_List>();
        query1.addWhereContains("thingDetails", search);
        BmobQuery<Goods_List> query2 = new BmobQuery<Goods_List>();
        query2.addWhereContains("goodsName", search);
        List<BmobQuery<Goods_List>> queries = new ArrayList<BmobQuery<Goods_List>>();
        queries.add(query1);
        queries.add(query2);
        BmobQuery<Goods_List> mainQuery = new BmobQuery<Goods_List>();
        mainQuery.or(queries);
        mainQuery.setSkip(page * limit);
        mainQuery.setLimit(limit);
        mainQuery.order("-updatedAt,-createdAt");
        mainQuery.findObjects(this, new FindListener<Goods_List>() {
            @Override
            public void onSuccess(List<Goods_List> list) {

                if (list.size() > 0) {
                    if (actionType == STATE_REFRESH) {
                        curPage = 0;
                        goodsList.clear();
                    }
                    for (Goods_List list1 : list) {
                        goodsList.add(list1);
                    }
                    curPage++;
                } else if (actionType == STATE_MORE) {
                    Toast.makeText(SearchGoodsActivity.this,"没有更多物品详情",Toast.LENGTH_LONG).show();
                } else if (actionType == STATE_REFRESH) {
                    Toast.makeText(SearchGoodsActivity.this,"没有更多物品详情",Toast.LENGTH_LONG).show();
                } else if (list.size() == 0) {
                    Toast.makeText(SearchGoodsActivity.this,"暂时没有人发布物品",Toast.LENGTH_LONG).show();
                }
                mPull.onRefreshComplete();
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(SearchGoodsActivity.this,"查询失败:"+s,Toast.LENGTH_LONG).show();
                mPull.onRefreshComplete();
            }
        });
    }
}
