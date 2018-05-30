package ch.supsi.texas.player;

import ch.supsi.texas.cards.Card;
import ch.supsi.texas.events.CardGivenToPlayer;
import ch.supsi.texas.events.PlayerBetHasChangedEvent;
import ch.supsi.texas.events.PlayerMoneyChangedEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class BasePlayer {
    private String name = "default name";
    private List<Card> cards = new ArrayList<>();
    private Integer money = 0;
    private Integer betMoney = 0;

    public BasePlayer() { }

    public BasePlayer(String name) {
        this.name = name;
        this.money = 1000;
        this.betMoney = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public List<Card> getCards() {
        return cards;
    }

    public Card getCard(Integer cardIndex) {
        if((cardIndex >= 0) && (cardIndex < cards.size()))
            return cards.get(cardIndex);

        return null;
    }

    public void giveCard(Card card) {
        cards.add(card);
        CardGivenToPlayer.getInstance().setPayLoadAndCall(this);
    }

    public void giveCards(List<Card> cards) {
        for(Card card : cards)
            giveCard(card);
    }

    public void clearCards() {
        cards.clear();
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
        PlayerMoneyChangedEvent.getInstance().setPayLoadAndCall(this);
    }

    public void giveMoney(Integer money) {
        this.setMoney(money += this.money);
    }

    public Integer getBetMoney() {
        return betMoney;
    }

    // setBet does not change the Money field
    public void setBetMoney(Integer betMoney) {
        this.betMoney = betMoney;
        PlayerBetHasChangedEvent.getInstance().setPayLoadAndCall(this);
    }

    // matchBet takes money from Money and puts them in betMoney so that betMoney matches value
    public Integer matchBet(Integer value) {
        if(betMoney < value) {
            Integer difference = value - betMoney;

            if(difference <= money) {
                this.setMoney(money-difference);
                this.setBetMoney(value);
                return difference;
            } else {
                // all-in placeholder, bet all you got, win as if you covered the bet fully
                this.setBetMoney(this.getBetMoney()+money);
                int myMoney = this.getMoney();
                this.setMoney(0);

                return myMoney;
            }
        }
        if(betMoney.equals(value))
            return 0;

        return 0;
    }
}