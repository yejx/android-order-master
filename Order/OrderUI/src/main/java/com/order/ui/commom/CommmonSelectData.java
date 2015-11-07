package com.order.ui.commom;

import java.io.Serializable;

/**
 * Created by yjx on 14-1-17.
 */
public class CommmonSelectData implements Serializable{


    /** item的id */
    private String  itemId = "";

    /** item的type */
    private String  type = "";

    /***  是否被选中     */
    private boolean isSelected = false;

    /***  是否可合并     0 不可合并 1 可合并    */
    private  String  mergeFlag = "";

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setMergeFlag(String mergeFlag) {
        this.mergeFlag = mergeFlag;
    }

    public String getMergeFlag() {
        return mergeFlag;
    }

    /***  是否支持多选     */
    private boolean isSurpportMutiSelect = false;

    public void setSurpportMutiSelect(boolean isSurpportMutiSelect) {
        this.isSurpportMutiSelect = isSurpportMutiSelect;
    }

    public boolean isSurpportMutiSelect() {
        return isSurpportMutiSelect;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    /**
     * 左侧图片
     */
    private String LeftIcon = "";

    public void setLeftIcon(String leftIcon) {
        LeftIcon = leftIcon;
    }

    public String getLeftIcon() {
        return LeftIcon;
    }

    /**
     * 第一行左侧文字
      */
    private String leftTopText = "";

    public void setLeftTopText(String leftTopText) {
        this.leftTopText = leftTopText;
    }

    public String getLeftTopText() {
        return leftTopText;
    }

    /** 第二行左侧文字 */
    private String leftBottomText = "";

    public void setLeftBottomText(String leftBottomText) {
        this.leftBottomText = leftBottomText;
    }

    public String getLeftBottomText() {
        return leftBottomText;
    }

    /* *
             *  右侧文字
            */
    private String rightText = "";

    public void setRightText(String rightText) {
        this.rightText = rightText;
    }

    public String getRightText() {
        return rightText;
    }


    /* *
        *
        * 中间文字    */
    private String   centerText = "";

    public void setCenterText(String centerText) {
        this.centerText = centerText;
    }

    public String getCenterText() {
        return centerText;
    }

    /* *
     *  金额
     */
    private String amount = "";

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    /**
     * item输入hint
     */
    private String itemHint = "";

    public void setItemHint(String itemHint) {
        this.itemHint = itemHint;
    }

    public String getItemHint() {
        return itemHint;
    }

    /**
     * item标识--输入类型
     */
    private String itemFlag= "";

    public void setItemFlag(String itemFlag) {
        this.itemFlag = itemFlag;
    }

    public String getItemFlag() {
        return itemFlag;
    }
}
