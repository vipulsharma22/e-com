package com.nitsoft.ecommerce.enums;

import org.apache.commons.lang3.StringUtils;

public enum PickrrStatusEnum {
    OP("OP","Order Placed"),
    OM("OM","Order Manifested"),
    OC("OC","Order Cancelled"),
    PP("PP","Order Picked Up"),
    OD("OD","Order Dispatched"),
    OT("OT","Order in Transit"),
    OO("OO","Order Out for Delivery"),
    NDR("NDR","Failed Attempt at Delivery"),
    DL("DL","Order Delivered"),
    RTO("RTO","Order Returned Back"),
    RTO_OT("RTO-OT","RTO in Transit"),
    RTO_OO("RTO-OO",	"RTO out for delivery"),
    RTP	("RTP","RTO Reached Pickrr Warehouse"),
    RTD("RTD","Order Returned to Consignee");

    private final String key;
    private final String value;

    PickrrStatusEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static String getValueByKey(String key) {
        if (StringUtils.isEmpty(key))
            return null;
        for (PickrrStatusEnum statusEnum : PickrrStatusEnum.values()) {
            if (key.equalsIgnoreCase(statusEnum.getKey()))
                return statusEnum.getValue();
        }
        return null;
    }
}
