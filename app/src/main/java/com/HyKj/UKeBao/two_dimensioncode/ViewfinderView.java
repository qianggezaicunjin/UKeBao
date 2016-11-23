package com.HyKj.UKeBao.two_dimensioncode;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.HyKj.UKeBao.R;
import com.google.zxing.ResultPoint;

import java.util.Collection;
import java.util.HashSet;

public final class ViewfinderView extends View {
	private static final int[] SCANNER_ALPHA = { 0, 64, 128, 192, 255, 192,
			128, 64 };
	private static final long ANIMATION_DELAY = 10L;
	private static final int OPAQUE = 0xFF;// 锟斤拷透锟斤拷

	private int ScreenRate;

	private static final int CORNER_WIDTH = 16;
	private static final int MIDDLE_LINE_WIDTH = 6;

	private static final int MIDDLE_LINE_PADDING = 5;
	private static final int SPEEN_DISTANCE = 5;

	private static float density;
	private static final int TEXT_SIZE = 16;
	private static final int TEXT_PADDING_TOP = 30;

	private final Paint paint;
	private Bitmap resultBitmap;
	private final int maskColor;
	private final int resultColor;
	private final int frameColor;
	private final int laserColor;
	private final int resultPointColor;
	private Collection<ResultPoint> possibleResultPoints;
	private Collection<ResultPoint> lastPossibleResultPoints;
	private int slideTop;
	private int slideBottom;
	private boolean isFirst;
	private int scannerAlpha;//
	private int width;
	private int height;

	// This constructor is used when the class is built from an XML resource.
	public ViewfinderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		density = context.getResources().getDisplayMetrics().density;
		ScreenRate = (int) (25 * density);

		// Initialize these once for performance rather than calling them every
		// time in onDraw().
		paint = new Paint();
		Resources resources = getResources();
		maskColor = resources.getColor(R.color.viewfinder_mask);
		resultColor = resources.getColor(R.color.backgroud);
//		frameColor = resources.getColor(R.color.top_color);// resources.getColor(R.color.viewfinder_frame);
//		laserColor = resources.getColor(R.color.top_color);
		frameColor = resources.getColor(R.color.status_color);// resources.getColor(R.color.viewfinder_frame);
		laserColor = resources.getColor(R.color.status_color);
		resultPointColor = resources.getColor(R.color.possible_result_points);
		scannerAlpha = 0;
		possibleResultPoints = new HashSet<ResultPoint>(5);
		WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

