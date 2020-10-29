package v2.model;


import v2.controller.APIModel;
import v2.model.priv.Host;
import v2.model.priv.Switch;

public class v2modelTests {
    public static void main(String[] args) {

        APIModel arch = new ModelArch();

        arch.addRouter("R1");
        arch.addRouter("R2");
        arch.addSwitch("sw1");
        arch.addSwitch("sw2");
        arch.addHost("PC1");
        arch.addHost("PC2");

        arch.addIf("PC1");
        arch.addIf("PC2");
        arch.addIf("R1");
        arch.addIf("R1");
        arch.addIf("R2");
        arch.addIf("R2");

        arch.connect("PC1", "if0", "sw1", null);
        arch.connect("PC2", "if0", "sw2", null);
        arch.connect("R1", "if0", "R2", "if0");
        arch.connect("R1", "if1", "sw1", null);
        arch.connect("R2", "if1", "sw2", null);

        System.out.println(arch.disconnect("sw1", null));
        arch.delSwitch("sw2");
        Switch swx = new Switch("swX");
        Host pcx = new Host("PCX");
        pcx.addInterface();
        pcx.getByName("if0").connect(swx.getByName(null));
        //System.out.println(pcx.printConf());

        System.out.println(arch.printConf());



    }
}
