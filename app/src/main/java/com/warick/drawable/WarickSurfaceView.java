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
    private static final String f3021a = "WarickSurfaceView";

    /* renamed from: b */
    private Context f3022b;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public SurfaceHolder f3023c;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public Canvas f3024d;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public Handler f3025e;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public Paint f3026f;
    /* access modifiers changed from: private */

    /* renamed from: g */
    public List<DrawListener> mDrawListenerlist;
    /* access modifiers changed from: private */

    /* renamed from: h */
    public boolean f3028h;

    /* renamed from: i */
    private Timer f3029i;
    /* access modifiers changed from: private */

    /* renamed from: j */
    public int f3030j;
    /* access modifiers changed from: private */

    /* renamed from: k */
    public int f3031k;

    /* renamed from: l */
    private SurfaceHolder.Callback f3032l;

    /* renamed from: com.warick.drawable.WarickSurfaceView$a */
    public interface DrawListener {
        /* renamed from: a */
        void onMyDraw(Canvas canvas, Paint paint);
    }

    /* renamed from: com.warick.drawable.WarickSurfaceView$b */
    class C0810b implements Runnable {

        /* renamed from: b */
        private Looper f3037b;

        public void run() {
            Looper.prepare();
            this.f3037b = Looper.myLooper();
            f3025e = new Handler(this.f3037b) {
                /* JADX WARNING: Code restructure failed: missing block: B:13:0x004f, code lost:
                    return;
                 */
                public void handleMessage(Message message) {
                    SurfaceHolder d;
                    Canvas a;
                    if (WarickSurfaceView.this.f3028h) {
                        synchronized (WarickSurfaceView.this.f3025e) {
                            try {
                                f3024d = f3023c.lockCanvas();
                                if (WarickSurfaceView.this.f3024d != null) {
                                    Log.d("WarickSurfaceView", "lockCanvas not null!");
                                    WarickSurfaceView.this.f3026f.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                                    WarickSurfaceView.this.f3024d.drawPaint(WarickSurfaceView.this.f3026f);
                                    WarickSurfaceView.this.f3026f.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
                                    for (int i = 0; i < mDrawListenerlist.size(); i++) {
                                        ((DrawListener) WarickSurfaceView.this.mDrawListenerlist.get(i)).onMyDraw(f3024d, f3026f);
                                    }
                                    if (WarickSurfaceView.this.f3024d != null) {
                                        d = WarickSurfaceView.this.f3023c;
                                        a = WarickSurfaceView.this.f3024d;
                                        d.unlockCanvasAndPost(a);
                                    }
                                } else if (WarickSurfaceView.this.f3024d != null) {
                                    WarickSurfaceView.this.f3023c.unlockCanvasAndPost(WarickSurfaceView.this.f3024d);
                                }
                            } catch (Exception e) {
                                try {
                                    e.printStackTrace();
                                    if (WarickSurfaceView.this.f3024d != null) {
                                        d = WarickSurfaceView.this.f3023c;
                                        a = WarickSurfaceView.this.f3024d;
                                    }
                                    super.handleMessage(message);
                                } catch (Throwable th) {
                                    if (WarickSurfaceView.this.f3024d != null) {
                                        WarickSurfaceView.this.f3023c.unlockCanvasAndPost(WarickSurfaceView.this.f3024d);
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
        this.f3024d = null;
        this.f3026f = null;
        this.f3028h = false;
        this.f3029i = null;
        this.f3030j = 0;
        this.f3031k = 0;
        this.f3032l = new SurfaceHolder.Callback() {

            /* renamed from: a */
            Thread f3033a = null;

            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
                int unused = WarickSurfaceView.this.f3030j = i2;
                int unused2 = WarickSurfaceView.this.f3031k = i3;
            }

            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                boolean unused = WarickSurfaceView.this.f3028h = true;
                this.f3033a = new Thread(new C0810b());
                this.f3033a.start();
                Canvas unused2 = WarickSurfaceView.this.f3024d = surfaceHolder.lockCanvas();
                int unused3 = WarickSurfaceView.this.f3030j = WarickSurfaceView.this.f3024d.getWidth();
                int unused4 = WarickSurfaceView.this.f3031k = WarickSurfaceView.this.f3024d.getHeight();
                surfaceHolder.unlockCanvasAndPost(WarickSurfaceView.this.f3024d);
            }

            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                boolean unused = WarickSurfaceView.this.f3028h = false;
                System.gc();
                try {
                    Thread thread = this.f3033a;
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Log.d(f3021a, "Create WarickSurfaceView");
        this.f3022b = context;
        this.f3023c = getHolder();
        this.f3023c.addCallback(this.f3032l);
        this.mDrawListenerlist = new ArrayList();
        m2934b();
    }

    /* renamed from: b */
    private void m2934b() {
        this.f3026f = new Paint();
        this.f3026f.setColor(-16777216);
        this.f3026f.setStrokeWidth(3.0f);
        this.f3026f.setStyle(Paint.Style.STROKE);
        this.f3026f.setStrokeCap(Paint.Cap.ROUND);
        this.f3026f.setAntiAlias(true);
        this.f3026f.setPathEffect(new CornerPathEffect(5.0f));
    }

    /* renamed from: a */
    public void mo2891a() {
        if (this.f3025e != null) {
            Message obtain = Message.obtain(this.f3025e);
            obtain.what = 0;
            this.f3025e.sendMessage(obtain);
        }
    }

    /* renamed from: a */
    public void setSpeed(int i) {
        if (this.f3029i == null) {
            this.f3029i = new Timer();
            this.f3029i.schedule(new TimerTask() {
                public void run() {
                    WarickSurfaceView.this.mo2891a();
                }
            }, 20, (long) i);
        }
    }

    /* renamed from: a */
    public void addDrawListener(DrawListener aVar) {
        this.mDrawListenerlist.add(aVar);
    }

    public int getCanvasHeight() {
        return this.f3031k;
    }

    public int getCanvasWidth() {
        return this.f3030j;
    }
}
