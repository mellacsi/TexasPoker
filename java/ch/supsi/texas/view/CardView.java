package ch.supsi.texas.view;

import ch.supsi.texas.cards.Card;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardView {
    //two decks are available
/*
    public static ImageView getImageView(Card card){
        String cardString = "";
        if(card.getSeed().equals(Card.Seed.HEART)) cardString += "h";
        if(card.getSeed().equals(Card.Seed.PIKE)) cardString += "s";
        if(card.getSeed().equals(Card.Seed.TILE)) cardString += "d";
        if(card.getSeed().equals(Card.Seed.CLOVER)) cardString += "c";

        if(card.getValue() < 10) cardString += "0";

        cardString += card.getValue();

        Image cardImage = new Image("boring_deck/" + cardString + ".bmp");
        ImageView cardImageView = new ImageView();
        cardImageView.setImage(cardImage);
        cardImageView.setFitWidth(100);
        cardImageView.setPreserveRatio(true);
        cardImageView.setSmooth(true);
        cardImageView.setCache(true);

        return cardImageView;
    }
*/
    public static ImageView getImageView(Card card){
        String cardString = "";
        if(card.getValue() == 1) cardString += "A";
        if((card.getValue() >= 2) && (card.getValue() <= 10)) cardString += card.getValue();
        if(card.getValue() == 11) cardString += "J";
        if(card.getValue() == 12) cardString += "Q";
        if(card.getValue() == 13) cardString += "K";

        if(card.getSeed().equals(Card.Seed.HEART)) cardString += "H";
        if(card.getSeed().equals(Card.Seed.PIKE)) cardString += "S";
        if(card.getSeed().equals(Card.Seed.TILE)) cardString += "D";
        if(card.getSeed().equals(Card.Seed.CLOVER)) cardString += "C";

        Image cardImage = new Image("cat_deck/" + cardString + ".jpg");
        ImageView cardImageView = new ImageView();
        cardImageView.setImage(cardImage);
        cardImageView.setFitWidth(100);
        cardImageView.setPreserveRatio(true);
        cardImageView.setSmooth(true);
        cardImageView.setCache(true);

        return cardImageView;
    }
}
