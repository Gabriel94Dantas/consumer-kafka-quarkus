package org.acme.daos;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.acme.contexts.MongoContext;
import org.acme.converters.EventConverter;
import org.acme.converters.OrderConverter;
import org.acme.models.Order;
import org.bson.Document;

public class OrderDao {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public OrderDao(){
        MongoContext mongoContext = new MongoContext();
        this.mongoClient = mongoContext.getConnection();
        this.database = this.mongoClient.getDatabase("marketplaceDB");
        this.collection = this.database.getCollection("orders");
    }

    public void insert(Order order){
        Gson gson = new Gson();
        Document document = gson.fromJson(OrderConverter.orderToJson(order), Document.class);
        collection.insertOne(document);
    }

    public Order returnOrderByClient(String clientId){
        Gson gson = new Gson();
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("clientId", clientId);

        Document documentReturned = collection.find(whereQuery).first();

        if (documentReturned != null){
            Order orderReturned = OrderConverter.jsonToOrder(gson.toJson(documentReturned));
            return orderReturned;
        }else{
            return null;
        }
    }

    public void update(Order order){
        Gson gson = new Gson();

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("id", order.getId());

        Document document = gson.fromJson(OrderConverter.orderToJson(order), Document.class);

        BasicDBObject updateDocument = new BasicDBObject();
        updateDocument.put("$set", document);

        collection.updateOne(whereQuery, updateDocument);
    }

    public void delete(Order order){
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("id", order.getId());
        collection.deleteOne(whereQuery);
    }
}
