package net.juxyc.guilds.commands.argument;

import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.component.LiteComponent;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import lombok.RequiredArgsConstructor;
import panda.std.Option;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@ArgumentName("liczba")
public class IntegerArgument implements SingleArgumentHandler<Integer> {

    @Override
    public Integer parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        return Option.attempt(NumberFormatException.class, () -> Integer.parseInt(argument))
                .orThrow(() -> new ValidationCommandException("&cPodany argument nie jest liczba!"));
    }

    @Override
    public List<String> tabulation(LiteInvocation liteInvocation, String s, String[] strings) {
        return Arrays.asList("1", "8", "16", "32", "64", "100");
    }

    @Override
    public boolean isValid(LiteComponent.ContextOfResolving context, String argument) {
        return Option.attempt(NumberFormatException.class, () -> Integer.parseInt(argument))
                .isPresent();
    }
}