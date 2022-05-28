/**
 * @Auther: Wei Ge
 * @Email: gewg@student.unimelb.edu.au
 *
 * The White Board provides functions and communicate with users through RMI
 */
package WhiteBoardServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import User.Authority;
import User.iUser;
import com.alibaba.fastjson.JSONObject;

public class WhiteBoard extends UnicastRemoteObject implements iWhiteBoard {
    // the user list
    ArrayList<iUser> userList = new ArrayList<>();

    // the record list
    ArrayList<JSONObject> recordList = new ArrayList<>();

    // the host user
    iUser host;

    public WhiteBoard(iUser host) throws RemoteException {
        super();
        this.host = host;
    }

    @Override
    public void rmiHello(){
        System.out.println("RMI connect Successfully.");
    }

    @Override
    public synchronized String joinServer(iUser user) throws RemoteException{
        // check whether the user's name is duplicate
        String userName = user.getUserName();
        for (iUser eachUser : userList) {
            if (eachUser.getUserName().equals(userName)) {
                // reject the user if its name is duplicate
                return "Duplicate";
            }
        }

        // get the authority from host
        Boolean allowed = true;
        if (user.getAuthority() == Authority.Guest){
            allowed = guestRequestJoin(user);
        }

        // allow the user to join the server if host agreed
        if (allowed) {
            // add the user if the name is not duplicate
            userList.add(user);
            // synchronize the canvas
            synchronizeCanvas(user);

            // update the user's list
            boardNewUser();

            return "SUCC";
        }
        // otherwise, notify the user it is rejected
        return "Rejected";
    }

    /**
     *
     * @param user
     * @return
     * @throws RemoteException
     */
    @Override
    public Boolean guestRequestJoin(iUser user) throws RemoteException {
        // notify the host
        return host.judgeRequest(user);
    }

    @Override
    public void guestQuit(String userName) throws RemoteException {
        try {
            // find the user
            for (iUser eachUser : userList) {
                if (eachUser.getUserName().equals(userName)) {

                    // check the user's role
                    if (eachUser.getAuthority() != Authority.Host) {
                        // remove the user
                        userList.remove(eachUser);
                        // board to other user
                        boardNewUser();
                    }

                }
            }
        }catch (RemoteException | ConcurrentModificationException e){
            // ignore the RemoteException when closing remote object
        }
    }


    /**
     * Board the message to all users
     * @param message
     * @throws RemoteException
     */
    @Override
    public void boardMsg(String message) throws RemoteException{
        for (iUser eachUser : userList){
            eachUser.acceptMsg(message);
        }
    }

    /**
     * Board the new draw to all users
     * @param newRecord
     * @throws RemoteException
     */
    @Override
    public void boardDraw(JSONObject newRecord) throws RemoteException {
        for (iUser eachUser : userList){
            eachUser.addRecord(newRecord);
            eachUser.rePaint();
        }
        // add the new draw to list
        recordList.add(newRecord);
    }

    /**
     * Board the entire new canvas to all users
     * @param newCanvas
     * @throws RemoteException
     */
    @Override
    public void boardNewCanvas(ArrayList<JSONObject> newCanvas) throws RemoteException {
        // update the server's record list
        recordList.clear();

        for (JSONObject eachRecord : newCanvas) {
            recordList.add(eachRecord);
        }

        // notify all users
        for (iUser eachUser : userList) {
            // empty the previous canvas
            eachUser.clearRecord();
            // add all records to current user's record
            for (JSONObject eachRecord : newCanvas) {
                eachUser.addRecord(eachRecord);
            }
            // update the canvas
            eachUser.rePaint();
        }
    }

    /**
     * Board the users with new user list
     * @throws RemoteException
     */
    private void boardNewUser() throws RemoteException {
        for (iUser eachUser : userList){
            eachUser.updateUserList(userList);
        }
    }

    /**
     * Kick the user out
     * @param userName
     * @return
     * @throws RemoteException
     */
    @Override
    public Boolean kickGuest(String userName) throws RemoteException{
        Boolean result = false;
        try {
            // find the user
            for (iUser eachUser : userList) {
                if (eachUser.getUserName().equals(userName)) {

                    // check the user's role
                    if (eachUser.getAuthority() != Authority.Host) {
                        // remove the user
                        userList.remove(eachUser);
                        // board to other user
                        boardNewUser();
                        // notify this be-removed user
                        eachUser.kickNotification();
                        result = true;
                    }

                }
            }
        }catch (RemoteException e){
            // ignore the RemoteException when closing remote object
        }
        return result;
    }

    /**
     * The host generate a new canvas
     * @throws RemoteException
     */
    @Override
    public void newCanvas() throws RemoteException {
        // empty the record list
        recordList.clear();
        // board to all users
        for (iUser eachUser : userList){
            eachUser.clearRecord();
            eachUser.rePaint();
        }
    }

    /**
     * Notify all users the hast has quit
     * @throws RemoteException
     */
    @Override
    public void hostQuit() throws RemoteException {
        // because this rmi server is built by the host, thus the host should be closed at last
        iUser host = null;

        try {
            // board to all users
            for (iUser eachUser : userList) {
                try {
                    // check the user's role
                    if (eachUser.getAuthority() != Authority.Host) {
                        // notify this be-removed user
                        eachUser.hostQuit();
                    } else {
                        host = eachUser;
                    }
                } catch (RemoteException e) {
                    // ignore the RemoteException when closing remote object
                }
            }

            // close the host
            assert host != null;
            host.hostQuit();

        } catch (RemoteException e){
            // ignore the RemoteException when closing remote object
        }
    }


    /**
     * Synchronize the canvas for new user
     */
    private void synchronizeCanvas(iUser user) throws RemoteException {
        for (JSONObject eachRecord : recordList){
            user.addRecord(eachRecord);
        }
        user.rePaint();
    }
}
