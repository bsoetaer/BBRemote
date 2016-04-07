# BBRemote

## Setup Instructions

### Desktop client

1. BB Remote uses custom drivers that are unsigned. In order to install unsigned drivers, you must boot up your computer with special settings. Hold down Shift and restart your computer. When the menu comes up, press:
  1. Troubleshoot
  2. Advanced Options
  3. Startup Settings
  4. Restart
2. When prompted, press 7 (disable driver signature enforcements)
3. The installer uses a command installed in the Windows Driver Kit. If you do not have this, you need to download it.
4. Run the file "BBRemote\BBRemoteDesktop\install.bat" with Admin priveleges.
5. If a warning pops up, click allow
6. Open Devices and Printers
7. If there is no section titled "BB Remote":
  1. Open a command prompt with Admin priveleges
  2. cd to "BBRemote\BBRemoteDesktop"
  3. run: 
``` "C:\Program Files (x86)\Windows Kits\10\Tools\x64\devcon.exe" install BBRemoteDrivers\x64\Release\BBRemoteDrivers.inf Root\BBRemoteDrivers ```
  4. If a warning pops up, click allow
8. In Devices and printers, open the "BBRemote" list
9. If there is a warning with the device:
  1. Right click the device
  2. Click "Update Driver Software..."
  3. Click "Browse my computer for driver software"
  4. Click "Browse..."
  5. Navigate to "BBRemoteDrivers\x64\Release"
  6. Click "OK"
  7. Click "Next"
  8. If a warning pops up, click allow.
10. Run "BBRemote\BBRemoteDesktop\app\x64\Release\app.exe"
