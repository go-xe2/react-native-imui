package cn.jiguang.imui.messagelist.viewmanager;

import android.app.Activity;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.PromiseImpl;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import java.util.Map;

import cn.jiguang.imui.chatinput.emoji.widget.EmoticonsEditText;
import cn.jiguang.imui.chatinput.utils.SimpleCommonUtils;

@ReactModule(name = ReactIMEditViewManager.REACT_CLASS)
public class ReactIMEditViewManager extends SimpleViewManager<EmoticonsEditText> implements
        LifecycleEventListener,
        TextWatcher {
    public final static String REACT_CLASS = "AndroidIMEditView";
    private final static String EVENT_ON_FOCUS = "onFocus";
    private final static String EVENT_ON_BLUR = "onBlur";
    private final static String EVENT_ON_CHANGE = "onChange";

    private final static String CMD_OPEN_KEYBOARD = "openKeyboard";  // 打开软键盘
    private final static String CMD_CLOSE_KEYBOARD = "closeKeyboard"; // 关闭软键盘
    private final static String CMD_INSERT_TEXT = "insertText"; // 插入文本
    private InputMethodManager inputMethodManager;
    private ReactContext mContext;
    private EmoticonsEditText mEditor;
    private Activity mActivity;

    @Override
    public String getName () {
        return REACT_CLASS;
    }

    @Override
    protected EmoticonsEditText createViewInstance (@NonNull ThemedReactContext reactContext) {
        mContext = reactContext;
//        mContext.addLifecycleEventListener(this);
//        mActivity = reactContext.getCurrentActivity();
//        if (mActivity == null) {
//            Log.d("ReactNativeJS", "getCurrentActivity is null");
//            return null;
//        }
        inputMethodManager = (InputMethodManager) mActivity.getSystemService(ReactContext.INPUT_METHOD_SERVICE);
        mEditor = new EmoticonsEditText(mContext);
        mEditor.setFocusable(true);
        mEditor.setFocusableInTouchMode(true);
        mEditor.setCursorVisible(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mEditor.setShowSoftInputOnFocus(true);
        }
        mEditor.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        mEditor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_BUTTON_PRESS) {
                    mEditor.setFocusable(true);
                    mEditor.requestFocus();
                }
                return false;
            }
        });
        mEditor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange (View v, boolean hasFocus) {
                if (hasFocus) {
                    dispatchEvent("topFocus", null);
                } else {
                    dispatchEvent("topBlur", null);
                }
            }
        });
        mEditor.addTextChangedListener(this);
        SimpleCommonUtils.initEmoticonsEditText(mEditor);
        return mEditor;
    }

    @Override
    public void onDropViewInstance (@NonNull EmoticonsEditText view) {
        super.onDropViewInstance(view);
        if (mContext == null) {
            return;
        }
        if (mEditor != null) {
            mEditor.removeTextChangedListener(this);
        }
//        mContext.removeLifecycleEventListener(this);
    }

    @Override
    public void onHostResume () {

    }

    @Override
    public void onHostPause () {

    }

    @Override
    public void onHostDestroy () {
//        mContext.removeLifecycleEventListener(this);
//        mEditor = null;
//        mActivity = null;
//        mContext = null;
    }


    @Override
    public void setPadding (EmoticonsEditText view, int left, int top, int right, int bottom) {
        super.setPadding(view, left, top, right, bottom);
        view.setPadding(left, top, right, bottom);
    }

    @Override
    public void receiveCommand (@NonNull EmoticonsEditText root, String commandId, @Nullable ReadableArray args) {
        super.receiveCommand(root, commandId, args);
        switch (commandId) {
            case CMD_OPEN_KEYBOARD:
                openKeyboard();
                break;
            case CMD_CLOSE_KEYBOARD:
                closeKeyboard();
                break;
            case CMD_INSERT_TEXT:
                if (args == null || args.size() < 1) {
                    return;
                }
                String text = args.getString(0);
                insertText(root, text);
                break;
        }
    }

    /**********************************************
     * TextWatcher
     * @param s
     * @param start
     * @param count
     * @param after
     */

    @Override
    public void beforeTextChanged (CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged (CharSequence s, int start, int before, int count) {
        WritableMap event = Arguments.createMap();
        event.putString("text", s.toString());
        dispatchEvent("topChange", event);
    }

    @Override
    public void afterTextChanged (Editable s) {

    }

    /*********************************************************
     * 属性设置
     ********************************************************/

    /**
     * 设置文字
     * @param editor
     * @param text
     */
    @ReactProp(name="text")
    public void text(EmoticonsEditText editor, String text) {
        editor.setText(text);
    }

    /**
     * 提示文本
     * @param editor
     * @param placeholder
     */
    @ReactProp(name="placeholder")
    public void placeholder(EmoticonsEditText editor, String placeholder) {
       editor.setHint(placeholder);
    }

    /*******************************************************
     * 私有方法
     ******************************************************/

    /**
     * 发送事件
     * @param eventName
     * @param event
     */
    private void dispatchEvent(String eventName, WritableMap event) {
        if (mContext == null) {
            return;
        }
        Log.d("ReactNativeJS", "dispatchEvent:" + eventName);
//        mContext.getJSModule(RCTEventEmitter.class).receiveEvent(mEditor.getId(), eventName, event);
    }

    /**
     * 打开软键盘
     */
    private void openKeyboard() {
        if (inputMethodManager == null || mActivity == null) {
            return;
        }
        View focusView = mActivity.getCurrentFocus();
        if (focusView != null) {
            inputMethodManager.showSoftInput(focusView, 0);
        } else {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    /**
     * 关闭软键键
     */
    private void closeKeyboard() {
        if (inputMethodManager == null || mActivity == null) {
            return;
        }
        View focusView = mActivity.getCurrentFocus();
        if (focusView != null) {
            inputMethodManager.hideSoftInputFromWindow(focusView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } else {
            inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
    }

    private void insertText(EditText editor, String text) {
        int index = editor.getSelectionStart();
        Editable editable = editor.getText();
        editable.insert(index, text);
    }


}
