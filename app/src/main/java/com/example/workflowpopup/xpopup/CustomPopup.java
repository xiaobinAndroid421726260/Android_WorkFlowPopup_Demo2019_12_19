package com.example.workflowpopup.xpopup;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.workflowpopup.R;
import com.lxj.xpopup.core.CenterPopupView;

public class CustomPopup extends CenterPopupView {


    public CustomPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        findViewById(R.id.btn_close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.xpopup_custom_popup;
    }


    @Override
    protected void onAnimationStart() {
        super.onAnimationStart();
    }
}
