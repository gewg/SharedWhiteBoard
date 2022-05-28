/**
 * @Auther: Wei Ge
 * @Email: gewg@student.unimelb.edu.au
 *
 * The Super Class for the Host and Guest. This class contains common user functions like painting, chatting, etc.
 */

package User;

import WhiteBoardServer.iWhiteBoard;
import com.alibaba.fastjson.JSONObject;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class User extends UnicastRemoteObject implements iUser{

    // the address and port number
    protected String portAddr = null;
    protected String portNum = null;
    // the white board stub for RMI
    public static iWhiteBoard whiteBoard;
    // the canvas's record list
    public static ArrayList<JSONObject> canvasRecord = new ArrayList<>();
    // the ui
    public static UserUI ui;
    // the user's name
    protected static String userName;
    // the user's authority
    private static Authority authority;
    // whether the user can join the server
    // default is false
    private static boolean joinServerAllow = false;


    public User(String userName, Authority authority) throws RemoteException {
        super();
        this.userName = userName;
        this.authority = authority;
    }

    public void setPort(String portAddr, String portNum){
        this.portAddr = portAddr;
        this.portNum = portNum;
    }

    public void setUI(UserUI ui){
        this.ui = ui;
    }

    /**
     * Connect to the WhiteBoard RMI
     */
    public void connectWhiteBoardServer(String portAddr, String portNum) throws MalformedURLException, NotBoundException, RemoteException{
        whiteBoard = (iWhiteBoard) Naming.lookup("rmi://" + portAddr + ":" + portNum + "/whiteboard");
    }

    /**
     * Board the new record to server
     * @param newRecord
     */
    public static void boardNewRecord(JSONObject newRecord){
        // check authority
        if (joinServerAllow) {
            try {
                whiteBoard.boardDraw(newRecord);
            } catch (RemoteException e) {
                System.out.println(e);
            }
        }
    }

    /**
     * Board the message to server
     * @param message
     */
    public static void boardMsg(String message){
        // check authority
        if (joinServerAllow) {
            try {
                // add the information
                message = userName + ": " + message + "\n";
                whiteBoard.boardMsg(message);
            } catch (RemoteException e) {
                System.out.println(e);
            }
        }
    }

    /**
     * Accept the record list and draw them on the canvas
     */
    @Override
    public void rePaint(){
        // update the canvas
        ui.getCanvas().repaint();
    }

    /**
     * Add new record to the list
     */
    @Override
    public void addRecord(JSONObject newRecord){
        canvasRecord.add(newRecord);
    }

    @Override
    public void clearRecord() throws RemoteException {
        // empty the record
        canvasRecord.clear();
    }

    /**
     * record list getter
     * @return the record list
     */
    public ArrayList<JSONObject> getRecordList(){
        return canvasRecord;
    }

    /**
     * Get the user's name
     * @return user name
     */
    @Override
    public String getUserName() {
        return userName;
    }

    /**
     * Get the user's authority
     * @return
     */
    @Override
    public Authority getAuthority() {
        return authority;
    }

    /**
     * Accept the message and show on the UI
     * @param message
     * @throws RemoteException
     */
    @Override
    public void acceptMsg(String message) throws RemoteException {
        ui.getChatArea().append(message);
    }

    /**
     * Check whether user is allowed to join the server
     * @return
     */
    public boolean isJoinServerAllow() {
        return joinServerAllow;
    }

    /**
     * Set the user's authority to join the server
     * @param joinServerAllow
     */
    @Override
    public void setJoinServerAllow(boolean joinServerAllow) {
        this.joinServerAllow = joinServerAllow;
    }

    @Override
    public Boolean judgeRequest(iUser user) throws RemoteException {return null;}

    @Override
    public void updateUserList(ArrayList<iUser> users) throws RemoteException {
        // empty the userList window
        ui.getUserList().setText("");

        // add all user into the list
        for (iUser eachUser : users){
            ui.getUserList().append(eachUser.getUserName() + "\n");
        }
    }

    @Override
    public void kickNotification() throws RemoteException {}
    @Override
    public void hostQuit() throws RemoteException {}
}
