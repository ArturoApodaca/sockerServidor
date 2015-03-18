package com.cristobal.servidor;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.io.*;

public class SocketServidor {
	private static String iplocal;
	static ServerSocket servidor = null;
	static Socket socket = null;
	static DataOutputStream dos;
	//DataInputStream dis;
	static ArrayList<String> chat = new ArrayList<String>();
	static Chat mensajes = new Chat();
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br;
		String mensaje;
		int puerto = 5003;
		int maximo = 10;
		try {
	      InetAddress ipaddress = InetAddress.getByName("cristobalhdez.ddns.net");
	      
	      servidor = new ServerSocket(puerto,maximo);
	      System.out.println("Servidor externo montado en: " + ipaddress.getHostAddress());
	      System.out.println("Direccion local: " + getIpLocal());
	      System.out.println("Servidor creado y escuchando en el puerto: " + puerto);
	      while(true){
		      socket = servidor.accept();
		      System.out.println("Cliente con la IP " + socket.getInetAddress().getHostName() + " conectado...");
              ConexionCliente cc = new ConexionCliente(socket, mensajes);
              cc.start();
              
	      }
	      
	    } catch ( UnknownHostException e ) {
	      System.out.println("No pudimos obtener la direccion ip: " + "cristobalhdez.ddns.net");
	    }
		finally{
            try {
                socket.close();
                servidor.close();
            } catch (Exception ex) {
                System.out.println("Error al cerrar el servidor: " + ex.getMessage());
            }
        }

	}
	public static String getIpLocal(){
        if (iplocal == null) {
            Enumeration<NetworkInterface> netInterfaces = null;
            try {
                netInterfaces = NetworkInterface.getNetworkInterfaces();
                while (netInterfaces.hasMoreElements()) {
                    NetworkInterface ni = netInterfaces.nextElement();
                    Enumeration<InetAddress> address = ni.getInetAddresses();
                    while (address.hasMoreElements()) {
                        InetAddress addr = address.nextElement();
                        if (!addr.isLoopbackAddress() && addr.isSiteLocalAddress()
                                && !(addr.getHostAddress().indexOf(":") > -1)) {
                        	iplocal = addr.getHostAddress();
                        }
                    }
                }
                if (iplocal == null) {
                	iplocal = "127.0.0.1";
                }

            } catch (SocketException e) {
            	iplocal = "127.0.0.1";
            }
        }
        return iplocal;
    }

}
