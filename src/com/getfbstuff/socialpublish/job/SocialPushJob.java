package com.getfbstuff.socialpublish.job;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import com.getfbstuff.socialpublish.reciever.Recieve;
import com.getfbstuff.socialpublish.socialmdeia.FacebookPublish;
import com.getfbstuff.socialpublish.socialmdeia.SocialPublish;
import com.getfbstuff.socialpublish.socialmdeia.TwitterPublish;
import com.getfbstuff.socialpublish.util.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;

public class SocialPushJob {
	
	
	
	public static void main(String args[]) throws ShutdownSignalException, ConsumerCancelledException, IOException, InterruptedException
	{
		Map<Integer, String> dequeuedList = new HashMap<Integer, String>();
		SocialPublish facebook = new FacebookPublish();
		SocialPublish twitter = new TwitterPublish();
		//facebook.publish("facebook video http://getfbstuff.com", "http://getfbstuff.com/facebook-video/1147505701943691/tyrese-gibson-facebook", "");
		Thread t1 = new Thread( new PushSocial(dequeuedList));
		t1.start();
		
		while(true)
		{
			if(dequeuedList.size() > 0)
				{
					System.out.println("time to publish");
					TreeMap<Integer, String> treeMap = new TreeMap<Integer, String>(dequeuedList);
					String contentTopublish = treeMap.lastEntry().getValue();
					Map<String, Object> retMap = new Gson().fromJson(contentTopublish, new TypeToken<HashMap<String, Object>>() {}.getType());
					//generate content
					String description = retMap.get("description").toString();
					String url = Constants.BASE_APP_URL+"/"+ retMap.get("id").toString() +"/"+retMap.get("url").toString();
					String thumbnail = Constants.BASE_IMG_URL+"/"+retMap.get("thumbnail");
					//publish to facebook
					facebook.publish(description,url,thumbnail);
					//publish to twitter
					twitter.publish(description, url, thumbnail);
					//clear the map for next iteration
					dequeuedList.clear();
				}
			System.out.println("going to sleep");
			
			Properties prop = new Properties();
			FileInputStream output = null;
			Integer duration = 0;
			try {
				
				output = new FileInputStream("config.properties");
				prop.load(output);
				// set the properties value
				duration = Integer.parseInt((String) prop.get("duration"));
		
		 
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
			
			Thread.sleep(duration);
			System.out.println("wakeup from sleep");
		}
	}
}

class PushSocial implements Runnable {
	Map<Integer, String> dequeuedList;
	Recieve recieve = new Recieve();
	public PushSocial(Map<Integer, String> dequeuedList) {
		this.dequeuedList = dequeuedList;
	}

	@Override
	public void run() {
		try {
			recieve.recieve(dequeuedList);
		} catch (ShutdownSignalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConsumerCancelledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}