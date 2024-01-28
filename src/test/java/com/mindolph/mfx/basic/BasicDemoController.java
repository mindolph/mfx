package com.mindolph.mfx.basic;

import com.mindolph.mfx.util.AsyncUtils;
import com.mindolph.mfx.util.FxmlUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author mindolph.com@gmail.com
 */
public class BasicDemoController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void onUnmanagedNode() {
        FxmlUtils.loadUriToStage("/basic/basic_unmanaged_node.fxml").show();
    }

    @FXML
    public void onAsyncCall() {
        AsyncUtils.fxAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, () -> System.out.println("Done basic async call"));

        AsyncUtils.fxAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "done with parameters";
        }, System.out::println);


        AsyncUtils.fxAsync(() -> {
            throw new RuntimeException("test exception");
        }, (str) -> System.out.println());
    }
}
