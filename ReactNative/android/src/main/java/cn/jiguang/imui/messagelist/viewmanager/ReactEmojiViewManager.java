package cn.jiguang.imui.messagelist.viewmanager;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

public class ReactEmojiViewManager extends SimpleViewManager<ReactEmojiViewManager.RCTEmojiView> {
    private static final String REACT_EMOJI_VIEW = "RCTEmojiView";
    private ReactContext mContext;
    private RCTEmojiView mEmojiView;

    public ReactEmojiViewManager() {
        super();
    }

    private enum EmojiViewEvent{
        EVENT_SELECT("onSelect"),
        EVENT_DELETE("onDelete"),
        EVENT_PAGE_CHANGED("onPageChanged");

        private String mName;
        EmojiViewEvent(String name) {
            this.mName = name;
        }

        @Override
        public String toString() {
            return mName;
        }
    }

    @NonNull
    @Override
    public String getName() {
        return REACT_EMOJI_VIEW;
    }

    @NonNull
    @Override
    protected RCTEmojiView createViewInstance(@NonNull ThemedReactContext reactContext) {
        this.mContext = reactContext;
//        final Activity activity = reactContext.getCurrentActivity();
        this.mEmojiView = new RCTEmojiView(reactContext, null);
        reactContext.addLifecycleEventListener(this.mEmojiView);
        return this.mEmojiView;
    }

    @Override
    public void onDropViewInstance(@NonNull RCTEmojiView view) {
        super.onDropViewInstance(view);
        ((ThemedReactContext) view.getContext()).removeLifecycleEventListener((RCTEmojiView) view);
    }

    @Nullable
    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        MapBuilder.Builder<String, Object> builder = MapBuilder.builder();
        for (EmojiViewEvent event:EmojiViewEvent.values()){
            builder.put(event.toString(),MapBuilder.of("registrationName", event.toString()));
        }
        return builder.build();
    }


    public static class RCTEmojiView extends EmojiView implements EmoticonClickListener, LifecycleEventListener {
        private Context mContext;
        public RCTEmojiView(Context context) {
            super(context);
            this.init(context);
        }

        public RCTEmojiView(@androidx.annotation.NonNull Context context, @androidx.annotation.Nullable AttributeSet attrs) {
            super(context, attrs);
            this.init(context);
        }

        public RCTEmojiView(@androidx.annotation.NonNull Context context, @androidx.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            this.init(context);
        }

        private void init(Context context) {
            this.mContext = context;
            this.setAdapter(SimpleCommonUtils.getCommonAdapter(this.getContext(), this));
        }

        @Override
        public void onEmoticonClick(Object o, int actionType, boolean isDelBtn) {
            if (isDelBtn) {
                dispatchEvent(EmojiViewEvent.EVENT_DELETE.toString(), null);
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
                    WritableMap event = Arguments.createMap();
                    event.putString("text", content);
                    dispatchEvent(EmojiViewEvent.EVENT_SELECT.toString(), event);
//                    int index = mChatInput.getSelectionStart();
//                    Editable editable = mChatInput.getText();
//                    editable.insert(index, content);
                }
            }
        }

        @Override
        public void onHostResume() {

        }

        @Override
        public void onHostPause() {

        }

        @Override
        public void onHostDestroy() {
            this.setAdapter(null);
        }

        private void dispatchEvent(String eventName, WritableMap eventData){
            ReactContext reactContext = (ReactContext) getContext();
            reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                    getId(),//native和js两个视图会依据getId()而关联在一起
                    eventName,//事件名称
                    eventData
            );
        }
    }

}
