v3.0
* new `GlobalExecutor` to submit threads.
* Refactor `AsyncUtils` with `GlobalExecutor`.
* add new `close()` method to dialog controller.
* update java language level to 21.
* update javafx to 25.

v2.0.3
* add label to `RadioDialogBuilder`.
* update dependencies.

v2.0.2
* new class `RadioDialogBuilder` to build dialog with radio options.
* new Utils method for `FontUtils`.
* new `ControlUtils` to manipulate controls.
* update dependencies.

v2.0.1
* feat: new `PaneUtils` to manipulate `Pane`.
* update dependencies.

v2.0
* new Drawing apis for drawing pre-defined components on JavaFX Canvas.

v 1.3.0
* add `positive()` and `negative()` to `ConfirmDialogBuilder` for customizing button displaying text. 
* add `multiConfirmDialog()` to `DialogFactory` to support confirmation for multiple operations.

v 1.2.5
* add `getPreferenceAlias()` methods to `FxPreferences` class.
* Add new `GeometryConvertUtils` class
* some refactor.

v 1.2.4
* more utils method for Bounds.

v 1.2.3
* optimize logging performance.

v 1.2.2
* add `isPreferenceExist()` method to class `FxPreferences`.

v 1.2.1
* add `base64ToImage` utils method to `AwtImageUtils`.
* add `InputStream` class type to `ImageConverter`.
* add more utils for handling AWT image and FX image like writing image to stream/file, resizing fx image.
* convert image to bytes or base64, etc.
* add icon support to tabs in `SideTabPane`
* fix: the confirmation dialog's default title does not work

v 1.2
* add `ConfirmDialogBuilder` to build confirmation dialog instead of using `DialogFactory`.
* add `defaultButton` to all dialog builder classes to setup default button.