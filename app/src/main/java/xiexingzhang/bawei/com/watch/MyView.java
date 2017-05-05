package xiexingzhang.bawei.com.watch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

/**
 * autohor:谢兴张(asus)
 * date:2017/5/5
 * effect:
 */

public class MyView extends View {
    private int width;
    private int heigth;
    private Paint mPaintLine;
    private Paint mPaintCircle;
    private Paint mPaintText;
    private Paint mPaintPoint;
    private Calendar mCalendar;
    public static final int NEED_REFRESH = 0x23;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case NEED_REFRESH:
                    mCalendar = Calendar.getInstance();
                    invalidate();//提醒UI线程重新绘制
                    handler.sendEmptyMessageDelayed(NEED_REFRESH, 1000);//相当于迭代，每隔1s就发送一个空消息，告诉UI线程进行重绘操作
                    break;
            }
        }
    };

    //    View有4个构造器
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCalendar = Calendar.getInstance();//初始化Calendar，得到当前时间

        mPaintLine = new Paint();
        mPaintLine.setColor(Color.BLACK);//设置颜色
        mPaintLine.setAntiAlias(true);//设置抗锯齿
        mPaintLine.setStrokeWidth(10);//设置线条宽度

        mPaintCircle = new Paint();
        mPaintCircle.setStrokeWidth(10);//设置线条宽度
        mPaintCircle.setColor(Color.BLACK);//设置颜色
        mPaintCircle.setAntiAlias(true);//设置抗锯齿
        mPaintCircle.setStyle(Paint.Style.STROKE);//设置为空心

        mPaintText = new Paint();
        mPaintText.setColor(Color.BLUE);//设置颜色
        mPaintText.setTextAlign(Paint.Align.CENTER);//设置对齐方式
        mPaintText.setAntiAlias(true);//设置抗锯齿
        mPaintText.setTextSize(30);//设置字体大小

        mPaintPoint = new Paint();
        mPaintPoint.setAntiAlias(true);//设置抗锯齿
        mPaintPoint.setStyle(Paint.Style.FILL);//设置为FILL
        mPaintPoint.setStrokeWidth(10);//设置宽度
        mPaintPoint.setColor(Color.BLACK);//设置颜色

        handler.sendEmptyMessage(NEED_REFRESH);//向Handler发送一个空消息
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);//得到系统默认的宽度
        heigth = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);//得到系统默认的高度
        setMeasuredDimension(width, heigth);//将默认宽高设置上去
    }

    //onDraw是有UI线程调用，不需要做其他处理
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawLine(0,600,600,600,mPaintLine);
//        canvas.drawCircle(300,300,100,mPaintCircle);
        canvas.drawCircle(width / 2, heigth / 2, 300, mPaintCircle);
        canvas.drawCircle(width / 2, heigth / 2, 10, mPaintPoint);
        //利用for循环绘制时钟的时刻信息
        for (int i = 1; i <= 12; i++) {
            // canvas.save();和canvas.restore();需要配合使用
            //先保存，然后进行旋转，然后画线，最后在转到初始位置，
            canvas.save();//保存当前画布状态
            canvas.rotate(360 / 12 * i, width / 2, heigth / 2);//第一参数代表旋转角度，第二和第三个参数代表旋转中心
            canvas.drawLine(width / 2, heigth / 2 - 300, width / 2, heigth / 2 - 280, mPaintLine);//前两个表示起始位置，第三和第四个表示末位置，最后一个表示画笔
            canvas.drawText("" + i, width / 2, heigth / 2 - 250, mPaintText);//第一个是文本内容，第二和第三个表示显示文本位置
            canvas.restore();//恢复到保存的画布状态
        }

        int minutes = mCalendar.get(Calendar.MINUTE);//得到当前时间的分钟数
        int hours = mCalendar.get(Calendar.HOUR);//得到当前时间的小时数
        int second = mCalendar.get(Calendar.SECOND);//得到当前时间的秒数

        canvas.save();
        Float minutesDegree = minutes / 60f * 360;//得到当前分钟数所占的角度
        canvas.rotate(minutesDegree, width / 2, heigth / 2);
        canvas.drawLine(width / 2, heigth / 2 - 200, width / 2, heigth / 2 + 20, mPaintLine);
        canvas.restore();

        canvas.save();
        Float hoursDegree = (hours * 60 + minutes) / 12f / 60 * 360;//得到当前小时数所占的角度
        canvas.rotate(hoursDegree, width / 2, heigth / 2);
        canvas.drawLine(width / 2, heigth / 2 - 100, width / 2, heigth / 2 + 30, mPaintLine);
        canvas.restore();

        canvas.save();
        Float secondDegree = second / 60f * 360;//得到当前小时数所占的角度
        canvas.rotate(secondDegree, width / 2, heigth / 2);
        canvas.drawLine(width / 2, heigth / 2 - 240, width / 2, heigth / 2 + 40, mPaintLine);
        canvas.restore();
    }
}
