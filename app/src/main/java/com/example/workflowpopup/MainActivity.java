package com.example.workflowpopup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.qw.workflow.Node;
import com.qw.workflow.WorkFlow;
import com.qw.workflow.WorkNode;
import com.qw.workflow.Worker;

import razerdp.basepopup.QuickPopupBuilder;
import razerdp.basepopup.QuickPopupConfig;

public class MainActivity extends AppCompatActivity {

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

    private WorkFlow mWorkFlow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_workflow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initWorkFlow(v);
            }
        });
        findViewById(R.id.btn_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WorkFlowActivity.class));
            }
        });
    }

    private void initWorkFlow(final View v) {
        if (null != mWorkFlow) {
            mWorkFlow.dispose();
        }
        mWorkFlow = new WorkFlow.Builder()
                .withNode(WorkNode.build(NODE_FIRST_AD, new Worker() {
                    @Override
                    public void doWork(final Node current) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("这是一条有态度的广告")
                                .setPositiveButton("我看完了", null)
                                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialogInterface) {
                                        //仅仅只需关心自己是否完成，下一个节点会自动执行
                                        current.onCompleted();
                                    }
                                }).create().show();
                    }
                }))
                .withNode(WorkNode.build(NODE_CHECK_H5, new Worker() {
                    @Override
                    public void doWork(final Node current) {
                        QuickPopupBuilder.with(MainActivity.this)
                                .contentView(R.layout.popup_normal)
                                .config(new QuickPopupConfig()
                                        .gravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL)
                                        .withClick(R.id.tx_1, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_LONG).show();
                                            }
                                        }))

                                .show(v);
                    }
                }))
                .withNode(WorkNode.build(NODE_REGISTER, new Worker() {
                    @Override
                    public void doWork(final Node current) {
                        new XPopup.Builder(MainActivity.this)
                                .asBottomList("标题", new String[]{"条目1", "条目2", "条目3"},
                                        new OnSelectListener() {
                                            @Override
                                            public void onSelect(int position, String text) {
                                                Toast.makeText(MainActivity.this, "点击" + position, Toast.LENGTH_SHORT).show();
                                                current.onCompleted();
                                            }
                                        })
                                .show();
                    }
                }))
                .create();
        mWorkFlow.start();
    }
}
