package ibf2022.batch3.assessment.csf.orderbackend.respositories;

import java.util.ArrayList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Query;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;

@Repository
public class OrdersRepository {

	@Autowired
	private MongoTemplate mongo;

	// TODO: Task 3
	// WARNING: Do not change the method's signature.
	// Write the native MongoDB query in the comment below
	// Native MongoDB query here for add()
	public void add(PizzaOrder order) {
		Document d = Document.parse(order.toJsonObject().toString());
		mongo.insert(d, "order");

	}

	// TODO: Task 6
	// WARNING: Do not change the method's signature.
	// Write the native MongoDB query in the comment below
	// Native MongoDB query here for getPendingOrdersByEmail()
	public List<PizzaOrder> getPendingOrdersByEmail(String email) {
		Query query = Query.query(Criteria.where("email").is(email).sorted("date", Sort.Direction.DESC));
		List<Document> orders = mongo.find(query, Document.class, "orders");
		List<PizzaOrder> pizzaOrders = new ArrayList<>();
		for (Document d : orders) {
			PizzaOrder order = new PizzaOrder();
			order.setOrderId(d.getString("orderId"));
			order.setTotal(d.getDouble("total").floatValue());
			order.setName(d.getString("name"));
			pizzaOrders.add(order);
		}
		return pizzaOrders;
	}

	// TODO: Task 7
	// WARNING: Do not change the method's signature.
	// Write the native MongoDB query in the comment below
	// Native MongoDB query here for markOrderDelivered()
	public boolean markOrderDelivered(String orderId) {
		Query query = Query.query(Criteria.where("orderId").is(orderId));
		Document d = mongo.findOne(query, Document.class, "orders");
		if (d != null) {
			d.put("delivered", true);
			mongo.save(d, "orders");
			return true;
		}
		return false;
	}

	public JsonObject toJsonObject() {

		JsonArrayBuilder jsArr = Json.createArrayBuilder();

		return Json.createObjectBuilder()
				.add("orderId", orderId)
				.add("name", name)
				.add("email", email)
				.add("sauce", sauce)
				.add("size", size)
				.add("sauce", sauce)
				.add("thickCrust", thickCrust)
				.add("toppings", toppings[])
				.add("comments", comments == null ? "" : comments)
				.add("total", total)
				.build();


}
}
