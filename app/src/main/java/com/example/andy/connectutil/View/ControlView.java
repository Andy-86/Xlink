package com.example.andy.connectutil.View;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.andy.connectutil.PaintHelper;
import com.example.andy.connectutil.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 95815 on 2017/3/14.
 */

public class ControlView extends View {

    boolean btnState = false;
    int lightNum;
    int outBtnr;
    private int a;
    private Context context;
    private PaintHelper paintHelper;

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
    private List<Path> mPathList;
    private List<Region> mRegionList;
    private List<Path> mOutArcList;

    private int geerNum = 4;   //档位的默认个数

    private int innerWidth;
    private float bottomAngle = 30f;
    private float firstAngle;
    private float currentSweepAngle;

    private boolean mPowerOpen = false;   //默认电源开关状态
    private Bitmap mCircleBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.poweropen_48white);
    private Bitmap mCircleBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.poweropen_48orange);

    private Paint mDeafultPaint;   //默认画笔
    private Paint mCirclePaint;    //中心圆画笔
    private Paint mLinePaint;      //描边画笔
    private Paint mOutArcPaint;   //外圆框画笔
    private Paint textPaint;

    int mStrokeColor = Color.parseColor("#2E4057");
    int mDefaultColor = Color.parseColor("#114B5F");
    int mTouchedColor = Color.parseColor("#2E4057");
    int mCircleColor = Color.parseColor("#1A936F");

    int mOutArcDefaultColor = Color.parseColor("#092A56");
    int mOutArcFocusColor = Color.parseColor("#FF9F1C");

    int center_x;  //视图的x原点
    int center_y;  //视图的y原点

    //点击区域的标记
    int CENTER = 10;
    int BOTTOM = 0;
    int ONE = 1;
    int TWO = 2;
    int THREE = 3;
    int FOUR = 4;
    int FIVE = 5;
    int SIX = 6;
    int SEVEN = 7;
    int EIGHT = 8;
    int NINE = 9;

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

    public void setLightNum(int lightNum) {
        this.lightNum = lightNum;
        invalidate();
    }

    public interface OnControlListener {
        void onClickCenter();

        void onClickBottom();

        void onClickOne();

        void onClickTwo();

        void onClickThree();

        void onClickFour();

        void onClickFive();

        void onClickSix();

        void onClickSeven();

        void onClickEight();

        void onClickNine();
    }

    public void setOncontrolListener(OnControlListener listenr) {
        this.mlistener = listenr;
    }

    public ControlView(Context context) {
        this(context, null);
    }

    public ControlView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        mRegionList = new ArrayList<>();
        mPathList = new ArrayList<>();
        mOutArcList = new ArrayList<>();

        outCir_p = new Path();

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
        //添加10个region区域用于判断点击区域
        for (int i = 0; i < 10; i++) {
            mRegionList.add(new Region());
        }
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


        textPaint.setColor(Color.parseColor("#ffffff"));
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

         paintHelper = new PaintHelper(context,w, h);
        center_x = w / 2;  //绘图中心
        center_y = h / 2;


        globalRegion = new Region(-w, -h, w, h);
        int minwidth = w > h ? h : w;
        minwidth *= 0.95;
        a= minwidth/200;
        //外围按键的画图范围
         outBtnr = minwidth / 2;  //外围四按键
        //内围的圆的宽度，中心圆的宽度是内围圆的一半
        innerWidth = minwidth;
        innerWidth *= 0.8;
        int br = innerWidth / 2;   //中心圆的半径
        int sr = innerWidth / 4;   //内圆的半径


        mOutArcPaint.setStrokeWidth(br / 15);

        currentSweepAngle = (360 - bottomAngle) / (geerNum - 1);  //获得每个扇形扫过的角度
        firstAngle = -270 + bottomAngle / 2 + currentSweepAngle / 2;      //第一档位开始的中心角度
        float firstOutArcAngle = -270 + bottomAngle / 2;  //第一档位外围框开始的角度

        outCir_p.addCircle(center_x, center_y, br / 11 * 12, Path.Direction.CW);
        //画圆
        center_p.addCircle(center_x, center_y, sr, Path.Direction.CW);
        center_re.setPath(center_p, globalRegion);

        //根据传进来的档位个数画档位扇形
        for (int i = 0; i < geerNum; i++) {
            if (i == 0) {
                mPathList.add(paintHelper.getNumPath(bottomAngle, 90, br, sr, mRegionList.get(i)));
                mOutArcList.add(paintHelper.getOutArcPath(bottomAngle, 90 - bottomAngle / 2, br));

            } else {
                Path path = paintHelper.getNumPath(currentSweepAngle, firstAngle + currentSweepAngle * (i - 1), br, sr, mRegionList.get(i));
                mPathList.add(path);
                Path path1 = paintHelper.getOutArcPath(currentSweepAngle, firstOutArcAngle + currentSweepAngle * (i - 1), br);
                mOutArcList.add(path1);
            }

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            int round =30;
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
            rb_Btn_p = paintHelper.getRbBtnP(br, a*100);  //获取到右下角的按键
            rb_Btn_re.setPath(rb_Btn_p, globalRegion);

            lb_Btn_p = paintHelper.getLbBtnP(br, a*100);
            lb_Btn_re.setPath(lb_Btn_p, globalRegion);

            lt_Btn_p = paintHelper.getLtBtnP(br, a*100);
            lt_Btn_re.setPath(lt_Btn_p, globalRegion);

            rt_Btn_p = paintHelper.getRtBtnP(br, a*100);
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


//画档位扇形
        if (mPathList.size() > 0) {

            for (Path path : mPathList) {
                canvas.drawPath(path, mDeafultPaint);
                canvas.drawPath(path, mLinePaint);
            }
        }
        //画档位数字
        textPaint.setTextSize(innerWidth / 9);
        for (int i = 0; i < geerNum; i++) {


            String a = String.valueOf(i);
            float strwidth = textPaint.measureText(a);  // 获取到文字的宽度
            Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();  //获取到文字的高度
            float strheight = Math.abs(-fontMetrics.descent+(fontMetrics.bottom-fontMetrics.top)/2);

            float xx = (float) (innerWidth * 0.75 * 0.5 * Math.cos((firstAngle + currentSweepAngle * (i - 1)) / 180 * Math.PI));
            float yy = (float) (innerWidth * 0.75 * 0.5 * Math.sin((firstAngle + currentSweepAngle * (i - 1)) / 180 * Math.PI));
            if (mArcState >= 0 && i == mArcState) {

                textPaint.setColor(mOutArcFocusColor);
                if ( mArcState == 0 ) {
                    canvas.drawText(a, -strwidth / 2, strheight , textPaint);
                    canvas.drawText(a, center_x - strwidth / 2, (float) (center_y + innerWidth * 0.75 * 0.5) + strheight , textPaint);
                } else {
                    canvas.drawText(a, center_x + xx - strwidth / 2, center_y + yy + strheight, textPaint);
                }
                textPaint.setColor(Color.parseColor("#ffffff"));

            } else if (i == 0) {
                if(bottomAngle!=0){canvas.drawText(a, center_x - strwidth / 2, (float) (center_y + innerWidth * 0.75 * 0.5) + strheight, textPaint);}
            } else {
                canvas.drawText(a, center_x + xx - strwidth / 2, center_y + yy + strheight, textPaint);
            }

        }

//画圆外围的线框
        if (mOutArcList.size() > 0) {
            for (Path path : mOutArcList) {
                canvas.drawPath(path, mOutArcPaint);
            }

        }
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


        paintHelper.drawBitmapTL(canvas,textPaint,btnState);
        paintHelper.drawBitmapTR(canvas,textPaint,btnState);
        paintHelper.drawBitmapBR(canvas,textPaint,btnState);
        paintHelper.drawBitmapBL(canvas,textPaint,btnState);


        mOutArcPaint.setColor(mOutArcFocusColor);
        if (mArcState >= 0) {
            canvas.drawPath(mOutArcList.get(mArcState), mOutArcPaint);
        }

        mDeafultPaint.setColor(mTouchedColor);
        mCirclePaint.setColor(mTouchedColor);

        if (current_flag >= 0 && current_flag < mPathList.size()) {
            canvas.drawPath(mPathList.get(current_flag), mDeafultPaint);
        } else if (current_flag == RB_BTN) {
            canvas.drawPath(rb_Btn_p, mDeafultPaint);
        } else if (current_flag == RT_BTN) {
            canvas.drawPath(rt_Btn_p, mDeafultPaint);
            btnState = true;
            paintHelper.drawBitmapTR(canvas,textPaint,btnState);
            btnState = false;
            Toast.makeText(getContext(),"点击了右上角",Toast.LENGTH_SHORT).show();
        } else if (current_flag == LB_BTN) {
            canvas.drawPath(lb_Btn_p, mDeafultPaint);
        } else if (current_flag == LT_BTN) {
            canvas.drawPath(lt_Btn_p, mDeafultPaint);
            btnState = true;
            paintHelper.drawBitmapTL(canvas,textPaint,btnState);
            btnState = false;
        } else if (current_flag == CENTER) {
            canvas.drawPath(center_p, mCirclePaint);
            Toast.makeText(getContext(),"点击了中间",Toast.LENGTH_SHORT).show();
        }
        //画顶部显示
        canvas.drawPath(top_Show_p, mDeafultPaint);

        if(lightNum>=0&&lightNum<=100) {
            String str = String.valueOf(lightNum);
            canvas.drawText(str, center_x - paintHelper.getTextWidth(textPaint, str) / 2, center_y-outBtnr-paintHelper.getTexHeight(textPaint)*3, textPaint);
        }



        mDeafultPaint.setColor(mDefaultColor);
        mCirclePaint.setColor(mCircleColor);
        mOutArcPaint.setColor(mOutArcDefaultColor);
        Bitmap b = mPowerOpen ? mCircleBitmap2 : mCircleBitmap1; //开与关状态下的bitmap
        canvas.drawBitmap(b, center_x - b.getWidth() / 2
                , center_y - b.getHeight() / 2, mDeafultPaint);


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
                if (current_flag == touch_flag && current_flag != -1 && mlistener != null) {
                    if (current_flag == CENTER) {
                        mlistener.onClickCenter();
                    } else if (current_flag == BOTTOM) {
                        mlistener.onClickBottom();
                    } else if (current_flag == ONE) {
                        mlistener.onClickOne();
                    } else if (current_flag == TWO) {
                        mlistener.onClickTwo();
                    } else if (current_flag == THREE) {
                        mlistener.onClickThree();
                    } else if (current_flag == FOUR) {
                        mlistener.onClickFour();
                    } else if (current_flag == FIVE) {
                        mlistener.onClickFive();
                    } else if (current_flag == SIX) {
                        mlistener.onClickSix();
                    } else if (current_flag == SEVEN) {
                        mlistener.onClickSeven();
                    } else if (current_flag == EIGHT) {
                        mlistener.onClickEight();
                    } else if (current_flag == NINE) {
                        mlistener.onClickNine();
                    }
                }
                touch_flag = current_flag = -1;
                break;
            case MotionEvent.ACTION_CANCEL:
                touch_flag = current_flag = -1;
                break;
        }
        invalidate();
        return true;
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
        } else {
            for (int i = 0; i < mRegionList.size(); i++) {
                if (mRegionList.get(i).contains(x, y)) return i;
            }
        }
        return -1;
    }

    public void setPowerState(boolean b) {

        this.mPowerOpen = b;
    }

    //默认是30度
    public void setBottomAngle(float bottomAngle) {

        if (bottomAngle >= 0) {
            this.bottomAngle = bottomAngle;
        } else {
            this.bottomAngle = 30f;
        }
    }

    public void setGeerNum(int Num) {
        if (Num <= 10) {
            this.geerNum = Num;
        } else {
            this.geerNum = 4;
        }
        invalidate();
    }

}
