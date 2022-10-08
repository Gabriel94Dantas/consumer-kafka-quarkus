package org.acme.contexts;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;

public class KafkaContext {

    private String topic;
    private String getServerBroker(){
        if (System.getenv("BROKER_HOST") != null
                && !System.getenv("BROKER_HOST").isEmpty()){
            return System.getenv("BROKER_HOST");
        }else{
            return "localhost:9092";
        }
    }

    public Properties getProperties(){
        final Properties props = new Properties();

        props.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-java-getting-started");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, getServerBroker());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        return props;
    }

    public String getTopic(){
        if(System.getenv("TOPIC") != null
            && !System.getenv("TOPIC").isEmpty()){
            return System.getenv("TOPIC");
        }
        return "br.com.example.chiefs";
    }

}
