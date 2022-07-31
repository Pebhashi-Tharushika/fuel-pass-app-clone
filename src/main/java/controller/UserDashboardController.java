package controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import db.InMemoryDB;
import db.User;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import util.Navigation;
import util.Routes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UserDashboardController {
    public AnchorPane pneDashboard;
    public Label lblName;
    public Label lblNIC;
    public Label lblAddress;
    public Label lblQuota;
    public Button btnLogout;
    public Button btnDownload;
    public Button btnPrint;
    public ImageView imgQR;

    public void initialize(){
        Platform.runLater(pneDashboard::requestFocus);
    }

    public void setData(String nic) throws WriterException {
        User user = InMemoryDB.findUser(nic);
        lblName.setText(user.getFirstName() + " " + user.getLastName());
        lblNIC.setText(user.getNic());
        lblAddress.setText(user.getAddress());
        lblQuota.setText(user.getQuota()+" L weekly");

        String plainSecret = user.getNic()+"-"+user.getFirstName();
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix qrCode = qrCodeWriter.encode(plainSecret, BarcodeFormat.QR_CODE, 200, 200);

        WritableImage image = new WritableImage(200, 200);
        PixelWriter pixelWriter = image.getPixelWriter(); //write pixel in writableImage
        for(int y=0; y<qrCode.getHeight();y++){
            for(int x=0; x<qrCode.getWidth();x++){
                if(qrCode.get(x,y)){
                    pixelWriter.setColor(x,y, Color.MAROON);
                }else{
                    pixelWriter.setColor(x,y,Color.WHITE);
                }
            }
        }
        imgQR.setImage(image);

    }

    public void btnLogoutOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.WELCOME);
    }

    public void btnDownloadOnAction(ActionEvent actionEvent) throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save the QR Code");
        File file = new File(System.getProperty("user.home"));
        fileChooser.setInitialDirectory(file);
        fileChooser.setInitialFileName("qr-code.png");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files(*.png)","*.png"));
        File saveLocation = fileChooser.showSaveDialog(btnLogout.getScene().getWindow()); //display as modal window
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imgQR.getImage(), null); //convert image view of java fx to buffered image of java SE
        boolean saved = ImageIO.write(bufferedImage, "png", saveLocation);
        if(saved){
            new Alert(Alert.AlertType.INFORMATION,"Downloaded").show();
        }else{
            new Alert(Alert.AlertType.ERROR,"Failed to download").show();
        }
    }

    public void btnPrintOnAction(ActionEvent actionEvent) {
        if(Printer.getDefaultPrinter()==null){
            new Alert(Alert.AlertType.ERROR,"No default printer has been selected").showAndWait();
            return;
        }

        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if(printerJob!=null){
            printerJob.showPageSetupDialog(btnLogout.getScene().getWindow()); // display as modal window
            //boolean success = printerJob.printPage(imgQR);
            boolean success = printerJob.printPage(pneDashboard);
            if(success){
                printerJob.endJob();
            }else{
                new Alert(Alert.AlertType.ERROR,"Failed to print...Try again").showAndWait();
            }

        }else{
            new Alert(Alert.AlertType.ERROR,"Failed to initialize a new print job").show();
        }
    }
}
