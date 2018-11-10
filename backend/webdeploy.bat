@echo off
:a
xcopy "src\main\webapp\componetes" "build\exploded-app\componetes" /y
xcopy "src\main\webapp\imgs" "build\exploded-app\imgs" /y
xcopy "src\main\webapp\css" "build\exploded-app\css" /y
xcopy "src\main\webapp\js" "build\exploded-app\js" /y
xcopy "src\main\webapp\index.html" "build\exploded-app\index.html" /y

timeout 3
cls

goto a
pause