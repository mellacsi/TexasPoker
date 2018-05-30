package ch.supsi.texas.commands;

import ch.supsi.texas.player.BasePlayer;

public class CheckCommand implements ICommand {
    private final ActionMediator receiver;
    protected BasePlayer player;

    public CheckCommand(ActionMediator receiver, BasePlayer player) {
        if(receiver==null || player == null)
            throw new NullPointerException();
        this.player = player;
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.check(player);
    }

    public BasePlayer getPlayer() {
        return player;
    }

    public ActionMediator getReceiver() {
        return receiver;
    }
}
