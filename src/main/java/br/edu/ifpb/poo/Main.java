package br.edu.ifpb.poo;

public class Main {
    public static void main(String[] args) throws Exception
    {
        InterfaceFisica interface1 = new InterfaceFisica("eth1", "10.0.3.67");
        InterfaceFisica interface2 = new InterfaceFisica("eth2", "192.168.1.10");
        InterfaceFisica interface3 = new InterfaceFisica("eth3", "172.16.0.1");

        Rota rota1 = new Rota("127.0.32.3", "255.255.255.255", "10.0.6.32", interface1);
        Rota rota2 = new Rota("10.0.0.0", "255.0.0.0", "10.0.3.1", interface1);
        Rota rota3 = new Rota("192.168.1.0", "255.255.255.0", "192.168.1.1", interface2);
        Rota rota4 = new Rota("172.16.0.0", "255.240.0.0", "172.16.0.254", interface3);
        Rota rota5 = new Rota("8.8.8.8", "255.255.255.255", "10.0.3.254", interface1);
        Rota rota6 = new Rota("200.200.200.0", "255.255.255.0", "172.16.0.10", interface3);

        Rota rota7 = new Rota("200.200.200.0", "255.255.255.0", "172.16.0.10", interface3);
        
        Roteador roteador = new Roteador();
        roteador.addRota(rota1);
        roteador.addRota(rota2);
        roteador.addRota(rota3);
        roteador.addRota(rota4);
        roteador.addRota(rota5);
        roteador.addRota(rota6);
        
        System.out.println("\n\n");
        //System.out.println(roteador);

        //roteador.alteraRota();

        roteador.removeRota(rota1);
        System.out.println(roteador);
    }
}