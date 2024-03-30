set inputFolder="C:\Program Files (x86)\Steam\userdata\XXXXXXX\2054970\remote\win64_save"
set inputFileName="data000.bin;data00-1.bin;data001Slot.bin;SS1_data000.bin;SS1_data00-1.bin"
set outputFolder="C:/GameSaves/DragonsDogma2"
set maxSaveCount=10
set autoSaveInterval=10
java -jar Autosaver.jar %inputFolder% %inputFileName% %outputFolder% %maxSaveCount% %autoSaveInterval%
pause
