package net.juxyc.guilds.commands.argument;

import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@ArgumentName("tag")
public class TagArgument implements SingleArgumentHandler<String> {

    @Override
    public String parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        return argument;
    }

    @Override
    public List<String> tabulation(LiteInvocation liteInvocation, String command, String[] args) {
        return Collections.emptyList();
    }
}