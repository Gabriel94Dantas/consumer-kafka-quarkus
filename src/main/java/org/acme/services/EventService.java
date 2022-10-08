package org.acme.services;

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
                    this.orderService.addToCart(ProductConverter
                                    .jsonToProduct(event.getData()
                                            .getAsJsonObject("product")
                                            .toString()),
                            event.getData().get("clientId").toString());
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }

}
