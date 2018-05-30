package ch.supsi.texas.commands;

import ch.supsi.texas.GameMediator;
import ch.supsi.texas.player.BasePlayer;

public class RaiseCommand  implements ICommand {
    private final ActionMediator receiver;
    protected Integer money;
    protected BasePlayer player;

    public RaiseCommand(ActionMediator receiver, BasePlayer player) {
        if(receiver==null || player == null)
            throw new NullPointerException();
        this.player = player;
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.raise(player, money);
    }

    public void setMoney(int money){
        this.money = money;
    }

    public BasePlayer getPlayer() {
        return player;
    }

    public ActionMediator getReceiver() {
        return receiver;
    }
}
