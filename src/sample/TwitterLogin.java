package sample;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.awt.*;
import java.net.URI;

public class TwitterLogin {

    public static Twitter TwitterIs() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("0Cj4B7jX58aBX9weNgZJ2ymks")
                .setOAuthConsumerSecret("IcTBCn40cdBaDp3npx0QbGE7jR127Qajd5lNJbi8GrDQooTflW");

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        try {
            RequestToken requestToken = twitter.getOAuthRequestToken();
            System.out.println("Got request token.");
            AccessToken accessToken = null;


            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(URI.create(requestToken.getAuthorizationURL())); } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Enter the PIN");
            String pin = ConfirmPinBox.display();

            try {
                Integer.parseInt(pin);
            } catch (NumberFormatException e) {
                AlertBox.display("Message","Log in fail");
                return null;
            }

            try {
                accessToken = twitter.getOAuthAccessToken(requestToken, pin);
            } catch (TwitterException ignored) {
                AlertBox.display("Message","Pin is not validate");
                return null;
            }
            System.out.println("Got access token.");
            //System.out.println("Access token: " + accessToken.getToken());
            //System.out.println("Access token secret: " + accessToken.getTokenSecret());
        } catch (IllegalStateException | TwitterException ie) {
            AlertBox.display("Message", "No Internet connection");
            return null;
        }
        System.out.println("ready to twit");
        return twitter;
    }


}
