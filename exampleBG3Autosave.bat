set inputFolder="%localappdata%\Larian Studios\Baldur's Gate 3\PlayerProfiles\Public\Savegames\Story\dc0051b3-2e0c-a5f1-0023-77fed893d5e7__HonourMode"
set inputFileName="HonourMode.lsv"
set outputFolder="C:/GameSaves/BG3"
set maxSaveCount=10
set autoSaveInterval=20
java -jar Autosaver.jar %inputFolder% %inputFileName% %outputFolder% %maxSaveCount% %autoSaveInterval%
pause
