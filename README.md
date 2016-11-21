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
