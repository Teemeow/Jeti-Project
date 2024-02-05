package client;

import common.Message;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private String address;
    private int port;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Acceuil view;

    private Plateau plateau;


    public Client(String address, int port, Acceuil view){
        this.address = address;
        this.port = port;
        this.view = view;
        try{
            this.socket = new Socket(address, port);
            out = new ObjectOutputStream(socket.getOutputStream());
        }catch(Exception e){
            System.out.println(e);
        }

        Thread threadClientSend = new Thread(new ClientSend(this.out,this.socket));
        threadClientSend.start();

        Thread threadClientReceive = new Thread(new ClientReceive(this, this.socket));
        threadClientReceive.start();

    }

    public void disconnectedServer(){
        try{
            this.out.close();
            this.socket.close();
            if (this.in != null){
                this.in.close();
            }
            System.exit(0);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public Message messageReceived(Message message){

        if (plateau != null && message.getContent().length() == 3){
            List<Integer> infos =  getInfos(message.getContent());
            //System.out.println(infos);
            this.plateau.attackServer(infos.get(0), infos.get(1));
        } else if (this.plateau != null && message.getContent().length() <= 6){
            List<Integer> infos =  getInfos(message.getContent());
            //System.out.println(infos);
            this.plateau.moveServer( infos.get(0), infos.get(1), infos.get(2));
        }else if (plateau != null){
            plateau.printNewMessage(message);
        }
        return message;
    }

    public void sendMessage(Message mess){
        try{
            this.out.writeObject(mess);
            this.out.flush();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public List<Integer> getInfos(String data){
        // Sépare la chaîne en parties en utilisant la virgule comme délimiteur
        String[] parts = data.split(",");
        List<Integer> infos = new ArrayList<Integer>();


        for (String d:parts) {
            int in = Integer.parseInt(d);
            infos.add(in);
        }
        // Convertit les parties en entiers
        //int numUnite = Integer.parseInt(parts[0]);
        //int xCoordinate = Integer.parseInt(parts[1]);
        //int yCoordinate = Integer.parseInt(parts[2]);
//
        //infos.add(numUnite);
        //infos.add(xCoordinate);
        //infos.add(yCoordinate);

        return infos;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }



    public Acceuil getView() {
        return view;
    }

    public void setView(Acceuil view) {
        this.view = view;
    }
}
