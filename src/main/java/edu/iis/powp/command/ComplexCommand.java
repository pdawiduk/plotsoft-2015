package edu.iis.powp.command;

import java.util.ArrayList;
import java.util.List;

import edu.iis.client.plottermagic.IPlotter;

public class ComplexCommand implements IPlotterCommand {
    private List<IPlotterCommand> commands;

    public ComplexCommand() {
        commands = new ArrayList<>();
    }

    @Override
    public void execute(IPlotter iPlotter) {
        for (IPlotterCommand command : commands) {
            command.execute(iPlotter);
        }
    }

    public void addCommand(IPlotterCommand command) {
        commands.add(command);
    }
}