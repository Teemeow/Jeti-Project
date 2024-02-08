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

        //Initialisation des threads d'envoie et de reception de messages
        Thread threadClientSend = new Thread(new ClientSend(this.out,this.socket));
        threadClientSend.start();
        Thread threadClientReceive = new Thread(new ClientReceive(this, this.socket));
        threadClientReceive.start();

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
            //Cas ou le message sert pour transmettre les infos d'attaque
            List<Integer> infos =  getInfos(message.getContent());
            this.plateau.attackServer(infos.get(0), infos.get(1));
        } else if (this.plateau != null && message.getContent().length() <= 6){
            //Cas ou le message sert pour transmettre les infos de deplacement
            List<Integer> infos =  getInfos(message.getContent());
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


    /**
     * @return une liste de Integer qui sont les données reçus dans le message
     */
    public List<Integer> getInfos(String data){
        // Sépare la chaîne en parties en utilisant la virgule comme délimiteur
        String[] parts = data.split(",");
        List<Integer> infos = new ArrayList<Integer>();
        for (String d:parts) {
            int in = Integer.parseInt(d);
            infos.add(in);
        }
        return infos;
    }
}
