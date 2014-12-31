package com.getfbstuff.socialpublish.reciever;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
public class Recieve {

	public void recieve(Map<Integer, String> dequeuedList) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException{
		ConnectionFactory factory = new ConnectionFactory();
	    String host = null;
	    String queue = null;
	    String username = null;
	    String password = null;
	    
	    Properties prop = new Properties();
		FileInputStream output = null;
		try {

			output = new FileInputStream("config.properties");
			prop.load(output);
			// set the properties value
			host = (String) prop.get("host");
			queue = (String) prop.get("queue");
			username = (String) prop.get("username");
			password = (String) prop.get("password");

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		
		factory.setHost(host);
	    factory.setUsername(username);
	    factory.setPassword(password);
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();

	    channel.exchangeDeclare("media","direct" ,true);
	    channel.queueDeclare(queue, true, false, false, null);
	    //System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
	    
	    QueueingConsumer consumer = new QueueingConsumer(channel);
	    channel.basicConsume(queue, true, consumer);

	    while (true) {
	      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
	      String message = new String(delivery.getBody());
	      
	      Map<String, Object> retMap = new Gson().fromJson(message, new TypeToken<HashMap<String, Object>>() {}.getType());
	      //System.out.println(retMap);
	      Double views = (Double)retMap.get("views");
	      dequeuedList.put(views.intValue(), message);
	      //System.out.println(dequeuedList);
	    }
	}
}
