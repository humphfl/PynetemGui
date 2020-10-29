package v2.fileIO;


import v2.model.priv.abs.Terminal;

import java.util.ArrayList;

public abstract class ConfRW {

    public static String createConf(ArrayList<Terminal> terms){
        String starter = "[config]\n" +
                "image_dir = images\n" +
                "config_dir = configs\n";

        String enNode = "\n[nodes]\n";
        String enSwitches = "\n[switches]\n";
        String enBridges = "\n[bridges]\n";

        String nodes = "";
        String switches = "";
        String bridges = "";

        String finalStr = "";

        for (Terminal t : terms){

            if(t.getType().isNode()){
                nodes += t.printConf() + "\n";
            }
            if(t.getType().isSwitch()){
                switches += t.printConf();
            }
            if(t.getType().isBridge()){
                bridges += t.printConf() + "\n";
            }

            finalStr = starter +
                    enNode + nodes +
                    enSwitches + switches +
                    enBridges + bridges;


        }

        return finalStr;
    }
}
