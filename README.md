# BBRemote

BBRemote is an Android application that allows your phone to be used as a input device for a Windows computer. It supports use of the phone as a touchpad, keyboard, or game controller. To use BBRemote, you need both the Android application as well as the Windows Driver installed.

## Setup Instructions

### Mobile Client

1. Install android apk from BBRemote/BBRemoteMobile/app/app-release.apk
2. Turn on bluetooth on your android device
3. Start the BBRemote app

### Desktop Client

1. BB Remote uses custom drivers that are unsigned. In order to install unsigned drivers, you must boot up your computer with special settings. Hold down Shift and restart your computer. When the menu comes up, press:
  1. Troubleshoot
  2. Advanced Options
  3. Startup Settings
  4. Restart
2. When prompted, press 7 (disable driver signature enforcements)
3. The installer uses a command installed in the Windows Driver Kit. If you do not have this, you need to download it.
4. Run the file "BBRemote\BBRemoteDesktop\install.bat" with Admin priveleges.
5. If a warning pops up, click allow
6. Open Device Manager
7. If there is no section titled "BB Remote":
  1. Open a command prompt with Admin priveleges
  2. cd to "BBRemote\BBRemoteDesktop"
  3. run: 
``` "C:\Program Files (x86)\Windows Kits\10\Tools\x64\devcon.exe" install BBRemoteDrivers\x64\Release\BBRemoteDrivers.inf Root\BBRemoteDrivers ```
  4. If a warning pops up, click allow
8. In Device Manager, open the "BBRemote" list
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

## User Instructions

### General

1. Launch the desktop client's app.exe
2. Launch the mobile client's BBRemote app
3. Choose a paired device to connect to
  1. If there are no paired devices, choose an unpaired device to pair to
  2. Once paired, select the device to connect
4. You will be taken to the Keyboard input mode
5. Type in keyboard mode or use the menu in the top right corner to change input modes or change settings

### Customize Gamepad

1. Select Customize Gamepad from top right menu
2. Add inputs by using the top right menu to select Customize -> Add Input
3. Select the input you want to add from the displayed list
4. Move the input by dragging it around
5. Resize the input by performing a pinch gesture on the screen
6. Delete an input by long-clicking on an input without moving it
7. Save the layout by pressing Customize -> Save Layout from the menu
  1. Input a name that does not include the word default or a space
  2. Press save
8. Delete a layout that you saved or loaded 
  1. Press Customize -> Delete Layout from the menu
  2. Confirm deletion
9. Load a layout
  1. Press Customize -> Load Layout from the menu
  2. Choose layout to load

## Test Plan

### Android Side

1. Install Android Studio from http://developer.android.com/sdk/index.html
2. Open SDK Manager from Tools -> Android -> SDK Manager
3. Select SDK Platforms from API level 19 to 23
4. Install all selected platforms
5. Under SDK Tools select
  1. Android SDK Platform Tools
  2. Android Support Library
  3. Android SDK Build Tools
  4. Android SDK Tools
6. Install all selected tools
7. Open the BBRemoteMobile project
8. On your android device
  1. Open developer options
  2. Enable USB debugging
  3. Disable Window animation scale
  4. Disable Transition animation scale
  5. Disable Animator duration scale
9. Plug you android device into your computer via a USB cable
10. In Android studio's project explorer got to app -> java -> com.example.bbschool.bbremotemobile(androidTest)
11. Right click any test and choose to run it
12. Test results will be displayed in the bottorm windows.

### Windows Side

The tests for the windows client is composed of two sets:
1. Unit tests for the app, for attempted statement coverage
2. Functional tests for the drivers, covering use cases for the Mouse and Keyboard drivers

Setup and usage information for the tests are as follows:

1. Install Visual Studio from https://www.visualstudio.com/en-us/downloads/download-visual-studio-vs.aspx
2. Open BBRemoteDesktop\app\app.sln
3. Set the solution configuration to Release and the Solution platform to x64
4. Click Build -> Build Solution
5. Open BBRemoteDesktop\BBDesktopAppUnitTests\BBDesktopAppUnitTests.sln
6. In the Solution Explorer, right click BBDesktopAppUnitTests and click properties
7. In Configuration Properties -> Linker -> Input, ensure that Additional Dependencies include the following:
    ```
    
    ws2_32.lib
    Xinput.lib
    User32.lib
    ..\..\app\x64\Release\app.lib
    BluetoothReceiver.obj
    NoInputProxy.obj
    DriverProxy.obj
    KeyboardProxy.obj
    GlobalFile.obj
    ModeSwitcher.obj
    MouseProxy.obj
    GamepadProxy.obj
    ```
8. Set the solution configuration to Release and the Solution platform to x64
9. Under the tab Test, click Test Settings -> Default Processor Architecture -> x64
10. Under the tab Test, click Windows -> Test Explorer
11. In the test explorer, click Run All.
  1. Note that the tests rely on the state of your mouse and keyboard. Refrain from touching your mouse and keyboard while the tests are running.
