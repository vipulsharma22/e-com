package com.nitsoft.ecommerce.validators;

import com.nitsoft.ecommerce.api.request.model.AuthRequestModel;
import com.nitsoft.ecommerce.api.request.model.UserRequestModel;
import com.nitsoft.ecommerce.api.response.util.APIStatus;
import com.nitsoft.ecommerce.exception.ApplicationException;
import com.nitsoft.util.EmailUtil;
import org.apache.commons.lang.StringUtils;

public class UserValidator {

    private UserValidator() {
    }

    public static void registerUser(UserRequestModel user) {
        if (user == null || StringUtils.isEmpty(user.getPhone())
                || StringUtils.isEmpty(user.getEmail()) || user.getOtp() == null) {
            throw new ApplicationException(APIStatus.ERR_INVALID_DATA);
        }

        if (user.getPhone().length() != 10 || StringUtils.isAlpha(user.getPhone())) {
            throw new ApplicationException(APIStatus.ERR_INVALID_DATA);
        }

        if (!EmailUtil.isEmailFormat(user.getEmail())) {
            throw new ApplicationException(APIStatus.ERR_INVALID_DATA);

        }
    }

    public static void login(AuthRequestModel user) {
        if(StringUtils.isEmpty(user.getUserName()) && StringUtils.isEmpty(user.getPhone())){
            throw new ApplicationException(APIStatus.ERR_INVALID_DATA);
        }
        if ((user.getOtp() == null && user.getPassword() ==null)) {
            throw new ApplicationException(APIStatus.ERR_INVALID_DATA);
        }

        if(user.getUserName() == null && user.getPhone() == null){
            throw new ApplicationException(APIStatus.ERR_INVALID_DATA);
        }

        if (user.getPhone()!=null && user.getPhone().length() != 10 || StringUtils.isAlpha(user.getPhone())) {
            throw new ApplicationException(APIStatus.ERR_INVALID_DATA);
        }
    }
}
