package com.mindolph.mfx.dialog;

import com.mindolph.mfx.BaseController;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Base controller class to implement Dialog with {@link com.mindolph.mfx.dialog.CustomDialogBuilder}.
 * This dialog controller contains Dialog instance and use show() or show(Callback) methods.
 * Get result of the dialog through return of the show() method, the callback or getResult() method.
 *
 * @param <R> type of return value
 * @author allen
 * @see com.mindolph.mfx.dialog.CustomDialogBuilder
 */
public abstract class BaseDialogController<R> extends BaseController {

    private final Logger log = LoggerFactory.getLogger(BaseDialogController.class);

    /**
     * The Dialog object.
     * <p>
     * {@link Dialog}
     */
    protected Dialog<R> dialog;

    /**
     * The origin value to this dialog.
     * Be used to determine whether it's changed since dialog loaded.
     */
    protected R origin;

    /**
     * This result is used by outer like CustomDialogBuilder.
     * The value of result can not be null if you choose positive actions (like OK, YES buttons), otherwise the negative
     * logic will be executed automatically.
     */
    protected R result;

    /**
     * Whether user chose any negative actions.
     */
    protected boolean isNegative = false;

    public BaseDialogController() {
    }

    public BaseDialogController(R origin) {
        this.origin = origin;
        this.result = origin;
    }

    public R getResult() {
        return result;
    }

    /**
     * Get Button instance by button type.
     *
     * @param buttonType
     * @return
     */
    public Button getButton(ButtonType buttonType) {
        return (Button) dialog.getDialogPane().lookupButton(buttonType);
    }

    /**
     * Show dialog and callback to caller with result object.
     *
     * @param consumer be called either positive or negative button clicked.
     */
    public void show(Consumer<R> consumer) {
        if (dialog != null) {
            Optional<R> result = dialog.showAndWait();
            R r = result.orElse(null);
            if (consumer != null) {
                consumer.accept(r);
            }
            if (isNegative) {
                onNegative(this.result);
            }
            else {
                onPositive(this.result);
            }
        }
    }

    /**
     * Show and wait until user clicks any button.
     *
     * @return
     */
    public R showAndWait() {
        if (dialog != null) {
            Optional<R> result = dialog.showAndWait();
            R r = result.orElse(null);
            if (isNegative) {
                onNegative(this.result);
            }
            else {
                onPositive(this.result);
            }
            return r;
        }
        return null;
    }

    /**
     * @deprecated use showAndWait()
     */
    public R show() {
        return this.showAndWait(); // workaround for old implementations.
    }

    /**
     * Whether the result is different from origin value.
     * This requires the class {@link R} implements {@code equals()} and {@code hashCode()} methods.
     *
     * @return true if changed.
     */
    public boolean isChanged() {
        if (origin == null) {
            return result != null;
        }
        return !origin.equals(result);
    }

    /**
     * Confirm before closing the dialog only if
     * user changed the content of dialog and choose negative.
     *
     * @param msg
     * @return true if close the dialog.
     */
    public boolean confirmClosing(String msg) {
        if (this.isNegative) {
            if (isChanged()) {
                return DialogFactory.yesNoConfirmDialog(msg);
            }
            else {
                log.debug("Nothing changed");
            }
        }
        else {
            log.debug("Positively closing dialog");
        }
        return true;
    }

    public void onPositive(R result){}

    public void onNegative(R result){}

    void setNegative(boolean negative) {
        isNegative = negative;
    }

    /**
     * If any negative button clicked.
     *
     * @return
     */
    public boolean isNegative() {
        return isNegative;
    }
}
