package com.newapplication.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.newapplication.R;
import com.newapplication.bean.Goods_List;
import com.newapplication.bean.User;
import com.newapplication.config.BmobConstants;;
import com.newapplication.util.PhotoUtil;
import com.newapplication.view.HeaderLayout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class AddGoodsActivity extends BaseActivity implements View.OnClickListener{

    private LinearLayout LayoutAll;
    private LinearLayout AddPhoto;
    private ImageView Photo;

    private Spinner newSpinner;

    private EditText NewPrice;
    private EditText OldPrice;
    private EditText Telephone;
    private EditText Detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods);
        initView();
        init();
    }

    private void initView() {
        initTopBarForBoth("发布物品信息", R.drawable.base_action_bar_true_bg_selector,
                new HeaderLayout.onRightImageButtonClickListener() {
                    @Override
                    public void onClick() {
                        addGoods();
                    }
                });

        LayoutAll = (LinearLayout) findViewById(R.id.layout_all);
        AddPhoto = (LinearLayout) findViewById(R.id.add_photo);
        Photo = (ImageView) findViewById(R.id.goods_photo);
        NewPrice = (EditText) findViewById(R.id.edit_newprice);
        OldPrice = (EditText) findViewById(R.id.edit_oldprice);
        Telephone = (EditText) findViewById(R.id.edit_telephone);
        Detail = (EditText) findViewById(R.id.edit_detail);

        AddPhoto.setOnClickListener(this);
    }

    String sort;

    private void init(){
        newSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.spinner,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newSpinner.setAdapter(adapter);
        newSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sort = newSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sort = "其它";
            }
        });
    }

    private void addGoods() {
        final String username = userManager.getCurrentUser().getUsername();
        final String newPrice = NewPrice.getText().toString();
        final String oldPrice = OldPrice.getText().toString();
        final String telePhone = Telephone.getText().toString();
        final String detail = Detail.getText().toString();

        final Goods_List goods = new Goods_List();
        final BmobFile bmobFile = new BmobFile(new File(path));
        if(path != null) {
            bmobFile.upload(this, new UploadFileListener() {
                @Override
                public void onSuccess() {
                    goods.setUser(BmobUser.getCurrentUser(AddGoodsActivity.this,User.class));
                    goods.setThingsPhoto(bmobFile);
                    goods.setUsername(username);
                    goods.setBelongTo(sort);
                    goods.setNewPrice(newPrice);
                    goods.setOldPrice(oldPrice);
                    goods.setMobileNumber(telePhone);
                    goods.setThingsDetails(detail);
                    goods.save(AddGoodsActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            ShowToast("发布成功!");
                            finish();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            ShowToast("发布失败:" + s);
                        }
                    });
                }

                @Override
                public void onProgress(Integer arg0) {
                }

                @Override
                public void onFailure(int arg0, String arg1) {
                }
            });
        }else{
            ShowToast("请插入物品图片");
            return;
        }
    }

    Button layout_choose;
    Button layout_photo;
    Button layout_cancel;
    PopupWindow photoPop;

    public String filePath = "";

    @SuppressWarnings("deprecation")
    private void showPhotoPop() {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_show,
                null);
        layout_choose = (Button) view.findViewById(R.id.layout_choose);
        layout_photo = (Button) view.findViewById(R.id.layout_photo);
        layout_cancel = (Button) view.findViewById(R.id.layout_cancel);
        layout_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ShowLog("点击拍照");
                layout_choose.setBackgroundColor(getResources().getColor(
                        R.color.base_color_text_white));
                layout_photo.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.pop_bg_press));
                File dir = new File(BmobConstants.GoodsPhotoDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(dir, new SimpleDateFormat("yyMMddHHmmss")
                        .format(new Date()));
                filePath = file.getAbsolutePath();// 获取照片的保存路径
                Uri imageUri = Uri.fromFile(file);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent,
                        BmobConstants.REQUESTCODE_UPLOADAVATAR_CAMERA);
            }
        });
        layout_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ShowLog("点击相册");
                layout_photo.setBackgroundColor(getResources().getColor(
                        R.color.base_color_text_white));
                layout_choose.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.pop_bg_press));
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent,
                        BmobConstants.REQUESTCODE_UPLOADAVATAR_LOCATION);
            }
        });
        layout_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                photoPop.dismiss();
            }
        });
        photoPop = new PopupWindow(view, mScreenWidth, 600);
        photoPop.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    photoPop.dismiss();
                    return true;
                }
                return false;
            }
        });
        photoPop.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        photoPop.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        photoPop.setTouchable(true);
        photoPop.setFocusable(true);
        photoPop.setOutsideTouchable(true);
        photoPop.setBackgroundDrawable(new BitmapDrawable());
        // 动画效果 从底部弹起
        photoPop.setAnimationStyle(R.style.Animations_GrowFromBottom);
        photoPop.showAtLocation(LayoutAll, Gravity.BOTTOM, 0, 0);
    }

    private void startImageAction(Uri uri, int outputX, int outputY,
                                  int requestCode, boolean isCrop) {
        Intent intent = null;
        if (isCrop) {
            intent = new Intent("com.android.camera.action.CROP");
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, requestCode);
    }

    boolean isFromCamera = false;// 区分拍照旋转
    int degree = 0;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case BmobConstants.REQUESTCODE_UPLOADAVATAR_CAMERA:
                if (resultCode == RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        ShowToast("SD卡不可用");
                        return;
                    }
                    isFromCamera = true;
                    File file = new File(filePath);
                    degree = PhotoUtil.readPictureDegree(file.getAbsolutePath());
                    Log.i("life", "拍照后的角度:" + degree);
                    startImageAction(Uri.fromFile(file), 200, 200,
                            BmobConstants.REQUESTCODE_UPLOADAVATAR_CROP, true);
                }
                break;
            case BmobConstants.REQUESTCODE_UPLOADAVATAR_LOCATION:// 本地添加图片
                if (photoPop != null) {
                    photoPop.dismiss();
                }
                Uri uri = null;
                if (data == null) {
                    return;
                }
                if (resultCode == RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        ShowToast("SD卡不可用");
                        return;
                    }
                    isFromCamera = false;
                    uri = data.getData();
                    startImageAction(uri, 200, 200,
                            BmobConstants.REQUESTCODE_UPLOADAVATAR_CROP, true);
                } else {
                    ShowToast("照片获取失败");
                }
                break;
            case BmobConstants.REQUESTCODE_UPLOADAVATAR_CROP://裁剪图片返回
                if (photoPop != null) {
                    photoPop.dismiss();
                }
                if (data == null) {
                    return;
                } else {
                    saveCropPhoto(data);
                }
                // 初始化文件路径
                filePath = "";
                break;
            default:
                break;

        }
    }

    String path;

    private void saveCropPhoto(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap bitmap = extras.getParcelable("data");
            Log.i("life", "avatar - bitmap = " + bitmap);
            if (bitmap != null) {
                bitmap = PhotoUtil.toRoundCorner(bitmap, 10);
                if (isFromCamera && degree != 0) {
                    bitmap = PhotoUtil.rotaingImageView(degree, bitmap);
                }
                Photo.setImageBitmap(bitmap);
                //  保存图片
                String filename = new SimpleDateFormat("yyMMddHHmmss")
                        .format(new Date())+".png";
                path = BmobConstants.GoodsPhotoDir + filename;
                PhotoUtil.saveBitmap(BmobConstants.GoodsPhotoDir, filename,
                        bitmap, true);
                // 上传头像
                if (bitmap != null && bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_photo:
                showPhotoPop();
                break;
            default:
                break;
        }
    }
}
