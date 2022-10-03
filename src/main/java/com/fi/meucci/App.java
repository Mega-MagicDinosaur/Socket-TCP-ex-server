package com.fi.meucci;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ServerStr server = new ServerStr();
        while (true) {
            server.attendi();
            server.comunica();
        }
    }
}
