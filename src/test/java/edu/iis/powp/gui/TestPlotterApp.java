package edu.iis.powp.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LoggingPermission;

import edu.iis.client.plottermagic.ClientPlotter;
import edu.iis.client.plottermagic.IPlotter;
import edu.iis.client.plottermagic.preset.FiguresJoe;
import edu.iis.powp.adapter.LineAdapterPlotterDriver;
import edu.iis.powp.app.Application;
import edu.iis.powp.app.Context;
import edu.iis.powp.app.DriverManager;
import edu.iis.powp.appext.ApplicationWithDrawer;
import edu.iis.powp.appext.PlotterCommandManager;
import edu.iis.powp.command.ComplexCommand;
import edu.iis.powp.command.DrawToCommand;
import edu.iis.powp.command.IPlotterCommand;
import edu.iis.powp.command.SetPositionCommand;
import edu.iis.powp.events.predefine.SelectTestFigureOptionListener;
import edu.iis.powp.factory.ComplexCommandFactory;
import edu.iis.powp.factory.CompoundCommandCollection;
import edu.kis.powp.drawer.shape.LineFactory;

import javax.swing.*;


public class TestPlotterApp {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Setup test concerning preset figures in context.
     *
     * @param context Application context.
     */
    private static void setupPresetTests(Context context) {
        SelectTestFigureOptionListener selectTestFigureOptionListener = new SelectTestFigureOptionListener();

        context.addTest("Figure Joe 1", selectTestFigureOptionListener);

        context.addTest("Figure Joe 2", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FiguresJoe.figureScript2(Application.getComponent(DriverManager.class).getCurrentPlotter());
            }
        });

    }

    /**
     * Setup test using ploter commands in context.
     *
     * @param context Application context.
     */
    private static void setupCommandTests(Context context) {
        context.addTest("Load secret command", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<IPlotterCommand> commands = new ArrayList<IPlotterCommand>();
                commands.add(new SetPositionCommand(-20, -50));
                commands.add(new DrawToCommand(-20, -50));
                commands.add(new SetPositionCommand(-20, -40));
                commands.add(new DrawToCommand(-20, 50));
                commands.add(new SetPositionCommand(0, -50));
                commands.add(new DrawToCommand(0, -50));
                commands.add(new SetPositionCommand(0, -40));
                commands.add(new DrawToCommand(0, 50));
                commands.add(new SetPositionCommand(70, -50));
                commands.add(new DrawToCommand(20, -50));
                commands.add(new DrawToCommand(20, 0));
                commands.add(new DrawToCommand(70, 0));
                commands.add(new DrawToCommand(70, 50));
                commands.add(new DrawToCommand(20, 50));

                PlotterCommandManager manager = Application.getComponent(PlotterCommandManager.class);
                manager.setCurrentCommand(commands, "TopSecretCommand");
            }
        });

        context.addTest("Run command", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                IPlotterCommand command = Application.getComponent(PlotterCommandManager.class).getCurrentCommand();
                command.execute(Application.getComponent(DriverManager.class).getCurrentPlotter());
                LOGGER.info("Executing command: " + command);
            }
        });

        context.addTest("Menage Commands", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CompoundCommandCollection compoundCommandCollection = new CompoundCommandCollection();
                compoundCommandCollection.initMapWithDommyCommands();

                Map<ComplexCommand, Boolean> checkedMap = compoundCommandCollection.getCheckedMap();


                JPanel insideFrame = new JPanel();
                JFrame frame = new JFrame();

                GridBagLayout gridMainLayout = new GridBagLayout();
                gridMainLayout.

                GridLayout gridLayout = new GridLayout(checkedMap.size(), 2);
                
                insideFrame.setLayout(gridLayout);
                frame.setLayout(gridMainLayout);

                GridBagConstraints gridBagConstraints = new GridBagConstraints();
                gridBagConstraints.gridx=0;
                gridBagConstraints.gridy=0;
                gridBagConstraints.gridwidth=6;

                int row = 0;
                for (ComplexCommand command : checkedMap.keySet()) {
                    Checkbox checkbox = new Checkbox();
                    Label label = new Label(String.valueOf(Math.random()));

                    insideFrame.add(checkbox);
                    insideFrame.add(label);
                }

                frame.add(insideFrame,gridBagConstraints);
                frame.add(new JButton("DUPA"));



                frame.setSize(600, 400);
                frame.setVisible(true);
            }
        });

    }

    /**
     * Setup driver manager, and set default IPlotter for application.
     *
     * @param context Application context.
     */
    private static void setupDrivers(Context context) {
        IPlotter clientPlotter = new ClientPlotter();
        context.addDriver("Client Plotter", clientPlotter);

        IPlotter plotter = new LineAdapterPlotterDriver(LineFactory.getBasicLine(), "basic");
        context.addDriver("Line Simulator", plotter);
        Application.getComponent(DriverManager.class).setCurrentPlotter(plotter);

        plotter = LineAdapterPlotterDriver.getSpecialLineAdapter();
        context.addDriver("Special line Simulator", plotter);
        context.updateDriverInfo();
    }

    /**
     * Setup menu for adjusting logging settings.
     *
     * @param context Application context.
     */
    private static void setupLogger(Context context) {
        Application.addComponent(Logger.class);
        context.addComponentMenu(Logger.class, "Logger", 0);
        context.addComponentMenuElement(Logger.class, "Clear log", (ActionEvent e) -> context.flushLoggerOutput());
        context.addComponentMenuElement(Logger.class, "Fine level", (ActionEvent e) -> LOGGER.setLevel(Level.FINE));
        context.addComponentMenuElement(Logger.class, "Info level", (ActionEvent e) -> LOGGER.setLevel(Level.INFO));
        context.addComponentMenuElement(Logger.class, "Warning level", (ActionEvent e) -> LOGGER.setLevel(Level.WARNING));
        context.addComponentMenuElement(Logger.class, "Severe level", (ActionEvent e) -> LOGGER.setLevel(Level.SEVERE));
        context.addComponentMenuElement(Logger.class, "OFF logging", (ActionEvent e) -> LOGGER.setLevel(Level.OFF));
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                ApplicationWithDrawer.configureApplication();
                Application.addComponent(PlotterCommandManager.class);

                Context context = Application.getComponent(Context.class);

                setupDrivers(context);
                setupPresetTests(context);
                setupCommandTests(context);
                setupLogger(context);
            }
        });
    }

}
