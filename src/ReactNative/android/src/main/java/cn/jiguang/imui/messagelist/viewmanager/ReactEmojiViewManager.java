package cn.jiguang.imui.messagelist.viewmanager;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import java.util.Map;

import cn.jiguang.imui.chatinput.emoji.Constants;
import cn.jiguang.imui.chatinput.emoji.EmojiBean;
import cn.jiguang.imui.chatinput.emoji.EmojiView;
import cn.jiguang.imui.chatinput.emoji.data.EmoticonEntity;
import cn.jiguang.imui.chatinput.emoji.listener.EmoticonClickListener;
import cn.jiguang.imui.chatinput.utils.SimpleCommonUtils;
import cn.jiguang.imui.messagelist.R;

public class ReactEmojiViewManager extends SimpleViewManager<ReactEmojiViewManager.RCTEmojiView> implements LifecycleEventListener {
    private final static String RCTEmojiViewName = "RCTEmojiView";
    private Activity mActivity;
    private ReactContext mReactContext;
    private RCTEmojiView mEmojiView;
    @NonNull
    @Override
    public String getName () {
        return RCTEmojiViewName;
    }

    @NonNull
    @Override
    protected RCTEmojiView createViewInstance (@NonNull ThemedReactContext reactContext) {
        mReactContext = reactContext;
        mReactContext.addLifecycleEventListener(this);
        mActivity = reactContext.getCurrentActivity();
        mEmojiView = new RCTEmojiView(mActivity, mReactContext);
        mReactContext.addLifecycleEventListener(mEmojiView);
        return null;
    }

    @Override
    public void onHostResume () {

    }

    @Override
    public void onHostPause () {

    }

    @Override
    public void onHostDestroy () {
        if (mReactContext != null) {
            mReactContext.removeLifecycleEventListener(this);
        }
    }

    @androidx.annotation.Nullable
    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants () {
        MapBuilder.Builder<String, Object> builder = MapBuilder.builder();
        for (Event event:Event.values()) {
            builder.put(event.toString(), MapBuilder.of("registrationName", event.toString()));
        }
        return builder.build();
    }

    private enum Event {
        EVENT_SELECT("onSelect"),
        EVENT_DELETE("onDelete"),
        EVENT_PAGE_CHANGED("onPageChanged");

        private String mName;
        Event(String name) {
            mName = name;
        }

        @Override
        public String toString() {
            return mName;
        }
    }

    public static class RCTEmojiView extends FrameLayout implements EmoticonClickListener, ViewPager.OnPageChangeListener, LifecycleEventListener {
        private Context      mContext;
        private EmojiView    mEmojiView;
        private ReactContext mReactContext;

        public RCTEmojiView (@androidx.annotation.Nullable Context context, ReactContext reactContext) {
            super(context);
            mReactContext = reactContext;
            init(context, null);
        }

        public RCTEmojiView (@androidx.annotation.NonNull Context context) {
            super(context);
            init(context, null);
        }

        public RCTEmojiView (@androidx.annotation.NonNull Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            init(context, attrs);
        }

        public RCTEmojiView (@androidx.annotation.NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init(context, attrs);
        }

        @RequiresApi (api = Build.VERSION_CODES.LOLLIPOP)
        public RCTEmojiView (@androidx.annotation.NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            init(context, attrs);
        }

        private void init (Context context, AttributeSet attrs) {
            inflate(context, R.layout.im_emoji_view, this);
            this.mContext = context;
            mEmojiView = findViewById(R.id.id_im_emoji_view);
            mEmojiView.setAdapter(SimpleCommonUtils.getCommonAdapter(mContext, this));
            mEmojiView.getEmoticonsFuncView().addOnPageChangeListener(this);
        }

        @Override
        public void onEmoticonClick (Object o, int actionType, boolean isDelBtn) {
            if (isDelBtn) {
                // 删除
                dispatchEvent(Event.EVENT_DELETE.toString(), null);
            } else {
                if (o == null) {
                    return;
                }
                if (actionType == Constants.EMOTICON_CLICK_BIGIMAGE) {
                    // if(o instanceof EmoticonEntity){
                    // OnSendImage(((EmoticonEntity)o).getIconUri());
                    // }
                } else {
                    String content = null;
                    if (o instanceof EmojiBean) {
                        content = ((EmojiBean) o).emoji;
                    } else if (o instanceof EmoticonEntity) {
                        content = ((EmoticonEntity) o).getContent();
                    }

                    if (TextUtils.isEmpty(content)) {
                        return;
                    }
                    // 选择文件
                    WritableMap event = Arguments.createMap();
                    event.putString("text", content);
                    dispatchEvent(Event.EVENT_SELECT.toString(), event);
                }
            }
        }

        private void dispatchEvent(String name, WritableMap event) {
            if (mReactContext == null) {
                return;
            }
            mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(this.getId(), name, event);
        }

        @Override
        public void onPageScrolled (int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected (int position) {
            WritableMap event = Arguments.createMap();
            event.putInt("position", position);
            dispatchEvent(Event.EVENT_PAGE_CHANGED.toString(), event);
        }

        @Override
        public void onPageScrollStateChanged (int state) {

        }

        @Override
        public void onHostResume () {

        }

        @Override
        public void onHostPause () {

        }

        @Override
        public void onHostDestroy () {
            if (mEmojiView != null) {
                mEmojiView.getEmoticonsFuncView().removeOnPageChangeListener(this);
            }
            if (mReactContext != null) {
                mReactContext.removeLifecycleEventListener(this);
            }
        }
    }
}
