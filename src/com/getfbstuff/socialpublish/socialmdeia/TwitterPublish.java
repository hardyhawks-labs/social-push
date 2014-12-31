package com.getfbstuff.socialpublish.socialmdeia;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.getfbstuff.socialpublish.util.Utility;
import com.restfb.util.StringUtils;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterPublish implements SocialPublish {

	@Override
	public void publish(String description, String url, String thumbnailUrl) {
		String consumerKeyStr = null;
		String consumerSecretStr = null;
		String accessTokenStr = null;
		String accessTokenSecretStr = null;
		int tweetSize = 140;
		int tcoLinkSize = 25;
		Utility util = new Utility();

		Properties prop = new Properties();
		FileInputStream output = null;
		try {

			output = new FileInputStream("config.properties");
			prop.load(output);
			// set the properties value
			consumerKeyStr = (String) prop.get("consumerKeyStr");
			consumerSecretStr = (String) prop.get("consumerSecretStr");
			accessTokenStr = (String) prop.get("accessTokenStr");
			accessTokenSecretStr = (String) prop.get("accessTokenSecretStr");

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

		try {
			Twitter twitter = new TwitterFactory().getInstance();

			twitter.setOAuthConsumer(consumerKeyStr, consumerSecretStr);
			AccessToken accessToken = new AccessToken(accessTokenStr,
					accessTokenSecretStr);

			twitter.setOAuthAccessToken(accessToken);

			// use weka or lucene next time
			//massage description
				//remove unnessesary word like facebook video and see translation first
				description = description.replace("See Translation", "");
				description = description.replace("facebook video", "");
				//remove link
				description = util.removeLink(description);
				//check for null if yes get random string
				if (StringUtils.isBlank(description))
				{
					description = util.getRandomMessage();
				}
				
				//remove stop word
				description = util.removeStopWord(description);
				//remove adult content
				description = util.removeAdultWord(description);
			//shorten url
				//happens automatically t.co->22 chars, so lets reserve 25 chars for link
			//fit into 140 chars
				
			if(description.length()>140)
			{
			description = description.substring(0, tweetSize - tcoLinkSize);
			}
			String message = description + " " + url;
			twitter.updateStatus(message);
			System.out.println("Successfully updated the status in Twitter." + message);
		} catch (TwitterException te) {
			te.printStackTrace();
		}
	}

}
