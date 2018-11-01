package com.domain.test.contactmalapplication.models;

import android.text.TextUtils;

public class ContactDetail {


    private String contactName;
    private String contactNum;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        if(!TextUtils.isEmpty(contactName)){
            this.contactName = contactName;
        }
        else {
            this.contactName="defaultname";
        }

    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {

        if(!TextUtils.isEmpty(contactNum)){
            this.contactNum = contactNum;
        }
        else {
            this.contactNum="defaultnumber";
        }

    }
}
