/**
 * @Auther: Wei Ge
 * @Email: gewg@student.unimelb.edu.au
 *
 * The Host Starter.
 */
package Host;

import User.Authority;

import java.rmi.RemoteException;

public class HostStarter {

    public static void main(String[] args){
        // the default value
        String portAddr = "localhost";
        String portNum = "8888";
        String userName = "Host";

        try {
            // capture the port address and number
            portAddr = args[0];
            portNum = args[1];
            userName = args[2];
        } catch (ArrayIndexOutOfBoundsException e){
            // Do nothing
        }

        // new the host Object and UI
        Host host;
        HostUI ui;
        try {
            // the UI
            ui = new HostUI();
            ui.start();

            // the host object
            host = new Host(userName, Authority.Host);
            // set the RMI port address and number
            host.setPort(portAddr, portNum);
            host.setUI(ui);
            Thread hostThread = new Thread(host);
            hostThread.start();

            // set the canvas list
            ui.getCanvas().setCanvasRecord(host.getRecordList());
        } catch (RemoteException e) {
            //
        }
    }
}
