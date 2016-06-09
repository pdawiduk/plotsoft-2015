package edu.iis.powp.appext;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import edu.iis.client.plottermagic.IPlotter;
import edu.iis.powp.command.ICompoundCommand;
import edu.iis.powp.command.IPlotterCommand;

/**
 * Driver Manager.
 */
public class PlotterCommandManager
{
    private IPlotterCommand currentCommand = null;
    
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    /**
     * Set current command.
     * 
     * @param commandList Set the command as current.
     */
    public synchronized void setCurrentCommand(IPlotterCommand commandList)
    {
    	this.currentCommand = commandList;
    	LOGGER.info("Current command set to: " + commandList);
    }
    
    /**
     * Set current command.
     * 
     * @param commandList list of commands representing a compound command.
     * @param name name of the command.
     */
    public synchronized void setCurrentCommand(List<IPlotterCommand> commandList, String name)
    {
    	setCurrentCommand(new ICompoundCommand() {
			
    		List<IPlotterCommand> ploterCommands = commandList;
    		
			@Override
			public void execute(IPlotter plotter) {
				ploterCommands.forEach((c) -> c.execute(plotter));
			}
			
			@Override
			public Iterator<IPlotterCommand> iterator() {
				return ploterCommands.iterator();
			}
			
			@Override
			public String toString() {
				return name;
			}
		});
    }
    
    /**
     * Return current command.
     * 
     * @return Current command.
     */
    public synchronized IPlotterCommand getCurrentCommand()
    {
        return currentCommand;
    }
}
