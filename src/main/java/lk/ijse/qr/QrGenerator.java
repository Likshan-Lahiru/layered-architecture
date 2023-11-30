package lk.ijse.qr;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javafx.scene.control.Alert;
import lk.ijse.controller.EmployeeFormController;
import lk.ijse.util.SystemAlert;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

public class QrGenerator {
    private String data;
    private String path;

    public void setData(String data) {
        this.data = data;
    }

    public String getPath() {
        return path;
    }
   EmployeeFormController employeeController = new EmployeeFormController();

    public void getGenerator() throws IOException, WriterException {
        try {
            path = "C:\\Users\\user\\Desktop\\qr\\" + data + ".png";
            BitMatrix encode = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 200, 200);
            Path path1 = Paths.get(path);
            MatrixToImageWriter.writeToPath(encode, "PNG", path1);
            new SystemAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "QR Successfully Generated").show();
           // employeeController.setDetails();

        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        System.out.println(path);

    }
}
