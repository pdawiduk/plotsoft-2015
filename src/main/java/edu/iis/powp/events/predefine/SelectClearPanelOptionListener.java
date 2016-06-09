package edu.iis.powp.events.predefine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.iis.powp.app.Application;
import edu.kis.powp.drawer.panel.DrawPanelController;


public class SelectClearPanelOptionListener implements ActionListener
{

    @Override
    public void actionPerformed(ActionEvent e)
    {
        DrawPanelController controller = Application.getComponent(DrawPanelController.class);
        controller.clearPanel();
    }
}
