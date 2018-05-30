package ch.supsi.texas.commands;

import ch.supsi.texas.player.BasePlayer;

public class CallCommand implements ICommand {
    final ActionMediator receiver;
    BasePlayer player;

    public CallCommand(ActionMediator receiver, BasePlayer player) {
        if(receiver==null || player == null)
            throw new NullPointerException();
        this.player = player;
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.call(player);
    }

    public BasePlayer getPlayer() {
        return player;
    }

    public ActionMediator getReceiver() {
        return receiver;
    }
}
