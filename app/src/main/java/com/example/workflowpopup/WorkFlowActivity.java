package com.example.workflowpopup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.workflowpopup.xpopup.CustomPopup;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.qw.workflow.Node;
import com.qw.workflow.WorkFlow;
import com.qw.workflow.WorkNode;
import com.qw.workflow.Worker;

import razerdp.basepopup.QuickPopupBuilder;
import razerdp.basepopup.QuickPopupConfig;
import razerdp.widget.QuickPopup;


public class WorkFlowActivity extends AppCompatActivity {

    /**
     * 初次广告弹框
     */
    private static final int NODE_FIRST_AD = 10;

    /**
     * 初次进入的注册协议
     */
    private static final int NODE_REGISTER = 20;

    /**
     * 初次进入h5页
     */
    private static final int NODE_CHECK_H5 = 30;

    private static final int NODE_CHECK_END = 40;

    private WorkFlow mWorkFlow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_flow);
        findViewById(R.id.btn_basepopup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baseInputPopup(view);
            }
        });

        findViewById(R.id.btn_xpopup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xpopup();
            }
        });
    }

    private void baseInputPopup(final View view) {
//        new BaseInputPopup(WorkFlowActivity.this).showPopupWindow();

//        new BaseInputPopup(WorkFlowActivity.this)
//                .setAdjustInputMethod(true)
//                .showPopupWindow(view);

        QuickPopupBuilder.with(view.getContext())
                .contentView(R.layout.popup_input)
                .config(new QuickPopupConfig()
                        .withClick(R.id.btn_close, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }, true)) // 点击之后弹框是否消失
                .show();
    }

    private void xpopup() {
        if (null != mWorkFlow) {
            mWorkFlow.dispose();
        }
        mWorkFlow = new WorkFlow.Builder()
                .withNode(getFirstPopup())
                .withNode(getFirstAdNode())
                .withNode(getNodeChack())
                .withNode(WorkNode.build(NODE_CHECK_END, new Worker() {
                    @Override
                    public void doWork(Node current) {
                        new XPopup.Builder(WorkFlowActivity.this)
                                .asCustom(new CustomPopup(WorkFlowActivity.this))
                                .show();
                        current.onCompleted();
                    }
                }))
                .create();
        mWorkFlow.start();
    }

    private WorkNode getFirstPopup() {
        return WorkNode.build(NODE_FIRST_AD, new Worker() {
            @Override
            public void doWork(final Node current) {
                new XPopup.Builder(WorkFlowActivity.this)
                        .asConfirm("我是标题", "我是内容",
                                new OnConfirmListener() {
                                    @Override
                                    public void onConfirm() {
                                        Toast.makeText(WorkFlowActivity.this,
                                                "text", Toast.LENGTH_SHORT).show();
                                        current.onCompleted();
                                    }
                                })
                        .show();
            }
        });
    }

    private WorkNode getFirstAdNode() {
        return WorkNode.build(NODE_REGISTER, new Worker() {
            @Override
            public void doWork(final Node current) {
                new XPopup.Builder(WorkFlowActivity.this).asInputConfirm("我是标题", "请输入内容。",
                        new OnInputConfirmListener() {
                            @Override
                            public void onConfirm(String text) {
                                Toast.makeText(WorkFlowActivity.this,
                                        "text = " + text, Toast.LENGTH_SHORT).show();
                                current.onCompleted();
                            }
                        })
                        .show();
            }
        });
    }

    private WorkNode getNodeChack() {
        return WorkNode.build(NODE_CHECK_H5, new Worker() {
            @Override
            public void doWork(final Node current) {
                QuickPopupBuilder.with(WorkFlowActivity.this)
                        .contentView(R.layout.image_popup)
                        .config(new QuickPopupConfig()
                                .withClick(R.id.iv_close, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        current.onCompleted();
                                        Toast.makeText(view.getContext(), "clicked", Toast.LENGTH_LONG).show();
                                    }
                                }, true))
                        .show();
            }
        });
    }
}
