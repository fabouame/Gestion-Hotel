[Setup]
AppName=GestionHotel
AppVersion=1.0
DefaultDirName={pf}\GestionHotel
DefaultGroupName=GestionHotel
OutputDir=output
OutputBaseFilename=Setup_GestionHotel
Compression=lzma2
SolidCompression=yes
DisableStartupPrompt=yes
WizardStyle=modern

[Files]
; Jar principal
Source: "dist\GestionHotel.jar"; DestDir: "{app}"; Flags: ignoreversion

; Librairies
Source: "dist\lib\*"; DestDir: "{app}\lib"; Flags: ignoreversion recursesubdirs createallsubdirs

; JRE portable — celle que tu as dans ton projet
Source: "jre\*"; DestDir: "{app}\jre"; Flags: ignoreversion recursesubdirs createallsubdirs

[Icons]
; Menu démarrer
Name: "{group}\GestionHotel"; \
Filename: "{app}\jre\bin\javaw.exe"; \
Parameters: "-jar ""{app}\GestionHotel.jar"""

; Bureau (optionnel)
Name: "{autodesktop}\GestionHotel"; \
Filename: "{app}\jre\bin\javaw.exe"; \
Parameters: "-jar ""{app}\GestionHotel.jar"""; \
Tasks: desktopicon

[Run]
; Exécuter automatiquement à la fin
Filename: "{app}\jre\bin\javaw.exe"; \
Parameters: "-jar ""{app}\GestionHotel.jar"""; \
Flags: nowait postinstall skipifsilent
