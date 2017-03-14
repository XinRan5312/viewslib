全网最强Android开发/源码资源汇总，倾力打造，绝对精品！！

2016-07-15 APP架构师
最全的Android开源音乐播放器源码汇总
http://neast.cn/forum.php?mod=viewthread&tid=9586&fromuid=5

Android 直播项目解决方案汇总
http://neast.cn/forum.php?mod=viewthread&tid=61625&fromuid=5

全网最强Android开源高仿系列大收藏！！！！！！
http://neast.cn/forum.php?mod=viewthread&tid=61522&fromuid=5

2016最新独家老罗Android视频教程第二季重磅来袭！！！
http://neast.cn/forum.php?mod=viewthread&tid=61555&fromuid=31739

2016 android 源码 5000套源码打包下载
http://neast.cn/thread-61187-1-1.html

2015年最新810多套源码2.46GB免费一次性打包下载
http://neast.cn/forum.php?mod=viewthread&tid=26092&fromuid=5

2015Android精品源码大合集。百度网盘一次性免费打包下载！
http://neast.cn/forum.php?mod=viewthread&tid=58016&fromuid=5

2015年最棒的高仿系列源码打包下载（非常经典）
http://neast.cn/thread-59560-1-1.html?_dsign=b24a1a04

【持续更新中】Android福利贴（一）：资料源码
http://neast.cn/forum.php?mod=viewthread&tid=61334&fromuid=5

2015高质量几百个分类Android源码打包一次性下载！
http://neast.cn/forum.php?mod=viewthread&tid=58175&fromuid=5

Android电商项目源码合集打包下载！
http://neast.cn/forum.php?mod=viewthread&tid=61360&fromuid=5

Android 应用源码大收藏
http://neast.cn/forum.php?mod=viewthread&tid=9483&fromuid=5

全网最全的Android游戏源码汇总
http://neast.cn/forum.php?mod=viewthread&tid=5080&fromuid=5

AndroidUI设计之高仿系列
http://neast.cn/forum.php?mod=viewthread&tid=5293&fromuid=5

全网最全的Android源码汇总—几百个Android源码（不断更新中）
http://neast.cn/forum.php?mod=viewthread&tid=475&fromuid=5

ContentProvider专题
http://neast.cn/forum.php?mod=viewthread&tid=419&fromuid=5

Android多线程开发专题
http://neast.cn/forum.php?mod=viewthread&tid=418&fromuid=5

GitHub开源项目总结
http://neast.cn/forum.php?mod=viewthread&tid=5377&fromuid=5

汇集的几十个Android源码
http://neast.cn/forum.php?mod=viewthread&tid=5627&fromuid=5

Android开源项目大全之工具库
http://www.neast.cn/forum.php?mod=viewthread&tid=5487&fromuid=5

Android开源项目之优秀项目
http://www.neast.cn/forum.php?mod=viewthread&tid=5488&fromuid=5

Android开源项目之个性化控件(View)
http://www.neast.cn/forum.php?mo ... 89&fromuid=5Android

非常好用的组件或者框架。
http://www.neast.cn/forum.php?mod=viewthread&tid=5491&fromuid=5

Android开源项目开发及测试工具篇
http://www.neast.cn/forum.php?mod=viewthread&tid=5631&fromuid=5

Android源码之10个开源项目
http://www.neast.cn/forum.php?mod=viewthread&tid=5632&fromuid=5

Android视频教程大合集
http://www.neast.cn/forum.php?mod=viewthread&tid=611&fromuid=5

Android常见的开源项目汇集
http://www.neast.cn/forum.php?mod=viewthread&tid=5439&fromuid=5

分享10个Android应用源码
http://www.neast.cn/forum.php?mod=viewthread&tid=5330&fromuid=5

推荐10个google上的Android开源项目
http://www.neast.cn/forum.php?mod=viewthread&tid=5329&fromuid=5

分享多个google的Android开源项目非常有价值
http://www.neast.cn/forum.php?mod=viewthread&tid=5328&fromuid=5

收集的一些Android开源库——全都是经验之谈
http://neast.cn/forum.php?mod=viewthread&tid=5654&fromuid=5

AndroidUI设计之高仿系列
http://neast.cn/forum.php?mod=viewthread&tid=5293&fromuid=5


# #################viewslib################################
###########################################################

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

16/7/21：
     TestAlDLActivity这个activity所在的包及其相关类实现了AndroidStudio中AIDL的使用，以及Socket连接实现心跳

17/03/14 ：
   RxJava和Rxbus的简单使用
   OK3和Fastjson的结合使用：http://blog.csdn.net/watertekhqx/article/details/62042768