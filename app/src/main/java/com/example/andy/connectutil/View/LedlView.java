package com.example.andy.connectutil.View;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.andy.connectutil.PaintHelper;
import com.example.andy.connectutil.R;


/**
 * Created by 95815 on 2017/3/14.
 */

public class LedlView extends View {


    boolean btnState = false;


    private int lightNum = 50;

    public void setLightNum(int lightNum) {
        this.lightNum = lightNum;
        invalidate();
    }

    private int outBtnr;
    private int a;
    private Context context;
    private PaintHelper paintHelper;

    Path one_p;
    Path two_p;
    Path three_p;
    Region one_r;
    Region two_r;
    Region three_r;
    private int colorLightOne = Color.parseColor("#F2E6B3");
    private int colorLightTwo = Color.parseColor("#F2DE87");
    private int colorLightThree = Color.parseColor("#F2D348");


    private Region globalRegion;

    private Path top_Show_p;
    //右下角按钮
    private Region rb_Btn_re;
    private Path rb_Btn_p;
    //右上角按钮
    private Region rt_Btn_re;
    private Path rt_Btn_p;
    //左上角按钮
    private Region lt_Btn_re;
    private Path lt_Btn_p;
    //左下角按钮
    private Region lb_Btn_re;
    private Path lb_Btn_p;

    private Path outCir_p;

    private Region center_re;
    private Path center_p;

    private int geerNum = 4;   //档位的默认个数

    private int innerWidth;
    private float bottomAngle = 30f;
    private float firstAngle;
    private float currentSweepAngle;

    private boolean mPowerOpen = false;   //默认电源开关状态

    private Paint mDeafultPaint;   //默认画笔
    private Paint mCirclePaint;    //中心圆画笔
    private Paint mLinePaint;      //描边画笔
    private Paint mOutArcPaint;   //外圆框画笔
    private Paint textPaint;

    int mStrokeColor = Color.parseColor("#2E4057");
    int mDefaultColor = Color.parseColor("#114B5F");
    int mTouchedColor = Color.parseColor("#2E4057");
    int mCircleColor = getResources().getColor(R.color.colorInnerGray);

    int mOutArcDefaultColor = Color.parseColor("#092A56");
    int mOutArcFocusColor = Color.parseColor("#FF9F1C");

    int center_x;  //视图的x原点
    int center_y;  //视图的y原点

    //点击区域的标记
    int CENTER = 10;
    int ONE = 1;
    int TWO = 2;
    int THREE = 3;


    int RB_BTN = 31;
    int RT_BTN = 32;
    int LB_BTN = 33;
    int LT_BTN = 34;


    int touch_flag = -1;
    int current_flag = -1;

    private int mArcState = -1;  //标记当前的档位

    OnControlListener mlistener = null;

    public void setArcState(int mArcState) {
        this.mArcState = mArcState;
        invalidate();
    }

    public interface OnControlListener {
        void onClickCenter();

        void onClickOne();

        void onClickTwo();

        void onClickThree();

        void onClickBtnTL();

        void onClickBtnTR();

        void onClickBtnBL();

        void onClickBtnBR();

    }

    public void setOncontrolListener(OnControlListener listenr) {
        this.mlistener = listenr;
    }

    public LedlView(Context context) {
        this(context, null);
    }

    public LedlView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        outCir_p = new Path();

        one_p = new Path();
        two_p = new Path();
        three_p = new Path();
        one_r = new Region();
        two_r = new Region();
        three_r = new Region();

        top_Show_p = new Path();

        rb_Btn_p = new Path();
        rb_Btn_re = new Region();

        rt_Btn_p = new Path();
        rt_Btn_re = new Region();

        lb_Btn_p = new Path();
        lb_Btn_re = new Region();

        lt_Btn_p = new Path();
        lt_Btn_re = new Region();

        //中心圆的region区域
        center_p = new Path();
        center_re = new Region();

        //画笔初始化
        mOutArcPaint = new Paint();
        mOutArcPaint.setColor(mOutArcDefaultColor);
        mOutArcPaint.setStyle(Paint.Style.STROKE);
        mOutArcPaint.setAntiAlias(true);

        mLinePaint = new Paint();
        mLinePaint.setColor(mStrokeColor);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(5f);
        mLinePaint.setAntiAlias(true);

        mCirclePaint = new Paint();
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setAntiAlias(true);

        mDeafultPaint = new Paint();
        mDeafultPaint.setColor(mDefaultColor);
        mDeafultPaint.setStyle(Paint.Style.FILL);
        mDeafultPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(60f);
        textPaint.setColor(colorLightOne);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        paintHelper = new PaintHelper(context, w, h);
        center_x = w / 2;  //绘图中心
        center_y = h / 2;


        globalRegion = new Region(-w, -h, w, h);
       int  minwidth = w > h ? h : w;
        minwidth *= 0.95;
        a = minwidth / 200;
        //外围按键的画图范围
        outBtnr = minwidth / 2;  //外围四按键
        //内围的圆的宽度，中心圆的宽度是内围圆的一半
        innerWidth = minwidth;
        innerWidth *= 0.8;
        int br = innerWidth / 2;   //中心圆的半径
        int sr = innerWidth / 4;   //内圆的半径


