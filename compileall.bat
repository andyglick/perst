cd src
call compile.bat
call makedoc.bat
cd ..\src14
call compile.bat
call makedoc.bat
cd ..\src11
call compileall.bat
call makedoc.bat
cd ..\tst
call compile.bat
cd ..\tst14
call compile.bat
cd ..\tst11
call compile.bat
cd ..