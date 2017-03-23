import nl.toefel.java.protobuf.Search;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AppMain {
    public static void main(String[] args) throws IOException {
        Search.RE re = Search.RE.newBuilder()
                .setHtSerialNr("HT00201324")
                .setRouteNr("123")
                .setActivityCode("A")
                .setDriver("Chauffeurt")
                .setPickedUp(true)
                .setPickupAcceptedByDriver(true)
                .setPickupCancelledAcceptedByDriver(true)
                .setRecipientNotHome(true)
                .setLoadedHalfFloorspaces(75)
                .setTimestamp(System.currentTimeMillis())
                .setShipmentNr("JVGL1233456565")
                .setRitNr("rit123")
                .setUnknownAddressee(true)
                .setNewPickupDate(System.currentTimeMillis())
                .setNoGoods(true)
                .setVacation(true)
                .build();

        StringBuilder builder = new StringBuilder();
        re.getAllFields().forEach( (descriptor, value) -> builder.append(value));

        System.out.println("string msg" + builder.toString());

        Files.write(Paths.get("/tmp/remsgstr"), builder.toString().getBytes());

        File file = new File("/tmp/remsg");
        if (!file.exists())        file.createNewFile();
        try (FileOutputStream fos = new FileOutputStream(file)) {
            re.writeTo(fos);
        }
        System.out.println("message written");

        try (FileInputStream fin = new FileInputStream(file)) {
            Search.RE read = Search.RE.parseFrom(fin);

            System.out.println("Vacation: " + read.getVacation());
            System.out.println("Unknwn Adr: " + read.getUnknownAddress());
            System.out.println("driver: " + read.getDriver());

//            System.out.println("read: " + read.getHtSerialNr());
//            System.out.println("read: " + read.getLoadedHalfFloorspaces());
            System.out.println("read: " + read);
        }
    }
}
