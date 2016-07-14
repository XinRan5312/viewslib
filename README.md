# viewslib
UI View  更具自己工作中遇到的问题慢慢积攒实现的如下的一个综合的工具类
支持SrollView 嵌套多了listview下拉刷新  上拉加载更多。
自定义那个HotTab和底部Tad动态加载
里面还有一个FragmentStatePagerAdapter的支持
支持RecylerView下拉刷洗和上拉加载更多。

16/6/07：
    添加了利用RecylerView实现弹幕的功能，在项目的tanmu的包中
    recylerView.scrollToPosition(position)滚动到指定item

16/6/21：
    新添加：悬浮导航栏StickyNavLayout的实现--基本滑动的实现 来自：http://www.jianshu.com/p/e5ef7e36cbd3
    在项目的xuanfu包中

16/6/23：
    模仿IOS ActionSheet，来自：http://www.jianshu.com/p/1b548491bd5a
    在项目的actionsheet包中

16/6/24:
 * 一个狂赞时的心形动画,集成属性动画和贝赛尔曲线：在项目的heart包中
 * 来自：http://www.jianshu.com/p/9423ca99c303

16/7/4:
    1：在TestSeekBarActivity中添加对SeekBar的使用
    2：使用父布局是background="@android:color/transparent"是透明，其子布局可以随意自定义自己的背景颜色
    3：在MoveSonLayoutActivity中实现布局中加某个自布局可见时另外一个自布局网上或者往下移动的（或者消失时的）
       动画效果

16/7/5：
    在TestScrollerActivity中练习Scrooler

16/7/6：
        在TestLightTextViewActivity加入可以发光的字

16/7/14：
        在draglistview包中分别用两种方式实现了可以随意拖拽的ListView
    其中：
       1：DragListViewActivity实现了点击item的某个位置可以拖拽，但是长按item不可拖拽
       2：LongPressDragListViewActivity实现了长按item可以拖拽，但是Adapter要是ArrayAdapter的子类
       两者互不真正实现了ListView 的可拖拽操作


