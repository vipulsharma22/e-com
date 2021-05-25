package com.nitsoft.ecommerce.api.user;

import com.nitsoft.ecommerce.api.APIName;
import com.nitsoft.ecommerce.api.controller.AbstractBaseController;
import com.nitsoft.ecommerce.api.response.model.APIResponse;
import com.nitsoft.ecommerce.api.response.util.APIStatus;
import com.nitsoft.ecommerce.database.model.UserAddress;
import com.nitsoft.ecommerce.exception.ApplicationException;
import com.nitsoft.ecommerce.service.UserAddressService;
import com.nitsoft.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping(APIName.ADDRESS)
public class UserAddressAPI extends AbstractBaseController {

    @Autowired
    private UserAddressService userAddressService;

    @RequestMapping(value = APIName.UPDATE, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> updateAddress(
            @RequestBody UserAddress userAddress,
            HttpServletRequest request) {
        String userId = getAuthUserFromSession(request).getId();
        UserAddress userAddressDB = userAddressService.getAddressByIdAndUserId(userAddress.getId(),userId);
        if(userAddressDB != null){
            if(userAddress.getDeleted() == 0){
                userAddress.setDeleted(0);
            }else{
                userAddressDB.setCity(userAddress.getCity());
                userAddressDB.setPhone(userAddress.getPhone());
                userAddressDB.setAddress(userAddress.getAddress());
                userAddressDB.setLandMark(userAddress.getLandMark());
                userAddressDB.setDeleted(0);
                userAddressDB.setPinCode(userAddress.getPinCode());
                userAddressDB.setUpdatedAt(new Date());
            }
            userAddressService.save(userAddressDB);
        }else{
            throw new ApplicationException(APIStatus.USER_ADDRESS_NOT_FOUND);
        }
        return responseUtil.successResponse(userAddressDB);
    }


    @RequestMapping(value = APIName.SAVE, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> saveAddress(
            @RequestBody UserAddress userAddress,
            HttpServletRequest request) {
        String userId = getAuthUserFromSession(request).getId();
        Date date = new Date();
        userAddress.setUserId(userId);
        userAddress.setCity(userAddress.getCity());
        userAddress.setPhone(userAddress.getPhone());
        userAddress.setAddress(userAddress.getAddress());
        userAddress.setLandMark(userAddress.getLandMark());
        userAddress.setPinCode(userAddress.getPinCode());
        userAddress.setDeleted(0);
        userAddress.setUpdatedAt(date);
        userAddress.setCreatedAt(date);
        userAddress = userAddressService.save(userAddress);
        return responseUtil.successResponse(userAddress);
    }
}
