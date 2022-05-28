/**
 * @Auther: Wei Ge
 * @Email: gewg@student.unimelb.edu.au
 *
 * The White Board server
 */
package WhiteBoardServer;

import User.iUser;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class WhiteBoardServer {

    /**
     *
     * @param portAddr
     * @param portNum
     */
    public static void createWhiteBoardServer(String portAddr, String portNum, iUser host) throws RemoteException, MalformedURLException, AlreadyBoundException{
        // enroll the port number for white board RMI server
        iWhiteBoard whiteBoard = new WhiteBoard(host);
        LocateRegistry.createRegistry(Integer.parseInt(portNum));
        // bind the white board
        Naming.bind("rmi://" + portAddr + ":" + portNum + "/whiteboard", whiteBoard);
        System.out.println("White Board Server starts on port:" + portNum);
    }
}
