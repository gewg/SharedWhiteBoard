/**
 * @Auther: Wei Ge
 * @Email: gewg@student.unimelb.edu.au
 *
 * The Guest starter.
 */

package Guest;

import User.Authority;

import java.rmi.RemoteException;

public class GuestStarter {
    public static void main (String[] args){
        // the default value
        String portAddr = "localhost";
        String portNum = "8888";
        String userName = "Guest";

        try {
            // capture the port address and number
            portAddr = args[0];
            portNum = args[1];
            userName = args[2];
        } catch (ArrayIndexOutOfBoundsException e){
            // Do nothing
        }

        // new the host Object and UI
        Guest guest;
        GuestUI ui;
        try {
            // the UI
            ui = new GuestUI();
            ui.start();

            // the host object
            guest = new Guest(userName, Authority.Guest);
            // set the RMI port address and number
            guest.setPort(portAddr, portNum);
            guest.setUI(ui);
            Thread hostThread = new Thread(guest);
            hostThread.start();


            // set the canvas list
            ui.getCanvas().setCanvasRecord(guest.getRecordList());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
