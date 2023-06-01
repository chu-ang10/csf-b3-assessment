package ibf2022.batch3.assessment.csf.orderbackend.controllers;

import java.util.ArrayList;
import java.util.List;

import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;
import ibf2022.batch3.assessment.csf.orderbackend.services.OrderException;
import ibf2022.batch3.assessment.csf.orderbackend.services.OrderingService;
import jakarta.json.Json;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class OrderController {

	@Autowired
	private OrderingService orderSvc;

	// TODO: Task 3 - POST /api/order

	@PostMapping(path = "/api/order", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> createOrder(@RequestPart String name, @RequestPart String email,
			@RequestPart Integer pizzaSize, @RequestPart String sauce, @RequestPart List<String> toppings,
			@RequestPart String comments) {

		PizzaOrder order = new PizzaOrder();
		order.setName(name);
		order.setEmail(email);
		order.setSize(pizzaSize);
		order.setSauce(sauce);
		order.setTopplings(toppings);
		order.setComments(comments);

		try {
			order = orderSvc.placeOrder(order);
			return ResponseEntity.accepted()
					.body(
							Json.createObjectBuilder()
									.add("orderId", order.getOrderId())
									.add("date", order.getDate().getTime())
									.add("name", order.getName())
									.add("email", order.getEmail())
									.add("total", order.getTotal())
									.build()
									.toString());
		} catch (OrderException e) {
			// error: bad request, and a json object with an "error" key and error message
			// value
			return ResponseEntity.badRequest().body(
					Json.createObjectBuilder()
							.add("error", e.getMessage())
							.build()
							.toString());
		}
	}

	// TODO: Task 6 - GET /api/orders/<email>

	@GetMapping(value = "/api/orders/<email>")
	public ResponseEntity<String> getOrders(@RequestParam String email) {
		List<PizzaOrder> orders = orderSvc.getPendingOrdersByEmail(email);
		List<JsonObject> madeOrders = new ArrayList<>();
		for (PizzaOrder order : orders) {
			madeOrders.add(
					Json.createObjectBuilder()
							.add("orderId", order.getOrderId())
							.add("date", order.getDate().getTime())
							.add("total", order.getTotal())
							.build());
		}
		return ResponseEntity.ok()
				.body(Json.createArrayBuilder(orders).build().toString());
	}

	// TODO: Task 7 - DELETE /api/order/<orderId>

	@DeleteMapping(value = "/api/order/<orderId>")
	public ResponseEntity<String> deleteOrder(@RequestParam String orderId) {
		boolean didOrderDelivered = orderSvc.markOrderDelivered(orderId);
		if (!didOrderDelivered) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}

}
