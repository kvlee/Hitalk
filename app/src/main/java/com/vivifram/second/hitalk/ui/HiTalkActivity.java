package com.vivifram.second.hitalk.ui;

import android.os.Bundle;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.CountCallback;
import com.vivifram.second.hitalk.R;
import com.vivifram.second.hitalk.base.BaseActivity;
import com.vivifram.second.hitalk.base.EatMark;
import com.vivifram.second.hitalk.base.LayoutInject;
import com.vivifram.second.hitalk.manager.chat.ClientManager;
import com.vivifram.second.hitalk.manager.chat.FriendsManager;
import com.vivifram.second.hitalk.ui.layout.HiTalkLayout;
import com.zuowei.utils.bridge.constant.EaterAction;
import com.zuowei.utils.bridge.params.LightParam;
import com.zuowei.utils.bridge.params.chat.ClientEventParam;
import com.zuowei.utils.bridge.params.chat.ConnectChangedParam;
import com.zuowei.utils.bridge.params.push.InvitationParam;
import com.zuowei.utils.common.NToast;
import com.zuowei.utils.handlers.AbstractHandler;
import com.zuowei.utils.helper.HiTalkHelper;
import com.zuowei.utils.helper.UserCacheHelper;

import cn.bingoogolapple.badgeview.BGABadgeRadioButton;

@LayoutInject(name = "HiTalkLayout")
public class HiTalkActivity extends BaseActivity<HiTalkLayout> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        saveBaseInfo();
        refresh();
    }

    private void saveBaseInfo() {
        UserCacheHelper.getInstance().cacheUser(HiTalkHelper.getInstance().getCurrentUser());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    //
    @EatMark(action = EaterAction.ACTION_DO_CHECK_CLIENT)
    public class ClientProxy extends AbstractHandler<ClientEventParam>{

        @Override
        public boolean isParamAvailable(LightParam param) {
            return param != null && param instanceof ClientEventParam;
        }

        @Override
        public void doJobWithParam(ClientEventParam param) {
            switch (param.getActionType()){
                case ClientEventParam.ACTION_CONNECT_CHANGED:
                        ConnectChangedParam connectChangedParam = (ConnectChangedParam) param;
                        if (connectChangedParam.mConnected){
                            if (!ClientManager.getInstance().isOpend()){
                                tryReOpenClient();
                            }
                        }else {
                            NToast.shortToast(mAppCtx,getString(R.string.internet_not_connect_warn));
                        }
                    break;
            }
        }
    }

    @EatMark(action = EaterAction.ACTION_DO_INVITATION)
    public class InvitateListener extends AbstractHandler<InvitationParam>{

        @Override
        public boolean isParamAvailable(LightParam param) {
            return param != null && param instanceof InvitationParam;
        }

        @Override
        public void doJobWithParam(InvitationParam param) {
            FriendsManager.getInstance().unreadRequestsIncrement();
            updateNewRequestBadge();
        }
    }

    private void updateNewRequestBadge() {
        BGABadgeRadioButton radioButton = mLayout.getRadioBtn(2);
        if (FriendsManager.getInstance().hasUnreadRequests()) {
            radioButton.showCirclePointBadge();
        }else {
            radioButton.hiddenBadge();
        }
    }

    private void refresh() {
        FriendsManager.getInstance().countUnreadRequests(new CountCallback() {
            @Override
            public void done(int i, AVException e) {
                updateNewRequestBadge();
            }
        });
    }

    private void tryReOpenClient() {
        ClientManager.getInstance().open(HiTalkHelper.getInstance().getCurrentUserId(),null);
    }

}
