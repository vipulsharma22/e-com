package com.nitsoft.ecommerce.api.orders;

import com.nitsoft.ecommerce.api.APIName;
import com.nitsoft.ecommerce.api.AbstractBaseAPI;
import com.nitsoft.ecommerce.api.request.model.OrderRequestModel;
import com.nitsoft.ecommerce.api.request.model.PaymentRequest;
import com.nitsoft.ecommerce.api.response.model.APIResponse;
import com.nitsoft.ecommerce.api.response.model.OrderResponseModel;
import com.nitsoft.ecommerce.api.response.model.StatusResponse;
import com.nitsoft.ecommerce.api.response.util.ResponseUtil;
import com.nitsoft.ecommerce.database.model.entity.*;
import com.nitsoft.ecommerce.repository.OrdersRepository;
import com.nitsoft.ecommerce.service.UserService;
import com.nitsoft.ecommerce.service.OrdersService;
import com.nitsoft.ecommerce.service.UserAddressService;
import com.nitsoft.ecommerce.service.UserTokenService;
import com.nitsoft.ecommerce.service.orders.OrderAddressImpl;
import com.nitsoft.ecommerce.service.orders.OrderDetailImpl;
import com.nitsoft.ecommerce.service.orders.PaymentService;
import com.nitsoft.ecommerce.service.product.ProductService;
import com.nitsoft.util.Constant;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import io.swagger.annotations.ApiOperation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    RazorpayClient razorpayClient;
    @Autowired
    PaymentService paymentService;
    @Autowired
    OrdersRepository ordersRepository;

    @RequestMapping(value = APIName.ORDER_CREATE, method = RequestMethod.POST, produces = APIName.CHARSET)
    @ResponseBody
    public ResponseEntity<APIResponse> addOrders(
            @RequestBody OrderRequestModel orderRequest) throws Exception{

        Date createDate = new Date();
        Orders orders = new Orders();
        //Crerate User address
        UserAddress userAddress = null;
        User user = (User)httpServletRequest.getAttribute("user");
        if(orderRequest.getAddressId() != null){
            userAddress = userAddressService.getAddressByIdAndUserId(orderRequest.getAddressId(), user.getId());
            OrderAddress orderAddress = saveOrderAddress(userAddress);
            orders.setOrderAddressId(orderAddress.getId());
        }else{
            orders.setOrderAddressId(1L);//todo: get last ordered address
        }
        //Create Order General Info
        orders.setUserId(user.getId());
        orders.setCompanyId(1L);
        orders.setCustomerEmail(user.getEmail());
        orders.setStatus(Constant.ORDER_STATUS.ORDER_INITIATED.name());
        orders.setItemsCount(orderRequest.getProductList().size());
        orders.setDeliveryCharge(BigDecimal.ONE);//todo: fetch delivery charge
        orders.setTotalItemsCost(orderRequest.getTotalItemsCost());
        orders.setFinalAmount(orders.getTotalItemsCost().add(orders.getDeliveryCharge()));
        orders.setItemsQuantity(orderRequest.getProductList()
                .stream().map(t -> t.getQuantity())
                .reduce((q1,q2) -> q1+q2).get());
        orders.setCreatedAt(createDate);
        orders.setUpdatedAt(createDate);
        orders = ordersService.save(orders);
        List<OrderDetail> orderDetails = new ArrayList<>();
        if (orderRequest.getProductList().size() > 0) {
            for (OrderDetail orderDetailRequest : orderRequest.getProductList()) {
                Product product = productService.getProductById(1,orderDetailRequest.getProductId());
                if (product != null) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrderId(orders.getId());
                    orderDetail.setProductId(product.getId());
                    orderDetail.setName(product.getName());
                    orderDetail.setDescription(product.getDescription());
                    orderDetail.setSalePrice(product.getSalePrice());
                    orderDetail.setListPrice(product.getListPrice());
                    orderDetail.setQuantity(orderDetailRequest.getQuantity());
                    orderDetail.setCreatedAt(createDate);
                    orderDetail.setUpdatedAt(createDate);
                    orderDetails.add(orderDetailImpl.saveOrUpdate(orderDetail));
                }
            }
        }
        OrderResponseModel orderResponseModel = new OrderResponseModel();
        orderResponseModel.setOrders(orders);
        orderResponseModel.setProductList(orderDetails);
        orderResponseModel.setUserAddress(userAddress);
        return responseUtil.successResponse(orders);
    }

    private Order createRazorPayOrder(Orders order) throws RazorpayException {
        JSONObject options = new JSONObject();
        options.put("amount", convertRupeeToPaise(order.getFinalAmount()));
        options.put("currency", "INR");
        options.put("receipt", order.getId());
        return razorpayClient.Orders.create(options);
    }

    private String convertRupeeToPaise(BigDecimal paise) {
        BigDecimal value = paise.multiply(new BigDecimal("100"));
        return value.setScale(0, RoundingMode.UP).toString();
    }


    @ApiOperation(value = "get orders", notes = "")
    @RequestMapping(value = APIName.INITIATE_PAYMENT, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> initPayment(PaymentRequest initPaymentRequest) throws Exception {
        paymentService.initPayment(initPaymentRequest);
        return responseUtil.successResponse("SUCCESS");
    }

    @RequestMapping(value = APIName.COMPLETE_PAYMENT, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> completePayment(PaymentRequest paymentRequest) {
        paymentService.completePayment(paymentRequest);
        return responseUtil.successResponse("SUCCESS");
    }

    @RequestMapping(value = APIName.CANCEL_ORDER, method = RequestMethod.GET, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> cancelOrder(@RequestParam long orderId) {
        Orders orders = ordersRepository.findById(orderId).get();
        orders.setStatus(Constant.ORDER_STATUS.IN_CANCELLATION.name());
        return responseUtil.successResponse("SUCCESS");
    }

    @RequestMapping(value = APIName.ORDER_GET, method = RequestMethod.GET, produces = APIName.CHARSET)
    public String getOrdersCompanyId(
            @RequestParam(defaultValue = Constant.DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(defaultValue = Constant.DEFAULT_PAGE_SIZE, required = false) int pageSize) {
        User user = (User)httpServletRequest.getAttribute("user");
        //http://localhost:8080/api/orders/1?pagenumber=1&pagesize=2
        Page<Orders> orders = ordersService.findAllByUserId(user.getId(), pageNumber, pageSize);
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
}
