package ibf2022.batch3.assessment.csf.orderbackend.respositories;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;

public class PendingOrdersRepository {
	@Autowired
	private RedisTemplate<String, String> template;

	// TODO: Task 3
	// WARNING: Do not change the method's signature.
	public void add(PizzaOrder order) {
		JSONObject pendingOrder = new JSONObject();
		pendingOrder.put("orderId", order.getOrderId());
		pendingOrder.put("date", order.getDate());
		pendingOrder.put("total", order.getTotal());
		pendingOrder.put("name", order.getName());
		pendingOrder.put("email", order.getEmail());

		template.opsForValue().set(order.getOrderId(), pendingOrder.toString());
	}

	// TODO: Task 7
	// WARNING: Do not change the method's signature.
	public boolean delete(String orderId) {
		return template.delete(orderId);
	}

}
