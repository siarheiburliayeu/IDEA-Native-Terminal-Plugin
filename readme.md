<h2>Native Terminal Plugin</h2>
Adds a Terminal icon to the IDE toolbar and context menu to open project directories in your favorite terminal.

<br>By default, it uses OS native terminal:
* `cmd` _(Command Prompt)_ - for Windows
* `gnome-terminal`/`konsole` - for Linux
* `Terminal.app` - for macOS

If you want to use another terminal instead - you can!
<br>Just specify your favorite one in the **IDE Settings / Preferences**.
<br>_*Alacritty, Cmder, ConEmu, Ghostty, GitBash, Hyper, iTerm, kitty, PowerShell, RXVT, Terminator, WSL are also supported._
<br>
<br>Since **version 0.4**, it is available to specify a custom command with `${project.dir}` placeholder,
which will be replaced at runtime with the actual project directory.
