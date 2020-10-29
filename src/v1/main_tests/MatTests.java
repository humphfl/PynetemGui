package v1.main_tests;

import v1.materials.Arch;
import v1.materials.exepts.InvalidCommand;
import v1.materials.exepts.InvalidOrUsedName;
import v1.materials.priv.abs.Connect;
import v1.materials.priv.archmats.ExecCmdRes;

public class MatTests {
    public static void main(String[] args) {

        Arch archi = new Arch();


        //************************************************************
        //*    CREATION                                              *
        //************************************************************
        System.out.println("## Creation des terminaux");
        System.out.flush();
        try {
            printResult(archi.execute("add router R1"));
            printResult(archi.execute("add router R2"));
            printResult(archi.execute("add router R233"));
            printResult(archi.execute("add switch sw1"));
            printResult(archi.execute("add switch sw2"));
            printResult(archi.execute("add host h1"));
            printResult(archi.execute("add host h2"));


        } catch (InvalidOrUsedName | InvalidCommand invalidOrUsedName) {
            System.err.println(invalidOrUsedName.getMessage());
            //invalidOrUsedName.printStackTrace();
            System.err.flush();
        }

        //************************************************************
        //*    TEST AVEC ERREURS                                     *
        //************************************************************

        System.out.println("## Creation avec nom déjà existant");
        System.out.flush();
        try {
            printResult(archi.execute("add router R1"));
        } catch (InvalidOrUsedName | InvalidCommand invalidOrUsedName) {
            System.err.println(invalidOrUsedName.getMessage());
            System.err.flush();
        }

        System.out.println("## Creation avec nom incorrect");
        System.out.flush();
        try {
            printResult(archi.execute("add router R&&kj&&&oi&u&oiu---''1"));
        } catch (InvalidOrUsedName | InvalidCommand invalidOrUsedName) {
            System.err.println(invalidOrUsedName.getMessage());
            System.err.flush();
        }

        //************************************************************
        //*    TEST DES COMMANDES CONNECT ET DISCONNECT              *
        //************************************************************

        for (int i = 0; i < 10; i++) {
            try {
                printResult(archi.execute("add if R1"));
            } catch (InvalidCommand | InvalidOrUsedName invalidCommand) {
                System.err.println(invalidCommand.getMessage());
            }
            try {
                printResult(archi.execute("add if R2"));
            } catch (InvalidCommand | InvalidOrUsedName invalidCommand) {
                System.err.println(invalidCommand.getMessage());
            }


        }

        try {
            printResult(archi.execute("connect R1.0 R2.0"));
            printResult(archi.execute("connect R1.eth1 R2.eth1"));
            printResult(archi.execute("connect R1.if2 R2.if2"));
        } catch (InvalidCommand | InvalidOrUsedName invalidCommand) {
            System.err.println(invalidCommand.getMessage());
            System.err.flush();
        }
        try {
            printResult(archi.execute("connect sw1 sw2"));
        } catch (InvalidCommand | InvalidOrUsedName invalidCommand) {
            System.err.println(invalidCommand.getMessage());
            System.err.flush();
        }
        try {
            printResult(archi.execute("connect sw1 R1"));
        } catch (InvalidCommand | InvalidOrUsedName invalidCommand) {
            System.err.println(invalidCommand.getMessage());
            System.err.flush();
        }
        try {
            printResult(archi.execute("connect sw1 sw4"));
        } catch (InvalidCommand | InvalidOrUsedName invalidCommand) {
            System.err.println(invalidCommand.getMessage());
            System.err.flush();
        }

        System.out.println("## Après les connexions");
        //System.out.println(r1.printConf());
        //System.out.println(r2.printConf());
        System.out.flush();


        try {
            printResult(archi.execute("disconnect R1.0"));
        } catch (InvalidCommand | InvalidOrUsedName invalidCommand) {
            System.err.println(invalidCommand.getMessage());
            System.err.flush();
        }

        try {
            printResult(archi.execute("del if R1"));
        } catch (InvalidCommand | InvalidOrUsedName invalidCommand) {
            System.err.println(invalidCommand.getMessage());
            System.err.flush();
        }

        System.out.println("## Après les déconnexions");
        System.out.println(archi.printConf());
        System.out.flush();

        try {
            printResult(archi.execute("disconnect R1"));
        } catch (InvalidCommand | InvalidOrUsedName invalidCommand) {
            System.err.println(invalidCommand.getMessage());
            System.err.flush();
        }


    }

    public static void printResult(ExecCmdRes res){

        System.out.println("expected=" + res.whatExpected());
        switch (res.whatExpected()){
            case INTERFACE, ROUTER, SWITCH, HOST, CONNECT, NULL, TERMINAL, NODE -> System.out.println("item=" + res.getItem());
            case LINK -> {
                StringBuilder toPrint = new StringBuilder("item=[");
                for(Connect c : (Connect[])res.getItem()){
                    toPrint.append(c.getParentTerm().toString()).append(".").append(c.toString()).append(",");
                }
                System.out.println(toPrint + "]");
            }
        }
        System.out.flush();
    }
}
