/**
 * @Auther: Wei Ge
 * @Email: gewg@student.unimelb.edu.au
 *
 * The Guest class for the guest.
 */
package Guest;

import User.*;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Guest extends User implements Runnable{

    public Guest(String userName, Authority authority) throws RemoteException {
        super(userName, authority);
    }

    @Override
    public void run() {
        connect();
    }

    /**
     * Connect the whiteboard
     */
    public void connect(){

        try {
            // connect with the WhiteBoard Server
            connectWhiteBoardServer(portAddr, portNum);
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            ui.notificationWindow("Can not connect to the WhiteBoard Server. Check the Address and Port Number.");
            System.exit(0);
        }

        // enroll on the server
        String enrollResponse = null;
        try {
            enrollResponse = whiteBoard.joinServer(this);
        } catch (RemoteException e) {
            ui.notificationWindow("Error when enrolling into the server");
            System.exit(0);
        }

        // stop the application if user's name is duplicate
        if (enrollResponse.equals("Duplicate")) {
            ui.notificationWindow("The name already existed. Choose another one");
            System.exit(0);
        }else if (enrollResponse.equals("Rejected")){
            ui.notificationWindow("The Host rejected your join.");
            System.exit(0);
        } else{
            setJoinServerAllow(true);
        }
    }


    @Override
    public void kickNotification(){
        ((GuestUI)ui).kickNotification();
    }


    @Override
    public void hostQuit(){
        ((GuestUI)ui).hostQuit();
    }

    /**
     * Notify the server when the guest quit
     */
    protected static void guestQuit(){
        try {
            whiteBoard.guestQuit(userName);
        } catch (RemoteException e) {
            ui.notificationWindow("Remote Error when quit");
        }
    }
}
