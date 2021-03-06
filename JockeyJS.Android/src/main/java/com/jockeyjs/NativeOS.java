package com.jockeyjs;

import java.util.Map;

import android.content.Context;
import android.os.Vibrator;
import android.widget.Toast;

/**
 * <blockquote>Provides helper methods to easily create handlers that implement
 * system services
 * </blockquote>
 * <br>
 * 
 * <code>
 * //Creates a handler that will generate a toast when invoked <br>
 * 	nativeOS(context).toast("A message", Toast.LENGTH_SHORT); <br>
 * <br>
 *  //Creates a handler that operates the OS's haptic feed back <br>
 * 	nativeOS(context).vibrate(45); <br>
 * 
 * </code>
 * 
 * 
 * @author Paul
 *
 */
public final class NativeOS extends CompositeJockeyHandler {
	
	public static class ToastHandler extends JockeyHandler {

		private String _message;
		private int _length;
		private Context _context;

		private ToastHandler(Context context, String message, int length) {
			_message = message;
			_length = length;
			_context = context;
		}

		@Override
		protected void doPerform(Map<Object, Object> payload) {
			Toast.makeText(_context, _message, _length).show();
		}
		
		@Override
		protected void completed(OnCompletedListener listener) {
			if (listener != null)
				listener.onCompleted();
		}

	}

	public static class VibrateHandler extends JockeyHandler {

		private long _length;
		private Vibrator _vibrator;
		private Context _context;
		
		private VibrateHandler(Context context, long length) {
			_context= context;
			_length = length;
			_vibrator = (Vibrator) _context.getSystemService(Context.VIBRATOR_SERVICE);
		}

		@Override
		protected void doPerform(Map<Object, Object> payload) {
			_vibrator.vibrate(_length);
		}
		
		@Override
		protected void completed(OnCompletedListener listener) {
			if (listener != null)
				listener.onCompleted();
		}

	}

	private Context _context;

	private NativeOS(Context context) {
		_context = context;
	}

	public static NativeOS nativeOS(Context context) {
		return new NativeOS(context);
	}
	
	public NativeOS vibrate(long length) {
		this.add(new VibrateHandler(_context, length));
		return this;
	}
	
	public NativeOS toast(String message, int length) {
		this.add(new ToastHandler(_context, message, length));
		return this;
	}
	
}
