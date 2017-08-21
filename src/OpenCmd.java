import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class OpenCmd extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        try {
            String path = event.getProject().getBaseDir().getCanonicalPath();
            System.out.println("project base dir: " + path);
            Runtime.getRuntime().exec("cmd /c \"start cmd /K \"cd /d " + path + "\"");  // cmd /c "start cmd /K "cd C:\Windows\""

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
