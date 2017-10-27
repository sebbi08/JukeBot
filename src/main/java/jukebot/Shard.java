package jukebot;

import com.sedmelluq.discord.lavaplayer.jdaudp.NativeAudioSendFactory;
import jukebot.utils.Bot;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.requests.SessionReconnectQueue;

import javax.security.auth.login.LoginException;

import static jukebot.utils.Bot.LOG;

public class Shard {

    public JDA jda;

    Shard(int shardId, int totalShards) {
        LOG.info("[" + (shardId + 1) + "/" + totalShards + "] Logging in...");
        try {
            this.jda = new JDABuilder(AccountType.BOT)
                    .setToken(Database.getPropertyFromConfig("token"))
                    .setReconnectQueue(Bot.sesh)
                    .addEventListener(Bot.waiter, new EventListener())
                    .setAudioSendFactory(new NativeAudioSendFactory())
                    .setGame(Game.of(Bot.defaultPrefix + "help | jukebot.xyz"))
                    .useSharding(shardId, totalShards)
                    .buildAsync();
        } catch (LoginException | RateLimitedException ignored) {
            LOG.error("[" + (shardId + 1) + "/" + totalShards + "] Failed to login");
        }
    }
}
