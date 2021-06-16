package com.nitsoft.ecommerce.service.orders;

import com.nitsoft.ecommerce.api.request.model.InitPaymentResponse;
import com.nitsoft.ecommerce.api.request.model.PaymentRequest;
import com.nitsoft.ecommerce.database.model.entity.Orders;
import com.nitsoft.ecommerce.database.model.entity.Payments;
import com.nitsoft.ecommerce.database.model.entity.Refunds;
import com.nitsoft.ecommerce.database.model.entity.User;
import com.nitsoft.ecommerce.repository.OrdersRepository;
import com.nitsoft.ecommerce.repository.PaymentRepository;
import com.nitsoft.ecommerce.repository.RefundsRepository;
import com.nitsoft.util.Constant;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Refund;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private RazorpayClient razorpayClient;

    @Autowired
    private RefundsRepository refundsRepository;

    public InitPaymentResponse initPayment(PaymentRequest initPaymentRequest) throws Exception{
        User user = (User)httpServletRequest.getAttribute("user");
        Payments payments = new Payments();
        payments.setAmount(BigDecimal.valueOf(initPaymentRequest.getAmount()));
        payments.setPgProvider(Constant.PG_PROVIDER.RAZOR_PAY.name());
        payments.setPlatform(Constant.PLATFORM.WEB.name());
        payments.setUserId(String.valueOf(user.getId()));
        payments.setStatus(Constant.PAYMENT_STATUS.INITIATED.name());
        payments = paymentRepository.save(payments);
        Orders orders = ordersRepository.findById(Long.valueOf(initPaymentRequest.getOrderId())).get();
        if(orders.getPaymentPartnerOrderId().equals(initPaymentRequest.getOrderId()) && 0 == orders.getFinalAmount().compareTo(BigDecimal.valueOf(initPaymentRequest.getAmount()))){
            orders.setStatus(Constant.ORDER_STATUS.PAYMENT_INITIATED.name());
            orders.setPaymentId(payments.getId());
            orders.setPaymentPartnerOrderId(createRazorPayOrder(orders).get("id"));
        }
        ordersRepository.save(orders);
        return InitPaymentResponse.of(orders.getId(),orders.getPaymentId(),orders.getPaymentPartnerOrderId());
    }


    public void completePayment(PaymentRequest initPaymentRequest){
        User user = (User)httpServletRequest.getAttribute("user");
        Payments payments = paymentRepository.findById(initPaymentRequest.getPaymentId()).get();
        payments.setStatus(Constant.PAYMENT_STATUS.AUTHORIZED.name());
        paymentRepository.save(payments);
        Orders orders = ordersRepository.findById(Long.valueOf(initPaymentRequest.getOrderId())).get();
        orders.setStatus(Constant.ORDER_STATUS.PAYMENT_COMPLETED.name());
        ordersRepository.save(orders);
    }

    public void refundPayment(Long orderId){
        try{
            Orders orders = ordersRepository.findById(Long.valueOf(orderId)).get();
            Payments payment = paymentRepository.findById(orders.getPaymentId()).get();
            Refund refund = razorpayClient.Payments.refund(payment.getPgTransactionId());
            String status = refund.get("status");
            if("pending".equals(status)){
                payment.setStatus(Constant.PAYMENT_STATUS.REFUND_INITIATED.name());
            }else{
                payment.setStatus(Constant.PAYMENT_STATUS.REFUNDED.name());
            }
            paymentRepository.save(payment);
            Refunds refunds = new Refunds();
            refunds.setPaymentId(payment.getId());
            refunds.setAmount(payment.getAmount());
            refunds.setGatewayRefundResponse(refund.toString());
            refunds.setRefundId(refund.get("id"));
            refunds.setStatus(status);
            refundsRepository.save(refunds);
            orders.setStatus(Constant.ORDER_STATUS.CANCELLED.name());
            ordersRepository.save(orders);
        }catch (Exception ex){

        }

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
}
