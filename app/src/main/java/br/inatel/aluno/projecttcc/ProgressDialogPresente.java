package br.inatel.aluno.projecttcc;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

class ProgressDialogPresente {
    ProgressDialog progressDialog;

    ProgressDialogPresente(Context context) {
        progressDialog = new ProgressDialog(context);
    }
    void showProgressDialog(String message){
        progressDialog.setTitle(message);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        final Integer[] decre = {14};
        final Handler handle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                progressDialog.incrementProgressBy(decre[0]);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (progressDialog.getProgress() < progressDialog
                            .getMax()) {
                        if (progressDialog.getProgress()<40){
                            decre[0] = 8;
                        } else  if (progressDialog.getProgress()<70) {
                            decre[0] = 5;
                        } else if (progressDialog.getProgress()<95) {
                            decre[0] = 1;
                        }

                        Thread.sleep(100);
                        handle.sendMessage(handle.obtainMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    void hideProgressDialog(){
        progressDialog.hide();
        progressDialog.setProgress(0);
    }

}
