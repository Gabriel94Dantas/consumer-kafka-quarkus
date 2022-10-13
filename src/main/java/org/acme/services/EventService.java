package org.acme.services;

import org.acme.constants.EventConstant;
import org.acme.contexts.KafkaContext;
import org.acme.converters.EventConverter;
import org.acme.converters.ProductConverter;
import org.acme.models.Event;
import org.acme.models.Product;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;

public class EventService {

    private KafkaContext kafkaContext;
    private Consumer<String, String> consumer;

    private OrderService orderService;

    public EventService(){
        this.kafkaContext = new KafkaContext();
        this.consumer = new KafkaConsumer<String, String>(this.kafkaContext.getProperties());
        this.consumer.subscribe(Arrays.asList(this.kafkaContext.getTopic()));
        this.orderService = new OrderService();
    }

    public void getMessages(){
        try {
            while (true){
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    Event event = EventConverter.jsonToEvent(record.value());
                    if(event.getSubject().equals(EventConstant.ADD_TO_CART)){
                        addToCartTreatment(event);
                    } else if(event.getSubject().equals(EventConstant.UPDATE_QUANTITY)){
                        updateQuantityTreatment(event);
                    } else if (event.getSubject().equals(EventConstant.REMOVE_TO_CART)){
                        removeFromCartTreatment(event);
                    } else if(event.getSubject().equals(EventConstant.ORDER_CLOSED)){
                        orderClosedTreatment(event);
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }

    public void addToCartTreatment(Event event){
        this.orderService.addToCart(ProductConverter
                        .jsonToProduct(event.getData()
                                .getAsJsonObject("product")
                                .toString()),
                event.getData().get("clientId").getAsString());
    }

    public void updateQuantityTreatment(Event event){
        this.orderService.updateQuantity(ProductConverter
                .jsonToProduct(event.getData().getAsJsonObject("product")
                        .toString()),
                event.getData().get("clientId").getAsString());
    }

    public void removeFromCartTreatment(Event event){
        this.orderService.removeFromCart(ProductConverter
                .jsonToProduct(event.getData().getAsJsonObject("product")
                        .toString()),
                event.getData().get("clientId").getAsString());
    }

    public void orderClosedTreatment(Event event){
        this.orderService.orderClosed(event.getData().get("clientId").getAsString());
    }

}
