import fr.epita.datamodel.Contact;

public class TestFixedLengthBuffer {


    public static void main(String[] args) {
        String str = "0001thomas                            paris                epita           ";
        String elodieStr = "0099elodie decayeux                   lunel                photo|pro       ";
        Contact thomas = readContactFromBuffer(str);
        Contact elodie = readContactFromBuffer(elodieStr);
        System.out.println(thomas);
        System.out.println(elodie);

    }

    private static Contact readContactFromBuffer(String str) {
        String id = str.substring(0, 4).trim();
        String name = str.substring(4, 38).trim();
        String city = str.substring(38, 59).trim();
        String company = str.substring(59, 75).trim();
        return new Contact(id,name,city, company);
    }
}