        mOutArcPaint.setStrokeWidth(br / 18);

        currentSweepAngle = (360 - bottomAngle) / (geerNum - 1);  //获得每个扇形扫过的角度
        firstAngle = -270 + bottomAngle / 2 + currentSweepAngle / 2;      //第一档位开始的中心角度
        float firstOutArcAngle = -270 + bottomAngle / 2;  //第一档位外围框开始的角度

        outCir_p.addCircle(center_x, center_y, br, Path.Direction.CW);
        //画圆
        center_p.addCircle(center_x, center_y, sr, Path.Direction.CW);
        center_re.setPath(center_p, globalRegion);

        //画挡位扇形
        one_p = paintHelper.getNumPath(120f, 30f, br, sr, one_r);
        two_p = paintHelper.getNumPath(120f, 150f, br, sr, two_r);
        three_p = paintHelper.getNumPath(120f, 270f, br, sr, three_r);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            int round = 30;
            //安卓4.4以上的画path方法
            RectF rectRbF = new RectF(center_x + 15, center_y + 15, center_x + outBtnr, center_y + outBtnr);
            rb_Btn_p.addRoundRect(rectRbF, round, round, Path.Direction.CW);

            RectF rectLbF = new RectF(center_x - outBtnr, center_y + 15, center_x - 15, center_y + outBtnr);
            lb_Btn_p.addRoundRect(rectLbF, round, round, Path.Direction.CW);

            RectF rectLtF = new RectF(center_x - outBtnr, center_y - outBtnr, center_x - br / 5, center_y);
            lt_Btn_p.addRoundRect(rectLtF, round, round, Path.Direction.CW);

            RectF rectRtF = new RectF(center_x + br / 5, center_y - outBtnr, center_x + outBtnr, center_y);
            rt_Btn_p.addRoundRect(rectRtF, round, round, Path.Direction.CW);

            RectF rectTsF = new RectF(center_x - br / 6, center_y - outBtnr, center_x + br / 6, center_y);
            top_Show_p.addRoundRect(rectTsF, round, round, Path.Direction.CW);
            top_Show_p.op(outCir_p, Path.Op.DIFFERENCE);

            rb_Btn_p.op(outCir_p, Path.Op.DIFFERENCE);
            rb_Btn_re.setPath(rb_Btn_p, globalRegion);

            lb_Btn_p.op(outCir_p, Path.Op.DIFFERENCE);
            lb_Btn_re.setPath(lb_Btn_p, globalRegion);

            lt_Btn_p.op(outCir_p, Path.Op.DIFFERENCE);
            lt_Btn_re.setPath(lt_Btn_p, globalRegion);

            rt_Btn_p.op(outCir_p, Path.Op.DIFFERENCE);
            rt_Btn_re.setPath(rt_Btn_p, globalRegion);


        } else {
            //安卓4.4以下的画path解决方案
            rb_Btn_p = paintHelper.getRbBtnP(br, outBtnr);  //获取到右下角的按键
            rb_Btn_re.setPath(rb_Btn_p, globalRegion);

            lb_Btn_p = paintHelper.getLbBtnP(br, outBtnr);
            lb_Btn_re.setPath(lb_Btn_p, globalRegion);

            lt_Btn_p = paintHelper.getLtBtnP(br, outBtnr);
            lt_Btn_re.setPath(lt_Btn_p, globalRegion);

            rt_Btn_p = paintHelper.getRtBtnP(br, outBtnr);
            rt_Btn_re.setPath(rt_Btn_p, globalRegion);

        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#2B3036"));  // 画布颜色
//画圆

        canvas.drawPath(center_p, mCirclePaint);
        canvas.drawPath(center_p, mLinePaint);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.led_center);
        Rect rect = new Rect(center_x-innerWidth/8,center_y-innerWidth/8,center_x+innerWidth/8,center_y+innerWidth/8);
        canvas.drawBitmap(bitmap,null,rect,textPaint);

//画档位扇形
        canvas.drawPath(one_p, mDeafultPaint);
        canvas.drawPath(one_p, mLinePaint);
        canvas.drawPath(two_p, mDeafultPaint);
        canvas.drawPath(two_p, mLinePaint);
        canvas.drawPath(three_p, mDeafultPaint);
        canvas.drawPath(three_p, mLinePaint);
//画扇形上的图标
        paintHelper.getLightBitmapOne(canvas, textPaint, innerWidth, false);
        paintHelper.getLightBitmapTwo(canvas, textPaint, innerWidth, false);
        paintHelper.getLightBitmapThree(canvas, textPaint, innerWidth, false);


