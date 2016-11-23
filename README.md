# mylibrary

这个库的使用只要在gradle中添加 compile 'com.betterda:mylibrary:2.2.6'即可


1.自定义的Toast主要是为了解决在5.0以上用户关闭了通知时无法显示Toast的问题.用法很简单只要在之前使用系统的Toast的地方换成自己Toast就可以了

2.recycleview上拉加载的使用
  首先使用HeaderAndFooterRecyclerViewAdapter来包装普通的适配器.
  然后调用rvBalance.addOnScrollListener(new EndlessRecyclerOnScrollListener(getmActivity()) {
            //加载更多的方法
            @Override
            public void onLoadNextPage(View view) {
                //滑到了最后,如果是正常状态才开始加载
                if (LoadingFooter.State.Normal == RecyclerViewStateUtils.getFooterViewState(rvBalance)) {
                    //设置为加载状态 只需要修改rvbanlance 为你自己的rv
                    RecyclerViewStateUtils.setFooterViewState(this, rvBalance, LoadingFooter.State.Loading, null);
                    //网络请求加载更多
                    
                }
            }
           //用来完成当数据没满一页时,不显示加载更多
            @Override
            public void show(boolean isShow) {
                /**
                 * listOrder表示rv的容器
                 * rvBalance 表示rv
                 * size 表示你分页请求的数量
                 */
                
                RecyclerViewStateUtils.show(isShow, listOrder, rvBalance, this,size);
            }
        });
3.LoadingPager 集成加载,加载错误,数据为空的界面
   使用方法:    <FrameLayout
        android:layout_above="@id/relative_balbance_bottom"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <android.support.v7.widget.RecyclerView
            android:id="@+id/rv_layout"
            android:layout_width="match_parent"
            android:layout_height="match_parent">

        </android.support.v7.widget.RecyclerView>


        <com.betterda.mylibrary.LoadingPager
            android:id="@+id/loadpager_layout"
            android:layout_width="match_parent"
            android:layout_height="match_parent"></com.betterda.mylibrary.LoadingPager>

    </FrameLayout>
    将loadpager覆盖在需要显示的控件上方
    调用loadpager的setLoadVisable显示加载界面,调用setErrorVisable显示错误界面,调用setEmptyVisable显示数据为空的界面,调用hide方法可以将界面都隐藏.调用setonEmptyClickListener和setonErrorClickListener可以设置加载错误和数据为空时的点击事件
    
  4. 时间选择框
 
  ChangeBirthDialog changeBirthDialog = new ChangeBirthDialog(getmActivity());
        //设置初始时间
        changeBirthDialog.setDate(UtilMethod.getYear(), UtilMethod.getMonth(), UtilMethod.getDay());
        //设置监听
        changeBirthDialog.setBirthdayListener(new ChangeBirthDialog.OnBirthListener() {
            @Override
            public void onClick(String s, String s1, String s2) {
               
                   //s表示年,s1表示月,s2表示日
                 
             
            }
        });
        //显示
        changeBirthDialog.show();
    5.城市三级联动
      WheelDialog wheelDialog = new WheelDialog(this);
        wheelDialog.setOnAddressCListener(new WheelDialog.OnAddressCListener() {
            @Override
            public void onClick(String s, String s1, String s2) {
                //s表示省,s1表示市,s2表示区
            }
        });
        wheelDialog.show();
   6.城市选择控件
     <com.betterda.mylibrary.cityView.ChooseCityView
        android:id="@+id/ccv_city"
        android:layout_width="match_parent"
        android:layout_height="match_parent"></com.betterda.mylibrary.cityView.ChooseCityView>
         list = new ArrayList<>();
        list.add("广州");
        list.add("北京");
        list.add("上海");
        list.add("深圳");
        //根据定位信息来设置定位城市
        ccvCity.setLoacitonCity("北京");
        //设置热门城市数据
        ccvCity.setHotCityList(list);
        //设置热门城市的点击事件
        ccvCity.setOnCityItemClickListner(new ChooseCityView.onCityItemClickListner() {
            @Override
            public void click(int position) {
                Toast.makeText(getApplicationContext(), list.get(position), 0).show();
            }
        });

        //设置主要城市的点击事件
        ccvCity.setOnSortItemClickListner(new ChooseCityView.onSortItemClickListner() {
            @Override
            public void click(String city) {
                Toast.makeText(getApplicationContext(),city,0).show();
            }
        });
    7.菊花加载对话框  ShapeLoadingDialog
    调用UtilMethod的createDialog(Context mContext, String content)方法就可以创建一个对话框, content表示要显示的内容.然后再调用show显示,调用dismiss关闭
    8.60秒倒计时控件
        <com.betterda.xsnanosell.view.CountDown
            android:id="@+id/countdown_findpwd"
            android:layout_width="wrap_content"
            android:layout_height="match_parent">

        </com.betterda.xsnanosell.view.CountDown>
        调用showCountDown就可以开始倒计时
    9.  RatioLayout 可以动态的设置宽高的布局
    
                    <com.betterda.xsnanosell.view.RatioLayout
                        android:id="@+id/ratio_store"
                        android:layout_width="match_parent"
                        android:layout_height="match_paren"
                        app:ratio="2">

                        <RelativeLayout
                            android:id="@+id/relative_storedetail_picture"
                            android:layout_width="match_parent"
                            android:layout_height="match_parent">

                    
                       
                        </RelativeLayout>

                    </com.betterda.xsnanosell.view.RatioLayout>
        
       由ratio来决定宽高比,比如上面的ratio为2就表示宽和高的比例为2.这里的height只要设置一个固定值就好,其实是没有效果的,但不能为wrap_content.如上,宽为match_parent,ratio=2,那么高就是屏幕宽的一半.
    
    10.recycleview的item拖动和向左滑动删除
        同上拉加载一样用HeaderAndFooterRecyclerViewAdapter来包装适配器,然后调用setmItems(List list)方法设置数据关联.
        然后在ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(rvBalance);
        这样就将recycleview和itemTouchHelper关联起来了,就可以拖动或者滑动删除了
    
