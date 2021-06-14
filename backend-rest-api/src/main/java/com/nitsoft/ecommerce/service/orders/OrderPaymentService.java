
package com.nitsoft.ecommerce.service.orders;

import com.nitsoft.ecommerce.database.model.entity.OrderPayment;

/**
 *
 * @author Louis Duong
 */
public interface OrderPaymentService {
    public OrderPayment getOrderPaymentByOrderId(Long orderId);
}
