package com.example.andy.connectutil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;


/**
 * Created by 95815 on 2017/3/15.
 */

public class PaintHelper {


    int center_x ;
    int center_y ;
     private Region globalRegion ;
       int a;      //相对于屏幕的大小单位
    private Context context;

    public PaintHelper(Context context, int w, int h){
        int minwidth = w > h ? h : w;

        a = minwidth/200;
        this.context = context;
       this.center_x = w/2;
       this.center_y = h/2;
       this.globalRegion = new Region(-w,-h,w,h);

}

    public Path getNumPath(float sweepANgle, float centerAngle, int br, int sr, Region region) {
        Path path = new Path();
        RectF bigCircle = new RectF(center_x - br, center_y - br, center_x + br, center_y + br);
        RectF smallCircle = new RectF(center_x - sr, center_y - sr, center_x + sr, center_y + sr);
        path.addArc(bigCircle, centerAngle - sweepANgle / 2, sweepANgle);
        path.arcTo(smallCircle, centerAngle + sweepANgle / 2, -sweepANgle);
        path.close();
        if (globalRegion != null) {
            region.setPath(path, globalRegion);
        }
        return path;
    }

    public Path getOutArcPath(float sweepANgle, float startAngle, int width){
        Path path= new Path();
        RectF rectf = new RectF(center_x-width,center_y-width,center_x+width,center_y+width);
        path.addArc(rectf,startAngle,sweepANgle);
        return path;
    }
public Path getRbBtnP(int br, int outBtnr){
    Path path = new Path();
    RectF rectRbF = new RectF(center_x - br / 10 * 11, center_y - br / 10 * 11, center_x + br / 10 * 11, center_y + br / 10 * 11);
    path.addArc(rectRbF, 0, 90);
    path.lineTo(center_x, center_y + outBtnr);
    path.lineTo(center_x + outBtnr, center_y + outBtnr);
    path.lineTo(center_x + outBtnr, center_y);
    path.close();
    return path;
}
    public Path getLbBtnP(int br, int outBtnr){
        Path path = new Path();
        RectF rectRbF = new RectF(center_x - br / 10 * 11, center_y - br / 10 * 11, center_x + br / 10 * 11, center_y + br / 10 * 11);
        path.addArc(rectRbF, 90, 90);
        path.lineTo(center_x-outBtnr, center_y);
        path.lineTo(center_x-outBtnr, center_y + outBtnr);
        path.lineTo(center_x, center_y+outBtnr);
        path.close();
        return path;
    }
    public Path getLtBtnP(int br, int outBtnr){
        Path path = new Path();
        RectF rectRbF = new RectF(center_x - br / 10 * 11, center_y - br / 10 * 11, center_x + br / 10 * 11, center_y + br / 10 * 11);
        path.addArc(rectRbF, 180, 90);
        path.lineTo(center_x, center_y-outBtnr);
        path.lineTo(center_x-outBtnr, center_y - outBtnr);
        path.lineTo(center_x-outBtnr, center_y);
        path.close();
        return path;
    }
    public Path getRtBtnP(int br, int outBtnr){
        Path path = new Path();
        RectF rectRbF = new RectF(center_x - br / 10 * 11, center_y - br / 10 * 11, center_x + br / 10 * 11, center_y + br / 10 * 11);
        path.addArc(rectRbF, 270, 90);
        path.lineTo(center_x+outBtnr, center_y);
        path.lineTo(center_x+outBtnr, center_y - outBtnr);
        path.lineTo(center_x, center_y-outBtnr);
        path.close();
        return path;
    }
public Path getTShow(int br, int outBtnr){
    Path path = new Path();
    RectF rectF = new RectF(center_x-br/6,center_y-outBtnr,center_x+br/6,center_y-br/10*11);
      path.addRect(rectF, Path.Direction.CW);
    return path;

}
public void drawBitmapTL(Canvas canvas, Paint paint, boolean state){
    Rect dst = new Rect(center_x-a*95,center_y-a*95,center_x-a*85,center_y-a*85);
    Bitmap bitmap_white = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_tl_white);
    Bitmap bitmap_orange = BitmapFactory.decodeResource(context.getResources(),R.drawable.icon_tl_orange);
    Bitmap bitmap=state?bitmap_orange:bitmap_white;
    canvas.drawBitmap(bitmap,null,dst,paint);
    canvas.drawText("-",center_x-a*70-getTextWidth(paint,"-"),center_y-a*85-getTexHeight(paint),paint);

}

    public void drawBitmapTR(Canvas canvas, Paint paint, boolean state ){
        Rect dst = new Rect(center_x+a*80,center_y-a*100,center_x+a*100,center_y-a*80);
        Bitmap bitmap_white = BitmapFactory.decodeResource(context.getResources(),R.drawable.icon_tr_white);
        Bitmap bitmap_orange= BitmapFactory.decodeResource(context.getResources(),R.drawable.icon_tr_orange);
        Bitmap bitmap=state?bitmap_orange:bitmap_white;
        canvas.drawBitmap(bitmap,null,dst,paint);
        canvas.drawText("+",center_x+a*70-getTextWidth(paint,"+"),center_y-a*85-getTexHeight(paint),paint);
    }

    public void drawBitmapBL(Canvas canvas, Paint paint, boolean state ){
        Rect dst = new Rect(center_x-a*100,center_y+a*80,center_x-a*80,center_y+a*100);
        Bitmap bitmap_white = BitmapFactory.decodeResource(context.getResources(),R.drawable.icon_bl_white);
        Bitmap bitmap_orange = BitmapFactory.decodeResource(context.getResources(),R.drawable.icon_bl_orange);
        Bitmap bitmap=state?bitmap_orange:bitmap_white;
        canvas.drawBitmap(bitmap,null,dst,paint);

    }
    public void drawBitmapBR(Canvas canvas, Paint paint, boolean state ){
        Rect dst = new Rect(center_x+a*80,center_y+a*80,center_x+a*100,center_y+a*100);
        Bitmap bitmap_white = BitmapFactory.decodeResource(context.getResources(),R.drawable.icon_br_white);
        Bitmap bitmap_orange = BitmapFactory.decodeResource(context.getResources(),R.drawable.icon_br_orange);
        Bitmap bitmap=state?bitmap_orange:bitmap_white;
        canvas.drawBitmap(bitmap,null,dst,paint);

    }
    public void drawLedBitmapBR(Canvas canvas, Paint paint, boolean state ){
        Rect dst = new Rect(center_x-a*100,center_y+a*80,center_x-a*80,center_y+a*100);
        Bitmap bitmap_white = BitmapFactory.decodeResource(context.getResources(),R.drawable.led_colorpick_white);
        Bitmap bitmap_orange = BitmapFactory.decodeResource(context.getResources(),R.drawable.led_colorpick_orange);
        Bitmap bitmap=state?bitmap_orange:bitmap_white;
        canvas.drawBitmap(bitmap,null,dst,paint);

    }



    public void drawLedBitmapBL(Canvas canvas, Paint paint, boolean state ){
        Rect dst = new Rect(center_x+a*80,center_y+a*80,center_x+a*100,center_y+a*100);
        Bitmap bitmap_white = BitmapFactory.decodeResource(context.getResources(),R.drawable.led_nightlight_white);
        Bitmap bitmap_orange = BitmapFactory.decodeResource(context.getResources(),R.drawable.led_nightlight_orange);
        Bitmap bitmap=state?bitmap_orange:bitmap_white;
        canvas.drawBitmap(bitmap,null,dst,paint);

    }






