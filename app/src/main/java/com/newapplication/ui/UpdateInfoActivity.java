package com.newapplication.ui;

import android.os.Bundle;
import android.widget.EditText;
import cn.bmob.v3.listener.UpdateListener;

import com.newapplication.R;
import com.newapplication.bean.User;
import com.newapplication.view.HeaderLayout.onRightImageButtonClickListener;

public class UpdateInfoActivity extends ActivityBase {

	EditText edit_nick;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_updateinfo);
		initView();
	}

	private void initView() {
		initTopBarForBoth("修改昵称", R.drawable.base_action_bar_true_bg_selector,
				new onRightImageButtonClickListener() {
					@Override
					public void onClick() {
						String nick = edit_nick.getText().toString();
						if (nick.equals("")) {
							ShowToast("请填写昵称!");
							return;
						}
						updateInfo(nick);
					}
				});
		edit_nick = (EditText) findViewById(R.id.edit_nick);
	}

	/**
	 * 修改资料
	 */
	private void updateInfo(String nick) {
		final User user = userManager.getCurrentUser(User.class);
		User u = new User();
		u.setNick(nick);
		u.setObjectId(user.getObjectId());
		u.update(this, new UpdateListener() {
			@Override
			public void onSuccess() {
				final User c = userManager.getCurrentUser(User.class);
				ShowToast("修改成功:"+c.getNick());
				finish();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				ShowToast("onFailure:" + arg1);
			}
		});
	}
}
