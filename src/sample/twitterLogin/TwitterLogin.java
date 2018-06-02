package sample.twitterLogin;

import javafx.scene.text.Font;
import sample.fonts.Fonts;
import sample.messageBoxes.AlertBox;
import sample.messageBoxes.ConfirmPinBox;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.awt.*;
import java.net.URI;

public class TwitterLogin {

    public static Twitter LogIn() {

        Font font = Fonts.LoadFont("src/fonts/ObelixPro.ttf", 20);
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("0Cj4B7jX58aBX9weNgZJ2ymks")
                .setOAuthConsumerSecret("IcTBCn40cdBaDp3npx0QbGE7jR127Qajd5lNJbi8GrDQooTflW");

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        try {
            RequestToken requestToken = twitter.getOAuthRequestToken();
            System.out.println("Got request token.");

            System.out.println("Enter the PIN");
            String pin;
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            pin = ConfirmPinBox.display(font, desktop, requestToken);

            if (pin.equals("-1")) {
                return null;
            }
            try {
                Integer.parseInt(pin);
            } catch (NumberFormatException e) {
                AlertBox.display("Message","Pin is not validate", font);
                return null;
            }

            try {
                twitter.getOAuthAccessToken(requestToken, pin);
            } catch (TwitterException ignored) {
                AlertBox.display("Message","Pin is not validate", font);
                return null;
            }
            System.out.println("Got access token.");
        } catch (IllegalStateException | TwitterException ie) {
            AlertBox.display("Message", "No Internet connection", font);
            return null;
        }
        System.out.println("ready to twit");
        return twitter;
    }
}
