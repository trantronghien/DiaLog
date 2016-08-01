package com.it85.hientran.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    Button buttonDialog,listDialog,radioDialog,checkboxDialog,progressDialog,progressBar,customDialog;
    TextView txtDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonDialog = (Button) findViewById(R.id.btnButtonDialog);
        listDialog = (Button) findViewById(R.id.btnListDialog);
        radioDialog = (Button) findViewById(R.id.btnRadioDialog);
        checkboxDialog = (Button) findViewById(R.id.btnCheckboxDialog);
        progressDialog = (Button) findViewById(R.id.btnProgressDialog);
        progressBar = (Button) findViewById(R.id.btnProgressBar);
        customDialog = (Button) findViewById(R.id.btnCustomDialog);
        txtDisplay = (TextView) findViewById(R.id.txtDisplay);

        //Set Click
        buttonDialog.setOnClickListener(this);
        listDialog.setOnClickListener(this);
        radioDialog.setOnClickListener(this);
        checkboxDialog.setOnClickListener(this);
        progressDialog.setOnClickListener(this);
        progressBar.setOnClickListener(this);
        customDialog.setOnClickListener(this);

    }

    //mảng chuỗi sắp xếp tuần tự
    final CharSequence[] items = {"Red", "Green", "Blue"};
    //Mảng chứa trạng thái các item
    final boolean[] states = {false, false, true};

    Handler handler = new Handler();

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnButtonDialog:

                txtDisplay.setText("");
                //Bạn có thể lược bỏ phần nào trong Dialog mà bạn thích: Tile, Message...
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Pick");// Set tiêu đề
                builder.setMessage("Pick a Color");//Set nội dung cho Dialog
                builder.setCancelable(false);//Set có cho người dùng Cancer bằng nút quay lại (back) ko? false: ko
                builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Làm cái gì đó khi ấn Yes tại đây
                        txtDisplay.setText("YES");
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //
                        txtDisplay.setText("NO");
                        dialog.cancel();

                    }
                });

                builder.show();//Hiển thị Dialog
                break;

            case R.id.btnListDialog:

                txtDisplay.setText("");
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("Pick a color");
                builder1.setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();//Hiển thị item được lựa chọn
                        txtDisplay.setText(items[item].toString());
                    }
                });
                builder1.show();
                break;

            case R.id.btnRadioDialog:
                txtDisplay.setText("");
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle("Pick a color");
                //setSingleChoiceItems(CharSequence[] items, int checkedItem, OnClickListener listener):
                // -> checkedItem: Item muốn check mặc định, để giá trị -1 để ko chọn giá trị nào, vị trí bắt đầu từ 0
                builder2.setSingleChoiceItems(items,-1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();//Hiển thị item được lựa chọn
                        txtDisplay.setText(items[item].toString());
                        dialog.dismiss();

                    }
                });

                builder2.show();
                break;

            case R.id.btnCheckboxDialog:
                txtDisplay.setText("");
                AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
                builder3.setTitle("Pick a Color:");
                builder3.setMultiChoiceItems(items, states, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item, boolean isChecked) {
                        Toast.makeText(getApplicationContext(), items[item] + " set to " + isChecked, Toast.LENGTH_SHORT).show();

                    }
                });

                builder3.show();
                break;

            case R.id.btnProgressDialog:
                //true: Có cho cancel Dialog
                //false: indeterminate (Vô hạn)
                ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "progress Dialog", "Please waiting ...", false, true, new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        txtDisplay.setText("Done");

                    }
                });
                progressDialog.show();
                break;

            case R.id.btnProgressBar:

                final ProgressDialog progressDialog1;
                progressDialog1 = new ProgressDialog(this);
                progressDialog1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog1.setMessage("Loading...");
                progressDialog1.setCancelable(true);
                progressDialog1.setProgress(0);//Set tiến độ cho ProgressBar
                progressDialog1.setMax(100);//Set Giá trị cho tối đa cho ProgressBar
                progressDialog1.show();

                //Tạo Thread để tăng giá trị của Progress Bar theo thời gian chúng ta quy định
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        while(progressDialog1.getProgress() <= progressDialog1.getMax())
                        {
                            try
                            {

                                progressDialog1.incrementProgressBy(1);//Tăng Giá trị tiến độ
                                Thread.sleep(100);
                                if(progressDialog1.getProgress()== progressDialog1.getMax())   // Nếu đạt tối đa
                                    progressDialog1.cancel();

                            }
                            catch(Exception ex) {
                                Log.i("TabLog", "DialogProgressBar bi loi");

                            }
                        }

                    }
                }).start();
                break;

            case R.id.btnCustomDialog:

                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.customdialog); //Hiện thị Dialog với layout custom_Dialog
                dialog.setTitle("Custom Dialog");
                dialog.setCancelable(true);


                //Set Image
                ImageView imageView = (ImageView) dialog.findViewById(R.id.imgAndroid);
                imageView.setImageResource(R.drawable.ic_cross);

                //Set Text for TextView
                TextView txtContent = (TextView) dialog.findViewById(R.id.txtContent);
                txtContent.setText("VD về Custom Dialog.");

                //Show Dialog
                dialog.show();

                break;

        }

    }

}
