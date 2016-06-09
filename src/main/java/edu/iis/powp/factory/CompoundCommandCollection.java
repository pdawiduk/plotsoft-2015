package edu.iis.powp.factory;

import edu.iis.client.plottermagic.IPlotter;
import edu.iis.powp.command.ComplexCommand;
import edu.iis.powp.command.DrawToCommand;
import edu.iis.powp.command.ICompoundCommand;
import edu.iis.powp.command.SetPositionCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;

/**
 * Created by Shogun on 2016-06-09.
 */
public class CompoundCommandCollection {

    Map<ComplexCommand,Boolean> checkedMap = new HashMap<ComplexCommand,Boolean>();

    public static ComplexCommand rectangleComplexCommand() {

        ComplexCommand complexCommand = new ComplexCommand();

        complexCommand.addCommand(new SetPositionCommand(0, 0));
        complexCommand.addCommand(new DrawToCommand(0, 200));
        complexCommand.addCommand(new DrawToCommand(200, 200));
        complexCommand.addCommand(new DrawToCommand(200, 0));
        complexCommand.addCommand(new DrawToCommand(0, 0));

        return complexCommand;

    }

    public static ComplexCommand triangleComplexCommand() {

        ComplexCommand complexCommand = new ComplexCommand();

        complexCommand.addCommand(new SetPositionCommand(0, 0));
        complexCommand.addCommand(new DrawToCommand(0,100));
        complexCommand.addCommand(new DrawToCommand(200, 0));
        complexCommand.addCommand(new DrawToCommand(0, 0));

        return complexCommand;

    }

    public Map<ComplexCommand, Boolean> getCheckedMap() {
        return checkedMap;
    }

    public void setCheckedMap(Map<ComplexCommand, Boolean> checkedMap) {
        this.checkedMap = checkedMap;
    }

    public void initMapWithDommyCommands(){

        checkedMap.put(rectangleComplexCommand(),Boolean.TRUE);
        checkedMap.put(triangleComplexCommand(),Boolean.TRUE);
    }
}