//画外围四个按键
        canvas.drawPath(rb_Btn_p, mDeafultPaint);
        canvas.drawPath(rb_Btn_p, mLinePaint);

        canvas.drawPath(lb_Btn_p, mDeafultPaint);
        canvas.drawPath(lb_Btn_p, mLinePaint);

        canvas.drawPath(lt_Btn_p, mDeafultPaint);
        canvas.drawPath(lt_Btn_p, mLinePaint);

        canvas.drawPath(rt_Btn_p, mDeafultPaint);
        canvas.drawPath(rt_Btn_p, mLinePaint);


        //画外围按键上的图标
        paintHelper.drawBitmapTL(canvas, textPaint, btnState);
        paintHelper.drawBitmapTR(canvas, textPaint, btnState);
        paintHelper.drawBitmapBR(canvas, textPaint, btnState);
        paintHelper.drawBitmapBL(canvas, textPaint, btnState);


        mDeafultPaint.setColor(mTouchedColor);
        mCirclePaint.setColor(mTouchedColor);

        //点击变色效果
        if (current_flag == RB_BTN) {
            //点击右下角按键变色
            canvas.drawPath(rb_Btn_p, mDeafultPaint);
            paintHelper.drawLedBitmapBR(canvas, textPaint, true);
        } else if (current_flag == RT_BTN) {
            canvas.drawPath(rt_Btn_p, mDeafultPaint);
            paintHelper.drawBitmapTR(canvas, textPaint, true);
        } else if (current_flag == LT_BTN) {
            canvas.drawPath(lt_Btn_p, mDeafultPaint);
            paintHelper.drawBitmapTL(canvas, textPaint, true);
        } else if (current_flag == LB_BTN) {
            canvas.drawPath(lb_Btn_p, mDeafultPaint);
            paintHelper.drawLedBitmapBL(canvas, textPaint, true);
        } else if (current_flag == CENTER) {
            canvas.drawPath(center_p, mCirclePaint);
        } else if (current_flag == ONE) {
            canvas.drawPath(one_p, mDeafultPaint);
            paintHelper.getLightBitmapOne(canvas, textPaint, innerWidth, true);
        } else if (current_flag == TWO) {
            canvas.drawPath(two_p, mDeafultPaint);
            paintHelper.getLightBitmapTwo(canvas, textPaint, innerWidth, true);
        } else if (current_flag == THREE) {
            canvas.drawPath(three_p, mDeafultPaint);
            paintHelper.getLightBitmapThree(canvas, textPaint, innerWidth, true);
        }

        mDeafultPaint.setColor(mDefaultColor);
        mCirclePaint.setColor(mCircleColor);

        //画顶部的数字显示
        canvas.drawPath(top_Show_p, mDeafultPaint);

      if(lightNum>=0&&lightNum<=100) {
          String str = String.valueOf(lightNum);
          canvas.drawText(str, center_x - paintHelper.getTextWidth(textPaint, str) / 2, center_y-outBtnr-paintHelper.getTexHeight(textPaint)*3, textPaint);
      }
        //画光圈
        canvas.drawPath(outCir_p, mOutArcPaint);



    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //   return super.onTouchEvent(event);
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                touch_flag = getTouchPath(x, y);
                current_flag = touch_flag;
                break;
            case MotionEvent.ACTION_UP:
                returnListener(current_flag);
                touch_flag = current_flag = -1;
                break;
            case MotionEvent.ACTION_CANCEL:
                touch_flag = current_flag = -1;
                break;
        }
        invalidate();
        return true;
    }

    public void setLightNone() {
        mOutArcPaint.setColor(mOutArcDefaultColor);
        invalidate();

    }

    public void setLightOne() {
        mOutArcPaint.setColor(colorLightOne);
        invalidate();
    }

    public void setLightTwo() {
        mOutArcPaint.setColor(colorLightTwo);
        invalidate();
    }

    public void setLightThree() {
        mOutArcPaint.setColor(colorLightThree);
        invalidate();

    }

    //返回当前触摸的区域标志
    private int getTouchPath(int x, int y) {
        if (center_re.contains(x, y)) {
            return CENTER;
        } else if (rb_Btn_re.contains(x, y)) {
            return RB_BTN;
        } else if (rt_Btn_re.contains(x, y)) {
            return RT_BTN;
        } else if (lt_Btn_re.contains(x, y)) {
            return LT_BTN;
        } else if (lb_Btn_re.contains(x, y)) {
            return LB_BTN;
        } else if (one_r.contains(x, y)) {
            return ONE;
        } else if (two_r.contains(x, y)) {
            return TWO;
        } else if (three_r.contains(x, y)) {
            return THREE;
        }
        return -1;
    }

    public void setPowerState(boolean b) {
        this.mPowerOpen = b;
    }

    private void returnListener(int flag) {
        if (flag == touch_flag && flag != -1 && mlistener != null) {
            if (flag == CENTER) {
                mlistener.onClickCenter();
            } else if (flag == ONE) {
                mlistener.onClickOne();
            } else if (flag == TWO) {
                mlistener.onClickTwo();
            } else if (flag == THREE) {
                mlistener.onClickThree();
            } else if (flag == LB_BTN) {
                mlistener.onClickBtnBL();
            } else if (flag == LT_BTN) {
                mlistener.onClickBtnTL();
            } else if (flag == RB_BTN) {
                mlistener.onClickBtnBR();
            } else if (flag == RT_BTN) {
                mlistener.onClickBtnTR();
            }
        }
    }


}
