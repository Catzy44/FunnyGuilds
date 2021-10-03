package net.dzikoysk.funnyguilds.feature.command.user;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import net.dzikoysk.funnyguilds.feature.command.AbstractFunnyCommand;
import org.bukkit.command.CommandSender;
import panda.utilities.StringUtils;

import java.time.LocalTime;
import static net.dzikoysk.funnyguilds.feature.command.DefaultValidation.when;

@FunnyComponent
public final class TntCommand extends AbstractFunnyCommand {

    @FunnyCommand(
        name = "${user.tnt.name}",
        description = "${user.tnt.description}",
        aliases = "${user.tnt.aliases}",
        permission = "funnyguilds.tnt",
        acceptsExceeded = true
    )
    public void execute(CommandSender sender) {
        when (!config.tntProtection.time.enabled, messages.tntProtectDisable);

        LocalTime now = LocalTime.now();
        LocalTime start = config.tntProtection.time.startTime;
        LocalTime end = config.tntProtection.time.endTime;
        String message = messages.tntInfo;

        boolean isWithinTimeframe = config.tntProtection.time.passingMidnight
                ? now.isAfter(start) || now.isBefore(end)
                : now.isAfter(start) && now.isBefore(end);

        message = StringUtils.replace(message, "{PROTECTION_START}", config.tntProtection.time.startTime_);
        message = StringUtils.replace(message, "{PROTECTION_END}", config.tntProtection.time.endTime_);

        sender.sendMessage(message);
        sender.sendMessage(isWithinTimeframe ? messages.tntNowDisabled : messages.tntNowEnabled);
    }

}
