package com.warick.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WarickSurfaceView extends SurfaceView {

    /* renamed from: a */
    private static final String TAG = "WarickSurfaceView";

    /* renamed from: b */
    private Context mContext;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public SurfaceHolder mSurfaceHolder;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public Canvas mCanvas;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public Handler ecgHandle;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public Paint mPaint;
    /* access modifiers changed from: private */

    /* renamed from: g */
    public List<DrawListener> mDrawListenerlist;
    /* access modifiers changed from: private */

    /* renamed from: h */
    public boolean isRuning;

    /* renamed from: i */
    private Timer ecg20Timer;
    /* access modifiers changed from: private */

    /* renamed from: j */
    public int mWidth;
    /* access modifiers changed from: private */

    /* renamed from: k */
    public int mHeight;

    /* renamed from: l */
    private SurfaceHolder.Callback ecgCallback;

    /* renamed from: com.warick.drawable.WarickSurfaceView$a */
    public interface DrawListener {
        /* renamed from: a */
        void onMyDraw(Canvas canvas, Paint paint);
    }

    /* renamed from: com.warick.drawable.WarickSurfaceView$b */
    class ECGRunnable implements Runnable {

        /* renamed from: b */
        private Looper mLooper;

        public void run() {
            Looper.prepare();
            this.mLooper = Looper.myLooper();
            ecgHandle = new Handler(this.mLooper) {
                /* JADX WARNING: Code restructure failed: missing block: B:13:0x004f, code lost:
                    return;
                 */
                public void handleMessage(Message message) {
                    SurfaceHolder d;
                    Canvas a;
                    if (isRuning) {
                        synchronized (ecgHandle) {
                            try {
                                mCanvas = mSurfaceHolder.lockCanvas();
                                if (mCanvas != null) {
                                    Log.d("WarickSurfaceView", "lockCanvas not null!");
                                    WarickSurfaceView.this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                                    WarickSurfaceView.this.mCanvas.drawPaint(WarickSurfaceView.this.mPaint);
                                    WarickSurfaceView.this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
                                    for (int i = 0; i < mDrawListenerlist.size(); i++) {
                                        ((DrawListener) WarickSurfaceView.this.mDrawListenerlist.get(i)).onMyDraw(mCanvas, mPaint);
                                    }
                                    if (WarickSurfaceView.this.mCanvas != null) {
                                        d = WarickSurfaceView.this.mSurfaceHolder;
                                        a = WarickSurfaceView.this.mCanvas;
                                        d.unlockCanvasAndPost(a);
                                    }
                                } else if (mCanvas != null) {
                                    mSurfaceHolder.unlockCanvasAndPost(WarickSurfaceView.this.mCanvas);
                                }
                            } catch (Exception e) {
                                try {
                                    e.printStackTrace();
                                    if (mCanvas != null) {
                                        d = WarickSurfaceView.this.mSurfaceHolder;
                                        a = WarickSurfaceView.this.mCanvas;
                                    }
                                    super.handleMessage(message);
                                } catch (Throwable th) {
                                    if (WarickSurfaceView.this.mCanvas != null) {
                                        WarickSurfaceView.this.mSurfaceHolder.unlockCanvasAndPost(WarickSurfaceView.this.mCanvas);
                                    }
                                    throw th;
                                }
                            }
                        }
                    }
                }
            };
            Looper.loop();
        }
    }

    public WarickSurfaceView(Context context) {
        this(context, (AttributeSet) null);
    }

    public WarickSurfaceView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WarickSurfaceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCanvas = null;
        this.mPaint = null;
        this.isRuning = false;
        this.ecg20Timer = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.ecgCallback = new SurfaceHolder.Callback() {

            /* renamed from: a */
            Thread mThread = null;

            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
                int unused = WarickSurfaceView.this.mWidth = i2;
                int unused2 = WarickSurfaceView.this.mHeight = i3;
            }

            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                boolean unused = WarickSurfaceView.this.isRuning = true;
                this.mThread = new Thread(new ECGRunnable());
                this.mThread.start();
                Canvas unused2 = WarickSurfaceView.this.mCanvas = surfaceHolder.lockCanvas();
                int unused3 = WarickSurfaceView.this.mWidth = WarickSurfaceView.this.mCanvas.getWidth();
                int unused4 = WarickSurfaceView.this.mHeight = WarickSurfaceView.this.mCanvas.getHeight();
                surfaceHolder.unlockCanvasAndPost(WarickSurfaceView.this.mCanvas);
            }

            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                boolean unused = WarickSurfaceView.this.isRuning = false;
                System.gc();
                try {
                    Thread thread = this.mThread;
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Log.d(TAG, "Create WarickSurfaceView");
        this.mContext = context;
        this.mSurfaceHolder = getHolder();
        this.mSurfaceHolder.addCallback(this.ecgCallback);
        this.mDrawListenerlist = new ArrayList();
        initPaint();
    }

    /* renamed from: b */
    private void initPaint() {
        this.mPaint = new Paint();
        this.mPaint.setColor(-16777216);
        this.mPaint.setStrokeWidth(3.0f);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setPathEffect(new CornerPathEffect(5.0f));
    }

    /* renamed from: a */
    public void sendMessage0() {
        if (this.ecgHandle != null) {
            Message obtain = Message.obtain(this.ecgHandle);
            obtain.what = 0;
            this.ecgHandle.sendMessage(obtain);
        }
    }

    /* renamed from: a */
    public void setSpeed(int i) {
        if (this.ecg20Timer == null) {
            this.ecg20Timer = new Timer();
            this.ecg20Timer.schedule(new TimerTask() {
                public void run() {
                    WarickSurfaceView.this.sendMessage0();
                }
            }, 20, (long) i);
        }
    }

    /* renamed from: a */
    public void addDrawListener(DrawListener aVar) {
        this.mDrawListenerlist.add(aVar);
    }

    public int getCanvasHeight() {
        return this.mHeight;
    }

    public int getCanvasWidth() {
        return this.mWidth;
    }
}
