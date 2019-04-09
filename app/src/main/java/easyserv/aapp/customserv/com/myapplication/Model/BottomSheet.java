package easyserv.aapp.customserv.com.myapplication.Model;

import easyserv.aapp.customserv.com.myapplication.R;

public class BottomSheet {

    public enum BottomSheetMenuType {
        EMAIL(R.drawable.ic_contact_phone, "Mail"), ACCOUNT(R.drawable.ic_close,
                "Acount"), SETTING(R.drawable.ic_email, "Setitng");

        int resId;

        String name;

        BottomSheetMenuType(int resId, String name) {
            this.resId = resId;
            this.name = name;
        }

        public int getResId() {
            return resId;
        }

        public String getName() {
            return name;
        }
    }

    BottomSheetMenuType bottomSheetMenuType;

    public static BottomSheet to() {
        return new BottomSheet();
    }

    public BottomSheetMenuType getBottomSheetMenuType() {
        return bottomSheetMenuType;
    }

    public BottomSheet setBottomSheetMenuType(BottomSheetMenuType bottomSheetMenuType) {
        this.bottomSheetMenuType = bottomSheetMenuType;
        return this;
    }
}