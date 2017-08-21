import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import java.io.IOException;

public class OpenPaint extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        try {
            Runtime.getRuntime().exec("cmd /c %windir%/system32/mspaint.exe");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
