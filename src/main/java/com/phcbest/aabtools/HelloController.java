package com.phcbest.aabtools;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

public class HelloController {

    @FXML
    public Label bundledPath;
    @FXML
    public Label aabFilePath;
    @FXML
    public Label keyPath;
    @FXML
    public TextField keyPwdInput;
    @FXML
    public TextField keyAliasInput;
    @FXML
    public Label apksPath;

    @FXML
    public void selectBundletool(ActionEvent actionEvent) {
        File file = selectFile("jar");
        bundledPath.setText(file.getPath());
    }

    @FXML
    public void selectAAB(ActionEvent actionEvent) {
        File file = selectFile("aab");
        aabFilePath.setText(file.getPath());
    }


    @FXML
    public void selectKey(ActionEvent actionEvent) {
        File file = selectFile("jks");
        keyPath.setText(file.getPath());
    }

    @FXML
    public void selectApks(ActionEvent actionEvent) {
        File file = selectFile("apks");
        apksPath.setText(file.getPath());
    }

    @FXML
    public void generateApks(ActionEvent actionEvent) throws IOException {
        File file = new File(aabFilePath.getText());
        String apksOutput = file.getParent() + "\\" + removeExtension(file.getName()) + ".apks";
        apksPath.setText(apksOutput);

        //执行生成命令
        Runtime runtime = Runtime.getRuntime();
//        String[] cmd = new String[2];
//        cmd[0] = ";
//        cmd[1] = "cmd /k start pause";
        String cmd = "cmd /k start java -jar " + bundledPath.getText() + " build-apks --bundle=" + aabFilePath.getText() + " --output=" + apksOutput + " --ks=" + keyPath.getText() + " --ks-pass=pass:" + keyPwdInput.getText() + " --ks-key-alias=" + keyAliasInput.getText() + " --key-pass=pass:" + keyPwdInput.getText() + " && pause";
        runtime.exec(cmd);
    }

    @FXML
    public void installApks(ActionEvent actionEvent) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        String cmd = "cmd /k start java -jar " + bundledPath.getText() + " install-apks --apks=" + apksPath.getText() + " && pause";
        System.out.println(cmd);
        runtime.exec(cmd);
    }

    private File selectFile(String filetype) {

        Window window = bundledPath.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择" + filetype);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(filetype + " Files", "*." + filetype));
        return fileChooser.showOpenDialog(window);
    }

    public static String removeExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf('.');
        if (lastIndexOfDot > 0) {
            return fileName.substring(0, lastIndexOfDot);
        } else {
            return fileName;
        }
    }
}