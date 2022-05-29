/**
 * @Auther: Wei Ge
 * @Email: gewg@student.unimelb.edu.au
 *
 * The Host class for host.
 */
package Host;

import User.User;
import User.Authority;
import User.Action;
import User.iUser;
import WhiteBoardServer.WhiteBoardServer;

import com.alibaba.fastjson.JSONObject;
import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Host extends User implements Runnable{

    public Host(String userName, Authority authority) throws RemoteException {
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
            // start the white board server
            WhiteBoardServer.createWhiteBoardServer(portAddr, portNum, this);
            // connect with the WhiteBoard Server
            connectWhiteBoardServer(portAddr, portNum);
        } catch (MalformedURLException | NotBoundException | RemoteException | AlreadyBoundException | IllegalArgumentException e) {
            ui.notificationWindow("Can not connect to the WhiteBoard Server. Check the Address and Port Number.");
            System.exit(0);
        }

        // set the authority
        setJoinServerAllow(true);

        // enroll on the server
        String enrollResponse = null;
        try {
            enrollResponse = whiteBoard.joinServer(this);
        } catch (RemoteException e) {
            ui.notificationWindow("Error when enrolling into the server");
            System.exit(0);
        }

        // stop the application if user's name is duplicate
        if (enrollResponse.equals("Duplicate")){
            ui.notificationWindow("The name already existed. Choose another one");
            System.exit(0);
        }
    }

    /**
     * Judge the join request
     * @param user
     * @return
     * @throws RemoteException
     */
    @Override
    public Boolean judgeRequest(iUser user) throws RemoteException {
        return ((HostUI)ui).judgeRequest(user.getUserName());
    }

    /**
     * Generate a new canvas
     * @throws RemoteException
     */
    protected static void newCanvas() throws RemoteException{
        whiteBoard.newCanvas();
    }

    /**
     * Kick the guest
     * @param userName
     * @return
     * @throws RemoteException
     */
    protected static void kickGuest(String userName) throws RemoteException {
        Boolean flag;
        flag = whiteBoard.kickGuest(userName);

        if (flag){
            ui.notificationWindow("Successfully");
        }else{
            ui.notificationWindow("Kick user failed. Check the username");
        }
    }

    /**
     * Close the white board
     */
    protected static void closeWhiteBoard() throws RemoteException {
        whiteBoard.hostQuit();
    }

    /**
     * Quit
     */
    public void hostQuit(){
        System.exit(0);
    }

    /**
     * Save the record to the file
     * @param fileName
     */
    protected static void saveToFile(String fileName) {
        BufferedWriter writer = null;
        File file = new File(fileName);

        // create the file if it does not exist
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                ui.notificationWindow("The path is not acceptable. Check it.");
                return;
            }
        }

        // store all the records into the file
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8"));
            for (JSONObject eachRecord : canvasRecord) {
                writer.write((eachRecord.toJSONString()));
                writer.newLine();
            }
            writer.close();
            ui.notificationWindow("File saved successfully.");
        } catch (IOException e) {
            ui.notificationWindow("File write failed.");
        }
    }

    /**
     * Load the records from file into record list
     * @param fileName
     */
    protected static void loadFromFile(String fileName){
        // empty the record list
        canvasRecord.clear();

        BufferedReader reader = null;
        String currString = null;
        try{
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            while((currString = reader.readLine()) != null){
                JSONObject eachJsonRecord = JSONObject.parseObject(currString);

                canvasRecord.add(convertInputFormat(eachJsonRecord));
            }
        } catch (FileNotFoundException e) {
            ui.notificationWindow("File not found.");
            return;
        } catch (IOException e) {
            ui.notificationWindow("File load failed.");
            return;
        } catch (ClassCastException | NullPointerException e){
            ui.notificationWindow("Part of the file's content is not acceptable. Check ths file.");
        }

        try {
            // board to all the other users
            whiteBoard.boardNewCanvas(canvasRecord);
            ui.notificationWindow("Load the file successfully..");
        } catch (RemoteException e) {
            ui.notificationWindow("Board to other users failed.");
        }
    }

    /**
     * Convert the input from file to the acceptable format
     * @param inputJson
     * @return
     */
    private static JSONObject convertInputFormat(JSONObject inputJson){
        // convert the Color Object into color format
        JSONObject eachJsonRecordColor = (JSONObject)(inputJson.get("color"));
        Color color = new Color(eachJsonRecordColor.getInteger("r"), eachJsonRecordColor.getInteger("b"),
                eachJsonRecordColor.getInteger("g"));
        inputJson.put("color", color);

        // convert the Action Object into Action
        Action action = null;
        String actionString = inputJson.getString("action");
        switch (actionString){
            case "Circle":
                action = Action.Circle;
                break;
            case "Line":
                action = Action.Line;
                break;
            case "Rectangle":
                action = Action.Rectangle;
                break;
            case "Text":
                action = Action.Text;
                break;
            case "Triangle":
                action = Action.Triangle;
                break;
        }
        inputJson.put("action", action);

        return inputJson;
    }
}
