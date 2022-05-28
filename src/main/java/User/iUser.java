/**
 * @Auther: Wei Ge
 * @Email: gewg@student.unimelb.edu.au
 *
 * The User's interface. Used as the skeleton of user's methods.
 */
package User;

import com.alibaba.fastjson.JSONObject;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface iUser extends Remote {
    void rePaint() throws RemoteException;
    void addRecord(JSONObject newRecord) throws RemoteException;
    void clearRecord() throws RemoteException;
    void acceptMsg(String message) throws RemoteException;
    void setJoinServerAllow(boolean joinServerAllow) throws RemoteException;
    void updateUserList(ArrayList<iUser> users) throws RemoteException;
    void kickNotification() throws RemoteException;
    void hostQuit() throws RemoteException;
    String getUserName() throws RemoteException;
    Authority getAuthority() throws RemoteException;
    Boolean judgeRequest(iUser user) throws RemoteException;
}
