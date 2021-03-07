@echo off
echo Work dir is: %~dp0
cd %~dp0
java --module-path "javafx_home\windows\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml,javafx.web -jar RegExpTool-1.0-SNAPSHOT.jar