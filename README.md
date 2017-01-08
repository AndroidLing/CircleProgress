#CircleProgress
一个仿Google Material Design 风格的ProgressBar, 一开始感觉这种效果挺难实现的, 但是仔细想想之后发现其实没有什么复杂的地方, 代码比较精简, 主要用ValueAnimator去实现, 对show 和 hide时候做了动画停止处理, 所以不会有内存溢出上的问题, 因为代码比较精简, 比较容易看懂, 所以很适合学习来用, 欢迎大家star
#演示
![](http://ojb3df4yc.bkt.clouddn.com/CircleProgress_1.gif)

#xml属性
	<declare-styleable name="CircleProgress">
        <attr name="circleWidth" format="dimension" />
        <attr name="circleColor" format="color" />
    </declare-styleable>

#对外提供的方法
	/**
     * @param width 圆环的宽度
     */
    void setCircleWidth(float width);
    float getCircleWidth();

    /**
     * @param colors 设置颜色变化数组
     */
    void setSchemeColors(int... colors);
    int[] getSchemeColors();
	
	/**
     * 显示(默认为显示)
     */
	void show();

	/**
     * 隐藏
     */
	void hide();
#布局文件使用
	<com.doctor.CircleProgress
        android:layout_width="40dp"
        android:layout_height="40dp"
        android:layout_marginLeft="26dp"
        app:circleColor="@android:color/black"
        app:circleWidth="3dp" />
#JCenter
	compile 'com.doctor:CircleProgress:1.0.3'