/**
 * @Auther: Wei Ge
 * @Email: gewg@student.unimelb.edu.au
 *
 * The WhiteBoard's interface. Used as the skeleton of whiteboard's methods.
 */
package WhiteBoardServer;

import User.iUser;
import com.alibaba.fastjson.JSONObject;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface iWhiteBoard extends Remote {
    // test
    void rmiHello() throws RemoteException;

    // common methods
    String joinServer(iUser user) throws RemoteException;
    Boolean guestRequestJoin(iUser user) throws RemoteException;
    void guestQuit(String userName) throws RemoteException;
    void boardMsg(String message) throws RemoteException;
    void boardDraw(JSONObject newRecord) throws RemoteException;

    // host methods
    void boardNewCanvas(ArrayList<JSONObject> newCanvas) throws RemoteException;
    Boolean kickGuest(String userName) throws RemoteException;
    void newCanvas() throws RemoteException;
    void hostQuit() throws RemoteException;

}
