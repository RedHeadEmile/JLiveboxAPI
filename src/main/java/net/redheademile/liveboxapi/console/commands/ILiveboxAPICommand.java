package net.redheademile.liveboxapi.console.commands;

import net.redheademile.liveboxapi.LiveboxAPI;
import net.redheademile.liveboxapi.exceptions.LiveboxException;

import java.util.Scanner;

public interface ILiveboxAPICommand {
    String[] getNameAndAliases();
    void execute(LiveboxAPI api, Scanner in, Object... options) throws LiveboxException;
}
