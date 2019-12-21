package com.example.workflowpopup.Basepupop;

import android.animation.Animator;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.example.workflowpopup.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * description:
 *
 * @author Db_z
 * date 2019/12/19 19:00
 * @version V1.0
 */
public class BaseInputPopup extends BasePopupWindow {

    public BaseInputPopup(Context context) {
        super(context);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_input);
    }

    @Override
    public void showPopupWindow() {
        super.showPopupWindow();
    }

    @Override
    protected Animation onCreateShowAnimation() {
        Animation showAnimation = new ScaleAnimation(0, 1f, 0, 1f);
        showAnimation.setDuration(500);
        return showAnimation;
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        Animation showAnimation = new ScaleAnimation(1f, 0, 1f, 0);
        showAnimation.setDuration(500);
        return showAnimation;
    }
}
