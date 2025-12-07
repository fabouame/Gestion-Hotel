[Setup]
AppName=GestionHotel
AppVersion=1.0
DefaultDirName={pf}\GestionHotel
DefaultGroupName=GestionHotel
OutputDir=output
OutputBaseFilename=GestionHotel_Setup
Compression=lzma2
SolidCompression=yes

[Files]
Source: "dist\GestionHotel.jar"; DestDir: "{app}"; Flags: ignoreversion
Source: "dist\lib\*"; DestDir: "{app}\lib"; Flags: ignoreversion recursesubdirs
Source: "jre\*"; DestDir: "{app}\jre"; Flags: ignoreversion recursesubdirs createallsubdirs

[Icons]
Name: "{group}\GestionHotel"; Filename: "{app}\jre\bin\javaw.exe"; \
Parameters: "-cp ""{app}\GestionHotel.jar;{app}\lib\*"" ui.LoginWindow"

Name: "{autodesktop}\GestionHotel"; Filename: "{app}\jre\bin\javaw.exe"; \
Parameters: "-cp ""{app}\GestionHotel.jar;{app}\lib\*"" ui.LoginWindow"

[Run]
Filename: "{app}\jre\bin\javaw.exe"; \
Parameters: "-cp ""{app}\GestionHotel.jar;{app}\lib\*"" ui.LoginWindow"; \
Flags: nowait postinstall skipifsilent
