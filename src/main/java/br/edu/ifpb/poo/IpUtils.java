package br.edu.ifpb.poo;

public class IpUtils
{
    //converte String "10.1.0.0" para int32bits
    public static int ipStringParaInt(String ip)
    {
        String[] partes = ip.split("\\.");
        int p1 = Integer.parseInt(partes[0]);
        int p2 = Integer.parseInt(partes[1]);
        int p3 = Integer.parseInt(partes[2]);
        int p4 = Integer.parseInt(partes[3]);

        return (p1 << 24) | (p2 << 16) | (p3 << 8) | p4;
    }

    public static int mascaraParaCIDR(String mascara)
    {
        int maskInt = ipStringParaInt(mascara);
        return Integer.bitCount(maskInt);
    }
}