			 width = wm.getDefaultDisplay().getWidth();
			 height = wm.getDefaultDisplay().getHeight();

	}

	@Override
	public void onDraw(Canvas canvas) {
		Rect frame = CameraManager.get().getFramingRect();
		if (frame == null) {
			return;
		}

		if (!isFirst) {
			isFirst = true;
			slideTop = frame.top + CORNER_WIDTH;
			slideBottom = frame.bottom - CORNER_WIDTH;
		}

		int width = canvas.getWidth();
		int height = canvas.getHeight();

		// Draw the exterior (i.e. outside the framing rect) darkened
		// 锟斤拷锟斤拷锟斤拷
		paint.setColor(resultBitmap != null ? resultColor : maskColor);
		canvas.drawRect(0, 0, width, frame.top, paint);
		canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
		canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1,
				paint);
		canvas.drawRect(0, frame.bottom + 1, width, height, paint);

		if (resultBitmap != null) {
			// Draw the opaque result bitmap over the scanning rectangle
			paint.setAlpha(OPAQUE);
			canvas.drawBitmap(resultBitmap, frame.left, frame.top, paint);
		} else {

			// Draw a two pixel solid black border inside the framing rect
			// 锟斤拷锟斤拷锟?
			paint.setColor(frameColor);
			// canvas.drawRect(frame.left, frame.top, frame.right + 1, frame.top
			// + 2, paint);
			// canvas.drawRect(frame.left, frame.top + 2, frame.left + 2,
			// frame.bottom - 1, paint);
			// canvas.drawRect(frame.right - 1, frame.top, frame.right + 1,
			// frame.bottom - 1, paint);
			// canvas.drawRect(frame.left, frame.bottom - 1, frame.right + 1,
			// frame.bottom + 1, paint);
			// public void drawRect (float left, float top, float right, float
			// bottom, Paint paint)
			// lefttop
			canvas.drawRect(frame.left, frame.top, frame.left + ScreenRate,
					frame.top + CORNER_WIDTH / 2, paint);
			canvas.drawRect(frame.left, frame.top, frame.left + CORNER_WIDTH
					/ 2, frame.top + ScreenRate, paint);
			// leftbutton
			canvas.drawRect(frame.left, frame.bottom - ScreenRate, frame.left
					+ CORNER_WIDTH / 2, frame.bottom, paint);
			canvas.drawRect(frame.left, frame.bottom - CORNER_WIDTH / 2,
					frame.left + ScreenRate, frame.bottom, paint);
			// righttop
			canvas.drawRect(frame.right - ScreenRate, frame.top, frame.right,
					frame.top + CORNER_WIDTH / 2, paint);

			canvas.drawRect(frame.right - CORNER_WIDTH / 2, frame.top,
					frame.right, frame.top + ScreenRate, paint);
			// rightbuttom
			canvas.drawRect(frame.right - CORNER_WIDTH / 2, frame.bottom
					- ScreenRate, frame.right, frame.bottom, paint);
			canvas.drawRect(frame.right - ScreenRate, frame.bottom
					- CORNER_WIDTH / 2, frame.right, frame.bottom, paint);
			// 直锟斤拷锟斤拷图?
			// Rect bigRect = new Rect();
			// bigRect.left = frame.left;
			// bigRect.right = frame.right;
			// bigRect.top = frame.top;
			// bigRect.bottom = frame.bottom;
			// Drawable drawable =
			// getResources().getDrawable(R.drawable.qr_mask);
			// BitmapDrawable b= (BitmapDrawable) drawable;
			// canvas.drawBitmap(b.getBitmap(), null, bigRect, paint);

			// Draw a red "laser scanner" line through the middle to show
			// decoding is active
			// paint.setColor(laserColor);
			// paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
			// scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
			// int middle = frame.height() / 2 + frame.top;
			// canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1,
			// middle + 2, paint);

			// 锟斤拷锟叫硷拷锟确讹拷锟斤拷锟斤拷
			slideTop += SPEEN_DISTANCE;
			if (slideTop >= slideBottom) {
				slideTop = frame.top + CORNER_WIDTH;
			}
			// 锟皆硷拷锟斤拷
			paint.setColor(laserColor);
			canvas.drawRect(frame.left + CORNER_WIDTH, slideTop, frame.right
					-CORNER_WIDTH, slideTop + MIDDLE_LINE_WIDTH, paint);

			// 锟斤拷图?
			// Rect lineRect = new Rect();
			// lineRect.left = frame.left;
			// lineRect.right = frame.right;
			// lineRect.top = slideTop;
			// lineRect.bottom = slideTop + MIDDLE_LINE_PADDING;
			// canvas.drawBitmap(((BitmapDrawable)(getResources().getDrawable(R.drawable.qrcode_scan_line))).getBitmap(),
			// null, lineRect, paint);

			// 锟斤拷扫锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
			paint.setColor(Color.WHITE);
			paint.setTextSize(TEXT_SIZE * density);
//			paint.setAlpha(0x40);
			paint.setAlpha(0xbb);
			paint.setTypeface(Typeface.create("System", Typeface.BOLD));
			canvas.drawText(
					getResources().getString(R.string.msg_default_status),
					frame.left+width/8, frame.bottom + TEXT_PADDING_TOP * density,
					paint);
			

			Collection<ResultPoint> currentPossible = possibleResultPoints;
			Collection<ResultPoint> currentLast = lastPossibleResultPoints;
			if (currentPossible.isEmpty()) {
				lastPossibleResultPoints = null;
			} else {
				possibleResultPoints = new HashSet<ResultPoint>(5);
				lastPossibleResultPoints = currentPossible;
				paint.setAlpha(OPAQUE);
				paint.setColor(resultPointColor);
				for (ResultPoint point : currentPossible) {
					canvas.drawCircle(frame.left + point.getX(), frame.top
							+ point.getY(), 6.0f, paint);// 锟斤拷扫锟借到锟≧匡拷锟杰的碉拷
				}
			}
			if (currentLast != null) {
				paint.setAlpha(OPAQUE / 2);
				paint.setColor(resultPointColor);
				for (ResultPoint point : currentLast) {
					canvas.drawCircle(frame.left + point.getX(), frame.top
							+ point.getY(), 3.0f, paint);
				}
			}

			// Request another update at the animation interval, but only
			// repaint the laser line,
			// not the entire viewfinder mask.
			postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top,
					frame.right, frame.bottom);
		}
	}
			
	public void drawViewfinder() {
		resultBitmap = null;
		invalidate();
	}

	/**
	 * Draw a bitmap with the result points highlighted instead of the live
	 * scanning display.
	 * 
	 * @param barcode
	 *            An image of the decoded barcode.
	 */
	public void drawResultBitmap(Bitmap barcode) {
		resultBitmap = barcode;
		invalidate();
	}

	public void addPossibleResultPoint(ResultPoint point) {
		possibleResultPoints.add(point);
	}

}
