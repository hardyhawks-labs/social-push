package com.getfbstuff.socialpublish.socialmdeia;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.getfbstuff.socialpublish.util.Utility;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import com.restfb.util.StringUtils;

public class FacebookPublish implements SocialPublish{

	@Override
	public void publish(String description, String url, String thumbnailUrl) {
		//AccessToken accessToken =
		//		  new DefaultFacebookClient().obtainExtendedAccessToken("",
		//		    "", "");

		//		System.out.println("My extended access token: " + accessToken.getAccessToken());
		//		System.out.println("expiry: " + accessToken.getExpires());
				
				//AccessToken accessTokenApp =
				//		  new DefaultFacebookClient().obtainAppAccessToken("", "");
				//String accessTokenAccount = new Account().getAccessToken();
					//	System.out.println("My account access token: " + accessTokenApp);
				Properties prop = new Properties();
				FileInputStream output = null;
				String accessToken = null;
				Utility util = new Utility();
				try {
					 
					output = new FileInputStream("config.properties");
					prop.load(output);
					// set the properties value
					accessToken = (String) prop.get("accessToken");
			
			 
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
				FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
				//Page page = facebookClient.fetchObject("", Page.class);
				//System.out.println("Page likes: " + page.getLikes());
				
				//String accessTokenPage = page.getAccessToken();
				
				//FacebookClient facebookClientPage = new DefaultFacebookClient(accessTokenPage);
				//System.out.println("page access token:" + accessTokenPage);
				description = description.replace("See Translation", "");
				description = description.replace("facebook video", "");
				//remove link
				description = util.removeLink(description);
				//check for null if yes get random string
				if (StringUtils.isBlank(description))
				{
					description = util.getRandomMessage();
				}
				
				//remove adult content
				description = util.removeAdultWord(description);
				
				FacebookType publishMessageResponse =
						facebookClient.publish("me/feed", FacebookType.class,
								  Parameter.with("message", description),
								  Parameter.with("link", url),
								  Parameter.with("picture", thumbnailUrl));
				System.out.println(publishMessageResponse);
	}
	
}
