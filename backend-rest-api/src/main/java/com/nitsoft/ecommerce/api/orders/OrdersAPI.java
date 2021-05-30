package com.nitsoft.ecommerce.api.orders;

import com.nitsoft.ecommerce.api.APIName;
import com.nitsoft.ecommerce.api.AbstractBaseAPI;
import com.nitsoft.ecommerce.api.request.model.OrderRequestModel;
import com.nitsoft.ecommerce.api.request.model.ProductInfo;
import com.nitsoft.ecommerce.api.response.model.APIResponse;
import com.nitsoft.ecommerce.api.response.model.StatusResponse;
import com.nitsoft.ecommerce.api.response.util.ResponseUtil;
import com.nitsoft.ecommerce.database.model.*;
import com.nitsoft.ecommerce.service.UserService;
import com.nitsoft.ecommerce.service.OrdersService;
import com.nitsoft.ecommerce.service.UserAddressService;
import com.nitsoft.ecommerce.service.UserTokenService;
import com.nitsoft.ecommerce.service.orders.OrderAddressImpl;
import com.nitsoft.ecommerce.service.orders.OrderDetailImpl;
import com.nitsoft.ecommerce.service.product.ProductService;
import com.nitsoft.util.Constant;
import io.swagger.annotations.ApiOperation;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author NHU LINH
 */
@RestController
@RequestMapping(value = APIName.ORDERS)
public class OrdersAPI extends AbstractBaseAPI {

    @Autowired
    OrdersService ordersService;
    @Autowired
    UserService customerService;
    @Autowired
    UserTokenService userTokenService;
    @Autowired
    UserAddressService userAddressService;
    @Autowired
    OrderAddressImpl orderAddressImpl;
    @Autowired
    ProductService productService;
    @Autowired
    OrderDetailImpl orderDetailImpl;
    @Autowired
    ResponseUtil responseUtil;

    @RequestMapping(value = APIName.ORDER_CREATE, method = RequestMethod.POST, produces = APIName.CHARSET)
    @ResponseBody
    public ResponseEntity<APIResponse> addOrders(
            @RequestBody OrderRequestModel orderRequest) {

        Date createDate = new Date();

        //Crerate User address
        UserAddress userAddress = null;
        userAddress = userAddressService.getAddressByIdAndUserId(orderRequest.getAddressId(), 1l);// pass userId here
        OrderAddress orderAddress = saveOrderAddress(userAddress);
        //Create Order General Info
        Orders orders = new Orders();
        orders.setUserId(1l);
        orders.setOrderAddressId(orderAddress.getId());
        orders.setCompanyId(1L);
        orders.setCustomerEmail(orderRequest.getUser().getEmail());
        orders.setStatus(Constant.ORDER_STATUS.PENDING.name());
        orders.setItemsCount(orderRequest.getProductList().size());
        orders.setItemsQuantity(orderRequest.getProductList()
                .stream().map(t -> t.getQuantity())
                .reduce((q1,q2) -> q1+q2).get());
        orders.setCreatedAt(createDate);
        orders.setUpdatedAt(createDate);
        ordersService.save(orders);

        if (orderRequest.getProductList().size() > 0) {
            for (ProductInfo productInfo : orderRequest.getProductList()) {
                Product product = productService.getProductById(company_id, productInfo.getProductId());
                if (product != null) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrderId(orders.getId());
                    orderDetail.setProductId(product.getProductId());
                    orderDetail.setName(product.getName());
                    orderDetail.setPrice(product.getSalePrice());
                    orderDetail.setQuantity(productInfo.getQuantity());
                    orderDetail.setCreatedAt(createDate);
                    orderDetailImpl.saveOrUpdate(orderDetail);
                }
            }
        }
        return responseUtil.successResponse(orders);
    }

    @ApiOperation(value = "get orders by company id", notes = "")
    @RequestMapping(value = APIName.ORDERS_BY_COMPANY, method = RequestMethod.GET, produces = APIName.CHARSET)
    public String getOrdersCompanyId(
            @PathVariable(value = "id") Long companyId,
            @RequestParam(defaultValue = Constant.DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(defaultValue = Constant.DEFAULT_PAGE_SIZE, required = false) int pageSize) {

        //http://localhost:8080/api/orders/1?pagenumber=1&pagesize=2
        Page<Orders> orders = ordersService.findAllByCompanyId(companyId, pageNumber, pageSize);
        return writeObjectToJson(new StatusResponse(200, orders.getContent(), orders.getTotalElements()));

    }

    private OrderAddress saveOrderAddress(UserAddress userAddress){
        OrderAddress orderAddress = new OrderAddress();
        orderAddress.setAddress(userAddress.getAddress());
        orderAddress.setCustomerName(userAddress.getUserName());
        orderAddress.setCity(userAddress.getCity());
        orderAddress.setLandMark(userAddress.getLandMark());
        orderAddress.setPhone(userAddress.getPhone());
        orderAddress.setPinCode(userAddress.getPinCode());
        return orderAddressImpl.saveOrUpdate(orderAddress);
    }

    private List<Order>
}