public void getLightBitmapOne(Canvas canvas, Paint paint, int width, boolean state)
{
    //画档位上的图标
    canvas.save();
    Bitmap light_one_white = BitmapFactory.decodeResource(context.getResources(),R.drawable.ibtn_3_light_one_white_12);
    Bitmap light_one_orange = BitmapFactory.decodeResource(context.getResources(),R.drawable.ibtn_3_light_one_orange_12);
    Bitmap bitmap=state?light_one_orange:light_one_white;
    canvas.translate((float) (center_x+width/8*3* Math.cos(Math.PI/6)), (float) (center_y+width/8*3* Math.sin(Math.PI/6)));
    canvas.drawBitmap(bitmap,0-light_one_white.getWidth()/2,0-light_one_white.getHeight()/2,paint);
    canvas.restore();
}
    public void getLightBitmapTwo(Canvas canvas, Paint paint, int width, boolean state)
    {
        //画档位上的图标
        canvas.save();
        Bitmap light_two_white = BitmapFactory.decodeResource(context.getResources(),R.drawable.ibtn_3_light_two_white_12);
        Bitmap light_two_orange = BitmapFactory.decodeResource(context.getResources(),R.drawable.ibtn_3_light_two_orange_12);
        Bitmap bitmap=state?light_two_orange:light_two_white;
        canvas.translate((float) (center_x+width/8*3* Math.cos(Math.PI/6*5)), (float) (center_y+width/8*3* Math.sin(Math.PI/6*5)));
        canvas.drawBitmap(bitmap,0-light_two_white.getWidth()/2,0-light_two_white.getHeight()/2,paint);
        canvas.restore();
    }
    public void getLightBitmapThree(Canvas canvas, Paint paint, int width, boolean state)
    {
        //画档位上的图标
        canvas.save();
        Bitmap light_three_white = BitmapFactory.decodeResource(context.getResources(),R.drawable.ibtn_3_light_three_white_12);
        Bitmap light_three_orange = BitmapFactory.decodeResource(context.getResources(),R.drawable.ibtn_3_light_three_orange_12);
        Bitmap bitmap=state?light_three_orange:light_three_white;
        canvas.translate((float) (center_x+width/8*3* Math.cos(-Math.PI/2)), (float) (center_y+width/8*3* Math.sin(-Math.PI/2)));
        canvas.drawBitmap(bitmap,0-light_three_white.getWidth()/2,0-light_three_white.getHeight()/2,paint);
        canvas.restore();
    }



//获取到画文字应偏移 的y轴量
public float getTexHeight(Paint paint){
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();  //获取到文字的高度
        float height = -Math.abs(-fontMetrics.descent+(fontMetrics.bottom-fontMetrics.top)/2);
        return height;
}
    //获取到画文字应偏移的x轴量
public float getTextWidth(Paint paint, String str){
    float width =paint.measureText(str);
    return width;
}



}
