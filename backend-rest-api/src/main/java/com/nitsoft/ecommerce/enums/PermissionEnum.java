package com.nitsoft.ecommerce.enums;

import com.nitsoft.util.Constant;

public enum PermissionEnum {
    PRODUCT_CRUD(Constant.USER_ROLE.SYS_ADMIN),
    CHECKOUT(Constant.USER_ROLE.NORMAL_USER),
    GUEST(Constant.USER_ROLE.GUEST);

    PermissionEnum(Constant.USER_ROLE role) {
        this.role = role;
    }

    private Constant.USER_ROLE role;

    public Constant.USER_ROLE getRole() {
        return role;
    }
}
